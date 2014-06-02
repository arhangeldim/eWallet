package arhangel.dim.ewallet;

import arhangel.dim.ewallet.gui.LoginFrame;

import javax.swing.*;

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

        Controller controller = new Controller();
        JFrame frame = new LoginFrame(controller);
        frame.setVisible(true);
    }
}
