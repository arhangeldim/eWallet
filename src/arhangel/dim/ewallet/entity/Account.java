package arhangel.dim.ewallet.entity;

import java.math.BigDecimal;

/**
 *
 */
public class Account {
    private int id;
    private String description;
    private BigDecimal amount;

    public Account() {
        amount = new BigDecimal(0);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                '}';
    }
}