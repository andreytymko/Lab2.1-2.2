

import java.util.stream.IntStream;

public class Lab4 {

    public static void main(String[] args) throws Exception {
        Lab4 lab4 = new Lab4();
        Lab3 lab3 = new Lab3();
        Lab1 lab1 = new Lab1();
        double[] randomSignalArray = lab1.getRandomSignalArray();

        double[] spectrum = lab4.fft(randomSignalArray);

        int[] nums = IntStream.rangeClosed(0, 1024).toArray();
        //lab3.chartPanel(spectrum, nums, "f(t) - lab4");
        //lab3.chartPanel(lab3.getSpectrum(randomSignalArray, randomSignalArray.length), nums, "f(t) - lab4");
    }

    private double[] fft(double[] randomSignalArray) {
        int N = randomSignalArray.length;
        double[] f;

        if (N == 2) {
            f = new double[2];
            f[0] = randomSignalArray[0] + randomSignalArray[1];
            f[1] = randomSignalArray[0] - randomSignalArray[1];
        } else {

            double[] signalsEven = new double[N / 2];
            double[] signalsOdd = new double[N / 2];

            for (int i = 0; i < N / 2; i++) {
                signalsEven[i] = randomSignalArray[2 * i];
                signalsOdd[i] = randomSignalArray[2 * i + 1];
            }

            double[] xEven = fft(signalsEven);
            double[] xOdd = fft(signalsOdd);
            f = new double[N];

            for (int k = 0; k < N / 2; k++) {
                f[k] = xEven[k] + Math.exp(-2 * Math.PI * k / N) * xOdd[k];
                f[k + N / 2] = xEven[k] - Math.exp(-2 * Math.PI * k / N) * xOdd[k];
            }
        }
        return f;
    }
}
