package schwarz.it.MatchingClient.backend;

public class Match {
    private Wrapper wrapper;

    public Wrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(Wrapper wrapper) {
        this.wrapper = wrapper;
    }

    public Match() {
    }

    public Match(Wrapper wrapper) {
        this.wrapper = wrapper;
    }
}

class Wrapper {
    private String initiator;
    private User matchedUser;

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public User getMatchedUser() {
        return matchedUser;
    }

    public void setMatchedUser(User matchedUser) {
        this.matchedUser = matchedUser;
    }

    public Wrapper() {
    }

    public Wrapper(String initiator, User matchedUser) {
        this.initiator = initiator;
        this.matchedUser = matchedUser;
    }
}