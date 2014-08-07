package arhangel.dim.ewallet.db;

import arhangel.dim.ewallet.DataStore;
import arhangel.dim.ewallet.entity.Account;
import arhangel.dim.ewallet.entity.Record;
import arhangel.dim.ewallet.entity.User;
import arhangel.dim.ewallet.gui.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class DbDataStore implements DataStore {

    private static Logger logger = LoggerFactory.getLogger(DbDataStore.class);

    DbHelper dbHelper = DbHelper.getInstance();

    @Override
    public Set<Category> getCategories() {
        Set<Category> categories = new HashSet<>();

        // Close it manually
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = dbHelper.getConn().createStatement();
            rs = stmt.executeQuery("SELECT * FROM CATEGORIES;");
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                Category cat = new Category();
                cat.setId(id);
                cat.setName(name);
                categories.add(cat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHelper.closeResource(rs);
            DbHelper.closeResource(stmt);
        }
        logger.debug("categories: {}", categories);
        return categories;
    }

    @Override
    public User getUser(String name) {
        User user = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        try {
            pStmt = dbHelper.getConn().prepareStatement("SELECT * FROM USERS WHERE NAME=?");
            pStmt.setString(1, name);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                String userName = rs.getString(1);
                String pass = rs.getString(2);
                user = new User();
                user.setName(userName);
                user.setPass(pass);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHelper.closeResource(rs);
            DbHelper.closeResource(pStmt);
        }

        logger.debug("getUser(): " + user);
        return user;
    }

    @Override
    public Set<String> getUserNames() {
        Set<String> names = new HashSet<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = dbHelper.getConn().createStatement();
            rs = stmt.executeQuery("SELECT NAME FROM USERS;");
            while (rs.next()) {
                String name = rs.getString(1);
                names.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHelper.closeResource(rs);
            DbHelper.closeResource(stmt);
        }
        logger.debug("Users: {}", names);
        return names;
    }

    @Override
    public Set<Account> getAccounts(User owner) {
        Set<Account> accounts = new HashSet<>();
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        try {
            pStmt = dbHelper.getConn().prepareStatement("SELECT * FROM ACCOUNTS WHERE USER_NAME=?");
            pStmt.setString(1, owner.getName());
            rs = pStmt.executeQuery();
            while (rs.next()) {
                Account acc = new Account();
                acc.setId(rs.getInt(rs.findColumn("ID")));
                acc.setDescription(rs.getString(rs.findColumn("DESCR")));
                accounts.add(acc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHelper.closeResource(rs);
            DbHelper.closeResource(pStmt);
        }
        logger.debug("User: {} -> {}", owner, accounts);
        return accounts;
    }

    @Override
    public Set<Record> getRecords(Account account) {
        Set<Record> records = new HashSet<>();

        PreparedStatement pStmt = null;
        ResultSet rs = null;
        try {
            pStmt = dbHelper.getConn().prepareStatement("SELECT * FROM RECORDS WHERE ACCOUNT_ID=? ");
            pStmt.setInt(1, account.getId());
            rs = pStmt.executeQuery();
            while (rs.next()) {
                Record record = new Record();
                record.setDescription(rs.getString(rs.findColumn("DESCR")));
                record.setSum(new BigDecimal(rs.getFloat(rs.findColumn("AMOUNT"))));
                int categoryId = (rs.getInt(rs.findColumn("CATEGORY_ID")));
                record.setCategory(getCategoryById(categoryId));
                int putField = rs.getInt(rs.findColumn("IS_PUT"));
                record.setPut(putField == 1);
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHelper.closeResource(rs);
            DbHelper.closeResource(pStmt);
        }
        logger.debug("Account: {} -> {}", account, records);
        return records;
    }

    private Category getCategoryById(int id) {
        Category category = null;
        logger.debug("Get category {}", id);
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        try {
            pStmt = dbHelper.getConn().prepareStatement("SELECT * FROM CATEGORIES WHERE ID=?");
            pStmt.setInt(1, id);
            rs = pStmt.executeQuery();
            if (rs.next()) {
                category = new Category();
                String name = rs.getString(rs.findColumn("NAME"));
                category.setName(name);
                category.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHelper.closeResource(rs);
            DbHelper.closeResource(pStmt);
        }
        return category;
    }

    @Override
    public void addUser(User user) {
        logger.debug("Adding new user: {}", user);
        PreparedStatement pStmt = null;
        try {
            pStmt = dbHelper.getConn().prepareStatement("INSERT INTO USERS (NAME, PASSWORD) VALUES (?, ?);");
            pStmt.setString(1, user.getName());
            pStmt.setString(2, user.getPass());
            int result = pStmt.executeUpdate();
            logger.info("Add user " + user + ": " + result);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHelper.closeResource(pStmt);
        }

    }

    @Override
    public void addAccount(User user, Account account) {
        logger.debug("Adding account {} -> {}", user, account);
        PreparedStatement pStmt = null;
        try {
            pStmt = dbHelper.getConn().prepareStatement("INSERT INTO ACCOUNTS (DESCR, USER_NAME) VALUES (?, ?);");
            pStmt.setString(1, account.getDescription());
            pStmt.setString(2, user.getName());
            int result = pStmt.executeUpdate();
            logger.info("Add account " + account + ": " + result);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHelper.closeResource(pStmt);
        }
    }

    @Override
    public void addRecord(Account account, Record record) {
        logger.debug("Adding record {}->{}", account, record);
        PreparedStatement pStmt = null;
        try {
            pStmt = dbHelper.getConn().prepareStatement("INSERT INTO RECORDS (DESCR, AMOUNT, IS_PUT, ACCOUNT_ID, CATEGORY_ID) VALUES (?, ?, ?, ?, ?);");
            pStmt.setString(1, record.getDescription());
            pStmt.setFloat(2, record.getSum().floatValue());
            pStmt.setInt(3, record.isPut() ? 1 : 0);
            pStmt.setInt(4, account.getId());
            pStmt.setInt(5, record.getCategory().getId());
            int result = pStmt.executeUpdate();
            logger.info("Add record " + account + ": " + result);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHelper.closeResource(pStmt);
        }
    }
}
