package arhangel.dim.ewallet.gui;

import arhangel.dim.ewallet.Controller;
import arhangel.dim.ewallet.entity.Account;
import arhangel.dim.ewallet.entity.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

/**
 *
 */
public class GeneralFrame extends JFrame implements ActionListener, ListSelectionListener {

    private static final Logger logger = LoggerFactory.getLogger(GeneralFrame.class);

    private static final String CMD_ADD_ACCOUNT = "cmd_add_account";
    private static final String CMD_DELETE_ACCOUNT = "cmd_delete_account";
    private static final String CMD_EDIT_ACCOUNT = "cmd_edit_account";
    private static final String CMD_ADD_RECORD = "cmd_add_record";

    private Controller controller;
    private JList<Record> recordsList;
    private JComboBox<Account> accountBox;
    private JButton addAccountButton;
    private JButton addRecordButton;
    private DefaultListModel<Record> recordsListModel;

    public GeneralFrame(Controller controller) {
        super("eWallet");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(600, 400));
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
        this.controller = controller;

        /* Init account selector */
        DefaultComboBoxModel<Account> boxModel = new DefaultComboBoxModel<>();
        for (Account c : controller.getAccounts(controller.getCurrentUser())) {
            boxModel.addElement(c);
        }
        accountBox = new JComboBox<>(boxModel);

        /* Records list */
        recordsListModel = new DefaultListModel<>();
        recordsList = new JList<>();
        recordsList.setCellRenderer(new RecordCellRenderer());
        recordsList.setModel(recordsListModel);
        recordsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        recordsList.setLayoutOrientation(JList.VERTICAL);
        recordsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //
            }
        });

        addRecordButton = new JButton("Add");
        addRecordButton.addActionListener(this);
        addRecordButton.setActionCommand(CMD_ADD_RECORD);
        JScrollPane recordScrollPane = new JScrollPane(recordsList);

        /* Set selected account */
        Account selectedAcc;
        if ((selectedAcc = boxModel.getElementAt(0)) != null) {
            controller.setCurrentAccount(selectedAcc);
            accountBox.setSelectedIndex(0);
            showRecords(selectedAcc);
        }

        add(accountBox, BorderLayout.WEST);
        add(recordScrollPane, BorderLayout.CENTER);
        add(addRecordButton, BorderLayout.SOUTH);

        setJMenuBar(createMenu());

        pack();
        setVisible(true);

    }

    private JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu accountMenu = new JMenu("Account");

        JMenuItem createAccount = new JMenuItem("Create");
        createAccount.setActionCommand(CMD_ADD_ACCOUNT);
        createAccount.addActionListener(this);
        accountMenu.add(createAccount);

        JMenuItem editAccount = new JMenuItem("Edit");
        editAccount.setActionCommand(CMD_EDIT_ACCOUNT);
        editAccount.addActionListener(this);
        accountMenu.add(editAccount);

        JMenuItem deleteAccount = new JMenuItem("Delete");
        deleteAccount.setActionCommand(CMD_DELETE_ACCOUNT);
        deleteAccount.addActionListener(this);
        accountMenu.add(deleteAccount);

        menuBar.add(accountMenu);
        return menuBar;
    }

    private void showRecords(Account account) {
        recordsListModel.clear();
        Set<Record> records = controller.getRecords(account);
        for (Record r : records) {
            recordsListModel.addElement(r);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        switch (cmd) {
            case CMD_ADD_RECORD:
                RecordDialog dialog = new RecordDialog(controller);
                dialog.setModal(true);
                dialog.setVisible(true);
                showRecords(controller.getCurrentAccount());
                break;
            case CMD_EDIT_ACCOUNT:
            case CMD_DELETE_ACCOUNT:
            case CMD_ADD_ACCOUNT:
                logger.info("Command: " + cmd);
                break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            Account account = (Account) accountBox.getSelectedItem();
            controller.setCurrentAccount(account);
            showRecords(account);
        }
    }

    public static void main(String[] args) {
        Controller c = new Controller();
        c.login("Bob", "321");
        GeneralFrame frame = new GeneralFrame(c);
        frame.setVisible(true);

    }

}
