package arhangel.dim.ewallet.plot;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

/**
 *
 */
public class PieChartPanel<X, Y> extends JPanel {

    private PlotModel<X, Y> model;

    private BigDecimal[] values;
    private Color[] colors = {Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.DARK_GRAY, Color.CYAN, Color.GREEN, Color.YELLOW};

    public PieChartPanel() {
        super();
    }


    public void setValues(BigDecimal[] values) {
        this.values = values;
    }

    public void setColors(Color[] colors) {
        this.colors = colors;
    }

    void drawPie(Graphics2D g, Rectangle area) {
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHints(rh);
        BigDecimal total = new BigDecimal(0);
        for (int i = 0; i < values.length; i++) {
            total = total.add(values[i]);
        }
        double curValue = 0.0D;
        int startAngle = 0;
        for (int i = 0; i < values.length; i++) {
            startAngle = (int) (curValue * 360 / total.doubleValue());
            int arcAngle = (int) (values[i].doubleValue() * 360 / total.doubleValue());
            g.setColor(colors[i]);
            g.fillArc(area.x, area.y, area.width, area.height,
                    startAngle, arcAngle);
            curValue += values[i].doubleValue();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawPie((Graphics2D) g, new Rectangle(0, 0, 200, 200));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(400, 400);
        frame.add(new PieChartPanel<>());
        frame.setVisible(true);
    }


}
