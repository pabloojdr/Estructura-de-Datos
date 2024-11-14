/**
 * Student's name:
 *
 * Student's group:
 */

import dataStructures.list.ArrayList;
import dataStructures.list.List;
import dataStructures.list.LinkedList;

import java.util.Iterator;


class Bin {
    private int remainingCapacity; // capacity left for this bin
    private List<Integer> weights; // weights of objects included in this bin

    public Bin(int initialCapacity) {
        // todo
        remainingCapacity = initialCapacity;
        weights = new ArrayList<>();
    }

    // returns capacity left for this bin
    public int remainingCapacity() {
        // todo
        return remainingCapacity;
    }

    // adds a new object to this bin
    public void addObject(int weight) {
        // todo
        if(weight > remainingCapacity){
            System.out.println("El objeto no entra");
        } else {
            weights.append(weight);
            remainingCapacity -= weight;
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
            Node nodo = new Node();

            Bin c = bin;
            Node l = left;
            Bin x = right.bin;
            Node r1 = right.left;
            Node r2 = right.right;

            nodo.bin = x;
            nodo.left.bin = c;
            nodo.left.left = l;
            nodo.left.right = r1;
            nodo.right = r2;
            nodo.setHeight();
            nodo.setMaxRemainingCapacity();

            return nodo;
        }
    }

    private static int height(Node node) {
        // todo
        if(node == null){
            return 0;
        } else {
            return node.height;
        }
    }

    private static int maxRemainingCapacity(Node node) {
        // todo
        if(node == null){
            return 0;
        } else {
            return node.maxRemainingCapacity;
        }
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

    private Node addNewBinRec(Bin bin, Node root){
        if(root == null){
            root.bin = bin;
            return root;
        } else if(root.right == null) {
            root.right.bin = bin;
        } else {
            root.right = addNewBinRec(bin, root.right);

            if(height(root.right) - height(root.left) > 1){
                root = root.rotateLeft();
            }
        }
        return root;
    }

    // adds an object to first suitable bin. Adds
    // a new bin if object cannot be inserted in any existing bin
    public void addFirst(int initialCapacity, int weight) {
        // todo
        root = addFirstRec(initialCapacity, weight, root);
    }

    private Node addFirstRec(int initialCapacity, int weight, Node root){
        if(weight > root.maxRemainingCapacity || root == null){
            if(root.right == null){
                Bin bin = new Bin(initialCapacity);
                bin.addObject(weight);
                root = addNewBinRec(bin, root);
            } else {
                root.right = addFirstRec(initialCapacity, weight, root.right);
            }
        } else if(weight <= maxRemainingCapacity(root.left)){
                Bin bin = new Bin(initialCapacity);
                bin.addObject(weight);
                root.left = addNewBinRec(bin, root.left);
        } else if(weight <= root.bin.remainingCapacity()){
            root.bin.addObject(weight);
        } else {
            Bin bin = new Bin(initialCapacity);
            bin.addObject(weight);
            root.right = addNewBinRec(bin, root.right);
        }

        return root;
    }
    public void addAll(int initialCapacity, int[] weights) {
        // todo
        addAllRec(initialCapacity, weights, root);
    }

    private Node addAllRec(int initialCapacity, int[] weights, Node root){
        for(int w : weights){
            root = addFirstRec(initialCapacity, w, root);
        }

        return root;
    }

    public List<Bin> toList() {
        // todo
        return toListRec(root);
    }

    private List<Bin> toListRec(Node root){
        if(root == null){
            return null;
        } else {
            List<Bin> listl = toListRec(root.left);
            listl.append(root.bin);

            List<Bin> listr = toListRec(root.right);

            for(Bin b : listr){
                listl.append(b);
            }

            return listl;
        }
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