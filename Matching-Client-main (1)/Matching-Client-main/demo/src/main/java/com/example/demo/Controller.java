package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class Controller {
    private final UserService userService;
    private final List<User> users; // Assuming you have a list of users available

    public Controller(UserService userService, List<User> users) {
        this.userService = userService;
        this.users = users;
    }

    @GetMapping("/matchusersRandomly")
    public List<User> matchUsersRandomly() {
        return userService.getandmatchusersRandomly(users);
    }

    @GetMapping("/matchUsers")
    public List<User> matchUsers() {
        return userService.getandmatchUsers(users);
    }
}