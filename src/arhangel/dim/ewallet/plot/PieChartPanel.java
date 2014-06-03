package arhangel.dim.ewallet.plot;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

/**
 *
 */
public class PieChartPanel extends JPanel {

    private PlotModel model;


    public PieChartPanel() {
        super();
    }

    public PlotModel getModel() {
        return model;
    }

    public void setModel(PlotModel model) {
        this.model = model;
    }

    void drawChart(Graphics2D g, Rectangle area) {
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHints(rh);
        double total = 0.0;
        double[] values = model.getYData();
        for (int i = 0; i < values.length; i++) {
            total += values[i];
        }

        double curValue = 0.0D;
        int startAngle = 0;
        for (int i = 0; i < values.length; i++) {
            startAngle = (int) (curValue * 360 / total);
            int arcAngle = (int) (values[i] * 360 / total);
            g.setColor(model.getColors()[i]);
            g.fillArc(area.x, area.y, area.width, area.height,
                    startAngle, arcAngle);
            curValue += values[i];
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawChart((Graphics2D) g, new Rectangle(0, 0, 200, 200));
    }
}
