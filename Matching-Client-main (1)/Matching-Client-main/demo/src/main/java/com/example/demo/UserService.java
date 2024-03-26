package com.example.demo;

import com.example.demo.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserService {

    boolean isExecuted;
    private final List<User> userList;

    public UserService(List<User> userList) {
        this.userList = userList;
    }

    public User generateRandomUser() {
        return new User("User" + ThreadLocalRandom.current().nextInt(100), "ID" + ThreadLocalRandom.current().nextInt(100));
    }

    @PostConstruct
    public void writetoJSON() {
        File file = new File("Matching-Client-main (1)/Matching-Client-main/demo/src/main/java/com/example/demo/json/users.JSON");
        ObjectMapper mapper = new ObjectMapper();

        if (file.exists() && file.length() == 0) {
            try {
                List<User> users = new ArrayList<>();
                for (int i = 0; i <= 100; i++) {
                    users.add(generateRandomUser());
                }
                mapper.writeValue(file, users);
                isExecuted = true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public List<User> getmatchedUsers() {
        List<User> matcheduserList = new ArrayList<>();
        List<User> users = new ArrayList<>(userList);
        if (users.size() < 2) {
            throw new IllegalStateException("Not enough users to match");
        }
        while (matcheduserList.size() == 1) {
            int randomIndex = ThreadLocalRandom.current().nextInt(users.size());
            matcheduserList.add(users.get(randomIndex));
            users.remove(randomIndex);
        }
        return matcheduserList;
    }
}