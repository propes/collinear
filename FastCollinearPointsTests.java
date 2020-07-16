import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPointsTests {
    public static void main(String[] args) {
        Point[] points = {
                new Point(2, 2),
                new Point(3, 3),
                new Point(0, 0),
                new Point(1, 1),
                new Point(0, 1),
                new Point(0, 2),
                new Point(0, 3),
                new Point(0, 4)
        };
        FastCollinearPoints collinear = new FastCollinearPoints(points);

        Assert.equal(2, collinear.numberOfSegments(),
                     "segment count should be correct given collinear points");

        Assert.equal("(0, 0) -> (3, 3)", collinear.segments()[0].toString(),
                     "segment should be correct given collinear points");

        Assert.equal("(0, 0) -> (0, 4)", collinear.segments()[1].toString(),
                     "segment should be correct given collinear points");

        if (Assert.allTestsPassed()) StdOut.println("All tests passed.");
    }
}
