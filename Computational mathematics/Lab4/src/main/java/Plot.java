import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.LegendTitle;
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
}
