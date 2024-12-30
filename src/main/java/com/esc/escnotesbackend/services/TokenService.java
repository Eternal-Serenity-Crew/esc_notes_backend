package com.esc.escnotesbackend.services;

import com.esc.escnotesbackend.dto.token.CreateTokenDTO;
import com.esc.escnotesbackend.dto.token.ValidateTokenDTO;
import com.esc.escnotesbackend.entities.TokenStorage;
import com.esc.escnotesbackend.exceptions.ExecutionFailedException;
import com.esc.escnotesbackend.mapper.TokenMapper;
import com.esc.escnotesbackend.repositories.TokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;
    private final TokenMapper tokenMapper;

    @Autowired
    public TokenService(TokenRepository tokenRepository, TokenMapper tokenMapper) {
        this.tokenRepository = tokenRepository;
        this.tokenMapper = tokenMapper;
    }

    public final TokenStorage getToken(Long userId) {
        return this.tokenRepository.findByUserId(userId);
    }

    @Transactional
    public void setToken(CreateTokenDTO createTokenDTO) {
        TokenStorage actualToken = getToken(createTokenDTO.userId());
        TokenStorage newToken = tokenMapper.toTokenStorage(createTokenDTO);

        if (actualToken != null) this.deleteToken(createTokenDTO.userId());
        this.tokenRepository.save(newToken);
    }

    @Transactional
    public void deleteToken(Long userId) {
        this.tokenRepository.deleteByUserId(userId);
    }

    public boolean validateToken(ValidateTokenDTO validateTokenDTO) {
        TokenStorage actualToken = getToken(validateTokenDTO.userId());

        if (actualToken == null) throw new ExecutionFailedException("This user hasn't got any tokens");
        return actualToken.getToken().equals(validateTokenDTO.token());
    }
}
