package schwarz.it.lws;

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
import org.slf4j.Logger;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class Controller {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(Controller.class);

    private final schwarz.it.lws.userService userService;
    private final List<User> Users;

    public Controller(schwarz.it.lws.userService userService, List<User> Users) {
        this.userService = userService;
        this.Users = Users;
    }

    @GetMapping("/matchusersRandomly")
    public List<User> matchUsersRandomly() {
        return userService.getandmatchusersRandomly(Users);
    }


    @GetMapping("/matchUsers")
    public List<User> matchUsers() {
        return userService.getandmatchUsers(Users);
    }

    @PutMapping("getmatchedUsers")
    public List<User> getMatchedUsers() {
        return userService.ReadmatchedUsersfromJSON();
    }

    @PostMapping("/writeUsers")
    public void writeUserstoJson() throws JsonProcessingException {
        userService.writeUserstoJSON();
    }

    @GetMapping("/readUsers")
    public List<User> readUsersFromJson(@RequestParam String data) {
        String decodedData = URLDecoder.decode(data, StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(decodedData);
            String time = jsonNode.get("time").asText();
            List<User> users = userService.readUsersfromJSON().stream()
                    .filter(User -> User.getUsertime().equals(time))
                    .collect(Collectors.toList());
            logger.info("Successfully read users from JSON and filtered by time: {}", time);
            return users;
        } catch (JsonProcessingException e) {
            logger.error("Error while reading users from JSON and filtering by time", e);
            return Collections.emptyList();
        }
    }



    @PostMapping("/writeMatchedUsers")
    public void writeMatchedUserstoJSON(@RequestBody List<MatchWrapper> matchedUsers) {
        userService.writeMatchedUserstoJSON(matchedUsers);
    }

    @GetMapping("/readMatchedUsers")
    public List<User> readMatchedUsersFromJson() {
        return userService.ReadmatchedUsersfromJSON();
    }



}