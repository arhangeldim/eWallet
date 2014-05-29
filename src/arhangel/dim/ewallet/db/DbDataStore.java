package arhangel.dim.ewallet.db;

import arhangel.dim.ewallet.DataStore;
import arhangel.dim.ewallet.entity.Account;
import arhangel.dim.ewallet.entity.Record;
import arhangel.dim.ewallet.entity.User;
import com.sun.istack.internal.logging.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 */
public class DbDataStore implements DataStore {

    private static Logger logger = Logger.getLogger(DbDataStore.class);

    public static void main(String[] args) {
        DbDataStore ds = new DbDataStore();
        User user = ds.getUser("Alisa");
        System.out.println(ds.getUserNames());
        Set<Account> accs = ds.getAccounts(user);
        Iterator<Account> accIter = accs.iterator();
        while (accIter.hasNext()) {
            Account a = accIter.next();
            System.out.println(a);
            Set<Record> recs = ds.getRecords(a);
            System.out.println(recs);
        }


        System.out.println("list" + ds.getUserNames());
    }

    DbHelper dbHelper = new DbHelper();

    @Override
    public User getUser(String name) {
        User user = null;
        try {
            PreparedStatement pStmt = dbHelper.getConn().prepareStatement("SELECT * FROM USERS WHERE NAME=?");
            pStmt.setString(1, name);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                String userName = rs.getString(1);
                String pass = rs.getString(2);
                user = new User();
                user.setName(userName);
                user.setPass(pass);
                System.out.println("USER: " + user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.info("getUser(): " + user);
        return user;
    }

    @Override
    public Set<String> getUserNames() {
        Set<String> names = new HashSet<>();

        try {
            Statement stmt = dbHelper.getConn().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT NAME FROM USERS;");
            while (rs.next()) {
                String name = rs.getString(1);
                names.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }

    @Override
    public Set<Account> getAccounts(User owner) {
        Set<Account> accounts = new HashSet<>();
        try {
            PreparedStatement pStmt = dbHelper.getConn().prepareStatement("SELECT * FROM ACCOUNTS WHERE USER_NAME=?");
            pStmt.setString(1, owner.getName());
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                Account acc = new Account();
                acc.setId(rs.getInt(rs.findColumn("ID")));
                acc.setDescription(rs.getString(rs.findColumn("DESCR")));
                accounts.add(acc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Set<Record> getRecords(Account account) {
        Set<Record> records = new HashSet<>();

        try {
            PreparedStatement pStmt = dbHelper.getConn().prepareStatement("SELECT * FROM RECORDS WHERE ACCOUNT_ID=?");
            pStmt.setInt(1, account.getId());
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                Record record = new Record();
                record.setDescription(rs.getString(rs.findColumn("DESCR")));
                record.setSum(new BigDecimal(rs.getFloat(rs.findColumn("AMOUNT"))));
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    @Override
    public void addUser(User user) {
        try {
            PreparedStatement pStmt = dbHelper.getConn().prepareStatement("INSERT INTO USERS (NAME, PASSWORD) VALUES (?, ?);");
            pStmt.setString(1, user.getName());
            pStmt.setString(2, user.getPass());
            int result = pStmt.executeUpdate();
            logger.info("Add user " + user + ": " + result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addAccount(User user, Account account) {
        try {
            PreparedStatement pStmt = dbHelper.getConn().prepareStatement("INSERT INTO ACCOUNTS (DESCR, USER_NAME) VALUES (?, ?);");
            pStmt.setString(1, account.getDescription());
            pStmt.setString(2, user.getName());
            int result = pStmt.executeUpdate();
            logger.info("Add account " + account + ": " + result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addRecord(Account account, Record record) {
        try {
            PreparedStatement pStmt = dbHelper.getConn().prepareStatement("INSERT INTO RECORDS (DESCR, AMOUNT, ACCOUNT_ID) VALUES (?, ?, ?);");
            pStmt.setString(1, record.getDescription());
            pStmt.setFloat(2, record.getSum().floatValue());
            pStmt.setInt(3, account.getId());
            int result = pStmt.executeUpdate();
            logger.info("Add record " + account + ": " + result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
