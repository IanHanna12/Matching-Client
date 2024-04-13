package schwarz.it.lws;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    @NonNull
    @Getter
    @Setter
    private String name;

    @NonNull
    @Getter
    @Setter
    private String id;

    @NonNull
    @Getter
    @Setter
    private String time;

    @Data
    public static class GenerateRandomUser {
        public static User generateRandomUser() {
            // Generate random values for User fields
            String name = "User" + ThreadLocalRandom.current().nextInt(100);
            String id = UUID.randomUUID().toString();
            LocalTime startingTime = LocalTime.of(11, 30);
            int randomMinutes = ThreadLocalRandom.current().nextInt(0, 150);
            LocalTime randomTime = startingTime.plusMinutes(randomMinutes);
            String time = randomTime.toString();

            // Create and return a new User instance with the random values
            return new User(name, id, time);
        }
    }
}