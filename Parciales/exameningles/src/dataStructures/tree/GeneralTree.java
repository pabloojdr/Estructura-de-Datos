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

import dataStructures.list.ArrayList;
import dataStructures.list.List;
import dataStructures.list.LinkedList;

import java.util.Iterator;

public class GeneralTree<T extends Comparable<? super T>> {

    private static class Tree<E> {
        private E elem;
        private List<Tree<E>> list;

        public Tree(E e, List<Tree<E>> l) {
            elem = e;
            list = l;
        }

    }

    private Tree<T> root;

    public GeneralTree() {
        root = null;
    }

    public GeneralTree(T x) {
        root = new Tree<T>(x, new LinkedList<Tree<T>>());
    }
    public GeneralTree(T x, List<Tree<T>> lista){
        root = new Tree<T>(x,lista);
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
        return tree == null ? "null" : "Node<" + tree.elem + "," + tree.list.toString() + ">";
    }

    /**
     * Returns a String with the representation of tree in DOT (graphviz).
     */

    private static <T extends Comparable<? super T>> boolean isLeaf(Tree<T> current) {
        return current != null && current.list == null;
    }

    private void addChild(Tree<T> child){
        //TODO
        root.list.append(child);
    }

    // Ejercicio 1
    /*
    Funcion que cuente cuantas veces aparece un elemento en el general tree
     */
    public int count(T x){
        //TODO
        return countRec(x, root);
    }

    private int countRec(T x, Tree<T> node){
        int suma = 0;
        if(node == null) {
            return 0;
        } else if(node.elem.compareTo(x) == 0) {
            suma += 1;
            if(node.list != null){
                Iterator<Tree<T>> it1 = node.list.iterator();
                while(it1.hasNext()){
                    suma+=countRec(x, it1.next());
                }
            }

        } else {
            if(node.list != null){
                Iterator<Tree<T>> it2 = node.list.iterator();
                while (it2.hasNext()) {
                    suma += countRec(x, it2.next());
                }
            }

        }
        return suma;
    }

    //Ejercicio 2
    /*
    Static method rightSpine que toma como argumento un general tree y devuelve una lista con todos los elementos de su espina derecha
     */
    public List<T> rightSpine(GeneralTree<T> tree){
        //TODO
        return rightSpineRec(tree.root);
    }

    private List<T> rightSpineRec(Tree<T> node){
        int size = node.list.size();
        List<T> lista = new ArrayList<>();

        if(node == null){
            return lista;
        }else if(isLeaf(node)){
            lista.append(node.elem);
            return lista;
        } else {
            lista = rightSpineRec(node.list.get(size-1));
            lista.prepend(node.elem);
        }

        return lista;
    }



}
