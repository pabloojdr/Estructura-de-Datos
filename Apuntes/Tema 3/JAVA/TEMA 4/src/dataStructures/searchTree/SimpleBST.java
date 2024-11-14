/*
  Estructuras de Datos
  Grado en Ingeniería Informática, del Software y de Computadores
  Tema 4. Árboles
  Pablo López
*/

package busqueda;

public class SimpleBST<K extends Comparable<? super K>, V> {

    private static class Tree<C, D> {
        C key;
        D value;
        Tree<C, D> left;
        Tree<C, D> right;

        public Tree(C key, D value, Tree<C, D> left, Tree<C, D> right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    private Tree<K, V> root;

    public SimpleBST() {
        root = null;
    }

    public void insert(K k, V v) {
        insertRec(k, v, root);
    }

    private static <C extends Comparable <? super C>, D> Tree<C,D> insertRec(C k, D v, Tree<C, D> tree){
        if(tree == null){
            return new Tree<C,D>(k, v, null,null);
        } else {
          int comp = k.compareTo(tree.key);

          if(comp == 0){
              tree.value = v;
          } else if(comp < 0){
              tree.left = insertRec(k, v, tree.left);
          } else{
              tree.right = insertRec(k, v, tree.right);
          }
          return tree;
        }
    }

    public V search(K k) {
        // TODO
        return searchRec(k, root);
    }

    private static <C extends Comparable<? super C>, D> D searchRec(C k, Tree<C, D> tree){
        if(tree == null){
            return null;
        } else {
            int comp = k.compareTo(tree.key);

            if(comp == 0){
                return tree.value;
            }else if(comp < 0){
                return searchRec(k, tree.left);
            } else {
                return searchRec(k, tree.right);
            }
        }
    }

    public void delete(K k) {
        // TODO
        deleteRec(k, root);
    }

    private static <C extends Comparable<? super C>, D> Tree<C, D> deleteRec(C k, Tree<C, D> tree){
        if(tree != null){
         int comp = k.compareTo(tree.key);
         if(comp == 0){
             tree = deleteRoot(tree);
         } else if(comp < 0){
             tree.left = deleteRec(k, tree.left);
         } else {
             tree.right = deleteRec(k, tree.right);
         }
        }
        return tree;
    }

    private static <C extends Comparable<? super C>, D> Tree<C,D> deleteRoot(Tree<C, D> tree){
        if(tree.left == null){
            return tree.right;
        } else if (tree.right == null){
            return tree.left;
        } else {
            C minKey = minimum(tree.right);
            tree.key = minKey;
            tree.value = searchRec(minKey, tree);
            tree.right = deleteRec(minKey, tree);
            return tree;

        }
    }

    private static <C extends Comparable<? super C>, D> C minimum(Tree<C,D> tree){
        return tree.left == null ? tree.key : minimum(tree.left);
    }
    /**
     * Returns representation of tree as a String.
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + toStringRec(root) + ")";
    }

    private static <K,V> String toStringRec(Tree<K,V> tree) {
        if (tree == null) {
            return "";
        }
        else {
            String left = toStringRec(tree.left);
            left += String.format("(%s, %s) ", tree.key, tree.value);
            return left + toStringRec(tree.right);
        }
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

    private static void toDotRec(Tree<?,?> current, StringBuffer sb) {
        if (current != null) {
            final int currentId = System.identityHashCode(current);
            sb.append(String.format("%d [label=\"%s\"];\n", currentId, current.key));
            if (!isLeaf(current)) {
                processChild(current.left, sb, currentId);
                processChild(current.right, sb, currentId);
            }
        }
    }

    private static boolean isLeaf(Tree<?,?> current) {
        return current != null && current.right == null && current.left == null;
    }

    private static void processChild(Tree<?,?> child, StringBuffer sb, int parentId) {
        if (child == null) {
            sb.append(String.format("l%d [style=invis];\n", parentId));
            sb.append(String.format("%d -> l%d;\n", parentId, parentId));
        } else {
            sb.append(String.format("%d -> %d;\n", parentId, System.identityHashCode(child)));
            toDotRec(child, sb);
        }
    }
}
