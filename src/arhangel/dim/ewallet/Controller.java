package arhangel.dim.ewallet;

import arhangel.dim.ewallet.db.DbDataStore;
import arhangel.dim.ewallet.entity.Account;
import arhangel.dim.ewallet.entity.Record;
import arhangel.dim.ewallet.entity.User;
import arhangel.dim.ewallet.gui.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 *
 */
public class Controller {

    private static Logger logger = LoggerFactory.getLogger(Controller.class);

    private DataStore dataStore;
    private User currentUser;
    private Account currentAccount;

    public Controller() {
        dataStore = new DbDataStore();
    }

    private boolean isUserRegistered(String userName) {
        return dataStore.getUser(userName) != null;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(Account currentAccount) {
        this.currentAccount = currentAccount;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User login(String name, String pass) {
        logger.info("Logon: " + name);
        if (isUserRegistered(name)) {
            User user = getUserByName(name);
            if (isPasswordCorrect(user, pass)) {
                return user;
            }
        }
        return null;
    }

    public Set<Category> getCategories() {
        return dataStore.getCategories();
    }

    public void addAccount(User owner, Account account) {
        dataStore.addAccount(owner, account);
    }

    public Set<Account> getAccounts(User owner) {
        return dataStore.getAccounts(owner);
    }

    public Set<Record> getRecords(Account account) {
        return dataStore.getRecords(account);
    }

    public void addRecord(Account account, Record record) {
        dataStore.addRecord(account, record);
    }

    public User registerNewUser(String userName, String pass) {
        if (!isUserRegistered(userName)) {
            User user = new User(userName, pass);
            dataStore.addUser(user);
            return user;
        }
        return null;
    }

    private boolean isPasswordCorrect(User user, String pass) {
        return user.getPass().equals(pass);
    }

    private User getUserByName(String userName) {
        return dataStore.getUser(userName);
    }

}