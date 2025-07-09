package com.example.task.controller;

import com.example.task.model.User;
import com.example.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Vector;

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
        User user = userService.getCurrentUser();
        user.setPassword(null);
        return user;
    }

    @GetMapping("/users")
    public Iterable<User> getUsers() {
        Iterable<User> users = userService.getAllUsers();
        for (User user : users) {
            user.setPassword(null);
        }
        return users;
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }
}
