import java.util.Iterator;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Queue;

public class PointSET {
    private SET<Point2D> points;

    public PointSET() {
        points = new SET<Point2D>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException();
        }

        points.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException();
        }

        return points.contains(p);
    }

    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new java.lang.NullPointerException();
        }

        Queue<Point2D> q = new Queue<Point2D>();

        for (Point2D p : points) {
            if (rect.contains(p)) {
                q.enqueue(p);
            }
        }

        return q;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException();
        }

        if (size() == 0) {
            return null;
        }

        Iterator<Point2D> i = points.iterator();
        double distance = 0.0;
        Point2D nrst = null;

        for (int j = 0; i.hasNext(); j++) {
            Point2D pnt = i.next();

            if (j == 0) {
                nrst = pnt;
                distance = pnt.distanceTo(p);
            }
            else {
                if (pnt.distanceTo(p) < distance) {
                    distance = pnt.distanceTo(p);
                    nrst = pnt;
                }
            }
        }

        return nrst;
    }

    public static void main(String[] args) {
        StdDraw.setScale(0, 1);
        StdDraw.setPenRadius(0.02);
        PointSET set = new PointSET();

        set.insert(new Point2D(0.1, 0.1));
        set.insert(new Point2D(0.2, 0.2));

        set.draw();
    }
}
