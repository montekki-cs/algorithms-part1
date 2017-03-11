import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        int num = Integer.parseInt(args[0]);

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();

            rq.enqueue(s);
        }

        Iterator<String> it = rq.iterator();

        while (it.hasNext() && num-- > 0) {
            String ss = it.next();
            StdOut.println(ss);
        }
    }
}
