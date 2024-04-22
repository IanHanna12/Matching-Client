package schwarz.it.lws.backend.dto;

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

    public static String generaterandomname() {
        String[] firstnames = {"Ian", "Ivan", "Josh", "Jason", "John", "Jacob", "Jesse", "Jessica", "Jennifer", "Jenny", "Jessie", "Jill", "Jordan", "Joseph", "Josephine", "Joshua", "Juan", "Julia", "Julie", "Justin", "Karen", "Kevin", "Kimberly", "Kyle", "Lauren", "Laura"};
        String [] lastnames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Miller", "Davis", "Garcia", "Rodriguez", "Wilson", "Martinez", "Anderson", "Taylor", "Thomas", "Hernandez", "Moore", "Martin", "Jackson", "Thompson", "White", "Lopez", "Lee", "Gonzalez", "Harris", "Clark", "Lewis", "Robinson"};
        String randomfirstname = firstnames[ThreadLocalRandom.current().nextInt(0, firstnames.length)];
        String randomlastname = lastnames[ThreadLocalRandom.current().nextInt(0, lastnames.length)];
       String name = randomfirstname + " " + randomlastname;

        return name;
    }

    @Data
    public static class GenerateRandomUser {
        public static User generateRandomUser() {
            // Generate random values for User fields
            String name = generaterandomname();
            String id = UUID.randomUUID().toString();
            LocalTime startingTime = LocalTime.of(11, 30);
            int randomMinutes = ThreadLocalRandom.current().nextInt(0, 150);
            LocalTime randomTime = startingTime.plusMinutes(randomMinutes);
            String time = randomTime.toString();

            return new User(name, id, time);
        }
    }
}