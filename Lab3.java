
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Lab3 {

    public void run() {
        try {
            Lab1 lab1 = new Lab1();

            double[] randomSignals = lab1.getRandomSignalArray();
            int N = lab1.getN();

            getSpectrum(randomSignals, N);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTableRealization(double[] randomSignalArray, int N, double[][] table) {

        double[][] result = new double[N - 1][N - 1];

        double[] signals = randomSignalArray;
        for (int p = 0; p < N - 1; p++) {
            double re = 0;
            double im = 0;
            for (int k = 0; k < N - 1; k++) {
                re += signals[k] * table[p * k % N][0];
                im += signals[k] * table[p * k % N][1];
            }
            result[0][p] = re;
            result[1][p] = im;
        }
        countF(result, N);
    }

    public double[] getSpectrum(double[] randomSignalArray, int N) throws Exception {
        double[][] data = getData(randomSignalArray, N);

        double[] f = countF(data, N);

        int[] nums = IntStream.rangeClosed(0, 1022).toArray();
        chartPanel(f, nums, "f(t) - lab3");

        return f;
    }

    public void chartPanel(double[] f, int[] nums, String title) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            BarChart example = new BarChart("Bar Chart Window", Arrays.copyOfRange(f, 0, 1024), title, Arrays.copyOfRange(nums, 0, 1024));
            example.setSize(800, 400);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });

    }

    private double[] countF(double[][] cosSin, int N) {
        double[] f = new double[N - 1];

        for (int i = 0; i < N - 1; i++) {
            f[i] = Math.sqrt(Math.pow(cosSin[0][i], 2) + Math.sin(Math.pow(cosSin[1][i], 2)));
        }
        return f;
    }

    private double[][] getData(double[] signals, int N) {
        double[][] result = new double[N - 1][N - 1];
        for (int p = 1; p < N - 1; p++) {
            double re = 0;
            double im = 0;
            for (int k = 0; k < N - 1; k++) {
                re += signals[k] * Math.cos((2 * Math.PI / N) * p * k);
                im += signals[k] * Math.sin((2 * Math.PI / N) * p * k);
            }
            result[0][p] = re;
            result[1][p] = im;
        }
        return result;
    }
}
