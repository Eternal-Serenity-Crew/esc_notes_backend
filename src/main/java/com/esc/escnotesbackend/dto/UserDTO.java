package com.esc.escnotesbackend.dto;

import com.esc.escnotesbackend.config.enums.Roles;

public record UserDTO(String name, String email, String password, Roles role) {}
