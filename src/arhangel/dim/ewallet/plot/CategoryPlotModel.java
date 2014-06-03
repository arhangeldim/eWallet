package arhangel.dim.ewallet.plot;

import arhangel.dim.ewallet.gui.Category;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class CategoryPlotModel implements PlotModel {

    private Map<Category, BigDecimal> data;
    private Color[] colors;
    private String[] labels;

    public CategoryPlotModel(Map<Category, BigDecimal> data) {
        this.data = data;
        labels = new String[data.size()];
        colors = new Color[data.size()];
        int i = 0;
        for (Category c : data.keySet()) {
            colors[i] = c.getColor();
            labels[i] = c.getName();
            i++;
        }
    }

    @Override
    public double[] getXData() {
        return null;
    }

    @Override
    public double[] getYData() {
        double[] vals = new double[data.size()];
        int i = 0;
        for (BigDecimal bd : data.values()) {
            vals[i++] = bd.doubleValue();
        }
        return vals;
    }

    @Override
    public String[] getXLabels() {
        return labels;
    }

    @Override
    public Color[] getColors() {
        return colors;
    }
}
