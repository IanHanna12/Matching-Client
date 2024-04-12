package schwarz.it.lws;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    private String initiator;
    private User matchedUser;
}