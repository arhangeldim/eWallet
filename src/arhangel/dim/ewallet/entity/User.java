package arhangel.dim.ewallet.entity;

/**
 *
 */
public class User {
    private String name;
    private char[] passHash;

    public User() {

    }

    public User(String name, char[] passHash) {
        this.name = name;
        this.passHash = passHash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char[] getPassHash() {
        return passHash;
    }

    public void setPassHash(char[] passHash) {
        this.passHash = passHash;
    }

    @Override
    public String toString() {
        return "User: [" + name + ", " + passHash + "];";
    }

}