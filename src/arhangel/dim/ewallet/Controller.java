package arhangel.dim.ewallet;

import arhangel.dim.ewallet.entity.Account;
import arhangel.dim.ewallet.entity.Record;
import arhangel.dim.ewallet.entity.User;

import java.util.Arrays;
import java.util.Set;

/**
 *
 */
public class Controller {

    private DataStore dataStore;
    private User currentUser;

    public Controller() {
        dataStore = new MemoryDataStore();
    }

    private boolean isUserRegistered(String userName) {
        return dataStore.getUser(userName) != null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User login(String name, char[] pass) {
        if (isUserRegistered(name)) {
            User user = getUserByName(name);
            if (isPasswordCorrect(user, pass)) {
                return user;
            }
        }
        return null;
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

    public User registerNewUser(String userName, char[] pass) {
        if (!isUserRegistered(userName)) {
            User user = new User(userName, pass);
            dataStore.addUser(user);
            return user;
        }
        return null;
    }

    private boolean isPasswordCorrect(User user, char[] pass) {
        return (user.getPassHash().length == pass.length) && Arrays.equals(user.getPassHash(), pass);
    }

    private User getUserByName(String userName) {
        return dataStore.getUser(userName);
    }

}