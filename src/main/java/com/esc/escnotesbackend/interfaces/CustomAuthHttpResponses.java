package com.esc.escnotesbackend.interfaces;

import jakarta.servlet.ServletResponse;

public interface CustomAuthHttpResponses extends ServletResponse {
    int UNAUTHORIZED = 401;
    int TOKEN_EXPIRED = 402;
    int NO_RIGHTS = 403;
}
