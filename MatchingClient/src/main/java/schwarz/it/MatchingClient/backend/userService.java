package schwarz.it.MatchingClient.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
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


    @PostConstruct
    public User generateRandomUser() {
        java.time.LocalTime startingTime = java.time.LocalTime.of(11, 30);
        int randomMinutes = ThreadLocalRandom.current().nextInt(0, 150);
        java.time.LocalTime randomTime = startingTime.plusMinutes(randomMinutes);
        String time = randomTime.toString();
        String uniqueID = String.valueOf(userIdCounter++);
        return new User("User" + ThreadLocalRandom.current().nextInt(100), uniqueID, time);
    }

@PostConstruct
    public void writeUserstoJSON() {
    File file = new File("MatchingClient/src/main/java/schwarz/it/MatchingClient/json/users.JSON");
    ObjectMapper mapper = new ObjectMapper();
    if (!file.exists() || file.length() == 0) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try {
            List<User> Users = new ArrayList<>();
            for (int i = 0; i <= 100; i++) {
                User user = generateRandomUser();
                Users.add(user);
            }
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, Users);
            isExecuted = true;
        } catch (IOException e) {
            e.printStackTrace();
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



    public void writeMatchedUserstoJSON(Wrapper wrapper) {
        File file = new File("MatchingClient/src/main/java/schwarz/it/MatchingClient/json/matchedusers.JSON");
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Convert the Wrapper object to JSON
            String json = mapper.writeValueAsString(wrapper);

            // If file exists and is not empty, read the existing content
            List<Wrapper> existingMatches = new ArrayList<>();
            if (file.exists() && file.length() != 0) {
                existingMatches = mapper.readValue(file, new TypeReference<List<Wrapper>>() {});
            }

            // Add the new match to the existing matches
            existingMatches.add(wrapper);

            // Write the combined matches back to the file
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, existingMatches);
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
        List<User> users = new ArrayList<>(userList);
        if (users.size() < 2) {
            throw new IllegalStateException("Not enough users to Match");
        }
        while (!users.isEmpty()) {
            User user1 = users.remove(0);
            for (int i = users.size() - 1; i >= 0; i--) {
                User user2 = users.get(i);
                validateUser(user1);
                validateUser(user2);
                LocalTime time1 = LocalTime.parse(user1.getUsertime());
                LocalTime time2 = LocalTime.parse(user2.getUsertime());
                long timeDifference = ChronoUnit.MINUTES.between(time1, time2);
                if (Math.abs(timeDifference) <= 20) { // 20 minutes time tolerance
                    matcheduserList.add(user1);
                    matcheduserList.add(user2);
                    users.remove(i);
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