package JokoJabber2.Client;


import JokoJabber2.Protocol.EndThread;
import JokoJabber2.Protocol.InitiateThread;
import JokoJabber2.Protocol.Payload;

import java.io.IOException;

public class MessageReader extends Thread {
    Client client;


    public MessageReader(Client client) {
        this.client = client;
    }



    public void run() {
        System.out.println("MessageReader started.");
        while (client.getMessageReaderActive()) {
            try {
                System.out.println("Waiting for object.");
                Object object = client.ois.readObject();

                if (object instanceof InitiateThread) {
                    client.chatInterface.getChatArea().append(((InitiateThread) object).getUsername() + " connected.\n");
                    if (client.userList.isEmpty()) {
                        client.userList = ((InitiateThread) object).getUserList();
                        for (String user : client.userList) {
                            client.chatInterface.addUserToList(user);
                        }
                    }else {
                        client.chatInterface.addUserToList(((InitiateThread) object).getUsername());
                        }
                    }
                if (object instanceof Payload) {
                    client.chatInterface.getChatArea().append(((Payload) object).getUsername() + ": " + ((Payload) object).getMessage() + "\n");
                    System.out.println(((Payload) object).getMessage());
                }
                if (object instanceof EndThread) {
                    client.chatInterface.getChatArea().append(((EndThread) object).getUsername() + " disconnected.\n");
                    client.userList.remove(((EndThread) object).getUsername());
                    client.chatInterface.removeUserFromList(((EndThread) object).getUsername());
                }
            } catch (ClassNotFoundException e) {
                System.out.println("ReadMessage->run->ClassNotFoundException.");
            } catch (IOException e) {
                System.out.println("ReadMessage->run->IOException.");
            }
        }
        System.out.println("thread stopped");
    }
}