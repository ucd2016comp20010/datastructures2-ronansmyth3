package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;

public class CircularlyLinkedList<E> implements List<E> {

    private class Node<T> {
        private final T data;
        private Node<T> next;

        public Node(T e, Node<T> n) {
            data = e;
            next = n;
        }

        public T getData() {
            return data;
        }

        public void setNext(Node<T> n) {
            next = n;
        }

        public Node<T> getNext() {
            return next;
        }
    }

    private Node<E> tail = null;
    private int size = 0;

    public CircularlyLinkedList() {

    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int i) {
        // check if valid
        if(i < 0 || i >= size)
        {
            throw new IndexOutOfBoundsException();
        }
        // loop till node
        Node<E> current = tail.next;
        for(int j = 0; j < i; j++)
        {
            current = current.next;
        }
        return current.getData();
    }

    /**
     * Inserts the given element at the specified index of the list, shifting all
     * subsequent elements in the list one position further to make room.
     *
     * @param i the index at which the new element should be stored
     * @param e the new element to be stored
     */
    @Override
    public void add(int i, E e) {
        // check if the position is valid
        if(i < 0 || i> size)
        {
            throw new IndexOutOfBoundsException();
        }

        // base case if its first element set head to new element
        if(i == 0)
        {
            addFirst(e);
            // return to stop it from duplicating size++
            return;
        }
        // if its the last element
        if(i == size)
        {
            addLast(e);
            return;
        }
        // loop and update pointers
        Node<E> previous = tail.next;
        Node<E> current = tail.next;
        for(int j = 0; j < i; j++)
        {
            previous = current;
            current = current.next;
        }
        Node<E> newNode = new Node<>(e, current);
        previous.next = newNode;
        size++;

    }

    @Override
    public E remove(int i) {
        // check if the position is valid
        if(i < 0 || i >= size)
        {
            throw new IndexOutOfBoundsException();
        }
        // if removing first element call method
        if(i == 0)
        {
            return removeFirst();
        }
        // if we are removing the last element call method
        if(i == size - 1)
        {
            return removeLast();
        }

        // loop and update pointers
        Node<E> previous = tail.next;
        Node<E> current = tail.next;
        for(int j = 0; j < i - 1; j++)
        {
            previous = current;
            current = current.next;
        }
        // set the previous.next pointer to current.next
        // return removed element
        previous.next = current.next;
        size--;
        return current.getData();
    }

    public void rotate() {
        tail = tail.next;
    }

    private class CircularlyLinkedListIterator<E> implements Iterator<E> {
        Node<E> curr = (Node<E>) tail;

        @Override
        public boolean hasNext() {
            return curr != tail;
        }

        @Override
        public E next() {
            E res = curr.data;
            curr = curr.next;
            return res;
        }

    }

    @Override
    public Iterator<E> iterator() {
        return new CircularlyLinkedListIterator<E>();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E removeFirst() {
        // if empty remove nothing
        if(isEmpty())
        {
            return null;
        }
        // check if there is only a single element
        if(size == 1)
        {
            E oldHead = tail.next.getData();
            // set head to the next node
            tail = null;
            size--;
            return oldHead;
        }
        // otherwise make tail.next skip the first node
        E oldHead = tail.next.getData();
        tail.next = tail.next.next;
        size--;
        return oldHead;
    }

    @Override
    public E removeLast() {
        if(isEmpty())
        {
            return null;
        }
        // check if there is only a single element
        if(size == 1)
        {
            return removeFirst();
        }
        // otherwise loop till last node
        Node<E> current = tail.next;
        while(current.next != tail)
        {
            current = current.next;
        }
        // skip over node and move tail back
        E oldTail = current.next.getData();
        current.next = tail.next;
        tail = current;
        size--;
        return  oldTail;
    }

    @Override
    public void addFirst(E e) {
        // check if this is the only node
        if(size == 0)
        {
            Node<E> newNode = new Node<>(e, null);
            tail = newNode;
            newNode.next = tail;
            size++;
        }
        // otherwise make tail point to new node and new node point to next
        else
        {
            Node<E> newNode = new Node<>(e, tail.next);
            tail.next = newNode;
            size++;
        }

    }

    @Override
    public void addLast(E e) {
        // if empty add it as first element
        if(size == 0)
        {
            addFirst(e);
        }
        // otherwise loop till end, add a node and move tail up
        else
        {
            Node<E> newNode = new Node<>(e, tail.next);
            Node<E> current = tail.next;
            while(current.next != tail)
            {
                current = current.next;
            }
            current.next = newNode;
            tail = newNode;
            size++;
        }
    }


    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = tail;
        do {
            curr = curr.next;
            sb.append(curr.data);
            if (curr != tail) {
                sb.append(", ");
            }
        } while (curr != tail);
        sb.append("]");
        return sb.toString();
    }


    public static void main(String[] args) {
        CircularlyLinkedList<Integer> ll = new CircularlyLinkedList<Integer>();
        for (int i = 10; i < 20; ++i) {
            ll.addLast(i);
        }

        System.out.println(ll);

        ll.removeFirst();
        System.out.println(ll);

        ll.removeLast();
        System.out.println(ll);

        ll.rotate();
        System.out.println(ll);

        ll.removeFirst();
        ll.rotate();
        System.out.println(ll);

        ll.removeLast();
        ll.rotate();
        System.out.println(ll);

        for (Integer e : ll) {
            System.out.println("value: " + e);
        }

    }
}
