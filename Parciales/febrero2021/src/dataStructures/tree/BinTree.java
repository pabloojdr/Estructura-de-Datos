/* *
 * Estructuras de Datos.
 * 2.º Grado en Ingeniería Informática, del Software y Computadores. UMA.
 *
 * Práctica evaluable 7. Enero 2021.
 *
 * Apellidos, Nombre:
 * Grupo:
 */

package dataStructures.tree;

import dataStructures.list.List;

public class BinTree<T extends Comparable<? super T>> {

    private static class Tree<E> {
        private E elem;
        private Tree<E> left;
        private Tree<E> right;

        public Tree(E e, Tree<E> l, Tree<E> r) {
            elem = e;
            left = l;
            right = r;
        }
    }

    private Tree<T> root;

    public BinTree() {
        root = null;
    }

    public BinTree(T x) {
        root = new Tree<>(x, null, null);
    }

    public BinTree(T x, BinTree<T> l, BinTree<T> r) {
        root = new Tree<>(x, l.root, r.root);
    }

    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Returns representation of tree as a String.
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + toStringRec(this.root) + ")";
    }

    private static String toStringRec(Tree<?> tree) {
        return tree == null ? "null" : "Node<" + toStringRec(tree.left) + ","
                + tree.elem + "," + toStringRec(tree.right)
                + ">";
    }

    /**
     * Returns a String with the representation of tree in DOT (graphviz).
     */
    public String toDot(String treeName) {
        final StringBuffer sb = new StringBuffer();
        sb.append(String.format("digraph %s {\n", treeName));
        sb.append("node [fontname=\"Arial\", fontcolor=red, shape=circle, style=filled, color=\"#66B268\", fillcolor=\"#AFF4AF\" ];\n");
        sb.append("edge [color = \"#0070BF\"];\n");
        toDotRec(root, sb);
        sb.append("}");
        return sb.toString();
    }

    private void toDotRec(Tree<T> current, StringBuffer sb) {
        if (current != null) {
            final int currentId = System.identityHashCode(current);
            sb.append(String.format("%d [label=\"%s\"];\n", currentId, current.elem));
            if (!isLeaf(current)) {
                processChild(current.left, sb, currentId);
                processChild(current.right, sb, currentId);
            }
        }
    }

    private static <T extends Comparable<? super T>> boolean isLeaf(Tree<T> current) {
        return current != null && current.left == null && current.right == null;
    }

    private void processChild(Tree<T> child, StringBuffer sb, int parentId) {
        if (child == null) {
            sb.append(String.format("l%d [style=invis];\n", parentId));
            sb.append(String.format("%d -> l%d;\n", parentId, parentId));
        } else {
            sb.append(String.format("%d -> %d;\n", parentId, System.identityHashCode(child)));
            toDotRec(child, sb);
        }
    }

    // Ejercicio 1

    public T maximum() {
        // TODO
        return maximumRec(root);
    }

    private <T extends Comparable<? super T>> T maximumRec(Tree<T> tree){
        if(tree == null){
            throw new BinTreeException("El arbol está vacío");
        } else {
            T res = tree.elem;
            T resl = maximumRec(tree.left);
            T resr = maximumRec(tree.right);

            if(res.compareTo(resl) < 0){
                res = resl;
            } else if(res.compareTo(resr) < 0){
                res = resr;
            }
            return res;
        }
    }

    // Ejercicio 2

    public int numBranches() {
        return numBranchesRec(root);
    }

    private int numBranchesRec(Tree<T> tree){
        if(tree == null){
            return 0;
        } else if(tree.right == null && tree.right == null){
            return 1;
        } else {
            return numBranchesRec(tree.left) + numBranchesRec(tree.right);
        }
    }

    // Ejercicio 3

    public List<T> atLevel(int i) {
        // TODO
        return atLevelRec(i, root);
    }

    private List<T> atLevelRec(int i, Tree<T> tree) {
        List<T> list = new dataStructures.list.ArrayList<>();
        List<T> list2 = new dataStructures.list.ArrayList<>();

        if(tree == null) {
          return list;
        } else if (i == 0) {
            list.append(tree.elem);
        } else {
            list = atLevelRec(i-1, tree.left);
            list2 = atLevelRec(i-1, tree.right);

            for(int j = 0; j < list2.size(); j++){
                list.append(list2.get(j));

            }
        }

        return list;
    }


    // Ejercicio 4

    public void rotateLeftAt(T x) {
        // TODO
        root = rotateLeftAtREC(x, root);
    }

    private Tree<T> rotateLeftRoot(Tree<T> node){
        if(node.right!=null){
            T x = node.elem;
            T y = node.right.elem;
            Tree<T> A = node.left;
            Tree<T> B = node.right.left;
            Tree<T> C = node.right.right;

            return new Tree<>(y, new Tree<>(x,A,B), C);
        }else{
            return node;
        }
    }
    private Tree<T> rotateLeftAtREC(T x, Tree<T> node){
        if(node==null || !isElem(x,node)){
            return node;
        }
        if(x.compareTo(node.elem)==0){
            return rotateLeftRoot(node);
        }else if(x.compareTo(node.elem) < 0){
            return new Tree<>(node.elem,rotateLeftAtREC(x,node.left), node.right );
            //return rotateLeftAtREC(x,node.left);
        }else {
            return new Tree<>(node.elem, node.left,rotateLeftAtREC(x,node.right) );
            //return rotateLeftAtREC(x,node.right);
        }

    }

    private boolean isElem(T x, Tree<T> node){
        if(node==null){
            return false;
        }else if(x.compareTo(node.elem)==0){
            return true;
        }else {
            return isElem(x,node.right) || isElem(x, node.left);
        }
    }

    // Ejercicio 5

    public void decorate(T x) {
        // TODO
        root = decorateRec(x, root);
    }

    private <T extends Comparable<? super T>> Tree<T> decorateRec(T x, Tree<T> tree){
        if(tree == null){
            return new Tree<>(x, null, null);
        } else {
            if(tree.right == null){
                tree.right = new Tree<>(x, null,null);
                tree.left = decorateRec(x, tree.left);
            } else if(tree.left == null) {
                tree.left = new Tree<>(x, null, null);
                tree.right = decorateRec(x, tree.right);
            } else {
                tree.right = decorateRec(x, tree.right);
                tree.left = decorateRec(x, tree.left);
            }
        }
        return tree;
    }
}
