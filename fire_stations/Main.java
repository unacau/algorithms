package alg;

import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * Created by ekishigo on 6.6.16.
 */

// assignment
// https://cw.felk.cvut.cz/courses/a4b33alg/task.php?task=firestations
public class Main {
    private static Integer M;
    private static Integer N;
    private static Integer D;
    private static Integer P; // count of fire stations
    private static int[][] prices;
    private static int[] pricesSorted;

    private static boolean[][] used; // for bt
    private static int actMin = 0; // global minimum so far
    private static int min = Integer.MAX_VALUE; // global minimum so far
    private static int bestPrice = Integer.MAX_VALUE;

    private static int k = 0;

    public static void main(String[] args) {
        start();
    }

    private static void start() {
        long t = System.currentTimeMillis();
        read();
        solve();
        System.out.println(min);
//        System.out.println("time = " + (System.currentTimeMillis() - t));
    }

    private static void solve() {
//        long limit = 100 * (P);
//        randomSearch(limit);
//        k = 0;
//        actMin = 0;
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                put(i, j);
                used[i][j] = false;
                actMin -= prices[i][j];
                k -= 1;
            }
        }
    }

    private static void randomSearch(long limit) {
        long timeStart = System.currentTimeMillis();
        Random random = new Random(39980183);
        int minLocal = 0;
        int k = 0;
        boolean used[][] = new boolean[M][N];
        while (System.currentTimeMillis() - timeStart < limit) {

            if (k != P) {
                int randRow = random.nextInt(M);
                int randCol = random.nextInt(N);

                if (canBuild(randRow, randCol, used)) {
                    used[randRow][randCol] = true;
                    minLocal += prices[randRow][randCol];
                    k++;
                }
            }
            if (k == P) {
//                System.out.println("RANDOM DONE. PRICE = " + minLocal);
                if (minLocal < min) {
                    min = minLocal;
                }
                clear(used);
                k = 0;
                minLocal = 0;
                continue;
            }
        }
    }

    private static void clear(boolean[][] used) {
        for (int i = 0; i < used.length; i++) {
            for (int j = 0; j < used[i].length; j++) {
                used[i][j] = false;
            }
        }
    }


    private static int avgPrice = 0;

    private static void read() {
        try {
            String s = "/home/ekishigo/Documents/ALG/domaci ulohy/src/main/resources/pozar_exam/pub05.in";
//            BufferedReader reader = new BufferedReader(new FileReader(s));
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());

            M = Integer.valueOf(tokenizer.nextToken());
            N = Integer.valueOf(tokenizer.nextToken());
            D = Integer.valueOf(tokenizer.nextToken());
            P = Integer.valueOf(tokenizer.nextToken());

            pricesSorted = new int[N*M];
            int c = 0;
            prices = new int[M][N];

            for (int i = 0; i < M; i++) {
                tokenizer = new StringTokenizer(reader.readLine());
                for (int j = 0; j < N; j++) {
                    prices[i][j] = Integer.valueOf(tokenizer.nextToken());

                    if (prices[i][j] < bestPrice) {
                        bestPrice = prices[i][j];
                    }

                    pricesSorted[c++] = prices[i][j];

//                    avgPrice += prices[i][j];
                }
            }

            Arrays.sort(pricesSorted);
            avgPrice = avgPrice / (N*M);
            used = new boolean[M][N];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void put(int row, int col) {
        used[row][col] = true;
        actMin += prices[row][col];
        k += 1;
        if (actMin >= min) {
            return;
        }

        if (badSituation(k)) {
            return;
        }

        if (k == P) {
            if (actMin < min) {
                min = actMin;
            }
        }

        if (++row == M) {
            if (++col == N) {
                return;
            } else {
                for (int i = 0; i < M; i++) {
                    for (int j = 0; j < N; j++) {
                        if (canBuild(i, j, used) && k < P) {
                            put(i, j);
                            used[i][j] = false;
                            actMin -= prices[i][j];
                            k -= 1;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < M; i++) {
                for (int j = 0; j < N; j++) {
                    if (canBuild(i, j, used) && k < P) {
                        put(i, j);
                        used[i][j] = false;
                        actMin -= prices[i][j];
                        k -= 1;
                    }
                }
            }
        }

    }

    private static boolean badSituation(int k) {
        int zbyva = P - k;
        int sum = actMin;
        for (int i = 0; i < zbyva; i++) {
            sum += pricesSorted[i];
        }
        if (sum >= min) {
            return true;
        }
//        if ((actMin + (zbyva * bestPrice)) >= min) {
//            return true;
//        }
        return false;
    }

    private static void print(boolean[][] used) {
        for (int i = 0; i < used.length; i++) {
            for (int j = 0; j < used.length; j++) {
                if (used[i][j]) {
                    System.out.printf("(%d, %d) = %d\n", i, j, prices[i][j]);
                }
            }
        }
    }

    private static boolean canBuild(int row, int col, boolean used[][]) {
        if (used[row][col]) {
            return false;
        }
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (used[i][j]) {
                    if (i == row || j == col) {
                        return false;
                    }
                    int delta1 = Math.abs(row - i);
                    int delta2 = Math.abs(col - j);
                    if ((delta1 + delta2) < D) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
