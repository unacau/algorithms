package alg;

import javafx.util.Pair;

/**
 * Created by ekishigo on 19.4.16.
 */
public class Main {

    public static void main(String[] args) {
        String fname = "/home/ekishigo/Documents/ALG/domaci ulohy/src/main/java/barebranch/pub/pub07.in";
        String up = "";
        BareBranch myTree = new BareBranch();
        myTree.read(up);
        doOP(myTree);
    }

    private static void doOP(BareBranch myTree) {
        for (int i = 0; i < myTree.operations.length; i++) {
            Pair<Integer, Integer> pair = myTree.operations[i];
            switch (pair.getKey()) {
                case BareBranch.INSERT:
                    myTree.insert(pair.getValue());
                    break;
                case BareBranch.DELETE:
                    myTree.delete(pair.getValue());
                    break;
            }
        }
        System.out.println(myTree.result);
    }
}
