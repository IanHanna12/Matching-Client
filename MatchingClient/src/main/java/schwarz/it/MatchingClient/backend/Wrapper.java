package schwarz.it.MatchingClient.backend;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Wrapper {
    private String initiator;
    private User matchedUser;
}
