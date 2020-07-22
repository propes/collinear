import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private final Bag<LineSegment> segments = new Bag<>();

    public FastCollinearPoints(Point[] points) {
        validatePoints(points);
        Point[] pointsCopy = copyPoints(points);
        Merge.sort(pointsCopy);

        for (int i = 0; i <= pointsCopy.length - 4; i++) {
            Point p = pointsCopy[i];
            Point[] orderedPoints = copyPoints(pointsCopy);
            Arrays.sort(orderedPoints, p.slopeOrder());

            int j = 1;
            while (j <= orderedPoints.length - 3) {
                Point q;
                double slope;
                int collinearSegments = 0;
                boolean slopeCounted = false;
                do {
                    q = orderedPoints[j];
                    if (p.compareTo(q) > 0)
                        slopeCounted = true;

                    slope = p.slopeTo(q);
                    if (!slopeCounted) collinearSegments++;
                    j++;
                } while (j < orderedPoints.length && slope == p.slopeTo(orderedPoints[j]));

                if (collinearSegments >= 3) {
                    segments.add(new LineSegment(p, q));
                }
            }
        }
    }

    private void validatePoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("At least one point is null.");
            if (i < points.length - 1 && points[i].compareTo(points[i + 1]) == 0)
                throw new IllegalArgumentException("At least two points are the same.");
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
