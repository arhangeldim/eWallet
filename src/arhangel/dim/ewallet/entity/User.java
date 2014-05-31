package arhangel.dim.ewallet.entity;

/**
 *
 */
public class User {
    private String name;
    private String pass;

    public User() {

    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "User: [" + name + ", " + pass + "];";
    }

}