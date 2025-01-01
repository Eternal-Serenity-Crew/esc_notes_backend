package com.esc.escnotesbackend.dto.user;

import java.util.Optional;

public record UpdateUserDTO(Long id, Optional<String> name, Optional<String> email) {
}
