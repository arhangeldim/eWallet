package arhangel.dim.ewallet.plot;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PlotModel<X, Y> {
    List<Tuple<X, Y>> data;

    public PlotModel(List<X> xData, List<Y> yData) {
        if (xData.size() != yData.size()) {
            throw new IllegalArgumentException("Must have same length");
        }
        data = new ArrayList<>(xData.size());
        for (int i = 0; i < xData.size(); i++) {
            data.add(i, new Tuple<X, Y>(xData.get(i), yData.get(i)));
        }

    }

    public void addTuple(Tuple<X, Y> tuple) {

    }

    public class Tuple<X, Y> {
        X x;
        Y y;

        public Tuple(X x, Y y) {
            this.x = x;
            this.y = y;
        }
    }


}
