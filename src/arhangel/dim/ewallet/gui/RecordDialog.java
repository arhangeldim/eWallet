package arhangel.dim.ewallet.gui;

import arhangel.dim.ewallet.Controller;
import arhangel.dim.ewallet.entity.Record;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;

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

    private Record record;


    public RecordDialog(JFrame frame, boolean modal, Controller controller) {
        super(frame, modal);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        addWindowListener(new WinListener());

        this.controller = controller;

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
        fieldPane.setLayout(new BoxLayout(fieldPane, BoxLayout.Y_AXIS));
        sumField = new JTextField();
        descriptionField = new JTextField();
        sumField.setColumns(20);
        descriptionField.setColumns(20);
        fieldPane.add(sumField);
        fieldPane.add(descriptionField);

        JPanel labelPane = new JPanel();
        labelPane.setLayout(new BoxLayout(labelPane, BoxLayout.Y_AXIS));
        JTextArea sumLabel = new JTextArea("Sum:");
        JTextArea descLabel = new JTextArea("Description:");
        labelPane.add(sumLabel);
        labelPane.add(descLabel);

        JPanel editPane = new JPanel();
        editPane.add(labelPane);
        editPane.add(fieldPane);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(editPane);
        getContentPane().add(buttonPane);

        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (CMD_OK.equals(cmd)) {

            if (sumField.getText().isEmpty()) {
                return;
            } else {
                record = new Record();
                record.setSum(new BigDecimal(sumField.getText()));
                record.setDescription(descriptionField.getText());
            }
        }
        RecordDialog.this.dispose();
    }

    class WinListener extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            dispose();
        }
    }
}
