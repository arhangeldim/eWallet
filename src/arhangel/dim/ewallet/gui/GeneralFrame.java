package arhangel.dim.ewallet.gui;

import arhangel.dim.ewallet.Controller;
import arhangel.dim.ewallet.entity.Account;
import arhangel.dim.ewallet.entity.Record;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 *
 */
public class GeneralFrame extends JFrame implements ActionListener, ListSelectionListener {

    private static final String CMD_ADD_ACCOUNT = "cmd_add_account";
    private static final String CMD_ADD_RECORD = "cmd_add_record";

    private Controller controller;
    final private JList<Account> accountsList;
    private JList<Record> recordsList;
    private JButton addAccountButton;
    private JButton addRecordButton;
    private DefaultListModel<Record> recordsListModel;
    private DefaultListModel<Account> accountListModel;

    public GeneralFrame(Controller controller) {
        super("eWallet");
        this.controller = controller;
        setBounds(100, 100, 200, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(new Dimension(400, 300));

        // Accounts panel
        JPanel accountsPanel = new JPanel();
        accountsPanel.setLayout(new BoxLayout(accountsPanel, BoxLayout.Y_AXIS));
        accountListModel = new DefaultListModel<>();

        Set<Account> accounts = controller.getAccounts(controller.getCurrentUser());
        for (Account account : accounts) {
            accountListModel.addElement(account);
        }
        accountsList = new JList<>();
        accountsList.setModel(accountListModel);
        accountsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        accountsList.setLayoutOrientation(JList.VERTICAL);
        accountsList.addListSelectionListener(this);


        addAccountButton = new JButton("Add new account");
        addAccountButton.addActionListener(this);
        addAccountButton.setActionCommand(CMD_ADD_ACCOUNT);
        JScrollPane accountScrollPane = new JScrollPane(accountsList);
        accountsPanel.add(accountScrollPane);
        accountsPanel.add(addAccountButton);

        // Records panel
        JPanel recordsPanel = new JPanel();
        recordsPanel.setLayout(new BoxLayout(recordsPanel, BoxLayout.Y_AXIS));
        recordsListModel = new DefaultListModel<>();
        recordsList = new JList<>();
        recordsList.setModel(recordsListModel);
        recordsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        recordsList.setLayoutOrientation(JList.VERTICAL);
        recordsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //
            }
        });

        if (!accountListModel.isEmpty()) {
            Iterator<Account> iter = accounts.iterator();
            if (iter.hasNext()) {
                showRecords(iter.next());
            }
        }

        addRecordButton = new JButton("Add");
        addRecordButton.addActionListener(this);
        addRecordButton.setActionCommand(CMD_ADD_RECORD);
        JScrollPane recordScrollPane = new JScrollPane(recordsList);
        recordsPanel.add(recordScrollPane);
        recordsPanel.add(addRecordButton);

        getContentPane().add(accountsPanel, BorderLayout.WEST);
        getContentPane().add(recordsPanel, BorderLayout.EAST);

        pack();
        setVisible(true);

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
        if (CMD_ADD_ACCOUNT.equals(cmd)) {
            int i = new Random().nextInt();
            Account account = new Account();
            account.setDescription("acc" + i);
            accountListModel.addElement(account);
        } else if (CMD_ADD_RECORD.equals(cmd)) {
            RecordDialog dialog = new RecordDialog(controller);
            dialog.setModal(true);
            dialog.setVisible(true);
            showRecords(controller.getCurrentAccount());
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            Account account = accountsList.getSelectedValue();
            controller.setCurrentAccount(account);
            showRecords(account);
        }
    }


}
