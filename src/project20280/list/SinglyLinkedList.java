package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;
// Ronan test commit
public class SinglyLinkedList<E> implements List<E> {

    private static class Node<E> {

        private final E element;            // reference to the element stored at this node

        /**
         * A reference to the subsequent node in the list
         */
        private Node<E> next;         // reference to the subsequent node in the list

        /**
         * Creates a node with the given element and next node.
         *
         * @param e the element to be stored
         * @param n reference to a node that should follow the new node
         */
        public Node(E e, Node<E> n) {
            this.element = e;
            this.next = n;
        }

        // Accessor methods

        /**
         * Returns the element stored at the node.
         *
         * @return the element stored at the node
         */
        public E getElement() {
            return element;
        }

        /**
         * Returns the node that follows this one (or null if no such node).
         *
         * @return the following node
         */
        public Node<E> getNext() {
            return next;
        }

        // Modifier methods

        /**
         * Sets the node's next reference to point to Node n.
         *
         * @param n the node that should follow this one
         */
        public void setNext(Node<E> n) {
            this.next = n;
        }
    } //----------- end of nested Node class -----------

    /**
     * The head node of the list
     */
    private Node<E> head = null;               // head node of the list (or null if empty)


    /**
     * Number of nodes in the list
     */
    private int size = 0;                      // number of nodes in the list

    public SinglyLinkedList() {
    }              // constructs an initially empty list

    //@Override
    public int size() {
        return size;
    }

    //@Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int position) {
        // check the position is valid
        if(position < 0 || position >= size)
        {
            throw new IndexOutOfBoundsException();
        }
        // loop through list moving till we get to the correct index
        Node<E> current = head;
        for(int i = 0; i < position; i++)
        {
            current = current.next;
        }
        // once at the correct index return the element
        return current.getElement();
    }

    @Override
    public void add(int position, E e)
    {
        // check if the position is valid
        if(position < 0 || position > size)
        {
            throw new IndexOutOfBoundsException();
        }

        // base case if its first element set head to new element
        if(position == 0)
        {
            addFirst(e);
            // return to stop it from duplicating size++
            return;
        }
        // otherwise loop through until we find position
        else
        {
            Node<E> current = head;
            Node<E> previous = head;
            for(int i = 0; i < position; i++)
            {
                previous = current;
                current = current.next;
            }
            // set previous to node to point to new node and the new node point to current node
            previous.next = new Node<>(e, current);
        }
        size++;
    }


    @Override
    public void addFirst(E e) {
        // set head to the new node
        head = new Node<>(e, head);
        size++;
    }

    @Override
    public void addLast(E e) {
        // check if its empty set this to the head
        if(isEmpty())
        {
            head = new Node<>(e, null);
        }
        // otherwise loop through till we reach a node that points to null
        else
        {
            Node<E> current = head;
            while(current.next != null)
            {
                current = current.next;
            }
            // set this node to point to the new node and new node points to null
            current.next = new Node<>(e, null);
        }
        size++;
    }

    @Override
    public E remove(int position) {
        // check if the position is valid
        if(position < 0 || position >= size)
        {
            throw new IndexOutOfBoundsException();
        }
        // if removing first element call method
        if(position == 0)
        {
            return removeFirst();
        }
        // if we are removing the last element call method
        if(position == size - 1)
        {
            return removeLast();
        }
        // loop through till at correct index and store the previous and current
        Node<E> current = head;
        Node<E> previous = head;
        for(int i = 0; i < position - 1; i++)
        {
            previous = current;
            current = current.next;
        }
        // set the previous.next pointer to current.next
        // return removed element
        previous.next = current.next;
        size--;
        return current.getElement();

    }

    @Override
    public E removeFirst() {
        // if empty remove nothing
        if(isEmpty())
        {
            return null;
        }
        // store old head so we can return it after
        E oldHead = head.getElement();
        // set head to the next node
        head = head.next;
        size--;
        return oldHead;
    }

    @Override
    public E removeLast() {
        // if empty remove nothing
        if(isEmpty())
        {
            return null;
        }
        // if its only got 1 element call remove first
        if(size == 1)
        {
            return removeFirst();
        }

        // otherwise loop through until we reach a node which points to null
        Node<E> current = head;
        Node<E> previous = head;
        while(current.next != null)
        {
            previous = current;
            current = current.next;
        }
        // set the previous node before the last node to point to null
        previous.next = null;
        size--;
        // return the current node which is the last node
        return current.getElement();
    }

    //@Override
    public Iterator<E> iterator() {
        return new SinglyLinkedListIterator<E>();
    }

    private class SinglyLinkedListIterator<E> implements Iterator<E> {
        Node<E> curr = (Node<E>) head;

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public E next() {
            E res = curr.getElement();
            curr = curr.next;
            return res;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = head;
        while (curr != null) {
            sb.append(curr.getElement());
            if (curr.getNext() != null)
                sb.append(", ");
            curr = curr.getNext();
        }
        sb.append("]");
        return sb.toString();
    }

    public void reverse()
    {
        if(head != null)
        {
            head = reverseHelper(null, head);
        }
    }

    public Node<E> reverseHelper(Node<E> current, Node<E> successor)
    {
        if (successor.next == null)
        {
            successor.next = current;
            return successor;
        }

        Node<E> newNode = successor.next;
        successor.next = current;
        return reverseHelper(successor, newNode);
    }

    public SinglyLinkedList<E> recursiveCopy()
    {
        SinglyLinkedList<E> copiedList = new SinglyLinkedList<E>();
        if(head!= null)
        {
            copiedList.head = copyHelper(this.head);
        }

        return copiedList;
    }

    public Node <E> copyHelper(Node<E> node)
    {
        if(node == null)
        {
            return null;
        }
        Node<E> newNode = new Node<>(node.getElement(), copyHelper(node.next));
        return newNode;
    }

    public static void main(String[] args) {
        SinglyLinkedList<Integer> ll = new SinglyLinkedList<Integer>();
        System.out.println("ll " + ll + " isEmpty: " + ll.isEmpty());
        //LinkedList<Integer> ll = new LinkedList<Integer>();

        ll.addFirst(0);
        ll.addFirst(1);
        ll.addFirst(2);
        ll.addFirst(3);
        ll.addFirst(4);
        ll.addLast(-1);
        ll.removeLast();
        ll.removeFirst();
        System.out.println("I accept your apology");
        ll.add(3, 2);
        System.out.println(ll);
        ll.reverse();
        System.out.println(ll);
        SinglyLinkedList<Integer> copy = ll.recursiveCopy();
        System.out.println(copy);

    }
}
