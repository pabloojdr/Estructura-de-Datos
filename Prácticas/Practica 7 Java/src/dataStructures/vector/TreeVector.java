/*
 * Estructuras de Datos. 2º Curso. ETSI Informática. UMA
 * PRACTICA 7. Vectores
 * APELLIDOS, NOMBRE:
 */

package dataStructures.vector;

import dataStructures.list.List;

import java.util.Iterator;


public class TreeVector<T> {

    private final int size;
    private final Tree<T> root;

    private interface Tree<E> {
        E get(int index);

        void set(int index, E x);

        List<E> toList();
    }

    private static class Leaf<E> implements Tree<E> {
        private E value;

        public Leaf(E x) {
            value = x;
        }

        @Override
        public E get(int i) {
            return value;
        }

        @Override
        public void set(int i, E x) {
            value = x;
        }

        @Override
        public List<E> toList() {
            List<E> list = new dataStructures.list.LinkedList<>();
            list.append(value);
            return list;
        }
    }

    private static class Node<E> implements Tree<E> {
        private Tree<E> left;
        private Tree<E> right;

        public Node(Tree<E> l, Tree<E> r) {
            left = l;
            right = r;
        }

        @Override
        public E get(int i) {
            int j = i / 2;
            if(i % 2 == 0){
                return right.get(j);
            } else {
                return left.get(j);
            }
        }

        @Override
        public void set(int i, E x) {
            int j = i / 2;
            if(i % 2 == 0){
                right.set(j, x);
            } else {
                left.set(j, x);
            }
        }

        @Override
        public List<E> toList() {
            return intercalate(left.toList(), right.toList());
        }
    }

    public TreeVector(int n, T value) {
        // TODO
       if(n < 0){
           throw new VectorException("n no puede ser negativo");
       } else {
           this.size = (int) Math.pow(2,n);
           root = mkTree(n, value);
       }
    }

    private Tree<T> mkTree (int n, T value){
        if(n == 0){
            return new Leaf<>(value);
        } else {
            Tree<T> l = mkTree(n-1, value);
            Tree<T> r = mkTree(n-1, value);
            return new Node<>(l,r);
        }
    }

    public int size() {
        // TODO
        return size;
    }

    public T get(int i) {
        if(i < 0 || i >= size){
            throw new VectorException("El índice no es correcto");
        } else {
            return root.get(i);
        }
    }

    public void set(int i, T x) {
        // TODO
        if (i < 0 || i >= size) {
            throw new VectorException("El índice no es correcto");
        } else {
            root.set(i, x);
        }
    }

    public List<T> toList() {
        // TODO
        return root.toList();
    }

    protected static <E> List<E> intercalate(List<E> xs, List<E> ys) {
        List<E> zs = new dataStructures.list.LinkedList<>();
        Iterator<E> it1 = xs.iterator();
        Iterator<E> it2 = ys.iterator();
        while(it1.hasNext() && it2.hasNext()){
            E x = it1.next();
            E y = it2.next();
            zs.append(x);
            zs.append(y);
        }
        return zs;
    }

    static protected boolean isPowerOfTwo(int n) {
        // TODO
        int i = 1;
        if (n != 0) {
            int potencia = 1;
            while (i > 0 && n > potencia) {
                if (potencia == n) {
                    return true;
                } else {
                    i++;
                    potencia = 2 ^ i;
                }
            }

        }
        return false;
    }

    public static <E> TreeVector<E> fromList(List<E> l) {
        // TODO
        if(isPowerOfTwo(l.size())){
           for(int i = 0; i < l.size(); i++)
        }


        return tree;
    }
}
