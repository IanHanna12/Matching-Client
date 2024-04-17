package schwarz.it.lws.backend;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchWrapper {
    private String initiator;
    private User matchedUser;

}