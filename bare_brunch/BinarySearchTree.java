package alg;

import java.util.List;

/**
 * Created by ekishigo on 23.4.16.
 */
public class BinarySearchTree {
    private static final int WITHOUT_SUCCESSORS = 0;
    private static final int ONE_SUCCESSOR = 1;
    private static final int TWO_SUCCESSORS_RIGHT_FOCUS = 2;
    private static final int TWO_SUCCESSORS_LEFT_FOCUS = 3;

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
                    return newNode;
                }
            } else {
                current = current.right;
                if (current == null) {
                    newNode.parent = parent;
                    parent.right = newNode;
                    return newNode;
                }
            }
        }
    }

    public Node find(int key) {
        Node current = root;
        while (current != null) {
            if (key > current.key) {
                current = current.right;
            } else if (key <  current.key) {
                current = current.left;
            } else {
                return current;
            }
        }
        return current;
    }

    public Node delete(int key) {
        Node deleted = find(key);
        int delitionCode = deletionCode(deleted);
        Node ret = null;
        Node focus;
        switch (delitionCode) {
            case WITHOUT_SUCCESSORS:
                ret = delWithoutSuccessors(deleted);
                break;
            case ONE_SUCCESSOR:
                ret = delWithOneSuccessor(deleted);
                break;
            case TWO_SUCCESSORS_RIGHT_FOCUS:
                focus = findFocusRight(deleted.right);
                ret = delWithTwoSuccessors(deleted, focus);
                break;
            case TWO_SUCCESSORS_LEFT_FOCUS:
                focus = findFocusLeft(deleted.left);
                ret = delWithTwoSuccessors(deleted, focus);
        }
        return ret;
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
        int focusKey = focus.key;
        int delCode = deletionCode(focus);
        switch (delCode) {
            case WITHOUT_SUCCESSORS:
                delWithoutSuccessors(focus);
                break;
            case ONE_SUCCESSOR:
                delWithOneSuccessor(focus);
                break;
        }
        deleted.key = focusKey;
        return focusParent;
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

    private int deletionCode(Node deleted) {
        if (deleted.left == null && deleted.right == null) {
            return WITHOUT_SUCCESSORS;
        }
        if ((deleted.right == null && deleted.left != null) ||
                (deleted.left == null && deleted.right != null)) {
            return ONE_SUCCESSOR;
        }
        return TWO_SUCCESSORS_RIGHT_FOCUS;
    }

    public void walkInorder(Node root, List<Integer> list) {
        if (root.left != null) {
            walkInorder(root.left, list);
        }
        list.add(root.key);
        if (root.right != null) {
            walkInorder(root.right, list);
        }
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
}
