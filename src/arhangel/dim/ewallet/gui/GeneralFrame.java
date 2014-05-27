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
import java.util.Random;

/**
 *
 */
public class GeneralFrame extends JFrame implements ActionListener, ListSelectionListener {

    private static final String CMD_ADD_ACCOUNT = "cmd_add_account";
    private static final String CMD_ADD_RECORD = "cmd_add_record";

    private Controller controller;
    private JList<Account> accountsList;
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
        recordsListModel = new DefaultListModel<>();
        recordsList = new JList<>();
        recordsList.setModel(recordsListModel);
        recordsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        recordsList.setLayoutOrientation(JList.VERTICAL);
        recordsList.addListSelectionListener(GeneralFrame.this);
        addRecordButton = new JButton("Add");
        addRecordButton.addActionListener(GeneralFrame.this);
        addRecordButton.setActionCommand(CMD_ADD_RECORD);
        JScrollPane recordScrollPane = new JScrollPane(recordsList);
        recordsPanel.add(recordScrollPane);
        recordsPanel.add(addRecordButton);

        getContentPane().add(accountsPanel, BorderLayout.WEST);
        getContentPane().add(recordsPanel, BorderLayout.CENTER);

        pack();
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (CMD_ADD_ACCOUNT.equals(cmd)) {
            System.out.println("Add new acc");
            int i = new Random().nextInt();
            Account account = new Account();
            account.setDescription("acc" + i);
            accountListModel.addElement(account);
        } else  if (CMD_ADD_RECORD.equals(cmd)) {
            JDialog dialog = new RecordDialog(this, true, null);
            dialog.setVisible(true);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            System.out.println(e.getFirstIndex());
            Account selected = accountListModel.elementAt(e.getFirstIndex());
            System.out.println(selected.toString());
        }
    }


}