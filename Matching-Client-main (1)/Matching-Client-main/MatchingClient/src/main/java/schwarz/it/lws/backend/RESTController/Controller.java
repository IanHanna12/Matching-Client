package schwarz.it.lws.backend.RESTController;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import schwarz.it.lws.backend.dto.MatchWrapper;
import schwarz.it.lws.backend.dto.Restaurant;
import schwarz.it.lws.backend.dto.User;
import schwarz.it.lws.backend.service.UserService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class Controller {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(Controller.class);

    private final UserService userService;
    private final List<User> Users;

    public Controller(UserService userService, List<User> Users) {
        this.userService = userService;
        this.Users = Users;
    }

    @GetMapping("/findAndMatchUsersRandomly")
    public User findAndMatchUsersRandomly(@RequestParam String enteredTime, @RequestParam String enteredId) throws IOException {
        return userService.findAndMatchUsersRandomly(enteredTime, enteredId);
    }

    @GetMapping("/matchUsers")
    public List<User> matchUsers() {
        return userService.getandmatchusers(Users);
    }

    @PutMapping("getmatchedUsers")
    public List<User> getMatchedUsers() {
        return userService.readmatchedusersfromjson();
    }

    @PostMapping("/writeUsers")
    public void writeUserstoJson() throws JsonProcessingException {
        userService.writeuserstoJSON();
    }

    @GetMapping("/readUsers")
    public List<User> readUsersFromJson(@RequestParam String data) {
        String decodedData = URLDecoder.decode(data, StandardCharsets.UTF_8);
        return userService.processUserJsonData(decodedData);
    }

    @PostMapping("/writeMatchedUsers")
    public void writeMatchedUserstoJSON(@RequestBody List<MatchWrapper> matchedUsers) {
        userService.writematcheduserstoJSON(matchedUsers);
    }

    @GetMapping("/readMatchedUsers")
    public List<User> readMatchedUsersFromJson() {
        return userService.readmatchedusersfromjson();
    }

    @GetMapping("/readrestaurants")
    public List<Restaurant> readRestaurantsFromJson() {
        return UserService.readrestaurants();
    }


    @PostMapping("/writerestaurants")
    public ResponseEntity<?> writeRestaurants(@RequestBody List<String> restaurants) {
        try {
            UserService.writeRestaurants(restaurants);
            return ResponseEntity.ok("Restaurants saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving restaurants");
        }
    }
}
