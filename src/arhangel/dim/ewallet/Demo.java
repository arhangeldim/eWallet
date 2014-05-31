package arhangel.dim.ewallet;

import arhangel.dim.ewallet.gui.LoginPanel;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class Demo {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {

        JFrame frame = new JFrame("ButtonDemo");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Controller controller = new Controller();
        LoginPanel loginPanel = new LoginPanel(frame, controller);
        loginPanel.setOpaque(true);

        frame.setSize(new Dimension(400, 300));
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(loginPanel, BorderLayout.WEST);

        //frame.pack();
        frame.setVisible(true);
    }
}
