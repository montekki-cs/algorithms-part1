import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private LineSegment[] segs;
    private int segsNum;
 
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.NullPointerException();
        }

        segs = new LineSegment[2];
        segsNum = 0;

        Point[] pointsSorted = new Point[points.length];
        Point[] pointsTemp = new Point[points.length];

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new java.lang.NullPointerException();
            }

            pointsTemp[i] = points[i];
            pointsSorted[i] = points[i];
        }

        Arrays.sort(pointsTemp);
        Arrays.sort(pointsSorted);

        for (int i = 0; i < points.length - 1; i++) {
            if (pointsTemp[i].compareTo(pointsTemp[i + 1]) == 0) {
                throw new java.lang.IllegalArgumentException();
            }
        }

        for (int i = 0; i < points.length; i++) {
            Point p = pointsSorted[i];

            Arrays.sort(pointsTemp, p.slopeOrder());

            double slope = p.slopeTo(pointsTemp[0]);
            int adjNum = 0;
            int j = 0;

            for (j = 0; j < pointsTemp.length; j++) {
                if (p.slopeTo(pointsTemp[j]) == slope) {
                    adjNum++;
                }
                else {
                    if (adjNum > 1) {
                        processSegment(pointsTemp, j, j + adjNum + 2, p);
                    }
                    adjNum = 0;
                    slope = p.slopeTo(pointsTemp[j]);
                }
            }

            if (adjNum > 1) {
                processSegment(pointsTemp, j, j + adjNum + 2, p);
                Point[] segment = new Point[adjNum + 2];
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

    private void processSegment(Point[] points, int from, int to, Point current) {
        int segmentLength = to - from;
        Point[] segment = new Point[segmentLength];

        segment[0] = current;

        for (int i = 1; i < segmentLength; i++) {
            segment[i] = points[from + i - segmentLength];
        }

        Arrays.sort(segment);

        int flag = 1;

        for (int i = 0; i < segmentLength; i++) {
            if (current.compareTo(segment[i]) < 0) {
                flag = 0;
                break;
            }
        }

        if (flag != 0) {
            LineSegment segm = new LineSegment(segment[0], segment[segmentLength - 1]);
            addSegment(segm);
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

    public static void main(String[] args) {
        int num = StdIn.readInt();
        int x, y;
        Point[] points = new Point[num];

        for (int i = 0; i < num; i++) {
            x = StdIn.readInt();
            y = StdIn.readInt();

            points[i] = new Point(x, y);
        }

        FastCollinearPoints fcp = new FastCollinearPoints(points);

        LineSegment[] seg = fcp.segments();
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setScale(0, 35000);

        for (Point p : points) {
            p.draw();
        }

        for (int i = 0; i < fcp.numberOfSegments(); i++) {
            seg[i].draw();
            StdOut.println(seg[i].toString());
        }
    }
}
