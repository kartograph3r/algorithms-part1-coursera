/*******************************************************************************
 *  Name:
 *  Date:
 *  Description:
 ******************************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int n;

    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // Nodes
    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    // Check if queue is empty
    public boolean isEmpty() {
        return n == 0;
    }

    // Number of items in the queue
    public int size() {
        return n;
    }

    // Add item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("You can't store a null value!");
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        if (n == 0) {
            first.next = null;
            first.previous = null;
            last = first;
        }
        else {
            first.next = oldFirst;
            oldFirst.previous = first;
        }
        n++;
    }

    // Add items to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("You can't store a null value!");
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        if (n == 0) {
            last.next = null;
            last.previous = null;
            first = last;
        }
        else {
            oldLast.next = last;
            last.next = null;
            last.previous = oldLast;
        }
        n++;
    }

    // Remove and return an item from the front
    public Item removeFirst() {
        if (n == 0) {
            throw new java.util.NoSuchElementException("No elements to remove!");
        }
        n--;
        Item temp = first.item;
        if (n == 0) {
            last = null;
            first = null;
        }
        else {
            first = first.next;
            first.previous = null;
        }
        return temp;
    }

    // Remove and return an item from the end
    public Item removeLast() {
        if (n == 0) {
            throw new java.util.NoSuchElementException("No elements to remove!");
        }
        n--;
        Item temp = last.item;
        if (n == 0) {
            first = null;
            last = null;
        }
        else {
            last = last.previous;
            last.next = null;
        }
        return temp;
    }

    // Iterator from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException(
                    "This implementation does not support this method!");
        }

        public Item next() {
            if (current == null) {
                throw new java.util.NoSuchElementException(
                        "No more elements left to iterate through!");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }


    public static void main(String[] args) {
        Deque<String> deutsch = new Deque<String>();
        while (StdIn.hasNextLine()) {
            String temp = StdIn.readLine();
            deutsch.addFirst(temp);
        }
        deutsch.addLast("Hallo");
        StdOut.println("You've entered " + deutsch.size() + " words.");
        StdOut.println(deutsch.removeFirst());
        StdOut.println(deutsch.removeLast());

        for (String z : deutsch) {
            StdOut.println(z);
        }

    }
}
