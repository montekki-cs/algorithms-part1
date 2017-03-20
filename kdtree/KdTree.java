import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;

public class KdTree {
    private Node root;
    private int size;
    private Point2D res;

    private static class Node {
        private Point2D p;
        private Node left;
        private Node right;

        public Node(Point2D pt) {
            p = pt;
        }
    }

    public KdTree() {
        size = 0;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    private Node put(Node x, Point2D p, int left) {
        double cmp;

        if (x == null) {
            size++;
            return new Node(p);
        }

        if (left == 1) {
            left = 0;
            cmp = x.p.y() - p.y();

            if (cmp == 0.0) {
                cmp = x.p.x() - p.x();
            }
        }
        else {
            left = 1;
            cmp = x.p.x() - p.x();
            
            if (cmp == 0.0) {
                cmp = x.p.y() - p.y();
            }
        }

        if (cmp < 0) x.right = put(x.right, p, left);
        else if (cmp > 0) x.left = put(x.left, p, left);
        else x.p = p;

        return x;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException();
        }

        root = put(root, p, 0);
    }

    private Node get(Node x, Point2D p, int left) {
        if (x == null) return null;
        double cmp;

        if (left == 1) {
            left = 0;
            cmp = x.p.y() - p.y();

            if (cmp == 0.0) {
                cmp = x.p.x() - p.x();
            }
        }
        else {
            left = 1;
            cmp = x.p.x() - p.x();

            if (cmp == 0.0) {
                cmp = x.p.y() - p.y();
            }
        }

        if (p.compareTo(x.p) == 0) {
            return x;
        }

        if (cmp < 0) return get(x.right, p, left);
        else if (cmp > 0) return get(x.left, p, left);
        else return null;
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException();
        }

        Node pt = get(root, p, 0);

        return pt != null;
    }

    private void drawNode(Node n, Node prev, int left) {
        if (n == null) {
            return;
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        n.p.draw();

        StdDraw.setPenRadius(0.001);

        if (prev == null) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(n.p.x(), 0, n.p.x(), 1);
            drawNode(n.left, n, 1);
            drawNode(n.right, n, 1);
            return;
        }
        else {
            if (left == 1) {
                left = 0;
                StdDraw.setPenColor(StdDraw.BLUE);

                if (n.p.x() < prev.p.x()) {
                    StdDraw.line(0, n.p.y(), prev.p.x(), n.p.y());
                }
                else {
                    StdDraw.line(prev.p.x(), n.p.y(), 1, n.p.y());
                }
            }
            else {
                left = 1;

                StdDraw.setPenColor(StdDraw.RED);

                if (n.p.y() < prev.p.y()) {
                    StdDraw.line(n.p.x(), 0, n.p.x(), prev.p.y());
                }
                else {
                    StdDraw.line(n.p.x(), prev.p.y(), n.p.x(), 1);
                }
            }
        }

        drawNode(n.right, n, left);
        drawNode(n.left, n, left);
    }

    public void draw() {
        drawNode(root, null, 0);
    }
    private void walkTree(RectHV rect, Queue<Point2D> result, Node node, int left)
    {
        if (node == null) {
            return;
        }

        if (rect.contains(node.p)) {
            result.enqueue(node.p);
        }

        if (left == 1) {
            left = 0;

            if (node.p.y() <= rect.ymax()) {
                walkTree(rect, result, node.right, left);
            }
            if (node.p.y() >= rect.ymin()) {
                walkTree(rect, result, node.left, left);
            }
        }
        else {
            left = 1;

            if (node.p.x() <= rect.xmax()) {
                walkTree(rect, result, node.right, left);
            }

            if (node.p.x() >= rect.xmin())  {
                walkTree(rect, result, node.left, left);
            }

        }
    }


    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new java.lang.NullPointerException();
        }

        Queue<Point2D> q = new Queue<Point2D>();

        walkTree(rect, q, root, 0);

        return q;
    }


    private void findNearest(Point2D to, Node node, int left, double mindistance) {
        if (node == null) {
            return;
        }

        if (left == 0) {
            left = 1;

        }
        else {
            left = 0;
        }

        if (to.distanceTo(node.p) <= to.distanceTo(res)) {
            mindistance = to.distanceTo(node.p);
            res = node.p;
        }

        if (node.left != null) {
            findNearest(to, node.left, left, mindistance);
        }

        if (node.right != null) {
            findNearest(to, node.right, left, mindistance);
        }

    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException();
        }

        if (root == null) {
            return null;
        }

        res = root.p;

        findNearest(p, root, 0, p.distanceTo(root.p));

        return res;
    }

    public static void main(String[] args) {
        String filename = args[0];
        KdTree kdtree = new KdTree();

        In in = new In(filename);

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            kdtree.draw();

            String s = StdIn.readString();
            StdDraw.clear();
        }

        kdtree.draw();
    }
}
