package dataStructures.set;

import java.util.Iterator;
import java.util.*;

public class MySortedLinkedSet <T extends Comparable<? super T>> implements Set<T>{

    private static class Node<E>{
        E elem;
        Node<E> next;
        Node(E x, Node<E> nextNode){
            elem = x;
            next = nextNode;
        }
    }

    private Node<T> first;
    private int size;

    public MySortedLinkedSet(){
        first = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void insert(T x) {
        Node<T> previous = null;
        Node<T> current = first;
        while(current != null && current.elem.compareTo(x) < 0){
            previous = current;
            current = current.next;
        }

        if(current == null || !current.elem.equals(x)){
            size++;
            Node<T> nuevo = new Node<>(x, current);
            if(previous != null) {
                previous.next = nuevo;
            } else{
                first = null;
            }
        }
    }

    @Override
    public boolean isElem(T x) {
        Node<T> current = first;
        while(current != null && current.elem.compareTo(x) < 0){
            current = current.next;
        }


        return current != null && current.elem.equals(x);
    }

    @Override
    public void delete(T x) {
        Node<T> previous = null;
        Node<T> current = first;
        while(current != null && current.elem.compareTo(x) < 0){
            previous = current;
            current = current.next;
        }
        if(current != null && current.elem.equals(x)){
            size--;
            if(previous != null){
                previous.next = current.next;
            } else{
                first = current.next;
            }
        }
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",", "Conjunto(", ")");
        for(Node<T> current = first; current != null; current = current.next){
            sj.add(current.elem.toString());
        }
        return sj.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new MySortedLinkedSetIterator();
    }

    private class MySortedLinkedSetIterator implements Iterator<T> {
        Node<T> current;

        MySortedLinkedSetIterator(){
            current = first;
        }
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if(!hasNext()){
                throw new NoSuchElementException("iterador agotado");
            }
            T result = current.elem;
            current = current.next;
            return result;
        }
    }
}
