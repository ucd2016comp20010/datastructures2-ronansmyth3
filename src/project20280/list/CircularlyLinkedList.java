package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;

public class CircularlyLinkedList<E> implements List<E> {

    private class Node<T> {
        private T data;
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
        if(i < 0 || i >= size)
        {
            throw new IndexOutOfBoundsException();
        }

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
        if(i < 0 || i > size)
        {
            throw new IndexOutOfBoundsException();
        }
        if(i == 0)
        {
            addFirst(e);
            return;
        }
        else if(i == size)
        {
            addLast(e);
            return;
        }
        else
        {
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
    }

    @Override
    public E remove(int i) {
        if(i < 0 || i >= size)
        {
            throw new IndexOutOfBoundsException();
        }
        if(i == 0)
        {
            return removeFirst();
        }
        else if(i == size - 1)
        {
            return removeLast();
        }
        else
        {
            Node<E> previous = tail.next;
            Node<E> current = tail.next;
            for(int j = 0; j < i; j++)
            {
                previous = current;
                current = current.next;
            }
            previous.next = current.next;
            size--;
            return current.getData();

        }
    }

    public void rotate() {
        if(tail != null)
        {
            tail = tail.next;
        }
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
        if(isEmpty())
        {
            return null;
        }
        if(size == 1)
        {
            E value = tail.getData();
            tail = null;
            size--;
            return value;
        }
        else
        {
            Node<E> head = tail.next;
            tail.next = head.next;
            size--;
            return head.getData();
        }

    }

    @Override
    public E removeLast()
    {
        if(isEmpty())
        {
            return null;
        }
        else if(size == 1)
        {
            return removeFirst();
        }
        else
        {
            Node<E> current = tail.next;
            while(current.next != tail)
            {
                current = current.next;
            }
            E value = tail.getData();
            current.next = tail.next;
            tail = current;
            size--;
            return value;
        }
    }

    @Override
    public void addFirst(E e) {
        if(isEmpty())
        {
            tail= new Node<>(e, null);
            tail.setNext(tail);
            size++;
            return;
        }
        Node<E> newNode = new Node<>(e, tail.next);
        tail.next = newNode;
        size++;
    }

    @Override
    public void addLast(E e) {
        if(isEmpty())
        {
            addFirst(e);
            return;
        }
        else if(size == 1)
        {
            Node<E> head = tail;
            Node<E> newNode = new Node<>(e, head);
            head.next = newNode;
            tail = newNode;
            size++;
        }
        else
        {
            Node<E> current = tail.next;
            while(current != tail)
            {
                current = current.next;
            }
            Node<E> newNode = new Node<>(e, tail.next);
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
