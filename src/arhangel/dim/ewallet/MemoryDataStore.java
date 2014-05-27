package arhangel.dim.ewallet;

import arhangel.dim.ewallet.entity.Account;
import arhangel.dim.ewallet.entity.Record;
import arhangel.dim.ewallet.entity.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class MemoryDataStore implements DataStore {

    private Map<String, User> users = new HashMap<>();
    private Map<User, Set<Account>> accounts = new HashMap<>();
    private Map<Account, Set<Record>> records = new HashMap<>();

    @Override
    public User getUser(String name) {
        return users.get(name);
    }

    @Override
    public Set<String> getUserNames() {
        return users.keySet();
    }

    @Override
    public Set<Account> getAccounts(User owner) {
        if (accounts.containsKey(owner)) {
            return accounts.get(owner);
        } else {
            return new HashSet<>();
        }
    }

    @Override
    public Set<Record> getRecords(Account account) {
        if (records.containsKey(account)) {
            return records.get(account);
        } else {
            return new HashSet<>();
        }
    }

    @Override
    public void addUser(User user) {
        users.put(user.getName(), user);
    }

    @Override
    public void addAccount(User user, Account account) {
        Set<Account> userAccounts;
        if (!accounts.containsKey(user)) {
            userAccounts = new HashSet<>();
            userAccounts.add(account);
            accounts.put(user, userAccounts);
        } else {
            userAccounts = accounts.get(user);
            userAccounts.add(account);
        }
    }

    @Override
    public void addRecord(Account account, Record record) {
        Set<Record> accountRecords;
        if (!records.containsKey(account)) {
            accountRecords = new HashSet<>();
            records.put(account, accountRecords);
        } else {
            accountRecords = records.get(account);
        }
        accountRecords.add(record);

    }

}