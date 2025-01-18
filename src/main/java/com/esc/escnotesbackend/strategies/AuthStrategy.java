package com.esc.escnotesbackend.strategies;

import com.esc.escnotesbackend.dto.token.CreateTokenDTO;
import com.esc.escnotesbackend.dto.token.UserJwtTokensDTO;
import com.esc.escnotesbackend.dto.token.ValidateSignUpToken;
import com.esc.escnotesbackend.dto.token.ValidateTokenDTO;
import com.esc.escnotesbackend.dto.user.LoginUserDTO;
import com.esc.escnotesbackend.dto.user.UserDTO;
import com.esc.escnotesbackend.entities.User;
import com.esc.escnotesbackend.exceptions.DoubleRecordException;
import com.esc.escnotesbackend.exceptions.IncorrectUserDataException;
import com.esc.escnotesbackend.exceptions.InvalidTokenException;
import com.esc.escnotesbackend.exceptions.LoginFailedException;
import com.esc.escnotesbackend.mapper.UserMapper;
import com.esc.escnotesbackend.repositories.UserRepository;
import com.esc.escnotesbackend.services.MailerService;
import com.esc.escnotesbackend.services.TokenService;
import com.esc.escnotesbackend.services.UserService;
import com.esc.escnotesbackend.utils.EmailCodeGeneratorUtil;
import com.esc.escnotesbackend.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Getter
@Service
public class AuthStrategy implements com.esc.escnotesbackend.interfaces.AuthStrategy {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TokenService tokenService;
    private final MailerService mailerService;

    @Autowired
    public AuthStrategy(
            UserService userService,
            PasswordEncoder passwordEncoder,
            UserRepository userRepository,
            UserMapper userMapper,
            TokenService tokenService,
            MailerService mailerService
    ) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
        this.mailerService = mailerService;
    }

    @Transactional
    public UserJwtTokensDTO registerUser(UserDTO user) {
        User existingUser = this.userRepository.findUserByEmail(user.email());
        if (existingUser != null) {
            throw new DoubleRecordException("User already exists");
        }

        String encodedPassword = passwordEncoder.encode(user.password());
        User newUser = userMapper.userDTOToUser(user);
        newUser.setPassword(encodedPassword);

        userRepository.save(newUser);

        return new UserJwtTokensDTO(
                JwtUtil.generateAuthToken(newUser),
                JwtUtil.generateRefreshToken(newUser)
        );
    }

    @Override
    public UserJwtTokensDTO login(LoginUserDTO userCredits) {
        try {
            User user = this.userService.findUserByEmail(userCredits.email());

            if (!passwordEncoder.matches(userCredits.password(), user.getPassword()))
                throw new IncorrectUserDataException("Incorrect user data.");

            String authToken = JwtUtil.generateAuthToken(user);
            String refreshToken = JwtUtil.generateRefreshToken(user);

            return new UserJwtTokensDTO(authToken, refreshToken);
        } catch (IncorrectUserDataException e) {
            throw new LoginFailedException("Incorrect email or password", e);
        }
    }

    @Override
    public UserJwtTokensDTO getAuthToken(String refreshToken) {
        if (JwtUtil.isTokenExpired(refreshToken)) throw new InvalidTokenException("Token was expired");

        String email = JwtUtil.getEmailFromToken(refreshToken);
        User user = this.userService.findUserByEmail(email);

        return JwtUtil.isTokenRefreshable(refreshToken) ? new UserJwtTokensDTO(
                JwtUtil.generateAuthToken(user), updateRefreshToken(refreshToken)
        ) : new UserJwtTokensDTO(
                JwtUtil.generateAuthToken(user), null
        );
    }

    @Override
    public String updateRefreshToken(String refreshToken) {
        String email = JwtUtil.getEmailFromToken(refreshToken);
        User user = this.userService.findUserByEmail(email);

        return JwtUtil.generateRefreshToken(user);
    }

    @Override
    public UserJwtTokensDTO validateUserTokens(UserJwtTokensDTO userJwtTokensDTO) {
        if (!JwtUtil.isTokenExpired(userJwtTokensDTO.authToken()))
            throw new InvalidTokenException("Auth token was expired");
        if (!JwtUtil.isTokenExpired(userJwtTokensDTO.refreshToken()))
            throw new InvalidTokenException("Refresh token was expired");

        return JwtUtil.isTokenRefreshable(userJwtTokensDTO.refreshToken()) ? new UserJwtTokensDTO(
                userJwtTokensDTO.authToken(), this.updateRefreshToken(userJwtTokensDTO.refreshToken())
        ) : new UserJwtTokensDTO(
                userJwtTokensDTO.authToken(), userJwtTokensDTO.refreshToken()
        );
    }

    @Override
    public void generateSighUpCode(String email) {
        String code = EmailCodeGeneratorUtil.getEmailCode();
        User user = this.userRepository.findUserByEmail(email);

        this.tokenService.setToken(new CreateTokenDTO(user.getId(), code));
        this.mailerService.sendMail(email, "Sigh Up Code", code);
    }

    @Override
    public boolean validateSighUpCode(ValidateSignUpToken validateSignUpToken) {
        User user = this.userRepository.findUserByEmail(validateSignUpToken.email());
        System.out.println(validateSignUpToken.email() + " " + user.getId());

        return this.tokenService.validateToken(new ValidateTokenDTO(user.getId(), validateSignUpToken.token()));
    }
}
