package JokoJabber2.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChatInterface implements ActionListener {
    private ImageIcon logo = new ImageIcon("C:\\Users\\jonat\\Desktop\\Nackademin-Nätverksprogrammering-Java\\src\\JokoJabber2\\GUI\\JokoJabberLogo.png");
    private JLabel jokojabberLabel = new JLabel(logo);
    private JFrame frame = new JFrame("JokoJabber");
    private JPanel mainPanel = new JPanel();
    private JPanel lowerPanel = new JPanel();
    private JButton sendButton = new JButton("Send");
    private JButton logoutButton = new JButton("Logout");
    private JTextField messageField = new JTextField();
    private JPanel chatAreaPanel = new JPanel();
    private JPanel listPanel = new JPanel();
    private JTextArea chatArea = new JTextArea();
    DefaultListModel<String> list = new DefaultListModel<>();
    JList<String> listGraphic = new JList<>(list);

    AtomicBoolean sendClicked = new AtomicBoolean();
    AtomicBoolean logoutClicked = new AtomicBoolean();

    public ChatInterface(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(false);
        mainPanel.setLayout(new BorderLayout());
        lowerPanel.setLayout(new BorderLayout());
        listPanel.setLayout(new BorderLayout());
        lowerPanel.add(sendButton, BorderLayout.LINE_START);
        lowerPanel.add(messageField, BorderLayout.CENTER);
        lowerPanel.add(logoutButton, BorderLayout.LINE_END);
        mainPanel.add(lowerPanel,BorderLayout.PAGE_END);
        mainPanel.add(chatAreaPanel, BorderLayout.LINE_START);
        chatAreaPanel.add(chatArea, BorderLayout.PAGE_START);
        chatArea.setPreferredSize(new Dimension(400, 300));
        mainPanel.add(listPanel, BorderLayout.LINE_END);
        listPanel.add(listGraphic,BorderLayout.PAGE_START);
        listPanel.add(jokojabberLabel,BorderLayout.PAGE_END);

        list.addElement("Users online: ");

        frame.add(mainPanel);
        frame.pack();

        sendButton.addActionListener(this);
        logoutButton.addActionListener(this);
        messageField.addActionListener(this);

    }



    public void addUserToList(String username){
        list.addElement(username);
    }

    public void removeUserFromList(String username){
        list.removeElement(username);
    }

    public JTextField getMessageField() {
        return messageField;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == messageField || ae.getSource() == sendButton) {
            sendClicked.set(true);
        }

        if (ae.getSource() == logoutButton) {
            logoutClicked.set(true);
        }
    }

    public AtomicBoolean getSendClicked() {
        return sendClicked;
    }

    public AtomicBoolean getLogoutClicked() {
        return logoutClicked;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JTextArea getChatArea() {
        return chatArea;
    }

//        public static void main(String[] args) throws Exception {
//        new ChatInterface();
//    }
}
