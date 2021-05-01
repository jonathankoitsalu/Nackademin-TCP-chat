package JokoJabber2.Server;

import JokoJabber2.Protocol.EndThread;
import JokoJabber2.Protocol.InitiateThread;
import JokoJabber2.Protocol.Payload;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PayloadReceiver extends Thread {
    Server server;
    Socket clientSocket;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    boolean userActive = true;

    public PayloadReceiver(Socket clientSocket, Server server) throws IOException {
        this.server = server;
        this.clientSocket = clientSocket;
        this.ois = new ObjectInputStream(clientSocket.getInputStream());
        this.oos = new ObjectOutputStream(clientSocket.getOutputStream());

    }

    public void run() {
        try {
            while (userActive) {
               Object object = ois.readObject();
                if (object instanceof InitiateThread) {
                    server.storeUserInfo(((InitiateThread) object).getUsername(), oos);
                    ((InitiateThread) object).setUserList(server.getUserList());
                    server.broadcast(object);
                    System.out.println(((InitiateThread) object).getUserList());
                    System.out.println(((InitiateThread) object).getUsername());
                    System.out.println("InitiateThread.");
                }

                if (object instanceof Payload) {
                    System.out.println(((Payload) object).getMessage());
                    server.broadcast(object);
                    System.out.println("Payload sent.");
                }

                if (object instanceof EndThread) {
                    server.removeUserInfo(((EndThread) object).getUsername());
                    server.broadcast(object);
                    System.out.println("EndThread");
                    break;
                }


            }
        }catch(ClassNotFoundException e){
            System.out.println("PayloadReceiver->run->ClassNotFoundException.");
        }catch(IOException e){
            System.out.println("PayloadReceiver->run->IOException.");
        }
    }
}