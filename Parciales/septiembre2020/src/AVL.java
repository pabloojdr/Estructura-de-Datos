/**
 * Student's name:
 *
 * Student's group:
 */

import dataStructures.list.List;
import dataStructures.list.LinkedList;

import java.util.Iterator;


class Bin {
    private int remainingCapacity; // capacity left for this bin
    private List<Integer> weights; // weights of objects included in this bin

    public Bin(int initialCapacity) {
        // todo
        this.remainingCapacity = initialCapacity;
    }

    // returns capacity left for this bin
    public int remainingCapacity() {
        // todo
        return remainingCapacity;
    }

    // adds a new object to this bin
    public void addObject(int weight) {
        // todo
        if(remainingCapacity - weight >= 0){
            this.weights.append(weight);
        } else {
            throw new RuntimeException("El objeto no cabe en el cubo");
        }
    }

    // returns an iterable through weights of objects included in this bin
    public Iterable<Integer> objects() {
        // todo
        //  SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
        //  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
        return null;
    }

    public String toString() {
        String className = getClass().getSimpleName();
        StringBuilder sb = new StringBuilder(className);
        sb.append("(");
        sb.append(remainingCapacity);
        sb.append(", ");
        sb.append(weights.toString());
        sb.append(")");
        return sb.toString();
    }
}

// Class for representing an AVL tree of bins
public class AVL {
    static private class Node {
        Bin bin; // Bin stored in this node
        int height; // height of this node in AVL tree
        int maxRemainingCapacity; // max capacity left among all bins in tree rooted at this node
        Node left, right; // left and right children of this node in AVL tree

        // recomputes height of this node
        void setHeight() {
            // todo
            height = Math.max(left.height, right.height) + 1;
        }

        // recomputes max capacity among bins in tree rooted at this node
        void setMaxRemainingCapacity() {
            // todo
            maxRemainingCapacity = Math.max(left.maxRemainingCapacity, right.maxRemainingCapacity);
        }

        // left-rotates this node. Returns root of resulting rotated tree
        Node rotateLeft() {
            // todo
            if(right != null) {
                Node root = null;

                root.bin = this.right.bin;
                root.left.bin = this.bin;
                root.left.left = this.left;
                root.left.right = this.right.left;
                root.right = this.right.right;
                root.height = right.height;
                root.left.height = height;

                return root;
            }
            return this;
        }
    }

    private static int height(Node node) {
        // todo
        return node.height;
    }

    private static int maxRemainingCapacity(Node node) {
        // todo
        return node.maxRemainingCapacity;
    }

    private Node root; // root of AVL tree

    public AVL() {
        this.root = null;
    }

    // adds a new bin at the end of right spine.
    private void addNewBin(Bin bin) {
        // todo
        root = addNewBinRec(bin, root);
    }

    private Node addNewBinRec(Bin bin, Node node){
        if(node.right == null){
            node.right.bin = bin;
            node.right.setHeight();
        } else {
            node = addNewBinRec(bin, node.right);
        }

        if(node.right.height > node.left.height + 1){
            node.rotateLeft();
        }

        return node;
    }

    // adds an object to first suitable bin. Adds
    // a new bin if object cannot be inserted in any existing bin
    public void addFirst(int initialCapacity, int weight) {
        // todo
        root = addFirstRec(initialCapacity, weight, root);
    }

    public Node addFirstRec(int initialCapacity, int weight, Node node){
        if(node == null) {
            Bin bin = new Bin(initialCapacity);
            bin.addObject(weight);
            addNewBin(bin);
        } else if(node.right.maxRemainingCapacity < weight){
            node = addFirstRec(initialCapacity, weight, node.right);
        } else if(node.left.maxRemainingCapacity >= weight){
            node.left.bin.addObject(weight);
        } else if(node.maxRemainingCapacity >= weight) {
            node.bin.addObject(weight);
        } else {
            node = addFirstRec(initialCapacity, weight, node.right);
        }

        return node;
    }

    public void addAll(int initialCapacity, int[] weights) {
        // todo
    }

    public List<Bin> toList() {
        // todo
        return null;
    }

    public String toString() {
        String className = getClass().getSimpleName();
        StringBuilder sb = new StringBuilder(className);
        sb.append("(");
        stringBuild(sb, root);
        sb.append(")");
        return sb.toString();
    }

    private static void stringBuild(StringBuilder sb, Node node) {
        if(node==null)
            sb.append("null");
        else {
            sb.append(node.getClass().getSimpleName());
            sb.append("(");
            sb.append(node.bin);
            sb.append(", ");
            sb.append(node.height);
            sb.append(", ");
            sb.append(node.maxRemainingCapacity);
            sb.append(", ");
            stringBuild(sb, node.left);
            sb.append(", ");
            stringBuild(sb, node.right);
            sb.append(")");
        }
    }
}

class LinearBinPacking {
    public static List<Bin> linearBinPacking(int initialCapacity, List<Integer> weights) {
        // todo
        //  SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
        //  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
        return null;
    }
	
	public static Iterable<Integer> allWeights(Iterable<Bin> bins) {
        // todo
        //  SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
        //  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
        return null;		
	}
}