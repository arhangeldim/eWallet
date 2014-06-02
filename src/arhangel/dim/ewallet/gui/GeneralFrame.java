package arhangel.dim.ewallet.gui;

import arhangel.dim.ewallet.Controller;
import arhangel.dim.ewallet.entity.Account;
import arhangel.dim.ewallet.entity.Record;
import arhangel.dim.ewallet.plot.PieChartPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
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
    private JButton addRecordButton;
    private JLabel summaryLabel;
    private JLabel usernameLabel;
    private DefaultListModel<Record> recordsListModel;

    public GeneralFrame(Controller controller) {
        super("eWallet");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(800, 600));
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
        recordsList.setVisibleRowCount(5);
        recordsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        recordsList.setLayoutOrientation(JList.VERTICAL);
        recordsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //
            }
        });

        addRecordButton = new JButton("Add record");
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

        // NOTE: account should be selected
        JPanel headerPanel = createHeader(selectedAcc);

        /* Chart */
        PieChartPanel chart = new PieChartPanel<>();
        Map<Category, BigDecimal> values = controller.getSumByCategories(selectedAcc);
        logger.info("Category values: {}", values);
        BigDecimal[] chartData = new BigDecimal[values.size()];
        int i = 0;
        for (BigDecimal bd : values.values()) {
            chartData[i] = bd;
            i++;
        }
        chart.setValues(chartData);
        //chart.setBackground(Color.BLUE);
        chart.repaint();
        chart.setMinimumSize(new Dimension(200, 200));
        chart.setPreferredSize(new Dimension(200, 200));


        /* Layout management */

        Insets insets5 = new Insets(5, 5, 5, 5);
        setLayout(new GridBagLayout());
        add(headerPanel, new GridBagConstraints(0, 0, 3, 1, 0, 0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, insets5, 0, 0));
        add(accountBox, new GridBagConstraints(0, 1, 1, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, insets5, 0, 0));
        add(recordScrollPane, new GridBagConstraints(0, 2, 1, 3, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, insets5, 0, 0));
        add(addRecordButton, new GridBagConstraints(1, 1, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, insets5, 0, 0));
        add(chart, new GridBagConstraints(1, 2, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, insets5, 0, 0));

        setJMenuBar(createMenu());

        pack();
        setVisible(true);

    }

    private void updateSummary() {
        BigDecimal sum = controller.getSummary(controller.getCurrentAccount(), null);
        summaryLabel.setText(sum + " rub");
        Font font = new Font("Arial", Font.BOLD, 24);
        summaryLabel.setFont(font);
        if (sum.signum() > 0) {
            summaryLabel.setForeground(Color.GREEN);
        } else {
            summaryLabel.setForeground(Color.RED);
        }
    }

    private JPanel createHeader(Account account) {
        JPanel header = new JPanel();
        header.setBorder(BorderFactory.createLineBorder(Color.black));
        Font font = new Font("Arial", Font.BOLD, 24);

        summaryLabel = new JLabel();
        if (account != null) {
            updateSummary();
        }

        usernameLabel = new JLabel(controller.getCurrentUser().getName());
        usernameLabel.setFont(font);
        usernameLabel.setForeground(Color.DARK_GRAY);
        JLabel logoLabel = new JLabel();
        try {
            BufferedImage callImage = ImageIO.read(this.getClass().getResource("wallet.png"));
            ImageIcon logo = new ImageIcon(callImage);
            logoLabel.setIcon(logo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        header.setLayout(new GridBagLayout());
        header.add(logoLabel, new GridBagConstraints(0, 0, 1, 3, 0, 3,
                GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        header.add(usernameLabel, new GridBagConstraints(1, 1, 1, 1, 1, 2,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        header.add(summaryLabel, new GridBagConstraints(1, 2, 1, 1, 1, 2,
                GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        return header;
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
                updateSummary();
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
