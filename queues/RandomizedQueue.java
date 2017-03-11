import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int n;

    public RandomizedQueue() {
        a = (Item[]) new Object[2];
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }
    
    public int size() {
        return n;
    }

    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];

        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }

        a = temp;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        if (n == a.length) resize(2*a.length);
        a[n++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        int i = StdRandom.uniform(n);

        Item item = a[i];
        a[i] = a[n - 1];
        a[n - 1] = null;
        n--;
        if (n > 0 && n == a.length/4) resize(a.length/2);

        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        int i = StdRandom.uniform(n);

        return a[i];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int[] order;
        private int pos = 0;

        public RandomizedQueueIterator() {
            order = new int[n];

            for (int i = 0; i < n; i++) {
                order[i] = i;
            }

            StdRandom.shuffle(order);
        }

        public boolean hasNext() {
            return pos < (n);
        }

        public void remove()
        {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next() {
            if (pos >= n) {
                throw new java.util.NoSuchElementException();
            }

            Item it = a[order[pos]];
            pos++;
            return it;
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        rq.enqueue("1");
        rq.enqueue("2");
        rq.enqueue("3");
        rq.enqueue("4");
        rq.enqueue("5");
        rq.enqueue("6");
        rq.enqueue("7");

        for (int i = 0; i < rq.size(); i++) {
            StdOut.println("Int " + rq.sample());
        }

        Iterator<String> it = rq.iterator();

        while (it.hasNext()) {
            String s = it.next();
            StdOut.println("Rand Iterator String: " + s);
        }

        while (!rq.isEmpty()) {
            String s = rq.dequeue();
            StdOut.println("res " + s); 
            StdOut.println("Size " + rq.size());
        }
    }
}
