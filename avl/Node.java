package alg;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ekishigo on 19.4.16.
 */
public class Node implements Comparable<Node> {
    public int height;
    public int key;
    public Node left;
    public Node right;
    public Node parent;

    public SpecialInfo info;

    public Node(int key) {
        this.key = key;
        parent = null;
        left = null;
        right = null;
        height = 0;
        info = new SpecialInfo();
    }

    public double getAvgDepth() {
        return info.getAvg();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (height != node.height) return false;
        if (key != node.key) return false;
        if (left != null ? !left.equals(node.left) : node.left != null) return false;
        if (right != null ? !right.equals(node.right) : node.right != null) return false;
        if (parent != null ? !parent.equals(node.parent) : node.parent != null) return false;
        return info != null ? info.equals(node.info) : node.info == null;

    }

    @Override
    public int hashCode() {
        int result = height;
        result = 31 * result + key;
        result = 31 * result + (left != null ? left.hashCode() : 0);
        result = 31 * result + (right != null ? right.hashCode() : 0);
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Node{" +
                "height=" + height +
                ", key=" + key +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        return o.key - this.key;
    }

}

