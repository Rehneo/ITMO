package plot;
import function.MathFunction;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Plot extends ApplicationFrame {

    public static final double DEFAULT_STEP = 0.05;

    public Plot(String title) {
        super(title);
    }

    public void draw(double a, double b, MathFunction... functions){
        XYDataset dataset = generateDataset(a, b, functions);
        generatePlot(dataset);
    }

    private void generatePlot(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Graph",
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );
        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(false);
        panel.setDomainZoomable(true);
        panel.setRangeZoomable(true);
        pack();
        setSize(800, 600);
        setLocationRelativeTo(null);
        setContentPane(panel);
        setVisible(true);
    }



    private XYDataset generateDataset(double from, double to, MathFunction... functions) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        for (MathFunction f: functions) {
            XYSeries series = new XYSeries(f.hashCode());
                for (double x = from; x < to + Plot.DEFAULT_STEP; x += Plot.DEFAULT_STEP) {
                    series.add(x, f.at(x));
                }
            dataset.addSeries(series);
        }
        return dataset;
    }


    public void systemFirst(double from, double to){
        MathFunction f1 = new MathFunction(x -> Math.cbrt(6*x - x*x*x - 3));
        MathFunction f2 = new MathFunction(y -> Math.cbrt(y*y*y + 6*y - 2));
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries(f1.hashCode());
        for (double x = from; x < to + Plot.DEFAULT_STEP; x += Plot.DEFAULT_STEP) {
            series1.add(x, f1.at(x));
        }
        dataset.addSeries(series1);
        XYSeries series2 = new XYSeries(f2.hashCode());
        for (double x = from; x < to + Plot.DEFAULT_STEP; x += Plot.DEFAULT_STEP) {
            series2.add(f2.at(x), x);
        }
        dataset.addSeries(series2);
        generatePlot(dataset);
    }

    //"x^3 + y^3 - 6x + 3 = 0\nx^3 - y^3 - 6y + 2 = 0"
    public void systemSecond(double from, double to) {
        MathFunction f1 = new MathFunction(x -> x*x - 1);
        MathFunction f2 = new MathFunction(x ->x*x*x);
        draw(from, to, f1, f2);
    }

}
