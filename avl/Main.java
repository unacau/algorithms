package alg;

/**
 * Created by ekishigo on 24.4.16.
 */
public class Main {
    public static final int INSERT = 73; // ASCI of char I
    public static final int DELETE = 68; // ASCI of chad D

    public static void main(String[] args) {
        String fname = "/home/ekishigo/Documents/ALG/domaci ulohy/src/main/java/finetuningavl/pub/pub10.in";
        String up = "";
        AvlTree tree = new AvlTree();
        FastReader fr = tree.read(up);
        //long t1 = System.currentTimeMillis();
        doOp(tree, fr);
        //System.out.println("Time is " + (System.currentTimeMillis() - t1));
    }

    private static void doOp(AvlTree tree, FastReader fr) {
        for (int i = 0; i < tree.N; i++) {

            switch (fr.nextString().charAt(0)) {
                case INSERT:
                    int num = fr.nextInt();
                    tree.insert(num);
                    break;
                case DELETE:
                    num = fr.nextInt();
                    tree.delete(num);
                    break;
            }
        }
        System.out.println(tree.resultLeft + " " + tree.resultRight);
    }


}
