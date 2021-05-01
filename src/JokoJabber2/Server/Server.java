package JokoJabber2.Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    int portNumber;
    Map<String, ObjectOutputStream> users = new HashMap<String, ObjectOutputStream>();
    List<String> userList = new ArrayList<>();

    public Server(int portNumber) {
        this.portNumber = portNumber;
    }

    public void storeUserInfo(String username, ObjectOutputStream oos){
        users.put(username, oos);
        userList.add(username);
    }

    public void removeUserInfo(String username){ users.remove(username);userList.remove(username);}

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(portNumber);) {
            System.out.println("JokoJabber server activated.");
            while (true) {
                System.out.println("waiting for clientsocket.");
                Socket clientSocket = serverSocket.accept();
                System.out.println("clientsocket accepted.");
                PayloadReceiver payloadReceiver = new PayloadReceiver(clientSocket,this);
                payloadReceiver.start();
                System.out.println("payloadReceiver started.");
            }
        }catch(IOException e){
            System.out.println("Server->execute->IOException.");
        }
    }

    public void broadcast(Object object) throws IOException {
        for (Map.Entry<String, ObjectOutputStream> entry : users.entrySet()) {
            entry.getValue().writeObject(object);
        }
    }

    public List<String> getUserList() {
        return userList;
    }

    public List sendUserList(){
        List<String> userList = new ArrayList<>();
        for (Map.Entry<String, ObjectOutputStream> entry : users.entrySet()) {
            userList.add(entry.getKey());
        }
        return userList;
    }


    public static void main(String[] args)throws Exception{
        Server server = new Server(12347);
        server.execute();
    }

}
