package com.esc.escnotesbackend.repositories;

import com.esc.escnotesbackend.entities.TokenStorage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<TokenStorage, Long> {
    TokenStorage findByUserId(Long userId);
    void deleteByUserId(Long userId);
}
