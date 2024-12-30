package com.esc.escnotesbackend.dto.user;

import com.esc.escnotesbackend.config.enums.Roles;

public record UserDTO(String name, String email, String password, Roles role) {}
