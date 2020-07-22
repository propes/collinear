import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] segments = new LineSegment[1];
    private int segmentCount = 0;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        if (points[0] == null) throw new IllegalArgumentException("At least one point is null.");

        int i = 0;
        while (i <= points.length - 4) {
            Point p = points[i];
            int j = i + 1;
            Arrays.sort(points, j, points.length, p.slopeOrder());

            while (j <= points.length - 3) {
                Point lo = p;
                Point hi = p;
                double s;
                int collinearSegments = 0;
                do {
                    if (hi.compareTo(points[j]) < 0) hi = points[j];
                    else if (lo.compareTo(points[j]) > 0) lo = points[j];
                    s = p.slopeTo(points[j]);
                    if (s == Double.NEGATIVE_INFINITY)
                        throw new IllegalArgumentException("At least two points are the same");
                    j++;
                    collinearSegments++;
                } while (j < points.length && p.slopeTo(points[j]) == s);

                if (collinearSegments >= 3) {
                    LineSegment segment = new LineSegment(lo, hi);
                    if (!segmentExists(segment))
                        addSegment(segment);
                    break;
                }
            }
            i++;
        }
    }

    public int numberOfSegments() {
        return segmentCount;
    }

    public LineSegment[] segments() {
        LineSegment[] copy = new LineSegment[segmentCount];
        for (int i = 0; i < segmentCount; i++)
            copy[i] = segments[i];
        return copy;
    }

    private void addSegment(LineSegment segment) {
        if (segments.length < segmentCount + 1)
            doubleSegments();
        segments[segmentCount] = segment;
        segmentCount++;
    }

    private void doubleSegments() {
        LineSegment[] segmentsCopy = new LineSegment[segments.length * 2];
        for (int i = 0; i < segments.length; i++) {
            segmentsCopy[i] = segments[i];
        }
        segments = segmentsCopy;
    }

    private boolean segmentExists(LineSegment segment) {
        for (int i = 0; i < segmentCount; i++) {
            if (segments[i].equals(segment))
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
