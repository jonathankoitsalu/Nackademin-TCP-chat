package JokoJabber2.Protocol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InitiateThread implements Serializable {
    String username;
    List<String> userList = new ArrayList<>();

    public InitiateThread(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUserList(List<String> userList) {
        this.userList = userList;
    }

    public List<String> getUserList() {
        return userList;
    }
}
