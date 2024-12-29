package com.esc.escnotesbackend.services;

import com.esc.escnotesbackend.dto.UpdateUserDTO;
import com.esc.escnotesbackend.dto.UserDTO;
import com.esc.escnotesbackend.entities.User;
import com.esc.escnotesbackend.exceptions.DoubleRecordException;
import com.esc.escnotesbackend.exceptions.ExecutionFailedException;
import com.esc.escnotesbackend.exceptions.IncorrectUserDataException;
import com.esc.escnotesbackend.mapper.UserMapper;
import com.esc.escnotesbackend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerUser(UserDTO user) {
        User existingUser = this.userRepository.findUserByEmail(user.email());
        if (existingUser != null) {
            throw new DoubleRecordException("User already exists");
        }

        String encodedPassword = passwordEncoder.encode(user.password());
        User newUser = userMapper.userDTOToUser(user);
        newUser.setPassword(encodedPassword);

        userRepository.save(newUser);
    }

    public void deleteUser(Long userId) {
        try {
            this.findUserById(userId);
            userRepository.deleteById(userId);
        } catch (IncorrectUserDataException e) {
            System.out.println(e.getClassName());
            throw new ExecutionFailedException("Execution failed by error: " + e.getClass().getSimpleName(), e);
        }
    }

    public void updateUser(UpdateUserDTO updateUserDTO) {
        User user = this.findUserById(updateUserDTO.id());

        updateUserDTO.name().ifPresent(user::setName);
        updateUserDTO.email().ifPresent(user::setEmail);
    }

    public User findUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);

        if (user == null)
            throw new IncorrectUserDataException("Incorrect user email");
        else
            return user;
    }

    public User findUserById(Long userId) {
        User user = userRepository.findUserById(userId);

        if (user == null)
            throw new IncorrectUserDataException("Incorrect user id");
        else
            return user;
    }
}


