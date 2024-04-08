package com.example.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@Service
public class userService {

    boolean isExecuted;
    private final List<User> userList;

    public userService(List<User> userList) {
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


    public void writeUserstoJSON() {
        File file = new File("Matching-Client-main (1)/Matching-Client-main/demo/src/main/java/com/example/demo/json/users.JSON");
        ObjectMapper mapper = new ObjectMapper();
        if (file.exists() && file.length() == 0) {
            try {
                List<User> Users = new ArrayList<>();
                for (int i = 0; i <= 100; i++) {
                    User user = generateRandomUser();
                    Users.add(user);
                }
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, Users);
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
                List<User> Users = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, User.class));
                userList.addAll(Users);
                isExecuted = true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return userList;
    }

    public void Ensureusersgetmatchedeventhoughtimeinputdoesnteexactlymatch() {


    }


    public void WritematchedUserstoJSON(List<Match> matchedusers) {
        File file = new File("Matching-Client-main (1)/Matching-Client-main/demo/src/main/java/com/example/demo/json/matchedusers.JSON");
        ObjectMapper mapper = new ObjectMapper();

        // If the file does not exist or is empty, write the new matches to the file
        if (!file.exists() || file.length() == 0) {
            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, matchedusers);
                isExecuted = true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // If the file exists and is not empty, read  existing matches
            List<Match> existingMatches = new ArrayList<>();
            try {
                existingMatches = mapper.readValue(file, new TypeReference<List<Match>>() {
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // Append new unique matches to existing matches
            for (Match newMatch : matchedusers) {
                if (!existingMatches.contains(newMatch)) {
                    existingMatches.add(newMatch);
                }
            }


            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, existingMatches);
                isExecuted = true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void writeUniqueUserstoJSON(List<User> newUniqueUsers) {
        File file = new File("Matching-Client-main (1)/Matching-Client-main/demo/src/main/java/com/example/demo/json/matchedusers.JSON");
        ObjectMapper mapper = new ObjectMapper();

        // If the file does not exist or is empty, write the new unique users to the file
        if (!file.exists() || file.length() == 0) {
            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, newUniqueUsers);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {

            List<User> existingUniqueUsers = new ArrayList<>();
            try {
                existingUniqueUsers = mapper.readValue(file, new TypeReference<List<User>>() {});
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            for (User newUser : newUniqueUsers) {
                if (!existingUniqueUsers.contains(newUser)) {
                    existingUniqueUsers.add(newUser);
                }
            }

            // Write the updated list of unique users back to the file
            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, existingUniqueUsers);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<User> ReadmatchedUsersfromJSON() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("Matching-Client-main (1)/Matching-Client-main/demo/src/main/java/com/example/demo/json/matchedusers.JSON");
        List<User> matchedusers = new ArrayList<>();
        if (file.exists() && file.length() != 0) {
            try {
                matchedusers = mapper.readValue(file, new TypeReference<List<User>>() {
                });
                userList.addAll(matchedusers);
                isExecuted = true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return matchedusers;
    }

    public List<User> getandmatchusersRandomly(List<User> matcheduserList) {
        List<User> Users = new ArrayList<>(userList);
        if (Users.size() < 2) {
            throw new IllegalStateException("Not enough users to Match");
        }
        while (!Users.isEmpty()) {
            int randomIndex = ThreadLocalRandom.current().nextInt(Users.size());
            matcheduserList.add(Users.remove(randomIndex));
            if (!Users.isEmpty()) {
                randomIndex = ThreadLocalRandom.current().nextInt(Users.size());
                matcheduserList.add(Users.remove(randomIndex));
            }
        }
        return matcheduserList;
    }


    public List<User> getandmatchUsers(List<User> matcheduserList) {
        List<User> Users = new ArrayList<>(userList);
        if (Users.size() < 2) {
            throw new IllegalStateException("Not enough users to Match");
        }
        while (!Users.isEmpty()) {
            User user1 = Users.removeFirst();
            for (int i = Users.size() - 1; i >= 0; i--) {
                User user2 = Users.get(i);
                validateUser(user1);
                validateUser(user2);
                LocalTime time1 = LocalTime.parse(user1.getUsertime());
                LocalTime time2 = LocalTime.parse(user2.getUsertime());
                long timeDifference = ChronoUnit.MINUTES.between(time1, time2);
                if (Math.abs(timeDifference) <= 20) { // 20 minutes tolerance
                    matcheduserList.add(user1);
                    matcheduserList.add(user2);
                    Users.remove(i);
                    break;
                }
            }
        }
        return matcheduserList;
    }

    private void validateUser(User user) {
        String Inputtime = user.getUsertime();
        if (Inputtime == null) {
            throw new IllegalStateException("User has no time");
        }
        if (Inputtime.equals(user.getUsertime())) {
            return;
        }
        throw new IllegalStateException("User not added");
    }
}