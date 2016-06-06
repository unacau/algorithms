package alg;

/**
 * Created by ekishigo on 19.4.16.
 */
//https://cw.felk.cvut.cz/courses/a4b33alg/task.php?task=finetunedAVL
public class AvlTree extends BinarySearchTree {
    public static final int RIGHT = 0;
    public static final int LEFT = 1;
    public static final int LEFT_RIGHT = 2;
    public static final int RIGHT_LEFT = 3;
    private static final int DELETE = 0;
    private static final int INSERT = 1;
    protected int resultLeft = 0;
    protected int resultRight = 0;
    protected int N;

    public AvlTree() {
        super();
    }

    // we want to define form what subtree we want to get focus
    private int redefDelCode(Node founded) {
        Node leftSubtree = founded.left;
        Node rightSubtree = founded.right;
        double avrgDepthLeft = leftSubtree.getAvgDepth();
        double avrgDepthRight = rightSubtree.getAvgDepth();
        if (avrgDepthLeft >= avrgDepthRight) {
            resultLeft++;
            return TWO_SUCCESSORS_LEFT_FOCUS;
        } else {
            resultRight++;
            return TWO_SUCCESSORS_RIGHT_FOCUS;
        }
    }

    @Override
    public Node insert(int key) {
        Node node = super.insert(key);
        checkAndRepairAvl(node, INSERT);
        return node;
    }

    @Override
    public Node delete(int key) {
        Node node = super.delete(key);
        checkAndRepairAvl(node, DELETE);
        return node;
    }

    @Override
    protected int deletionCode(Node deleted) {
        int delCode = super.deletionCode(deleted);
        int ret = 0;
        if (delCode == TWO_SUCCESSORS_RIGHT_FOCUS) {
            ret = redefDelCode(deleted);
        } else {
            ret = delCode;
        }
        return ret;
    }

    // go up to root, update level of nodes and repair tree if AVL prop is broken
    private void checkAndRepairAvl(Node node, int operation) {
        if (node == null) {
            return;
        }
        Node cur = node;
        int c = 0;
        while (cur != null) {                    //update level
            boolean ok = ok(cur);
            Node exParent = cur.parent;
            if (!ok) {
                c++;
                int rotationCode = defineRotation(cur);
                switch (rotationCode) {
                    case RIGHT:
                        rotateRight(cur);
                        break;
                    case LEFT:
                        rotateLeft(cur);
                        break;
                    case LEFT_RIGHT:
                        rotateLeft(cur.left);
                        rotateRight(cur);
                        break;
                    case RIGHT_LEFT:
                        rotateRight(cur.right);
                        rotateLeft(cur);
                        break;
                    default:
                        System.err.println("WTF!?");
                }
            }
            cur = exParent;
        }
    }

    private boolean ok(Node cur) {
        return ok(quickHeight(cur.left), quickHeight(cur.right));
    }

    private boolean ok(int depthLeft, int depthRight) {
        int dif = depthLeft - depthRight;
        return dif == 0 || dif == -1 || dif == 1;
    }

    private void rotateRight(Node rotated) {
        Node toMove = rotated.left.right;
        Node newRoot = rotated.left;
        Node parent = rotated.parent;
        if (parent != null) {
            if (isRight(rotated, parent)) {
                parent.right = newRoot;
            } else {
                parent.left = newRoot;
            }
        } else {
            root = newRoot;
        }
        newRoot.parent = parent;

        rotated.parent = newRoot;
        newRoot.right = rotated;
        if (toMove != null) {
            toMove.parent = rotated;
        }
        rotated.left = toMove;

        updateInfo(rotated);
        updateInfo(newRoot);
        updateOnlyHeight(rotated);
    }

    private void rotateLeft(Node rotated) {
        Node toMove;
        toMove = rotated.right.left;
        Node newRoot = rotated.right;
        Node parent = rotated.parent;
        if (parent != null) {
            if (isRight(rotated, parent)) {
                parent.right = newRoot;
            } else {
                parent.left = newRoot;
            }
        } else {
            root = newRoot;
        }
        newRoot.parent = parent;
        rotated.parent = newRoot;
        newRoot.left = rotated;
        if (toMove != null) {
            toMove.parent = rotated;
        }
        rotated.right = toMove;

        updateInfo(rotated);
        updateInfo(newRoot);
        updateOnlyHeight(rotated);
    }

    private int defineRotation(Node node) {
        Node cur = node;
        boolean right = false;
        boolean left = false;
        int ret;
        if (quickHeight(cur.right) > quickHeight(cur.left)) {
            left = true;
            cur = cur.right;
            ret = LEFT;
        } else {
            right = true;
            cur = cur.left;
            ret = RIGHT;
        }
        if (left && quickHeight(cur.right) < quickHeight(cur.left)) {
            ret = RIGHT_LEFT;
        } else if (right && quickHeight(cur.right) > quickHeight(cur.left)) {
            ret = LEFT_RIGHT;
        }
        return ret;
    }

    public FastReader read(String fname) {
        FastReader fr = FastReader.getReader(fname);
        N = fr.nextInt();
        return fr;
    }

}
