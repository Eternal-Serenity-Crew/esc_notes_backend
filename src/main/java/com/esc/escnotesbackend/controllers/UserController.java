package com.esc.escnotesbackend.controllers;

import com.esc.escnotesbackend.dto.user.*;
import com.esc.escnotesbackend.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Transactional
    public String registerUser(@RequestBody UserDTO user) {
        userService.registerUser(user);
        return userService.findUserByEmail(user.email()).toString();
    }

    @DeleteMapping("/delete")
    @Transactional
    public String deleteUser(@RequestBody DeleteUserDTO deleteUser) {
        userService.deleteUser(deleteUser.id());
        return "User deleted";
    }

    @PatchMapping("/update")
    @Transactional
    public String updateUser(@RequestBody UpdateUserDTO updateUser) {
        userService.updateUser(updateUser);
        return "User updated";
    }

    @GetMapping("/findByEmail")
    public String findByEmail(@RequestBody String userEmail) {
        return userService.findUserByEmail(userEmail).toString();
    }

    @PostMapping("/changePassword")
    public void changePasswordFirstStep(@RequestBody ChangeUserPasswordDTO changeUserPasswordDTO) {
        this.userService.changePasswordFirstStep(changeUserPasswordDTO);
    }

    @PostMapping("/verifyNewPassword")
    @Transactional
    public void changePasswordSecondStep(@RequestBody ChangeUserPasswordSecondStepDTO changeUserPasswordDTO) {
        this.userService.changePasswordSecondStep(changeUserPasswordDTO);
    }
}
