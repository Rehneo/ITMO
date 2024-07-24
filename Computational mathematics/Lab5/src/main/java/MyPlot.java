import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MyPlot {
    private final Plot plot;

    public MyPlot() {
        this.plot = Plot.create();
    }

    public void draw(double fromX, double toX, double fromY, double toY, double[][]... functionTables) throws PythonExecutionException, IOException {
        plot.xlim(fromX, toX);
        plot.ylim(fromY, toY);
        for (double[][] functionTable: functionTables) {
            List<Double> x = Arrays.stream(functionTable).mapToDouble(doubles -> doubles[0])
                    .boxed().toList();
            List<Double> y = Arrays.stream(functionTable).mapToDouble(doubles -> doubles[1])
                    .boxed().toList();
            plot.plot().add(x, y);
        }
        plot.show();
    }
}
