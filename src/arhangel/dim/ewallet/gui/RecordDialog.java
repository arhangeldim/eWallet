package arhangel.dim.ewallet.gui;

import arhangel.dim.ewallet.Controller;
import arhangel.dim.ewallet.entity.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

/**
 *
 */
public class RecordDialog extends JDialog implements ActionListener {
    private static Logger logger = LoggerFactory.getLogger(RecordDialog.class);

    private static final String CMD_PUT_ACTION = "cmd_put";
    private static final String CMD_CALL_ACTION = "cmd_call";
    private static final String CMD_CANCEL_ACTION = "cmd_cancel";
    private static final String CMD_SAVE_ACTION = "cmd_save";

    private Controller controller;
    private boolean isPut = false;
    private JTextArea descrArea;
    private JTextField sumField;
    private JComboBox<Category> categoryBox;

    public RecordDialog(Controller controller) {
        super();
        this.controller = controller;
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        setSize(300, 400);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);

            /* Create radio button group */
        JRadioButton putButton = new JRadioButton("Put");
        JRadioButton callButton = new JRadioButton("Call");
        putButton.setActionCommand(CMD_PUT_ACTION);
        callButton.setActionCommand(CMD_CALL_ACTION);
        putButton.addActionListener(this);
        callButton.addActionListener(this);
        ButtonGroup putCallGroup = new ButtonGroup();
        putCallGroup.add(putButton);
        putCallGroup.add(callButton);
        callButton.setSelected(true);

        descrArea = new JTextArea(5, 10);
        descrArea.setLineWrap(true);

        sumField = new JTextField(10);

            /* Create list of category */
        DefaultComboBoxModel<Category> boxModel = new DefaultComboBoxModel<>();
        for (Category c : controller.getCategories()) {
            boxModel.addElement(c);
        }
        categoryBox = new JComboBox<>(boxModel);
        categoryBox.setSelectedIndex(0);

            /* Save/Cancel buttons */
        final JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand(CMD_CANCEL_ACTION);
        cancelButton.addActionListener(this);
        final JButton saveButton = new JButton("Save");
        saveButton.setActionCommand(CMD_SAVE_ACTION);
        saveButton.addActionListener(this);

            /* Layout management */
        Insets insets5 = new Insets(5, 5, 5, 5);

        add(new JLabel("New Record"), new GridBagConstraints(0, 0, 3, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NORTH, insets5, 0, 0));

        add(callButton, new GridBagConstraints(0, 1, 1, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 5), 0, 0));
        add(putButton, new GridBagConstraints(0, 2, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 5), 0, 0));

        add(new JLabel("Sum"), new GridBagConstraints(0, 3, 1, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.WEST, insets5, 0, 0));
        add(sumField, new GridBagConstraints(1, 3, 2, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets5, 0, 0));

        add(new JLabel("Description"), new GridBagConstraints(0, 4, 1, 1, 0, 0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, insets5, 0, 0));
        add(descrArea, new GridBagConstraints(1, 4, 2, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets5, 0, 0));

        add(new JLabel("Category"), new GridBagConstraints(0, 5, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets5, 0, 0));
        add(categoryBox, new GridBagConstraints(1, 5, 2, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets5, 0, 0));

        add(cancelButton, new GridBagConstraints(1, 6, 1, 1, 0, 0,
                GridBagConstraints.LINE_END, GridBagConstraints.NONE, insets5, 0, 0));
        add(saveButton, new GridBagConstraints(2, 6, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, insets5, 0, 0));

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        logger.info("pressed: {}", e.getActionCommand());
        String cmd = e.getActionCommand();
        switch (cmd) {
            case CMD_CANCEL_ACTION:
                dispose();
                break;
            case CMD_SAVE_ACTION:
                logger.debug("isPut: {}", isPut);
                Record record = new Record();
                String sum = sumField.getText();
                Category category = (Category) categoryBox.getSelectedItem();
                record.setDescription(descrArea.getText());
                record.setPut(isPut);
                record.setCategory(category);
                record.setSum(new BigDecimal(sum));
                controller.addRecord(controller.getCurrentAccount(), record);
                dispose();
                break;
            case CMD_PUT_ACTION:
                isPut = true;
                break;
            case CMD_CALL_ACTION:
                isPut = false;
                break;

        }
    }
}
