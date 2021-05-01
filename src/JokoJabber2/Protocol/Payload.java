package JokoJabber2.Protocol;

import java.io.Serializable;

public class Payload implements Serializable {
    String username;
    String message;

    public Payload(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}


