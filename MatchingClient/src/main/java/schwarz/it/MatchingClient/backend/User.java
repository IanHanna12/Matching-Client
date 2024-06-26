package schwarz.it.MatchingClient.backend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
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


    public User() {
    }

    // Constructor with parameters
    public User(String name, String ID, String time) {
        this.name = name;
        this.ID = ID;
        this.time = time;

    }

    public String getUsertime() {
        return time;
    }
}