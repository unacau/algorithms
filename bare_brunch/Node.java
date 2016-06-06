package alg;
/**
 * Created by ekishigo on 19.4.16.
 */
public class Node implements Comparable<Node> {
    public int key;
    public Node left;
    public Node right;
    public Node parent;
    public int depth;
    public int depthRight;
    public int depthLeft;
    private char[] numberOfChildren;

    public Node(int key) {
        this.key = key;
        parent = null;
        left = null;
        right = null;
        depth = 0;
        this.depthLeft = -1;
        this.depthRight = -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node avlNode = (Node) o;

        if (key != avlNode.key) return false;
        if (depth != avlNode.depth) return false;
        if (depthRight != avlNode.depthRight) return false;
        if (depthLeft != avlNode.depthLeft) return false;
        if (left != null ? !left.equals(avlNode.left) : avlNode.left != null) return false;
        if (right != null ? !right.equals(avlNode.right) : avlNode.right != null) return false;
        return parent != null ? parent.equals(avlNode.parent) : avlNode.parent == null;

    }

    @Override
    public int hashCode() {
        int result = key;
        result = 31 * result + (left != null ? left.hashCode() : 0);
        result = 31 * result + (right != null ? right.hashCode() : 0);
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        result = 31 * result + depth;
        result = 31 * result + depthRight;
        result = 31 * result + depthLeft;
        return result;
    }

    @Override
    public String toString() {
        return "Node{" +
                "key=" + key +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        return o.key - this.key;
    }
}

