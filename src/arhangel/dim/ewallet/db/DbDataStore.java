package arhangel.dim.ewallet.db;

import arhangel.dim.ewallet.DataStore;
import arhangel.dim.ewallet.entity.Account;
import arhangel.dim.ewallet.entity.Record;
import arhangel.dim.ewallet.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 *
 */
public class DbDataStore implements DataStore {

    public static void main(String[] args) {
        DbDataStore ds = new DbDataStore();
        ds.getUser("Alisa");
    }


    DbHelper dbHelper = new DbHelper();


    @Override
    public User getUser(String name) {
        User user = null;
        try {
            PreparedStatement stmt = dbHelper.getConn().prepareStatement("SELECT * FROM USERS WHERE NAME=?");
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String userName = rs.getString(1);
                char[] pass = rs.getString(2).toCharArray();
                user = new User();
                user.setName(userName);
                user.setPassHash(pass);
                System.out.println("USER: " + user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public Set<String> getUserNames() {
        return null;
    }

    @Override
    public Set<Account> getAccounts(User owner) {
        return null;
    }

    @Override
    public Set<Record> getRecords(Account account) {
        return null;
    }

    @Override
    public void addUser(User user) {

    }

    @Override
    public void addAccount(User user, Account account) {

    }

    @Override
    public void addRecord(Account account, Record record) {

    }
}
