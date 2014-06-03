package arhangel.dim.ewallet.plot;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public interface PlotModel {
    public double[] getXData();

    public double[] getYData();

    public String[] getXLabels();

    public Color[] getColors();


}
