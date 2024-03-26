import com.example.demo.DemoApplication;
import com.example.demo.User;
import com.example.demo.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DemoApplication.class)
public class UserServiceTests {
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService(List.of(new User("User1", "ID1"), new User("User2", "ID2")));
    }

    @Test
    public void getRandomUserReturnsUserFromList() {
        User user = userService.getRandomUser();
        assertNotNull(user);
        assertTrue(user.getName().startsWith("User"));
        assertTrue(user.getID().startsWith("ID"));
    }

    @Test
    public void getRandomUserReturnsNullWhenListIsEmpty() {
        userService = new UserService(List.of());
        User user = userService.getRandomUser();
        assertNull(user);
    }

    @Test
    public void generateRandomUserReturnsNewUser() {
        User user = userService.generateRandomUser();
        assertNotNull(user);
        assertTrue(user.getName().startsWith("User"));
        assertTrue(user.getID().startsWith("ID"));
    }

    @Test
    public void getMatchedUsersReturnsTwoUsersFromList() {
        List<User> matchedUsers = userService.getmatchedUsers();
        assertEquals(2, matchedUsers.size());
    }

    @Test
    public void getMatchedUsersisnotEmpty() {
        List<User> matchedUsers = userService.getmatchedUsers();
        assertFalse(matchedUsers.isEmpty());
    }

}