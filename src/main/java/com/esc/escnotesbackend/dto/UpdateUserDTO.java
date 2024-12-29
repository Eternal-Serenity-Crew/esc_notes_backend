package com.esc.escnotesbackend.dto;

import java.util.Optional;

public record UpdateUserDTO(Long id, Optional<String> name, Optional<String> email) {
}
