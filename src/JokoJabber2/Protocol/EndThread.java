package JokoJabber2.Protocol;

import java.io.Serializable;

public class EndThread implements Serializable {
    String username;

    public EndThread(String username) {
        this.username = username;

    }

    public String getUsername() {
        return username;
    }

}
