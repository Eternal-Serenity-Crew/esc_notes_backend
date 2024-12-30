package com.esc.escnotesbackend.mapper;

import com.esc.escnotesbackend.dto.token.CreateTokenDTO;
import com.esc.escnotesbackend.entities.TokenStorage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenMapper {
    CreateTokenDTO toCreateTokenDTO(TokenStorage tokenStorage);
    TokenStorage toTokenStorage(CreateTokenDTO createTokenDTO);
}
