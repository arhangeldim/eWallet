package arhangel.dim.ewallet.gui;

import arhangel.dim.ewallet.Controller;
import arhangel.dim.ewallet.entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 */
public class LoginPanel extends JPanel implements ActionListener {

    private JButton loginButton;
    private JButton regButton;
    private JTextField loginField;
    private JPasswordField passField;
    private JTextArea infoArea;

    private Controller controller;
    private JFrame parent;

    private static final String CMD_LOGIN = "cmd_login";
    private static final String CMD_REG = "cmd_reg";

    public LoginPanel(JFrame parent, Controller controller) {
        this.controller = controller;
        this.parent = parent;

        setLayout(new BorderLayout());
        setSize(new Dimension(300, 200));

        JPanel fieldPane = new JPanel();
        JPanel buttonPane = new JPanel();

        loginButton = new JButton("Login");
        regButton = new JButton("Register");
        loginField = new JTextField(10);
        passField = new JPasswordField(10);
        infoArea = new JTextArea();

        loginButton.addActionListener(this);
        loginButton.setActionCommand(CMD_LOGIN);
        regButton.addActionListener(this);
        regButton.setActionCommand(CMD_REG);

        buttonPane.add(regButton);
        buttonPane.add(loginButton);

        fieldPane.setLayout(new BoxLayout(fieldPane, BoxLayout.Y_AXIS));
        fieldPane.add(loginField);
        fieldPane.add(passField);

        add(fieldPane, BorderLayout.NORTH);
        add(buttonPane, BorderLayout.CENTER);
        add(infoArea, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        String pass = new String(passField.getPassword());
        String name = loginField.getText();

        if (pass.trim().isEmpty() || name.trim().isEmpty()) {
            infoArea.setText("Empty login/pass");
            return;
        }
        if (CMD_LOGIN.equals(cmd)) {
            User user = controller.login(name, pass);
            if (user == null) {
                infoArea.setText("Invalid user/password.");
            } else {
                showAccounts(user);
            }
        } else if (CMD_REG.equals(cmd)) {
            System.out.println("Register");
            User user = controller.registerNewUser(name, pass);
            if (user == null) {
                infoArea.setText("User: " + name + " already exist");
            } else {
                showAccounts(user);
            }
        }
        passField.setText("");
        loginField.setText("");

    }

    private void showAccounts(User user) {
        controller.setCurrentUser(user);
        setVisible(false);
        JFrame general = new GeneralFrame(controller);
        general.setVisible(true);
        parent.setVisible(false);

    }
}

