package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class Controller {
    private final UserService userService;
    private final List<User> users;

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

    @PutMapping("getmatchedUsers")
    public List<User> getMatchedUsers() {
        return userService.ReadmatchedUsersfromJSON(users);
    }

    @PostMapping("/writeUsers")
    public void writeUserstoJson() {
        userService.writeUserstoJSON();
    }

    @GetMapping("/readUsers")
    public List<User> readUsersFromJson() {
        return userService.readUsersfromJSON();
    }

    @PostMapping("/writeMatchedUsers")
    public void writeMatchedUsersToJson() {
        userService.WritematchedUserstoJSON((ObjectMapper) users);
    }

    @GetMapping("/readMatchedUsers")
    public List<User> readMatchedUsersFromJson() {
        return userService.ReadmatchedUsersfromJSON(users);
    }
}