package alg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ekishigo on 22.3.16.
 */
// https://cw.felk.cvut.cz/courses/a4b33alg/task.php?task=manyrobots
public class Main {

    public static int rows = -1;
    public static int cols = -1;
    public static int[][] inputMatrix = null;
    public static Map<Integer, Integer> mapka = null; // for input processing
    public static boolean field[][] = null;
    private static int numberRobots;
    private static int uncoveredCells;

    public static void main(String[] args) {
        String fname =
                "/home/ekishigo/Documents/ALG/domaci " +
                        "ulohy/src/main/java/alg/manyrobots/datapub/pub09.in";
        try {
            //read(fname);
            read("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        prepareInput();
        prepareField();
        numberRobots = 1;
        uncoveredCells = rows * cols;

        //Stopwatch.tick();
        solve();
        //System.out.println("Solved for " + Stopwatch.tock() + " ms");
    }

    private static void prepareField() {
        field = new boolean[rows][cols];
    }

    private static void solve() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (field[row][col] == false) {
                    putRobo(row, col);
                    numberRobots++;
                }
            }
        }
    }

    private static void putRobo(int row, int col) {
        if (field[row][col] == false) {
            uncoveredCells--;
        }
        field[row][col] = true;
        if (fieldCompleted()) {
            System.out.println(numberRobots);
        } else {
            for (int j = 0; j < cols; j++) { // fix row, iterate through col
                if (inputMatrix[row][col] + inputMatrix[row][j] == Math.abs(col - j)
                        && field[row][j] == false && j !=col) {
                    putRobo(row, j);
                }
            }
            for (int i = 0; i < rows; i++) { // fix col, iterate through col
                if (inputMatrix[row][col] + inputMatrix[i][col] == Math.abs(row - i)
                        && field[i][col] == false && i != row) {
                    putRobo(i, col);
                }
            }
        }
    }

    private static boolean fieldCompleted() {
        return uncoveredCells == 0;
    }

    private static void prepareInput() {
        rows = mapka.get(0);
        cols = mapka.get(1);
        inputMatrix = new int[rows][cols];
        int cursor = 2;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                inputMatrix[i][j] = mapka.get(cursor++);
            }
        }
    }

    public static void read(String fname) throws IOException {
        BufferedReader bi;
        if (fname.equals("")) {
            bi = new BufferedReader(new InputStreamReader(System.in));
        } else {
            bi = new BufferedReader(new FileReader(fname));
        }
        String line;
        int nextInt;
        int counter = 0;
        mapka = new HashMap<>();
        while ((line = bi.readLine()) != null) {
            for (String numStr : line.split("\\s")) {
                nextInt = Integer.parseInt(numStr);
                mapka.put(counter++, nextInt);
            }
        }
    }
}
