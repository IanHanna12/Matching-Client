package schwarz.it.lws.backend.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import schwarz.it.lws.backend.dto.MatchWrapper;
import schwarz.it.lws.backend.RESTController.Controller;
import schwarz.it.lws.backend.dto.Restaurant;
import schwarz.it.lws.backend.dto.User;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static schwarz.it.lws.backend.dto.User.GenerateRandomUser.generateRandomUser;


@Service
public class UserService {
    String pathtoUsers = "Matching-Client-main (1)/Matching-Client-main/MatchingClient/src/main/java/schwarz/it/lws/json/users.JSON";
    String pathtomatchedUsers = "Matching-Client-main (1)/Matching-Client-main/MatchingClient/src/main/java/schwarz/it/lws/json/matchedusers.JSON";
    static String pathtoRestaurants = "Matching-Client-main (1)/Matching-Client-main/MatchingClient/src/main/java/schwarz/it/lws/json/restaurants.JSON";

    private final List<User> userlist;

    public UserService(List<User> userlist) {
        this.userlist = userlist;
    }

    public final Logger logger = org.slf4j.LoggerFactory.getLogger(Controller.class);



    @PostConstruct
    public void writeuserstoJSON() {
        File file = new File(pathtoUsers);
        ObjectMapper mapper = new ObjectMapper();

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> readusersfromJSON() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(pathtoUsers);

        if (file.exists() && file.length() != 0) {
            try {
                List<User> Users = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, User.class));
                userlist.addAll(Users);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return userlist;
    }

    public void writematcheduserstoJSON(List<MatchWrapper> matchedusers) {
        File file = new File(pathtomatchedUsers);
        ObjectMapper mapper = new ObjectMapper();
        List<MatchWrapper> existingusers = new ArrayList<>();


        if (file.exists() && file.length() != 0) {
            try {
                existingusers = mapper.readValue(file, new TypeReference<List<MatchWrapper>>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Append the new matched users to the existing list
        existingusers.addAll(matchedusers);
        try {
            file.createNewFile();
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, existingusers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static List<Restaurant> writerestaurantstoJSON() {
        File file = new File(pathtoRestaurants);
        ObjectMapper mapper = new ObjectMapper();
        if (file.exists()) {
            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, new ArrayList<Restaurant>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public List<Restaurant> readrestaurants() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(pathtoRestaurants);
        if (file.exists() && file.length() != 0) {
            try {
                List<Restaurant> restaurants = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, Restaurant.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public List<User> processUserJsonData(String decodedData) {
        ObjectMapper objectMapper = new ObjectMapper();
        LocalTime enteredTime;

        try {
            JsonNode rootNode = objectMapper.readTree(decodedData);
            String timeString = rootNode.get("time").asText();
            enteredTime = LocalTime.parse(timeString);
        } catch (IOException e) {
            e.printStackTrace();

            throw new RuntimeException("Error parsing JSON data", e);
        }
        LocalTime lowerBound = enteredTime.minusMinutes(20);
        LocalTime upperBound = enteredTime.plusMinutes(20);

        List<User> users = readusersfromJSON();

        return users.stream().filter(user -> {
            LocalTime userTime = LocalTime.parse(user.getTime());
            return !userTime.isBefore(lowerBound) && !userTime.isAfter(upperBound);
        }).collect(Collectors.toList());
    }


    public User findAndMatchUsersRandomly(String enteredTime, String enteredId) throws IOException {
        List<User> filteredUsers = processUserJsonData(enteredTime);

        if (filteredUsers.isEmpty()) {
            return null;
        }

        int randIndex = ThreadLocalRandom.current().nextInt(filteredUsers.size());
        return filteredUsers.get(randIndex);
    }

    public List<User> readmatchedusersfromjson() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(pathtomatchedUsers);
        List<User> matchedusers = new ArrayList<>();
        if (file.exists() && file.length() != 0) {
            try {
                matchedusers = mapper.readValue(file, new TypeReference<List<User>>() {
                });
                userlist.addAll(matchedusers);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return matchedusers;
    }


    public List<User> getandmatchusers(List<User> matcheduserlist) {
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

