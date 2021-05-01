package JokoJabber2.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoginInterface implements ActionListener {
     ImageIcon logo = new ImageIcon("C:\\Users\\jonat\\Desktop\\Nackademin-Nätverksprogrammering-Java\\src\\JokoJabber2\\GUI\\JokoJabberLogo.png");
     JFrame frame = new JFrame("JokoJabber");
     JPanel upperPanel = new JPanel();
     JPanel lowerPanel = new JPanel();
     JPanel usernameLabelPanel = new JPanel();
     JLabel jokojabberLabel = new JLabel(logo);
     JLabel usernameLabel = new JLabel("Username: ");
     JTextField usernameInputField = new JTextField();
     JButton loginButton = new JButton("login");
     JButton exitButton = new JButton("exit");

     //ActionListener Bools
     AtomicBoolean loginClicked = new AtomicBoolean();
     AtomicBoolean exitClicked = new AtomicBoolean();

    public LoginInterface(){
        //primaryPanel.setPreferredSize(new Dimension(600, 400));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(300,300);
        frame.setVisible(true);
        frame.setLayout(new GridLayout(2,1));
        frame.add(upperPanel);
        frame.add(lowerPanel);
        lowerPanel.setLayout(new GridLayout(4,1,20,1));
        usernameLabelPanel.setLayout(new BorderLayout());
        upperPanel.add(jokojabberLabel);
        lowerPanel.add(usernameLabelPanel);
        lowerPanel.add(usernameInputField);
        lowerPanel.add(loginButton);
        lowerPanel.add(exitButton);
        usernameLabelPanel.add(usernameLabel, BorderLayout.PAGE_END);
        frame.pack();

        loginButton.addActionListener(this);
        exitButton.addActionListener(this);
        usernameInputField.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == usernameInputField || ae.getSource() == loginButton) {
            loginClicked.set(true);
        }

        if (ae.getSource() == exitButton) {
            exitClicked.set(true);
        }
    }

    public JFrame getFrame() {
        return frame;
    }

    public JTextField getUsernameInputField() {
        return usernameInputField;
    }

    public AtomicBoolean getLoginClicked() {
        return loginClicked;
    }

    public AtomicBoolean getExitClicked() {
        return exitClicked;
    }

//    public static void main(String[] args) throws Exception {
//        new LoginInterface();
//    }


}
