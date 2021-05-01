package JokoJabber2.Client;

import JokoJabber2.GUI.ChatInterface;
import JokoJabber2.GUI.LoginInterface;
import JokoJabber2.Protocol.EndThread;
import JokoJabber2.Protocol.InitiateThread;
import JokoJabber2.Protocol.Payload;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    String username;

    String hostName;
    int portNumber;

    //connection processes
    Socket addressSocket;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    MessageReader messageReader = new MessageReader( this);

    boolean ProgramActive = true;
    Boolean messageReaderActive = false;

    LoginInterface loginInterface = new LoginInterface();
    Boolean inLoginInterface = true;

    ChatInterface chatInterface = new ChatInterface();
    Boolean inChatInterface = false;
    List<String> userList = new ArrayList<>();

    Actions EXIT = Actions.EXIT;
    Actions LOGIN = Actions.LOGIN;
    Actions SEND = Actions.SEND;
    Actions LOGOUT = Actions.LOGOUT;

    public enum Actions {
        EXIT,
        LOGIN,
        SEND,
        LOGOUT
    }

    public Client(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }

    public void setMessageReaderActive(Boolean messageReaderActive) {
        this.messageReaderActive = messageReaderActive;
    }

    public synchronized Boolean getMessageReaderActive() {
        return messageReaderActive;
    }

    public void setInLoginInterface(Boolean inLoginInterface) {
        this.inLoginInterface = inLoginInterface;
    }

    public void setInChatInterface(Boolean inChatInterface) {
        this.inChatInterface = inChatInterface;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public void openLoginWindow() {
        loginInterface.getFrame().setVisible(true);
        inLoginInterface = true;
    }

    public void openChatWindow() {
        chatInterface.getFrame().setVisible(true);
        inChatInterface = true;
    }

    public void closeLoginWindow() {
        loginInterface.getFrame().setVisible(false);
        setInLoginInterface(false);
    }

    public void closeChatWindow() {
        chatInterface.getFrame().setVisible(false);
        chatInterface.getChatArea().setText("");
        setInChatInterface(false);
    }

    public boolean click(Actions action) {
        switch (action) {
            case LOGIN -> {
                return loginInterface.getLoginClicked().compareAndSet(true, false); //startar if-satsen genom att returnera true när den sätts till true, och sätter boolen tillbaka till false efter if-satsen.
            }
            case EXIT -> {
                return loginInterface.getExitClicked().compareAndSet(true, false);
            }
            case SEND -> {
                return chatInterface.getSendClicked().compareAndSet(true, false);
            }
            case LOGOUT -> {
                return chatInterface.getLogoutClicked().compareAndSet(true, false);
            }
        }
        return false;
    }

    public void sendPayload() {
        try {
            oos.writeObject(new Payload(username, chatInterface.getMessageField().getText()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        chatInterface.getMessageField().setText("");
    }

    public void initiateThread(){
        try {
            oos.writeObject(new InitiateThread(username));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void endThread(){
        try {
            oos.writeObject(new EndThread(username));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startMessageReader(){
        setMessageReaderActive(true);
        messageReader.start();
    }

    public void connect(){
        try {
            this.addressSocket = new Socket(hostName, portNumber);
            this.oos = new ObjectOutputStream(addressSocket.getOutputStream());
            this.ois = new ObjectInputStream(addressSocket.getInputStream());
            System.out.println("Connected to JokoJabber.");

        } catch (Exception e) {
            System.out.println("Client->execute->Exception");
        }
    }

    public void storeUsername(){
        username = loginInterface.getUsernameInputField().getText();
        loginInterface.getUsernameInputField().setText(""); //Erase username from usernameField.
    }

    public void execute() {
        while(ProgramActive) {
            while (inLoginInterface) {
                if (click(LOGIN)) {
                    connect();
                    storeUsername();
                    initiateThread();
                    startMessageReader();
                    closeLoginWindow();
                    openChatWindow();
                }
                if (click(EXIT)) {
                    System.exit(0);
                    //loginInterface.getFrame().dispatchEvent(new WindowEvent(loginInterface.getFrame(), WindowEvent.WINDOW_CLOSING));
                }
            }
            while (inChatInterface) {
                if (click(SEND)) {
                    sendPayload();
                    System.out.println("send message.");
                }
                if (click(LOGOUT)) {
                    System.out.println("");
                    closeChatWindow();
                    for (String user : userList) {
                        chatInterface.removeUserFromList(user);
                    }
                    endThread();
                    openLoginWindow();
                    setMessageReaderActive(false);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {

            Client client = new Client("127.0.0.1", 12347);
            client.execute();

    }
}

