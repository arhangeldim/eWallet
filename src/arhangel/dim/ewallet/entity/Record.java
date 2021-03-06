package arhangel.dim.ewallet.entity;

import arhangel.dim.ewallet.gui.Category;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 */
public class Record {
    private String description;
    private BigDecimal sum;
    private Date date;
    private boolean isPut;
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isPut() {
        return isPut;
    }

    public void setPut(boolean put) {
        isPut = put;
    }

    @Override
    public String toString() {
        return "Record{" +
                "description='" + description + '\'' +
                ", sum=" + sum +
                ", isPut=" + isPut +
                ", category=" + category +
                '}';
    }
}

