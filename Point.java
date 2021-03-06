/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (that == null) throw new NullPointerException("Reference point can not be null.");

        if (this.x == that.x) {
            if (this.y == that.y) return Double.NEGATIVE_INFINITY;
            return Double.POSITIVE_INFINITY;
        }

        double slope = ((double) that.y - this.y) / (that.x - this.x);
        return slope == -0 ? +0 : slope;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        if (that == null) throw new NullPointerException("Reference point can not be null.");

        if (this.y < that.y || this.y == that.y && this.x < that.x) return -1;
        else if (this.y > that.y || this.x > that.x) return 1;
        return 0;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new SlopeComparator();
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    private class SlopeComparator implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            if (p1 == null || p2 == null)
                throw new NullPointerException("One or more reference points are null.");

            double slope1 = slopeTo(p1);
            double slope2 = slopeTo(p2);
            if (slope1 < slope2) return -1;
            else if (slope1 > slope2) return 1;
            return 0;
        }
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 1);

        Assert.equal(0, p1.compareTo(p2),
                     "compare should return zero when both points have same coordinates");

        p1 = new Point(1, 1);
        p2 = new Point(1, 2);

        Assert.equal(-1, p1.compareTo(p2),
                     "compare should return -1 when second point has greater y coordinate");

        p1 = new Point(1, 2);
        p2 = new Point(1, 1);

        Assert.equal(1, p1.compareTo(p2),
                     "compare should return 1 when first point has greater y coordinate");

        p1 = new Point(1, 1);
        p2 = new Point(2, 1);

        Assert.equal(-1, p1.compareTo(p2),
                     "compare should return -1 when second point has greater x coordinate");

        p1 = new Point(2, 1);
        p2 = new Point(1, 1);

        Assert.equal(1, p1.compareTo(p2),
                     "compare should return 1 when first point has greater x coordinate");

        p1 = new Point(1, 1);
        p2 = new Point(1, 1);

        Assert.equal(Double.NEGATIVE_INFINITY, p1.slopeTo(p2),
                     "slope should return negative infinity when points are same");

        p1 = new Point(0, 0);
        p2 = new Point(0, 1);

        Assert.equal(Double.POSITIVE_INFINITY, p1.slopeTo(p2),
                     "slope should return positive infinity when line is vertical up");

        p1 = new Point(0, 1);
        p2 = new Point(0, 0);

        Assert.equal(Double.POSITIVE_INFINITY, p1.slopeTo(p2),
                     "slope should return positive infinity when line is vertical down");

        p1 = new Point(0, 0);
        p2 = new Point(1, 0);

        Assert.equal(0, p1.slopeTo(p2),
                     "slope should return positive zero when line is horizontal right");

        p1 = new Point(1, 0);
        p2 = new Point(0, 0);

        Assert.equal(0, p1.slopeTo(p2),
                     "slope should return positive zero when line is horizontal left");

        p1 = new Point(0, 0);
        p2 = new Point(1, 1);

        Assert.equal(1, p1.slopeTo(p2), "slope should return correct value given points");

        p1 = new Point(1, 1);
        p2 = new Point(0, 0);

        Assert.equal(1, p1.slopeTo(p2), "slope should return correct value given points");

        p1 = new Point(0, 1);
        p2 = new Point(1, 0);

        Assert.equal(-1, p1.slopeTo(p2), "slope should return correct value given points");

        Point p0 = new Point(0, 0);
        p1 = new Point(0, 0);
        p2 = new Point(1, 1);

        Comparator<Point> comp = p0.slopeOrder();

        Assert.equal(-1, comp.compare(p1, p2),
                     "slope order should be negative if p1 is horizontal and p2 is positive slope");

        p0 = new Point(0, 0);
        p1 = new Point(1, 2);
        p2 = new Point(1, 1);

        comp = p0.slopeOrder();

        Assert.equal(1, comp.compare(p1, p2),
                     "slope order should be positive if p1 has greater slope than p2");

        if (Assert.allTestsPassed()) StdOut.println("All tests passed.");
    }
}
