package schwarz.it.lws.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
 @NonNull
public class MatchWrapper {
    private String initiator;
    private User matchedUser;
}