package alg;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ekishigo on 18.4.16.
 */
// https://cw.felk.cvut.cz/courses/a4b33alg/task.php?task=barebranchesBST
public class BareBranch extends BinarySearchTree {

    @Override
    public Node insert(int key) {
        Node node = super.insert(key);
        List<Node> bare = detectBare(node);
        if (bare != null) {
            repairBare(bare);
            result++;
        }
        return node;
    }

    @Override
    public Node delete(int key) {
        Node node = super.delete(key);
        if (node == null) {
            return null;
        }
        List<Node> bare = detectBare(node);
        while (bare != null) {
            result++;
            Node repairedBottom = repairBare(bare);
            bare = repairedBottom == null ? null : detectBare(repairedBottom);
        }
        return node;
    }

    public Node repairBare(List<Node> bareBranch) {
        Node bareTop = bareBranch.get(0);
        Node bareBot = bareBranch.get(bareBranch.size() - 1);
        Node bigFather = bareTop.parent;
        BinarySearchTree substitution = createPerfectlyBalanced(bareBranch);
        if (bigFather == null) {
            root = substitution.root;
        }
        return podves(bareTop, bareBot, substitution);
    }

    private Node podves(Node bareTop, Node bareBot, BinarySearchTree substitution) {
        Node bigD = bareTop.parent;
        Node zbytek;
        if (isLeaf(bareBot)) {
            zbytek = null;
        } else if (bareBot.right == null) {
            zbytek = bareBot.left;
        } else {
            zbytek = bareBot.right;
        }
        Node sem = null;
        if (zbytek != null) {
            sem = findSem(zbytek, substitution);
            zbytek.parent = sem;
            if (zbytek.key > sem.key) {
                sem.right = zbytek;
            } else {
                sem.left = zbytek;
            }
        }
        if (bigD != null) {
            if (bigD.key > substitution.root.key) {
                bigD.left = substitution.root;
            } else {
                bigD.right = substitution.root;
            }
        }
        substitution.root.parent = bigD;
        return sem;
    }

    private Node findSem(Node zbytek, BinarySearchTree substitution) {
        if (zbytek == null) {
            return null;
        }
        Node cur = substitution.root;
        while (!isLeaf(cur)) {
            cur = zbytek.key > cur.key ? cur.right : cur.left;
        }
        return cur;
    }

    private BinarySearchTree createPerfectlyBalanced(List<Node> bare) {
        Collections.sort(bare);
        binPuleni(bare);
        treeRet.clear();
        for (int i = 0; i < bareLength; i++) {
            treeRet.insert(list.get(i));
        }
        return treeRet;
    }

    public void binPuleni(List<Node> values) {
        list.clear();
        binPuleni(values, 0, values.size() - 1);
    }

    public void binPuleni(List<Node> values, int start, int end) {
        if (start > end) {
            return;
        }
        int mid = start + (end - start) / 2;
        list.add(values.get(mid).key);
        binPuleni(values, start, mid - 1);
        binPuleni(values, mid + 1, end);
    }

    public static final String UPLOAD_SYSTEM = "";
    public static final int INSERT = 73; // ASCI of char I
    public static final int DELETE = 68; // ASCI of chad D

    public BareBranch() {
        super();
        K = 0;
        N = 0;
        operations = null;
        bareLength = 0;
        result = 0;
    }

    private List<Integer> list;
    private BinarySearchTree treeRet = new BinarySearchTree();
    public int result;
    private int bareLength;
    public int K;
    public int N;
    public Pair[] operations; //key is operation, value is number
    private List<Node> bareBranch;

    public void read(String fname) {
        FastReader fr = FastReader.getReader(fname);
        K = fr.nextInt();
        N = fr.nextInt();
        operations = new Pair[N];
        bareLength = (1 << K) - 1;
        list = new ArrayList<>(bareLength);
        for (int i = 0; i < N; i++) {
            int operation = fr.nextString().charAt(0);
            int number = fr.nextInt();
            operations[i] = new Pair<>(operation, number);
        }
        bareBranch = new ArrayList<>(bareLength);
    }

    public List<Node> detectBare(Node cur) {
        bareBranch.clear();
        while (cur != null && !hasTwoChild(cur) && cur.parent != null
                && !hasTwoChild(cur.parent)) {
            cur = cur.parent;
        }
        for (int i = 0; i < bareLength; i++) {
            if (cur == null || hasTwoChild(cur)) {
                break;
            }
            bareBranch.add(cur);
            cur = cur.left == null ? cur.right : cur.left;
        }
        return bareBranch.size() == bareLength ? bareBranch : null;
    }
}
