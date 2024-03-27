package com.example.demo;

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
    public void writeUserstoJSON() {
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

    @PostConstruct
    public List<User> readUsersfromJSON() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("Matching-Client-main (1)/Matching-Client-main/demo/src/main/java/com/example/demo/json/users.JSON");
        if (file.exists() && file.length() != 0) {
            try {
                List<User> users = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, User.class));
                userList.addAll(users);
                isExecuted = true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return userList;
    }

    public void WritematchedUserstoJSON(ObjectMapper mapper) {
        File file = new File("Matching-Client-main (1)/Matching-Client-main/demo/src/main/java/com/example/demo/json/matchedusers.JSON");

        if (file.exists() && file.length() == 0) {
            try {
                List<User> matchedusers = new ArrayList<>();
                getandmatchusersRandomly(matchedusers);
                mapper.writeValue(file, matchedusers);
                isExecuted = true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<User> ReadmatchedUsersfromJSON(List<User> users) {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("Matching-Client-main (1)/Matching-Client-main/demo/src/main/java/com/example/demo/json/matchedusers.JSON");
        List<User> matchedusers = new ArrayList<>();
        if (file.exists() && file.length() != 0) {
            try {
                matchedusers = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, User.class));
                userList.addAll(matchedusers);
                isExecuted = true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return matchedusers;
    }

    public List<User> getandmatchusersRandomly(List<User> matcheduserList) {
        List<User> users = new ArrayList<>(userList);
        if (users.size() < 2) {
            throw new IllegalStateException("Not enough users to match");
        }
        while (!users.isEmpty()) {
            int randomIndex = ThreadLocalRandom.current().nextInt(users.size());
            matcheduserList.add(users.remove(randomIndex));
            if (!users.isEmpty()) {
                randomIndex = ThreadLocalRandom.current().nextInt(users.size());
                matcheduserList.add(users.remove(randomIndex));
            }
        }
        return matcheduserList;
    }

    public List<User> getandmatchUsers(List<User> matcheduserList) {
        for (User entereduser : userList) {
            validateUser(entereduser);
            for (User existingUser : userList) {
                validateUser(existingUser);
                if (entereduser.equals(existingUser)) {
                    matcheduserList.add(entereduser);
                }
            }
        }
        return matcheduserList;
    }

    private void validateUser(User user) {
        if (user.getID() == null || user.getName() == null) {
            throw new IllegalStateException("User ID or Name is null");
        } else if (user.getID().isEmpty() || user.getName().isEmpty()) {
            throw new IllegalStateException("User ID or Name is empty");
        }
        if (user.getID() != null && user.getName().equals(user.getID() + user.getName())) {
            throw new IllegalStateException("Users are the same and cant be matched");
        }
    }

    public List<User> getMatchedUsers(List<User> matcheduserList) {
        return matcheduserList;
    }
}

