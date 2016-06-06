package alg;

/**
 * Created by ekishigo on 23.4.16.
 */
public class BinarySearchTree {
    public static final int WITHOUT_SUCCESSORS = 0;
    public static final int ONE_SUCCESSOR = 1;
    public static final int TWO_SUCCESSORS_RIGHT_FOCUS = 2;
    public static final int TWO_SUCCESSORS_LEFT_FOCUS = 3;

    public Node root;
    public BinarySearchTree() {
        this.root = null;
    }

    public void clear() {
        root = null;
    }

    public int countNodes(Node subtree) {
        int n = 1;
        if (subtree.left != null) {
            n += countNodes(subtree.left);
        }
        if (subtree.right != null) {
            n += countNodes(subtree.right);
        }
        return n;
    }

    public Node insert(int key) {
        Node newNode = new Node(key);
        if (root == null) {
            root = newNode;
            return newNode;
        }
        Node current = root;
        Node parent;
        while (true) {
            parent = current;
            if (key < current.key) {
                current = current.left;
                if (current == null) {
                    newNode.parent = parent;
                    parent.left = newNode;
                    break;
                }
            } else {
                current = current.right;
                if (current == null) {
                    newNode.parent = parent;
                    parent.right = newNode;
                    break;
                }
            }
        }
        updateInfo(newNode);
        updateOnlyHeight(newNode);
        return newNode;
    }

    protected void updateOnlyHeight(Node newNode) {
        Node cur = newNode;
        int c = 0;
        while (cur != null && c < 2) {
            int oldH = cur.height;
            cur.height = depthSimple(cur);
            //speed up!
            if (oldH == cur.height) {
                c++;
            }
            //speed up!
            cur = cur.parent;
        }
    }

    private int depthSimple(Node cur) {
        return max(quickHeight(cur.left), quickHeight(cur.right)) + 1;
    }

    public Node find(int key) {
        Node current = root;
        while (current != null) {
            if (key > current.key) {
                current = current.right;
            } else if (key < current.key) {
                current = current.left;
            } else {
                return current;
            }
        }
        return current;
    }

    public Node delete(int key) {
        Node deleted = find(key);
        int delCode = deletionCode(deleted);
        Node par = null;
        Node focus;
        switch (delCode) {
            case WITHOUT_SUCCESSORS:
                par = delWithoutSuccessors(deleted);
                break;
            case ONE_SUCCESSOR:
                par = delWithOneSuccessor(deleted);
                break;
            case TWO_SUCCESSORS_RIGHT_FOCUS:
                focus = findFocusRight(deleted.right);
                par = delWithTwoSuccessors(deleted, focus);
                break;
            case TWO_SUCCESSORS_LEFT_FOCUS:
                focus = findFocusLeft(deleted.left);
                par = delWithTwoSuccessors(deleted, focus);
        }
        updateInfo(par);
        updateOnlyHeight(par);
        return par;
    }

    private Node delWithOneSuccessor(Node deleted) {
        boolean deletedIsRight = isRightChild(deleted);
        Node bigFather = deleted.parent;
        Node child;
        if (deleted.right != null) {
            child = deleted.right;
        } else {
            child = deleted.left;
        }
        if (bigFather != null) {
            if (deletedIsRight) {
                bigFather.right = child;
            } else {
                bigFather.left = child;
            }
            child.parent = bigFather;
        } else {
            root = child;
        }
        return bigFather;
    }

    private Node delWithTwoSuccessors(Node deleted, Node focus) {
        Node focusParent = focus.parent;
        int deletedKey = deleted.key;
        int focusKey = focus.key;
        int delCode = deletionCode(focus);
        Node par = null;
        switch (delCode) {
            case WITHOUT_SUCCESSORS:
                par = delWithoutSuccessors(focus);
                break;
            case ONE_SUCCESSOR:
                par = delWithOneSuccessor(focus);
                break;
        }
        updateInfo(par);
        updateOnlyHeight(par);
        deleted.key = focusKey;
        return focusParent;
    }

    public void updateInfo(Node node) {
        Node cur = node;
        while (cur != null) {
            for (int i = 0; i < cur.info.nodesOnLevel.length - 1; i++) {
                cur.info.nodesOnLevel[i + 1] = (cur.left == null ? 0 : cur.left.info.nodesOnLevel[i]) + (cur.right == null ? 0 : cur.right.info.nodesOnLevel[i]);
            }
            cur = cur.parent;
        }
    }

    private Node findFocusLeft(Node cur) {
        return cur.right == null ? cur : findFocusLeft(cur.right);
    }

    public Node findFocusRight(Node cur) {
        return cur.left == null ? cur : findFocusRight(cur.left);
    }

    private Node delWithoutSuccessors(Node deleted) {
        if (root.equals(deleted)) {
            root = null;
            return root;
        }
        if (deleted.key > deleted.parent.key) {
            deleted.parent.right = null;
        } else {
            deleted.parent.left = null;
        }
        return deleted.parent;
    }

    protected int deletionCode(Node deleted) {
        if (deleted.left == null && deleted.right == null) {
            return WITHOUT_SUCCESSORS;
        }
        if ((deleted.right == null && deleted.left != null) ||
                (deleted.left == null && deleted.right != null)) {
            return ONE_SUCCESSOR;
        }
        return TWO_SUCCESSORS_RIGHT_FOCUS;
    }

    public boolean isLeaf(Node founded) {
        return founded.left == null && founded.right == null;
    }

    public boolean hasTwoChild(Node founded) {
        return founded.right != null && founded.left != null;
    }

    public boolean isRightChild(Node founded) {
        Node parent = founded.parent;
        return parent != null && parent.right != null && parent.right.equals(founded);
    }

    public boolean isRight(Node rotated, Node parent) {
        return parent != null && parent.right != null && parent.right.equals(rotated);
    }

    public static int max(int a, int b) {
        return a > b ? a : b;
    }

    protected int quickHeight(Node node) {
        if (node == null) {
            return -1;
        }
        return node.height;
    }

    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();
        tree.delete(14);
        double a = tree.root.getAvgDepth();
        System.out.println("avg depth is ");
        System.out.println("Done");
    }
}
