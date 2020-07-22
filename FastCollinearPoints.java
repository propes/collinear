import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private Bag<LineSegment> segments = new Bag<>();

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
    
        Point[] orderedPoints = copyPoints(points);

        for (Point p : points) {
            Arrays.sort(orderedPoints, p.slopeOrder());

            int j = 1; // Skip the first point in the ordered set because it is the reference point.
            while (j <= orderedPoints.length - 3) {
                Point lo = p;
                Point hi = p;
                double s;
                int collinearSegments = 0;
                do {
                    if (hi.compareTo(orderedPoints[j]) < 0) hi = orderedPoints[j];
                    else if (lo.compareTo(orderedPoints[j]) > 0) lo = orderedPoints[j];
                    s = p.slopeTo(orderedPoints[j]);
                    if (s == Double.NEGATIVE_INFINITY)
                        throw new IllegalArgumentException("At least two points are the same");
                    j++;
                    collinearSegments++;
                } while (j < orderedPoints.length && p.slopeTo(orderedPoints[j]) == s);

                if (collinearSegments >= 3) {
                    LineSegment segment = new LineSegment(lo, hi);
                    if (!segmentExists(segment))
                        segments.add(segment);
                }
            }
        }
    }

    private Point[] copyPoints(Point[] points) {
        Point[] copy = new Point[points.length];
        for (int i = 0; i < points.length; i++)
            copy[i] = points[i];

        return copy;
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        LineSegment[] segArray = new LineSegment[segments.size()];
        int i = 0;
        for (LineSegment seg : segments)
            segArray[i++] = seg;

        return segArray;
    }

    private boolean segmentExists(LineSegment segment) {
        for (LineSegment seg : segments) {
            if (segment.equals(seg)) return true;
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
