package com.esc.escnotesbackend.dto.user;

public record ChangeUserPasswordSecondStepDTO(String email, String code, String password) {
}
