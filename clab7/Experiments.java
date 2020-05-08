import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by hug.
 */
public class Experiments {
    public static void experiment1() {
        List<Double> optimalDepthList = new ArrayList<>();
        List<Double> depthList = new ArrayList<>();
        List<Integer> xValues = new ArrayList<>();
        Random random = new Random();
        BST<Integer> integerBST = new BST<>();
        int randomNum = random.nextInt(10000);
        for (int i = 0; i < 5000; i++) {
            xValues.add(i + 1);
            integerBST.add(randomNum);
            depthList.add(integerBST.calAvgDepth());
            optimalDepthList.add(ExperimentHelper.optimalAverageDepth(i + 1));
            randomNum = random.nextInt(10000);
        }
        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("x label").yAxisTitle("y label").build();
        chart.addSeries("avgDepth", xValues, depthList);
        chart.addSeries("optimal", xValues, optimalDepthList);
        new SwingWrapper(chart).displayChart();

    }

    private static void randomExperiment(int N, int M, boolean isRandom) {
        int bound = N * 20;
        BST<Integer> integerBST = new BST<>();
        Random random = new Random();
        int randomNum = random.nextInt(bound);
        for (int i = 0; i < N; i++) {
            integerBST.add(randomNum);
            randomNum = random.nextInt(bound);
        }
        double initAvgDepth = integerBST.calAvgDepth();
        List<Double> depthList = new ArrayList<>();
        List<Integer> xValues = new ArrayList<>();
        xValues.add(0);
        depthList.add(initAvgDepth);
        for (int i = 1; i <= M ; i++) {
            System.out.println(i);
            double depth = ExperimentHelper.randomDeleteAndInsert(integerBST, bound, isRandom);
            depthList.add(depth);
            xValues.add(i);
        }
        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("x label").yAxisTitle("y label").build();
        chart.addSeries("avgDepth", xValues, depthList);
        new SwingWrapper(chart).displayChart();

    }

    public static void experiment2() {
        // initialization
        int N = 500;
        int M = 300000;
        randomExperiment(N, M, false);

    }

    public static void experiment3() {
        int N = 1000;
        int M = 500000;
        randomExperiment(N, M, true);

    }

    public static void main(String[] args) {
        experiment3();
    }
}
