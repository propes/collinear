import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private Point[] points;
    private LineSegment[] segments = new LineSegment[1];
    private int segmentCount = 0;

    public BruteCollinearPoints(Point[] points) {
        // Throw an IllegalArgumentException if the argument to the constructor is null,
        // if any point in the array is null, or if the argument to the constructor contains a repeated point.

        if (points == null) throw new IllegalArgumentException();

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("All points must be null");

            for (int j = i + 1; j < points.length; j++) {
                if (points[i].equals(points[j]))
                    throw new IllegalArgumentException("All points must be unique");
            }
        }

        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int m = k + 1; m < points.length; m++) {
                        Point[] aux = { points[i], points[j], points[k], points[m] };
                        Merge.sort(aux);

                        if (aux[0].slopeTo(aux[1]) == aux[0].slopeTo(aux[2]) &&
                                aux[0].slopeTo(aux[1]) == aux[0].slopeTo(aux[3])) {
                            addToSegments(new LineSegment(aux[0], aux[3]));
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return segmentCount;
    }

    public LineSegment[] segments() {
        return segments;
    }

    private void addToSegments(LineSegment segment) {
        if (segments.length < segmentCount + 1)
            doubleSegments();
        segments[segmentCount++] = segment;
    }

    private void doubleSegments() {
        LineSegment[] copy = new LineSegment[segments.length * 2];
        for (int i = 0; i < segments.length; i++) {
            copy[i] = segments[i];
        }
        segments = copy;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
