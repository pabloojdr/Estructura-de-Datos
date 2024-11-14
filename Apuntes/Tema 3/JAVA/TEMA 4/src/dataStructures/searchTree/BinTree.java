/*
  Estructuras de Datos
  Grado en Ingeniería Informática, del Software y de Computadores
  Tema 4. Árboles
  Pablo López
*/

package dataStructures.searchTree;

import dataStructures.list.ArrayList;
import dataStructures.list.LinkedList;
import dataStructures.list.List;

import java.util.Collection;
import java.util.Collections;
import java.util.NoSuchElementException;

public class BinTree<T> {

    private static class Tree<E> {
        private final E elem;
        private final Tree<E> left;
        private final Tree<E> right;

        public Tree(E e, Tree<E> l, Tree<E> r) {
            elem = e;
            left = l;
            right = r;
        }
    }

    private final Tree<T> root;

    public BinTree() {
        root = null;
    }

    public BinTree(T x) {
        root = new Tree<>(x, null, null);
    }

    public BinTree(T x, BinTree<T> l, BinTree<T> r) {
        root = new Tree<>(x, l.root, r.root);
    }

    private static boolean isLeaf(Tree<?> current) {
        return current != null && current.left == null && current.right == null;
    }

    public int weight() {
        return weightRec(root);
    }

    private int weightRec(Tree<?> t) {
        if (t == null) {
            return 0;
        } else if (isLeaf(t)) {
            return 1;
        } else {
            return 1 + weightRec(t.left) + weightRec(t.right);
        }
    }

    public int height() {
        return heightRec(root);
    }

    private int heightRec(Tree<?> t) {
        if (t == null) {
            return 0;
        } else if (isLeaf(t)) {
            return 1;
        } else {
            int heightL;
            int heightR;

            heightL = heightRec(t.left);
            heightR = heightRec(t.right);

            return (heightL > heightR) ? (heightL + 1) : (heightR + 1);
        }
    }

    public List<T> border() {
        // TODO
        return borderRec(root);
    }

    private List<T> borderRec(Tree<T> t) {
        List<T> list = new ArrayList<>();
        List<T> list1 = new ArrayList<>();

        if (t == null) {
            return list;
        } else if (isLeaf(t)) {
            list.append(t.elem);
        } else {
            list = borderRec(t.left);
            list1 = borderRec(t.right);

            for(int i = 0; i < list1.size(); i++) {
                list.append(list1.get(i));
            }
        }



        return list;
    }

    public boolean isElem(T x) {
        // TODO
        return isElemRec(x, root);
    }

    private boolean isElemRec(T x, Tree<T> t) {
        if (t == null) {
            return false;
        } else if (x.equals(t.elem)) {
            return true;
        } else {
            return isElemRec(x, t.right) || isElemRec(x, t.left);
        }
    }

    public List<T> atLevel(int i) {
        return atLevelRec(i, root);
    }

    private List<T> atLevelRec(int i, Tree<T> t) {
        List<T> list = new ArrayList<>();
        List<T> list2 = new ArrayList<>();
        if (t == null) {
            return list;
        } else if (i == 0) {
            list.append(t.elem);
        } else {
            list = atLevelRec(i - 1, t.left);
            list2 = atLevelRec(i - 1, t.right);

            for (int j = 0; j < list2.size(); j++) {
                list.append(list2.get(j));
            }
        }


        return list;
    }

    public List<T> inOrder() {
        // TODO
        return inOrderRec(root);
    }

    private List<T> inOrderRec(Tree<T> t) {
        List<T> list = new ArrayList<>();
        List<T> list2;

        if (t == null) {
            return list;
        }else {
            list = inOrderRec(t.left);
            list2 = inOrderRec(t.right);
            list.append(t.elem);
            for (int i = 0; i < list2.size(); i++) {
                list.append(list2.get(i));
            }
        }
        return list;
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
        sb.append(String.format("digraph \"%s\" {\n", treeName));
        sb.append("node [fontname=\"Arial\", fontcolor=red, shape=circle, style=filled, color=\"#66B268\", fillcolor=\"#AFF4AF\" ];\n");
        sb.append("edge [color = \"#0070BF\"];\n");
        toDotRec(root, sb);
        sb.append("}");
        return sb.toString();
    }

    private static void toDotRec(Tree<?> current, StringBuffer sb) {
        if (current != null) {
            final int currentId = System.identityHashCode(current);
            sb.append(String.format("%d [label=\"%s\"];\n", currentId, current.elem));
            if (!isLeaf(current)) {
                processChild(current.left, sb, currentId);
                processChild(current.right, sb, currentId);
            }
        }
    }

    private static void processChild(Tree<?> child, StringBuffer sb, int parentId) {
        if (child == null) {
            sb.append(String.format("l%d [style=invis];\n", parentId));
            sb.append(String.format("%d -> l%d;\n", parentId, parentId));
        } else {
            sb.append(String.format("%d -> %d;\n", parentId, System.identityHashCode(child)));
            toDotRec(child, sb);
        }
    }
}
