package arhangel.dim.ewallet;

import arhangel.dim.ewallet.entity.Record;
import arhangel.dim.ewallet.gui.RecordDialog;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 */
public class RecordsPanel extends JPanel implements ActionListener, ListSelectionListener {

    private static final String CMD_ADD = "cmd_add";

    private JFrame parent;
    private Controller controller;
    private JList recordsList;
    private JButton addRecordButton;
    private DefaultListModel<Record> recordsListModel;


    public RecordsPanel(JFrame parent, Controller controller) {
        this.controller = controller;

        recordsListModel = new DefaultListModel<>();
        recordsList = new JList();
        recordsList.setModel(recordsListModel);
        recordsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        recordsList.setLayoutOrientation(JList.VERTICAL);
        recordsList.addListSelectionListener(this);

        addRecordButton = new JButton("Add");
        addRecordButton.addActionListener(this);
        addRecordButton.setActionCommand(CMD_ADD);

        JScrollPane scrollPane = new JScrollPane(recordsList);

        add(scrollPane);
        add(addRecordButton);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (CMD_ADD.equals(cmd)) {
            JDialog dialog = new RecordDialog(parent, true, null);


        }
     }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
