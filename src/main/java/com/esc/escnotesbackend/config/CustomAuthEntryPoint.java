package com.esc.escnotesbackend.config;

import com.esc.escnotesbackend.interfaces.CustomAuthHttpResponses;
import com.esc.escnotesbackend.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        String uri = request.getRequestURI();

        try {
            if (request.getHeader("Authorization") != null) {
                System.out.println(1);
                String token = request.getHeader("Authorization").substring(7);
                String role = JwtUtil.getRoleFromToken(token);

                if (uri.startsWith("/error") && !role.equals("admin")) {
                    response.setContentType("application/json");
                    response.setStatus(CustomAuthHttpResponses.NO_RIGHTS);
                    response.getWriter().write(
                            "{\"code\": " + CustomAuthHttpResponses.NO_RIGHTS +
                                    ", \"error\": \"User hasn't got enough permissions\"" +
                                    ", \"message\": \"" + authException.getMessage() + "\"}"
                    );
                }
            } else {
                response.setContentType("application/json");
                response.setStatus(CustomAuthHttpResponses.UNAUTHORIZED);
                response.getWriter().write(
                        "{\"code\": " + CustomAuthHttpResponses.UNAUTHORIZED +
                                ", \"error\": \"User unauthorized\"" +
                                ", \"message\": \"" + authException.getMessage() + "\"}"
                );
            }
        } catch (ExpiredJwtException e) {
            response.setContentType("application/json");
            response.setStatus(CustomAuthHttpResponses.TOKEN_EXPIRED);
            response.getWriter().write(
                    "{\"code\": " + CustomAuthHttpResponses.TOKEN_EXPIRED +
                            ", \"error\": \"Auth token was expired\"" +
                            ", \"message\": \"" + authException.getMessage() + "\"}"
            );
        }
    }
}
