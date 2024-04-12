package schwarz.it.lws;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @JsonIgnore
    private String id;

    @JsonIgnore
    private String usertime;

    @JsonProperty("time")
    private String time;

    @JsonProperty("name")
    private String name;

    @JsonProperty("ID")
    private String ID;
}