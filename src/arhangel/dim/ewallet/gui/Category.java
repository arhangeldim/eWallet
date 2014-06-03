package arhangel.dim.ewallet.gui;

import java.awt.*;

/**
 *
 */
public class Category {
    private int id;
    private String name;
    private int colorId;
    private static Color[] colors = {Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.DARK_GRAY, Color.CYAN, Color.PINK, Color.MAGENTA};
    private static int counter = 0;
    public Category() {
        colorId = counter++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public Color getColor() {
        return colors[colorId % colors.length];
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {

        return this.id == ((Category)obj).id;
    }
}
