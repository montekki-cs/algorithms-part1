import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        private Item item;
        private Node prev;
        private Node next;
    }

    private Node first;
    private Node last;
    private int n;

    public Deque() {
        n = 0;
        first = null;
        last = null;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        n++;
        Node newnode = new Node();
        newnode.next = first;
        newnode.prev = null;
        newnode.item = item;

        if (first != null) {
            first.prev = newnode;
        }

        first = newnode;

        if (last == null) {
            last = newnode;
        }
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        n++;
        Node newnode = new Node();
        newnode.next = null;
        newnode.prev = last;
        newnode.item = item;
        
        if (last != null) {
            last.next = newnode;
        }

        if (first == null) {
            first = newnode;
        }

        last = newnode;
    }

    public Item removeFirst() {
        Item item = null;
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        if (first != null) {
            item = first.item;
            first.item = null;

            if (first.next != null) {
                first.next.prev = null;
            }

            first = first.next;

            if (first == null) {
                last = null;
            }
        }

        n--;

        return item;
    }

    public Item removeLast() {
        Item item = null;

        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        if (last != null) {
            item = last.item;

            last.item = null;

            if (last.prev != null) {
                last.prev.next = null;
            }

            last = last.prev;

            if (last == null) {
                first = null;
            }
        }

        n--;

        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private int i = n;
        private Node node = first;

        public boolean hasNext() {
            return i > 0;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next() {
            Item item = null;
            if (node != null) {
                item = node.item;

                node = node.next;
                i--;

            }
            else {
                throw new java.util.NoSuchElementException();
            }

            return item;
        }
    }

    public static void main(String[] args) {
        Deque<String> deq = new Deque<String>();

        deq.addLast("0");
        String ss = deq.removeLast();

        deq.addFirst("3");

        ss = deq.removeFirst();

        deq.addFirst("6");

        ss = deq.removeFirst();

        deq.addFirst("10");

        ss = deq.removeLast();

        StdOut.println("String " + ss);

        Iterator<String> i = deq.iterator();

        while (i.hasNext()) {
            String s = i.next();
            StdOut.println("String: " + s);
        }
    }
}
