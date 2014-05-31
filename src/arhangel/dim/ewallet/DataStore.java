package arhangel.dim.ewallet;

import arhangel.dim.ewallet.entity.Account;
import arhangel.dim.ewallet.entity.Record;
import arhangel.dim.ewallet.entity.User;
import arhangel.dim.ewallet.gui.Category;

import java.util.Set;

/**
 *
 */
public interface DataStore {

    User getUser(String name);

    Set<String> getUserNames();

    Set<Account> getAccounts(User owner);

    Set<Record> getRecords(Account account);

    Set<Category> getCategories();

    void addUser(User user);

    void addAccount(User user, Account account);

    void addRecord(Account account, Record record);

}