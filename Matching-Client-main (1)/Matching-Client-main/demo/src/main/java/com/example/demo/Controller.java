package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;




@CrossOrigin(origins = "*" , allowedHeaders = "*")
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
        return userService.ReadmatchedUsersfromJSON();
    }

    @PostMapping("/writeUsers")
    public void writeUserstoJson() throws JsonProcessingException {
        userService.writeUserstoJSON();
    }

    /*
    @CrossOrigin(origins = "*")
    @GetMapping("/readUsers") //Port: http://localhost:8080/readUsers
    public List<User> readUsersFromJson() {
        return userService.readUsersfromJSON();
    }

    */


    @GetMapping("/readUsers")
    public List<User> readUsersFromJson(@RequestParam String data) {
        String decodedData = URLDecoder.decode(data, StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(decodedData);
            String time = jsonNode.get("time").asText();
            return userService.readUsersfromJSON().stream()
                    .filter(user -> user.getUsertime().equals(time))
                    .collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }





    @PostMapping("/writeMatchedUsers")
    public void writeMatchedUsers(@RequestBody List<User> uniqueUsers) {
        userService.writeMatchedUsersToJSON(uniqueUsers);
    }
    @GetMapping("/readMatchedUsers")
    public List<User> readMatchedUsersFromJson() {
        return userService.ReadmatchedUsersfromJSON();
    }

}