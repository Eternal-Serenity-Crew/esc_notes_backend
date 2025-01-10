package com.esc.escnotesbackend.services;

import com.esc.escnotesbackend.dto.token.CreateTokenDTO;
import com.esc.escnotesbackend.dto.token.ValidateTokenDTO;
import com.esc.escnotesbackend.dto.user.ChangeUserPasswordDTO;
import com.esc.escnotesbackend.dto.user.ChangeUserPasswordSecondStepDTO;
import com.esc.escnotesbackend.dto.user.UpdateUserDTO;
import com.esc.escnotesbackend.entities.User;
import com.esc.escnotesbackend.exceptions.*;
import com.esc.escnotesbackend.repositories.UserRepository;
import com.esc.escnotesbackend.utils.EmailCodeGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailerService mailerService;
    private final TokenService tokenService;

    @Autowired
    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            TokenService tokenService,
            MailerService mailerService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailerService = mailerService;
        this.tokenService = tokenService;
    }

    public void deleteUser(Long userId) {
        try {
            this.findUserById(userId);
            userRepository.deleteById(userId);
        } catch (IncorrectUserDataException e) {
            System.out.println(e.getClassName());
            throw new ExecutionFailedException("Execution failed by error: " + e.getClass().getSimpleName(), e);
        }
    }

    public void updateUser(UpdateUserDTO updateUserDTO) {
        User user = this.findUserById(updateUserDTO.id());

        updateUserDTO.name().ifPresent(user::setName);
        updateUserDTO.email().ifPresent(user::setEmail);
    }


    public void changePasswordFirstStep(ChangeUserPasswordDTO userData) {
        String code = EmailCodeGeneratorUtil.getEmailCode();
        User user = this.findUserByEmail(userData.email());

        this.tokenService.setToken(new CreateTokenDTO(user.getId(), code));
        this.mailerService.sendMail(userData.email(), "Verification code", code);
    }

    public void changePasswordSecondStep(ChangeUserPasswordSecondStepDTO userData) {
        User user = this.findUserByEmail(userData.email());
        boolean checkValid = this.tokenService.validateToken(new ValidateTokenDTO(user.getId(), userData.code()));

        if (!checkValid) {
            throw new IncorrectTokenException();
        }

        user.setPassword(passwordEncoder.encode(userData.password()));
        this.tokenService.deleteToken(user.getId());
    }

    public User findUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);

        if (user == null)
            throw new IncorrectUserDataException("Incorrect user email");
        else
            return user;
    }

    public User findUserById(Long userId) {
        User user = userRepository.findUserById(userId);

        if (user == null)
            throw new IncorrectUserDataException("Incorrect user id");
        else
            return user;
    }
}


