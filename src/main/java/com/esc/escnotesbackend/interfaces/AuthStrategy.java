package com.esc.escnotesbackend.interfaces;

import com.esc.escnotesbackend.dto.token.UserJwtTokensDTO;
import com.esc.escnotesbackend.dto.token.ValidateSignUpToken;
import com.esc.escnotesbackend.dto.user.LoginUserDTO;

public interface AuthStrategy {
    UserJwtTokensDTO login(LoginUserDTO user);
    UserJwtTokensDTO getAuthToken(String refreshToken);
    String updateRefreshToken(String refreshToken);
    UserJwtTokensDTO validateUserTokens(UserJwtTokensDTO userJwtTokensDTO);
    void generateSighUpCode(String email);
    boolean validateSighUpCode(ValidateSignUpToken validateSignUpToken);
}
