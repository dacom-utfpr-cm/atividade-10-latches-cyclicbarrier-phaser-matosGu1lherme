package org.example;

public class StencilSequential {
    private static final int N = 100;
    private static final int K = 10;

    public static void main(String[] args) {
        double[] input = new double[N];
        double[] output = new double[N];

        for (int i = 0; i < N; i++) {
            input[i] = Math.random();
        }

        for (int iter = 0; iter < K; iter++) {
            for (int i = 1; i < N - 1; i++) {
                output[i] = (input[i - 1] + input[i] + input[i + 1]) / 3.0;
            }
            System.arraycopy(output, 0, input, 0, N);
        }

        for (int i = 0; i < N; i++) {
            System.out.println(input[i]);
        }
    }
}
