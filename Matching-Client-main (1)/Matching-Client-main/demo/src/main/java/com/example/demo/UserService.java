package com.example.demo;

import com.fasterxml.jackson.core.type.TypeReference;
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

    int userIdCounter = 0;

    public User generateRandomUser() {
        java.time.LocalTime startingTime = java.time.LocalTime.of(11, 30);
        int randomMinutes = ThreadLocalRandom.current().nextInt(0, 150);
        java.time.LocalTime randomTime = startingTime.plusMinutes(randomMinutes);
        String time = randomTime.toString();
        String uniqueID = String.valueOf(userIdCounter++);
        //return new User("User" + ThreadLocalRandom.current().nextInt(100), ""  + " " + ThreadLocalRandom.current().nextInt(100)
        return new User("User" + ThreadLocalRandom.current().nextInt(100), uniqueID, time);
    }

    @PostConstruct
    public void writeUserstoJSON() {
        File file = new File("Matching-Client-main (1)/Matching-Client-main/demo/src/main/java/com/example/demo/json/users.JSON");
        ObjectMapper mapper = new ObjectMapper();
        if (file.exists() && file.length() == 0) {
            try {
                List<User> users = new ArrayList<>();
                for (int i = 0; i <= 100; i++) {
                    User user = generateRandomUser();
                    users.add(user);
                }
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, users);
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


    public void WritematchedUserstoJSON(List<User> uniqueUsers) {
        File file = new File("Matching-Client-main (1)/Matching-Client-main/demo/src/main/java/com/example/demo/json/matchedusers.JSON");

        try {
            ObjectMapper mapper = new ObjectMapper();
            // Always write as a list
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, uniqueUsers);
            isExecuted = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> ReadmatchedUsersfromJSON() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("Matching-Client-main (1)/Matching-Client-main/demo/src/main/java/com/example/demo/json/matchedusers.JSON");
        List<User> matchedusers = new ArrayList<>();
        if (file.exists() && file.length() != 0) {
            try {
                matchedusers = mapper.readValue(file, new TypeReference<List<User>>(){});
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
        List<User> users = new ArrayList<>(userList);
        if (users.size() < 2) {
            throw new IllegalStateException("Not enough users to match");
        }
        while (!users.isEmpty()) {
            User user1 = users.removeFirst();
            User user2 = users.removeFirst();
            validateUser(user1);
            validateUser(user2);
            matcheduserList.add(user1);
            matcheduserList.add(user2);
        }
        return matcheduserList;
    }

    private User validateUser(User user) {
        String Inputtime= user.getUsertime();
        if (Inputtime == null) {
            throw new IllegalStateException("User has no time");
        }
        if (Inputtime.equals(user.getUsertime())) {
            return user;
        }
        throw new IllegalStateException("User not added");
    }
}



