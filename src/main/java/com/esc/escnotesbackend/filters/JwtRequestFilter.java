package com.esc.escnotesbackend.filters;

import com.esc.escnotesbackend.entities.User;
import com.esc.escnotesbackend.services.UserService;
import com.esc.escnotesbackend.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final UserService userService;

    @Autowired
    public JwtRequestFilter(UserService userService) {
        this.userService = userService;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain
    ) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String email = null;
        String jwt = null;
        String role = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            System.out.println(authorizationHeader.substring(7));
            email = JwtUtil.getEmailFromToken(jwt);
            System.out.println(email);
            role = JwtUtil.getRoleFromToken(jwt);
            System.out.println(role);
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = this.userService.findUserByEmail(email);

            if (JwtUtil.validateToken(jwt, user)) { // Проверка действительности токена
                UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(email, null, getAuthorities(role));
                SecurityContextHolder.getContext().setAuthentication(authReq);
                System.out.println(SecurityContextHolder.getContext().getAuthentication());
            }

        }

        chain.doFilter(request, response);
    }
}
