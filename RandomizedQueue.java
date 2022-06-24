/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] a;
    private int n = 0;
    private int size = 0;

    // Constructor
    public RandomizedQueue() {
        a = (Item[]) new Object[1];
    }

    // Check if Queue empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Return number of items
    public int size() {
        return size;
    }

    // Resize to 2x the size
    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    // Add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("You can't store a null value!");
        }
        if (n == a.length) resize(2 * a.length);
        a[n++] = item;
        size++;
    }

    // Remove and return a random item
    public Item dequeue() {
        if (n == 0) {
            throw new java.util.NoSuchElementException("No elements to remove!");
        }
        int indice = StdRandom.uniform(n);
        while (a[indice] == null) {
            indice = StdRandom.uniform(n);
        }
        Item temp = a[indice];
        a[indice] = null;
        size--;
        return temp;
    }

    // Return a random sample item
    public Item sample() {
        if (n == 0) {
            throw new java.util.NoSuchElementException("No elements to remove!");
        }
        int indice = StdRandom.uniform(n);
        while (a[indice] == null) {
            indice = StdRandom.uniform(n);
        }
        return a[indice];
    }

    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        private int i = 0;

        public boolean hasNext() {
            return i < size;
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("No elements to remove!");
            }
            i++;
            int temp = StdRandom.uniform(n);
            while (a[temp] == null) {
                temp = StdRandom.uniform(n);
            }
            return a[temp];
        }

        public void remove() {
            throw new UnsupportedOperationException(
                    "This implementation does not support this method!");
        }
    }

    public static void main(String[] args) {
        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
        for (int i = 0; i < n; i++)
            StdOut.println(queue.dequeue());
    }
}
