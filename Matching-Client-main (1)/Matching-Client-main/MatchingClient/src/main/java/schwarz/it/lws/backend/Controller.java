package schwarz.it.lws.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class Controller {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(Controller.class);

    private final schwarz.it.lws.backend.userService userService;
    private final List<User> Users;

    public Controller(schwarz.it.lws.backend.userService userService, List<User> Users) {
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
        return userService.processUserJsonData(decodedData);
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