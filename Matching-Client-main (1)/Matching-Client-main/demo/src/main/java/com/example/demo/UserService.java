
package com.example.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
    private final List<User> userList;

    public UserService(List<User> userList) {
        this.userList = userList;
    }

    public User generateRandomUser() {
        return new User("User" + ThreadLocalRandom.current().nextInt(100), "ID" + ThreadLocalRandom.current().nextInt(100));
    }


    //erstellt neue json Datei mit 10 Usern anstatt alte Datei zu verwenden
    @PostConstruct
    public void writetoJSON() {
        File file = new File("Matching-Client-main (1)/Matching-Client-main/demo/src/main/java/com/example/demo/json/users.JSON");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Check if the file exists and is not empty
        if (file.exists() && file.length() != 0) {
            try {
                // Try to read the file content
                List<User> existingUsers = mapper.readValue(file, new TypeReference<List<User>>() {});
                if (!existingUsers.isEmpty()) {
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // If the file does not exist or is empty, write to it
        List<User> users = new ArrayList<>();
        for (int i = 0; i <= 100; i++) {
            users.add(generateRandomUser());
        }
        try {
            mapper.writeValue(file, users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<User> getmatchedUsers() {
        List<User> matcheduserList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            matcheduserList.add(generateRandomUser());
        }
        return matcheduserList;
    }
}