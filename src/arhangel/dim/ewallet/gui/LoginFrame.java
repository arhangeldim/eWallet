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
public class LoginFrame extends JFrame implements ActionListener {

    private JTextField loginField;
    private JPasswordField passField;
    private JLabel infoLabel;

    private Controller controller;

    private static final String CMD_LOGIN = "cmd_login";
    private static final String CMD_REG = "cmd_reg";

    public LoginFrame(Controller controller) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(300, 200));
        setTitle("eWallet");
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);

        setLayout(new BorderLayout());
        this.controller = controller;

        JPanel fieldPane = new JPanel();
        JPanel buttonPane = new JPanel();
        buttonPane.setPreferredSize(new Dimension(300, 50));

        JButton loginButton = new JButton("Login");
        loginButton.setSize(new Dimension(70, 20));
        JButton regButton = new JButton("Register");
        regButton.setSize(new Dimension(70, 20));
        loginField = new JTextField(10);
        passField = new JPasswordField(10);
        infoLabel = new JLabel("Type user/password");

        loginButton.addActionListener(this);
        loginButton.setActionCommand(CMD_LOGIN);
        regButton.addActionListener(this);
        regButton.setActionCommand(CMD_REG);

        buttonPane.add(regButton);
        buttonPane.add(loginButton);

        fieldPane.setLayout(new BoxLayout(fieldPane, BoxLayout.Y_AXIS));
        fieldPane.add(loginField);
        fieldPane.add(passField);
        fieldPane.add(infoLabel);

        add(fieldPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        String pass = new String(passField.getPassword());
        String name = loginField.getText();

        if (pass.trim().isEmpty() || name.trim().isEmpty()) {
            infoLabel.setText("Empty login/pass");
            return;
        }
        if (CMD_LOGIN.equals(cmd)) {
            User user = controller.login(name, pass);
            if (user == null) {
                infoLabel.setText("Invalid user/password.");
            } else {
                showAccounts(user);
            }
        } else if (CMD_REG.equals(cmd)) {
            System.out.println("Register");
            User user = controller.registerNewUser(name, pass);
            if (user == null) {
                infoLabel.setText("User: " + name + " already exist");
            } else {
                showAccounts(user);
            }
        }
        passField.setText("");
        loginField.setText("");

    }

    private void showAccounts(User user) {
        setVisible(false);
        JFrame general = new GeneralFrame(controller);
        general.setVisible(true);

    }
}

