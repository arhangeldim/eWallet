package arhangel.dim.ewallet.gui;

import arhangel.dim.ewallet.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 */
public class RecordDialog extends JDialog implements ActionListener {

    private static final String CMD_CANCEL = "cmd_cancel";
    private static final String CMD_OK = "cmd_ok";

    private Controller controller;
    private JPanel panel;
    private JButton cancelButton;
    private JButton okButton;

    private JTextField sumField;
    private JTextField descriptionField;
    private JList<Category> categoryList;

    public RecordDialog(JFrame frame, boolean modal, Controller controller) {
        super(frame, modal);
        this.controller = controller;
        panel = new JPanel();
        getContentPane().add(panel);


        JPanel buttonPane = new JPanel();
        cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand(CMD_CANCEL);
        cancelButton.addActionListener(this);
        okButton = new JButton("Ok");
        okButton.setActionCommand(CMD_OK);
        okButton.addActionListener(this);
        buttonPane.add(okButton);
        buttonPane.add(cancelButton);

        JPanel fieldPane = new JPanel();
        sumField = new JTextField();
        descriptionField = new JTextField();

        panel.add(sumField);
        panel.add(descriptionField);
        panel.add(okButton);
        panel.add(cancelButton);

        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("pressed");
    }
}
