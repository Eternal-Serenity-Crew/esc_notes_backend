package com.esc.escnotesbackend.interfaces;

import com.esc.escnotesbackend.dto.token.UserJwtTokensDTO;
import com.esc.escnotesbackend.dto.user.LoginUserDTO;

public interface AuthStrategy {
    public UserJwtTokensDTO login(LoginUserDTO user);
    public UserJwtTokensDTO getAuthToken(String refreshToken);
    public String updateRefreshToken(String refreshToken);
    public UserJwtTokensDTO validateUserTokens(UserJwtTokensDTO userJwtTokensDTO);
}
