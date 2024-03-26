package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class Controller {
    private final UserService userService;

    public Controller(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/mappedUsers")
    public List<User> getUsers() {
        return userService.getmatchedUsers();
    }
}