package schwarz.it.lws;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Data
@Builder
public class User {
    @NonNull
    private final String id;
    private final String email;
    @NonNull
    private final String usertime;
    @NonNull
    private final String name;
    @NonNull
    private final String userId;

    // Static method to generate a random User
    public static User generateRandomUser() {
        // Generate random values for User fields
        String id = UUID.randomUUID().toString();
        String name = "User" + ThreadLocalRandom.current().nextInt(100);
        LocalTime startingTime = LocalTime.of(11, 30);
        int randomMinutes = ThreadLocalRandom.current().nextInt(0, 150);
        LocalTime randomTime = startingTime.plusMinutes(randomMinutes);
        String usertime = randomTime.toString();

        return User.builder()
                .id(id)
                .name(name)
                .usertime(usertime)
                .userId(id)
                .build();
    }
}
