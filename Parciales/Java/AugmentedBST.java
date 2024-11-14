import java.util.NoSuchElementException;

public class AugmentedBST<T extends Comparable<? super T>> {
    //class for implementing one node in search tree
    private static class Node<E>{
        E key; // value stored in node
        int weight; // weigth of node: total number of elements stored in tree rooted at this node
        Node<E> left; //left child
        Node<E> right; // right child

        public Node(E k){
            key = k;
            weight = 1;
            left = null;
            right = null;
        }
    }

    private Node<T> root; //reference to root node of binary search tree

    /*
    Creates an empty unbalanced ninary search tree
    Time complexity: O(1)
     */
    public AugmentedBST(){
        root = null;
    }
    public boolean isEmpty(){
        return root == null;
    }

    public int size(){
        return weight(root);
    }

    private int weight(Node<T> t){
       return t == null ? 0 : t.weight;
    }


    public void insert(T k){
        root = insertRec(T k, root);
    }

    private Node<T> insertRec(T k, Node<T> node){
        int comp = k.compareTo(node.key);
        if(node == null){
            return new Node<T>(k);
        } else if(comp == 0) {
            node.key = k;
        } else if(comp < 0){
            node.left = insertRec(k, node.left);
        } else{
            node.right = insertRec(k, node.right);
        }
        return node;
    }

    public T search(T key){
        return searchRec(key, root);
    }

    private <T extends Comparable<? super T>> T searchRec(T k, Node<T> node){
        int comp = k.compareTo(node.key);
        if(node == null){
            return null;
        } else if(comp == 0) {
            return node.key;
        } else if(comp < 0) {
            return searchRec(k, node.left);
        } else {
            return searchRec(k, node.right);
        }
    }

    public boolean isElem(T key){
        return isElemRec(key, root);
    }

    private boolean isElemRec(T k, Node<T> node){
        int comp = k.compareTo(node.key);
        if(node == null){
            return false;
        } else if(comp == 0){
            return true;
        } else if(comp < 0){
            return isElemRec(k, node.left);
        } else {
            return isElemRec(k, node.right);
        }
    }

    public void delete(T key){
        root = deleteRec(key, root);
    }

    private Node<T> deleteRec(T k, Node<T> node){
        if(node == null){
            ;
        } else {
            int comp = k.compareTo(node.key);
            if(comp < 0){
                node.left = deleteRec(k, node.left);
            } else if(comp > 0){
                node.right = deleteRec(k, node.right);
            } else {
                if(node.left == null){
                    node = node.right;
                } else if(node.right == null){
                    node = node.left;
                } else {
                    node = split(node, node.right);
                }
            }
        }

        if(node != null){
            node.weight = 1 + weight(node.left) + weight(node.right);
        }
        return node;
    }

    private static <T extends Comparable<? super T>> Node<T> split(Node<T> temp, Node<T> node){
        if(node.left == null){
            temp.key = node.key;
            return node.right;
        } else {
            node.left = split(temp, node.left);
            return node;
        }
    }

    @Override
    public String toString(){
        String className = getClass().getName().substring(getClass().getPackage().getName().length()+1);
        return className + "(" + toStringREC(this.root) + ")";
    }

    private static String toStringREC(Node<?> tree){
        return tree == null ? "null" : "Node<"+toStringREC(tree.left)+","+tree.key+","+tree.weight+","+toStringREC(tree.right)+">";
    }

    // returns i-th smallest key in BST (i=0 means returnung the samallest key in tree, i=1 the next one and so on
    public T select(int i) {
        return selectRec(i, root);
    }

    private static <T extends Comparable
    // returns largest key in BST whose value is less than or equal to k
    public T floor(T k){
        return null;
    }
    //returns smallest key in BST whose value is greater than or equal to k
    public T ceiling(T k){
        return null;
    }
    //returns number of keys in BST whose values are less than k
    public int rank(T k){
        return -1;
    }
    //returns number of keys in BST whose values are in range lo to hi
    public int size(T low, T high){
        return -1;
    }
    //returns minimum key stored in BST
    public T min(){
        return null;
    }
    //returns maximun key stored in BST
    public T max(){
        return null;
    }
    public void deleteMin(){

    }
    public void deleteMax(){

    }
}
