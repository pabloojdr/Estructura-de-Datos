/**
 * Estructuras de Datos. Grado en Informática, IS e IC. UMA.
 * Examen de Febrero 2015.
 *
 * Implementación del TAD Deque
 *
 * Apellidos:
 * Nombre:
 * Grado en Ingeniería ...
 * Grupo:
 * Número de PC:
 */

package dataStructures.doubleEndedQueue;

public class LinkedDoubleEndedQueue<T> implements DoubleEndedQueue<T> {

    private static class Node<E> {
        private E elem;
        private Node<E> next;
        private Node<E> prev;

        public Node(E x, Node<E> nxt, Node<E> prv) {
            elem = x;
            next = nxt;
            prev = prv;
        }
    }

    private Node<T> first, last;

    /**
     *  Invariants:
     *  if queue is empty then both first and last are null
     *  if queue is non-empty:
     *      * first is a reference to first node and last is ref to last node
     *      * first.prev is null
     *      * last.next is null
     *      * rest of nodes are doubly linked
     */

    /**
     * Complexity: 0(1)
     */
    public LinkedDoubleEndedQueue() {
        // TODO Auto-generated method stub
        first = null;
        last = null;
    }

    /**
     * Complexity: 0(1)
     */
    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return first == null && last == null;
    }

    /**
     * Complexity: 0(1)
     */
    @Override
    public void addFirst(T x) {
        // TODO Auto-generated method stub
        if(isEmpty()){
            Node<T> node = new Node<>(x, null, null);
            first = node;
            last = node;
        } else {
            Node<T> node2 = new Node<>(x, first, null);
            first.prev = node2;
            first = node2;
        }
    }

    /**
     * Complexity: 0(1)
     */
    @Override
    public void addLast(T x) {
        // TODO Auto-generated method stub
        if(isEmpty()){
            Node<T> node = new Node<>(x, null, null);
            first = node;
            last = node;
        } else {
            Node<T> node2 = new Node<>(x, null, last);
            last.next = node2;
            last = node2;
        }
    }

    /**
     * Complexity: 0(1)
     */
    @Override
    public T first() {
        // TODO Auto-generated method stub
        return first.elem;
    }

    /**
     * Complexity: 0(1)
     */
    @Override
    public T last() {
        // TODO Auto-generated method stub
        return last.elem;
    }

    /**
     * Complexity: 0(1)
     */
    @Override
    public void deleteFirst() {
        // TODO Auto-generated method stub
        if(isEmpty()){
            throw new EmptyDoubleEndedQueueException("delete sobre pito vacío");
        } else if(!isEmpty() && first.next == null){
            first = null;
            last = null;
        }else {
            first = first.next;
            first.prev = null;
        }
    }

    /**
     * Complexity: 0(1)
     */
    @Override
    public void deleteLast() {
        // TODO Auto-generated method stub
        if(isEmpty()){
            throw new EmptyDoubleEndedQueueException("delete sobre pito vacío");
        } else if(!isEmpty() && last.prev == null){
            first = null;
            last = null;
        } else {
            last = last.prev;
            last.next = null;
        }
    }

    /**
     * Returns representation of queue as a String.
     */
    @Override
    public String toString() {
    String className = getClass().getName().substring(getClass().getPackage().getName().length()+1);
        String s = className+"(";
        for (Node<T> node = first; node != null; node = node.next)
            s += node.elem + (node.next != null ? "," : "");
        s += ")";
        return s;
    }
}
