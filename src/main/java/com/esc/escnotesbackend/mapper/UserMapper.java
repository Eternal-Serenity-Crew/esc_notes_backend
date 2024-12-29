package com.esc.escnotesbackend.mapper;

import com.esc.escnotesbackend.dto.UserDTO;
import com.esc.escnotesbackend.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userToUserDTO(User user);
    User userDTOToUser(UserDTO userDTO);
}
