package com.example.task.controller;

import com.example.task.model.User;
import com.example.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userService.CreateUser(user);
    }

    @GetMapping("/users/current")
    public User getCurrentUser() {
        return userService.getCurrentUser();
    }

    @GetMapping("/users")
    public Iterable<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }
}
