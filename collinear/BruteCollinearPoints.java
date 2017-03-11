import edu.princeton.cs.algs4.StdDraw;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private LineSegment[] segs;
    private int segsNum;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.NullPointerException();
        }

        segs = new LineSegment[2];
        segsNum = 0;

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new java.lang.NullPointerException();
            }
        }

        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {

                if (points[i].compareTo(points[j]) == 0) {
                    throw new java.lang.IllegalArgumentException();
                }

                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int m = k + 1; m < points.length; m++) {
                        if (points[i] == null || points[j] == null || points[k] == null || points[m] == null) {
                            throw new java.lang.NullPointerException();
                        }

                        if (points[i] == points[j] || points[i] == points[k] || points[i] == points[m]) {
                            throw new java.lang.IllegalArgumentException();
                        }

                        double slopeTo1 = points[i].slopeTo(points[j]);
                        double slopeTo2 = points[i].slopeTo(points[k]);
                        double slopeTo3 = points[i].slopeTo(points[m]);

                        if (slopeTo1 == slopeTo2 && slopeTo1 == slopeTo3) {
                            Point[] pointsSegm = new Point[4];
                            pointsSegm[0] = points[i];
                            pointsSegm[1] = points[j];
                            pointsSegm[2] = points[k];
                            pointsSegm[3] = points[m];

                            Arrays.sort(pointsSegm);

                            LineSegment segm = new LineSegment(pointsSegm[0], pointsSegm[3]);

                            addSegment(segm);
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return segsNum;
    }

    public LineSegment[] segments() {
        LineSegment[] tempSegs = new LineSegment[segsNum];

        for (int i = 0; i < segsNum; i++) {
            tempSegs[i] = segs[i];
        }

        return tempSegs;
    }

    public static void main(String[] args) {
        int num = StdIn.readInt();
        Point[] points = new Point[num];

        for (int i = 0; i < num; i++) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();

            points[i] = new Point(x, y);
        }

        BruteCollinearPoints bcp = new BruteCollinearPoints(points);

        LineSegment[] seg = bcp.segments();
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setScale(0, 35000);

        for (Point p : points) {
            p.draw();
        }

        for (int i = 0; i < bcp.numberOfSegments(); i++) {
            seg[i].draw();
            StdOut.println(seg[i].toString());
        }
    }

    private void resize(int capacity) {
        LineSegment[] temp = new LineSegment[capacity];

        for (int i = 0; i < segsNum; i++) {
            temp[i] = segs[i];
        }

        segs = temp;
    }

    private void addSegment(LineSegment seg) {
        if (segsNum == segs.length) resize(2 * segs.length);
        segs[segsNum] = seg;
        segsNum++;
    }
}
