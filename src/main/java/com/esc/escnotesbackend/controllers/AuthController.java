package com.esc.escnotesbackend.controllers;

import com.esc.escnotesbackend.dto.token.UserJwtTokensDTO;
import com.esc.escnotesbackend.dto.user.LoginUserDTO;
import com.esc.escnotesbackend.dto.user.UserDTO;
import com.esc.escnotesbackend.strategies.AuthStrategy;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthStrategy authStrategy;

    @Autowired
    public AuthController(AuthStrategy authStrategy) {
        this.authStrategy = authStrategy;
    }

    @PostMapping("/register")
    @Transactional
    public UserJwtTokensDTO registerUser(@RequestBody UserDTO user) {
        return authStrategy.registerUser(user);
    }

    @PostMapping("/login")
    public UserJwtTokensDTO login(@RequestBody LoginUserDTO userCredits) {
        return this.authStrategy.login(userCredits);
    }

    @GetMapping("/getAuthToken")
    public UserJwtTokensDTO getAuthToken(@RequestBody String refreshToken) {
        System.out.println(refreshToken);
        return this.authStrategy.getAuthToken(refreshToken);
    }

    @GetMapping("/validateTokens")
    public UserJwtTokensDTO validateTokens(@RequestBody UserJwtTokensDTO userJwtTokensDTO) {
        return this.authStrategy.validateUserTokens(userJwtTokensDTO);
    }
}
