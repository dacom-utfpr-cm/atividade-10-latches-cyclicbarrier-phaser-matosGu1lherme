package org.example;
import java.util.concurrent.Phaser;

public class StencilPhaser {
    private static final int N = 100;
    private static final int K = 10;
    private static final int NUM_THREADS = 4;

    public static void main(String[] args) throws InterruptedException {
        double[] input = new double[N];
        double[] output = new double[N];

        for (int i = 0; i < N; i++) {
            input[i] = Math.random();
        }

        for (int iter = 0; iter < K; iter++) {
            Phaser phaser = new Phaser(NUM_THREADS);

            int chunkSize = N / NUM_THREADS;

            for (int t = 0; t < NUM_THREADS; t++) {
                int start = t * chunkSize;
                int end = (t + 1) * chunkSize;
                if (t == NUM_THREADS - 1) {
                    end = N;
                }

                int finalStart = start;
                int finalEnd = end;

                new Thread(() -> {
                    for (int i = finalStart + 1; i < finalEnd - 1; i++) {
                        output[i] = (input[i - 1] + input[i] + input[i + 1]) / 3.0;
                    }

                    phaser.arriveAndAwaitAdvance();
                }).start();
            }

            phaser.awaitAdvance(phaser.getPhase());
            System.arraycopy(output, 0, input, 0, N);
        }

        for (int i = 0; i < N; i++) {
            System.out.println(input[i]);
        }
    }
}
