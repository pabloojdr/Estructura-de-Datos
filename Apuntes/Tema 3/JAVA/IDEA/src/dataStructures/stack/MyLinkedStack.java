package dataStructures.stack;

import dataStructures.stack.EmptyStackException;
import dataStructures.stack.Stack;

import java.util.StringJoiner;

public class MyLinkedStack<T> implements Stack<T>{

    private static class Node<E>{
        E elem;
        Node <E> next;
        Node(E x, Node<E> node){
            elem = x;
            next = node;
        }
    }

    private Node<T> top;

    public MyLinkedStack(){
        top = null;
    }

    @Override
    public boolean isEmpty() {
        return top == null;
    }

    @Override
    public void push(T x) {
       top = new Node<>(x, top);
    }

    @Override
    public T top() {
        if(isEmpty()){
            throw new EmptyStackException("top sobre vacía");
        }
        return top.elem; //puede que la pila esté vacía y esto nos de error, por tanto, lo comprobamos con un if
    }

    @Override
    public void pop() {
        if(isEmpty()){
            throw new EmptyStackException("pop sobre vacía");
        }
        top = top.next;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",","Pila(",")");
        Node<T> current = top;
        while(current != null){
            sj.add(current.elem.toString());
            current = current.next;
        }
        return sj.toString();
    }
}
