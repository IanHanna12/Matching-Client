package schwarz.it.lws.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import schwarz.it.lws.backend.dto.MatchWrapper;
import schwarz.it.lws.backend.RESTController.Controller;
import schwarz.it.lws.backend.dto.User;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static schwarz.it.lws.backend.dto.User.GenerateRandomUser.generateRandomUser;



@Service
public class userService {

    boolean isExecuted;
    private final List<User> userlist;

    public userService(List<User> userlist) {
        this.userlist = userlist;
    }
    public final Logger logger = org.slf4j.LoggerFactory.getLogger(Controller.class);

@PostConstruct
    public void writeUserstoJSON() {
    File file = new File("Matching-Client-main (1)/Matching-Client-main/MatchingClient/src/main/java/schwarz/it/lws/json/users.JSON");
    ObjectMapper mapper = new ObjectMapper();
    if (!file.exists() || file.length() == 0) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try {
            List<User> users = new ArrayList<>();
            for (int i = 0; i <= 100; i++) {
                User user = generateRandomUser();
                users.add(user);
            }
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, users);
            isExecuted = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    @PostConstruct
    public List<User> readUsersfromJSON() {
        ObjectMapper mapper = new ObjectMapper();


        File file = new File("Matching-Client-main (1)/Matching-Client-main/MatchingClient/src/main/java/schwarz/it/lws/json/users.JSON");
        if (file.exists() && file.length() != 0) {
            try {
                List<User> Users = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, User.class));
                userlist.addAll(Users);
                isExecuted = true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return userlist;
    }


    public void writeMatchedUserstoJSON(List<MatchWrapper> matchedUsers) {

        File file = new File("Matching-Client-main (1)/Matching-Client-main/MatchingClient/src/main/java/schwarz/it/lws/json/matchedusers.JSON");
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<MatchWrapper> existingMatches = new ArrayList<>();
            // If file exists and is not empty, read the existing content
            if (file.exists() && file.length() != 0) {
                existingMatches = mapper.readValue(file, new TypeReference<List<MatchWrapper>>() {});
            }
            // Add the new matches to the existing matches
            existingMatches.addAll(matchedUsers);
            // Write the combined matches back to the file
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, existingMatches);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> ReadmatchedUsersfromJSON() {
        ObjectMapper mapper = new ObjectMapper();

        //TODO: fix path
        File file = new File("Matching-Client-main (1)/Matching-Client-main/demo/src/main/java/com/example/demo/json/matchedusers.JSON");
        List<User> matchedusers = new ArrayList<>();
        if (file.exists() && file.length() != 0) {
            try {
                matchedusers = mapper.readValue(file, new TypeReference<List<User>>() {
                });
                userlist.addAll(matchedusers);
                isExecuted = true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return matchedusers;
    }

    public List<User> getandmatchusersRandomly(List<User> matcheduserlist) {
        return getUsers(matcheduserlist);
    }

    public List<User> processUserJsonData(String decodedData) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(decodedData);
            String time = jsonNode.get("time").asText();
            LocalTime enteredTime = LocalTime.parse(time);
            LocalTime lowerBound = enteredTime.minusMinutes(20);
            LocalTime upperBound = enteredTime.plusMinutes(20);

            List<User> users = this.readUsersfromJSON();
            users = users.stream()
                    .filter(user -> {
                        LocalTime userTime = LocalTime.parse(user.getTime());
                        return userTime.isAfter(lowerBound) && userTime.isBefore(upperBound);
                    })
                    .collect(Collectors.toList());

            logger.info("Successfully read users from JSON and filtered by time: {}", time);
            return users;
        } catch (JsonProcessingException e) {
            logger.error("Error while reading users from JSON and filtering by time", e);
            return Collections.emptyList();
        }
    }

    public List<User> getandmatchUsers(List<User> matcheduserlist) {
        return getUsers(matcheduserlist);
    }

    private List<User> getUsers(List<User> matcheduserlist) {
        List<User> Users = new ArrayList<>(userlist);
        if (Users.size() < 2) {
            throw new IllegalStateException("Not enough users to Match");
        }
        while (!Users.isEmpty()) {
            int randomIndex = ThreadLocalRandom.current().nextInt(Users.size());
            matcheduserlist.add(Users.remove(randomIndex));
            if (!Users.isEmpty()) {
                randomIndex = ThreadLocalRandom.current().nextInt(Users.size());
                matcheduserlist.add(Users.remove(randomIndex));
            }
        }
        return matcheduserlist;
    }
}