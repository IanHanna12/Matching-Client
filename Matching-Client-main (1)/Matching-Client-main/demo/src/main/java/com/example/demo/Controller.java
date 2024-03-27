package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

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
        return userService.getMatchedUsers(users);
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