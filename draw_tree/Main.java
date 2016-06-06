package alg;

import java.io.*;
import java.util.*;

/**
 * Created by ekishigo on 17.3.16.
 */
// http://cw.felk.cvut.cz/courses/a4b33alg/task.php?task=drawtree
public class Main {


    private static int[][] tree;
    private static int[] labelsOfInspected;
    private static Set<Integer> pomucka;
    private static int rootLabel;
    private static int numberNodes;
    private static int numberOfInspected;

    private static Queue<Integer> fronticka = new ArrayDeque<>();
    private static Set<Integer> touched = new HashSet<>(); //for inorder


    private static int[] X;
    private static int[] Y;
    private static int treeDepth;
    private static int knownXs;
    private static int count;

    public static void main(String[] args) throws IOException {
        // PREPARE INPUT
        String fname = "/home/ekishigo/Documents/ALG/DU2/pub/pub10.in";
        //Stopwatch.tick();
        getInput(fname, 1); // NEW fast but ugly :(
        //System.out.println(Stopwatch.tock());
        //getInput(0, fname); // OLD! nice but slow

        treeDepth = 0;
        X = new int[numberNodes];
        Y = new int[numberNodes];

        // Inorder first searching, search X coordinates
        knownXs = 0;
        count = 0;
        inorder(rootLabel);

        // Breadth first searching, search Y coordinates
        count = 0;
        bfs(rootLabel);

        for (int i = 0; i < numberOfInspected; i++) {
            System.out.println(X[labelsOfInspected[i]] + " "
                            + (treeDepth - Y[labelsOfInspected[i]] ));
        }

    }

    private static void inorder(int rootLabel) {
        if (rootLabel == -1 || touched.contains(rootLabel)) {
            return;
        }
        if (knownXs < numberOfInspected) {
            touched.add(rootLabel);

            if (touched.contains(tree[rootLabel][0])) {
                inorder(tree[rootLabel][1]);
            } else {
                inorder(tree[rootLabel][0]);
            }

            if (pomucka.contains(rootLabel)) {
                knownXs++;
            }
            X[rootLabel] = count++;

            if (touched.contains(tree[rootLabel][1])) {
                inorder(tree[rootLabel][2]);
            } else {
                inorder(tree[rootLabel][1]);
            }
        }
    }

    private static void dfs(int rootLabel) {
        Set<Integer> touched = new HashSet<>();
        Stack<Integer> stack = new Stack<>();
        stack.push(rootLabel);
        touched.add(rootLabel);
        while (!stack.isEmpty()) {
            int node = stack.pop();
            System.out.println(node);
            for (int i = 0; i < 3; i++) {
                int neighbor = tree[node][i];
                if (!touched.contains(neighbor) && neighbor != -1) {
                    stack.push(neighbor);
                    touched.add(neighbor);
                }
            }
        }
    }

    private static void bfs(int rootLabel) {
        Set<Integer> visited = new HashSet<>();
        int patro = 0;
        fronticka.offer(rootLabel);
        Y[rootLabel] = 0;
        while (!fronticka.isEmpty()) {
            int node = fronticka.poll();
            if (!visited.contains(node)) {
                visited.add(node);
                //System.out.println("Node " + node + " patro " + Y.get(node));
                for (int i = 0; i < 3; i++) {
                    int neighbor = tree[node][i];
                    if (neighbor != -1 && !visited.contains(neighbor)) {
                        patro = Y[node];
                        ++patro;
                        Y[neighbor] = patro;
                        if (patro > treeDepth) {
                            treeDepth = patro;
                        }
                        fronticka.offer(neighbor);
                    }
                }
            }
        }
    }

    public static void getInput(int mode, String fname) throws FileNotFoundException {
        Scanner scan = getScanner(mode, fname);
        numberNodes = scan.nextInt();
        rootLabel = scan.nextInt();
        numberOfInspected = scan.nextInt();
        //graph = new LinkedList[numberNodes];
        tree = new int[numberNodes][3];
        fillMinusOne(tree);
        setNeighbours(scan);
        labelsOfInspected = new int[numberOfInspected];
        int i = 0;
        while (scan.hasNext()) {
            //labelsOfInspected.add(scan.nextInt());
            labelsOfInspected[i] = scan.nextInt();
            i++;
        }
    }

    private static Scanner getScanner(int mode, String fname) throws FileNotFoundException {
        Scanner scan;
        switch (mode) {
            case 0: //read from file
                scan = new Scanner(new BufferedReader(new FileReader(fname)));
                break;
            case 1: // read from System.in
                scan = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
                break;
            default:
                scan = new Scanner(new BufferedReader(new FileReader(fname)));
        }
        return scan;
    }

    private static void setNeighbours(Scanner scan) {
        for (int i = 0; i < tree.length - 1; i++) {
            int one = scan.nextInt();
            int two = scan.nextInt();
            for (int j = 0; j < 3; j++) {
                if (tree[one][j] == -1) {
                    tree[one][j] = two;
                    break;
                }
            }
            for (int j = 0; j < 3; j++) {
                if (tree[two][j] == -1) {
                    tree[two][j] = one;
                    break;
                }
            }
        }
    }

    private static void fillMinusOne(int[][] probablyTree) {
        for (int i = 0; i < probablyTree.length; i++) {
            for (int j = 0; j < probablyTree[i].length; j++) {
                probablyTree[i][j] = -1;
            }
        }
    }

    static void getInput(String fname, int mode) throws IOException {
        StringBuilder in = new StringBuilder();
        BufferedReader br;
        switch (mode) {
            case 0:
                br = new BufferedReader(new FileReader(fname));
                break;
            case 1:
                br = new BufferedReader(new InputStreamReader(System.in));
                break;
            default:
                br = new BufferedReader(new FileReader(fname));
        }
        String line;
        while ((line = br.readLine()) != null) {
            in.append(line).append(' ');
        }
        StringTokenizer tokenizer = new StringTokenizer(in.toString(), " ");
        pomucka = new HashSet<>();
        int nextToken;
        int counter = 0;
        int first;
        int second;
        int i = 0;
        while (tokenizer.hasMoreTokens()) {
            nextToken = Integer.parseInt(tokenizer.nextToken());
            if (counter == 0) {
                numberNodes = nextToken;
                tree = new int[numberNodes][3];
                i = numberNodes + 2;
                fillMinusOne(numberNodes);
            } else if (counter == 1) {
                rootLabel = nextToken;
            } else if (counter == 2) {
                numberOfInspected = nextToken;
                labelsOfInspected = new int[numberOfInspected];
            } else if (counter < i) {
                first = nextToken;
                second = Integer.parseInt(tokenizer.nextToken());
                put(tree[first], second);
                put(tree[second], first);
            } else {
                labelsOfInspected[counter % i] = nextToken;
                pomucka.add(nextToken);
            }
            counter++;
        }
    }

    static void fillMinusOne(int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 3; j++) {
                tree[i][j] = -1;
            }
        }
    }

    static void put(int[] array, int element) {
        for (int i = 0; i < 3; i++) {
            if (array[i] == -1) {
                array[i] = element;
                return;
            }
        }
    }

}

