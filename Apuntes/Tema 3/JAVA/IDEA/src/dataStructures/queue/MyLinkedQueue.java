package dataStructures.queue;

import dataStructures.queue.EmptyQueueException;
import dataStructures.queue.Queue;
import java.util.*;

public class MyLinkedQueue<T> implements Queue<T> {
    private static class Node<E> {
        E elem;
        Node<E> next;

        Node(E x, Node<E> node) {
            elem = x;
            next = node;
        }
    }

    private Node<T> first;
    private Node<T> last;

    public MyLinkedQueue() {
        first = null;
        last = null;
    }
    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public void enqueue(T x) {
        Node<T> nuevo = new Node<>(x, null);
        if(last != null){
            last.next = nuevo;
        }else{
            first = nuevo;
        }
        last = nuevo;
    }

    @Override
    public T first() {
        if(isEmpty()){
            throw new EmptyQueueException("first sobre vacía");
        }
        return first.elem;
    }

    @Override
    public void dequeue() {
        if(isEmpty()){
            throw new EmptyQueueException("dequeue sobre vacía");
        }
        first = first.next;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",", "Cola(", ")");
        for(Node <T> current = first; current != null; current = current.next){
            sj.add(current.elem.toString());
        }
        return sj.toString();
    }
}

