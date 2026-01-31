package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;

public class DoublyLinkedList<E> implements List<E> {

    private static class Node<E> {
        private final E data;
        private Node<E> next;
        private Node<E> prev;

        public Node(E e, Node<E> p, Node<E> n) {
            data = e;
            prev = p;
            next = n;
        }

        public E getData() {
            return data;
        }

        public Node<E> getNext() {
            return next;
        }

        public Node<E> getPrev() {
            return prev;
        }

    }

    private final Node<E> head;
    private final Node<E> tail;
    private int size = 0;

    public DoublyLinkedList() {
        head = new Node<E>(null, null, null);
        tail = new Node<E>(null, head, null);
        head.next = tail;
    }

    private void addBetween(E e, Node<E> pred, Node<E> succ) {
        // create a new node with the arguments
        Node<E> newNode = new Node<>(e, pred, succ);
        // set the list to point to new node
        pred.next = newNode;
        succ.prev = newNode;
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int i) {
        // check the position is valid
        if(i < 0 || i >= size)
        {
            throw new IndexOutOfBoundsException();
        }
        // check if its first or last and return them using their methods
        if(i == 0)
        {
            return first();
        }
        else if(i == size - 1)
        {
            return last();
        }
        // loop through list moving till we get to the correct index
        Node<E> current = head.next;
        for(int j = 0; j < i; j++)
        {
            current = current.next;
        }
        // once at the correct index return the element
        return current.data;
    }

    @Override
    public void add(int i, E e) {
        // check the position is valid
        if(i < 0 || i > size)
        {
            throw new IndexOutOfBoundsException();
        }
        // if its first call its method
        if(i == 0)
        {
            addFirst(e);
            return;
        }
        Node<E> previous = head;
        Node<E> current = head.next;
        for(int j = 0; j < i; j++)
        {
            previous = current;
            current = current.next;
        }
        // add between these two
        addBetween(e, previous, current);
    }

    @Override
    public E remove(int i) {
        // check the position is valid
        if(i < 0 || i >= size)
        {
            throw new IndexOutOfBoundsException();
        }
        // check if we remove the only element in a list then make head point to tail and tail point to head
        if(size == 1)
        {
            E nodeValue = head.next.getData();
           head.next = tail;
           tail.prev = head;
           size--;
           return nodeValue;
        }

        // loop to find index
        Node<E> previous = head;
        Node<E> current = head.next;
        for(int j = 0; j < i; j++)
        {
            previous = current;
            current = current.next;
        }
        // make nodes skip over the index;
        Node<E> nextNode = current.next;
        previous.next = current.next;
        nextNode.prev = previous;
        size--;

        return current.getData();
    }

    private class DoublyLinkedListIterator<E> implements Iterator<E> {
        Node<E> curr = (Node<E>) head.next;

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
        return new DoublyLinkedListIterator<E>();
    }

    private E remove(Node<E> n) {
        // get the node before and after it, update these pointers and decrease size
        Node<E> previousNode = n.prev;
        Node<E> nextNode = n.next;
        previousNode.next = nextNode;
        nextNode.prev = previousNode;
        size--;
        return n.getData();
    }

    public E first() {
        if (isEmpty()) {
            return null;
        }
        return head.next.getData();
    }

    public E last() {
        if (isEmpty()) {
            return null;
        }
        return tail.prev.getData();
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }
        return remove(head.next);
    }

    @Override
    public E removeLast() {
        if(isEmpty())
        {
            return null;
        }
        return remove(tail.prev);
    }

    @Override
    public void addLast(E e) {
        // add between the previous last node adn the tail
        addBetween(e, tail.prev, tail);
    }

    @Override
    public void addFirst(E e) {
        // add between the head and the heads next
        addBetween(e, head, head.next);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = head.next;
        while (curr != tail) {
            sb.append(curr.data);
            curr = curr.next;
            if (curr != tail) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        DoublyLinkedList<Integer> ll = new DoublyLinkedList<Integer>();
        ll.addFirst(0);
        ll.addFirst(1);
        ll.addFirst(2);
        ll.addLast(-1);
        System.out.println(ll);

        ll.removeFirst();
        System.out.println(ll);

        ll.removeLast();
        System.out.println(ll);

        for (Integer e : ll) {
            System.out.println("value: " + e);
        }
    }
}