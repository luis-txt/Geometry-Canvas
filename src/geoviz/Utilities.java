package geoviz;

import datascructures.MyList;
import geoviz.shapes.MyCircle;
import geoviz.shapes.MyLine;
import geoviz.shapes.MyPoint;

/**
 * Holds useful utilities in oder to deal with objects or shapes
 */
public class Utilities {


    //Static functions

    /**
     * Compares two double values and returns if they are equal
     *
     * @param a first double to compare
     * @param b second double to compare
     * @return true if a equals b else false
     */
    public static boolean doubleComparison(double a, double b) {

        return (Math.abs(a - b) < 0.000001);
    }

    /**
     * Calculates the slope of a straight line through two points (m)
     *
     * @param firstPoint  the first Point of the straight line
     * @param secondPoint the second Point of the straight line
     * @return the Slope of these lines
     */
    public static double getSlope(MyPoint firstPoint, MyPoint secondPoint) {

        //double deltaX = x2 - x1;
        double deltaX = secondPoint.getX() - firstPoint.getX();
        //double deltaY = y2 - y1;
        double deltaY = secondPoint.getY() - firstPoint.getY();

        return deltaY / deltaX;
    }

    /**
     * Calculates the slope of a straight line through two points (m)
     *
     * @param x1 x-coordinate of point 1
     * @param y1 y-coordinate of point 1
     * @param x2 x-coordinate of point 2
     * @param y2 y-coordinate of point 2
     * @return Slope of line between the two points
     */
    public static double getSlope(double x1, double y1, double x2, double y2) {

        double deltaX = x2 - x1;
        double deltaY = y2 - y1;

        return deltaY / deltaX;
    }

    /**
     * Cuts all numbers after the second decimal place
     *
     * @param value double value to cut
     * @return the same value but with a maximum of two decimal places
     */
    public static double round2(double value) {

        value = value * 100;
        int cutValue = (int) value; //cut decimal places
        value = (double) cutValue / 100;

        return value;
    }

    /**
     * Calculates the interception of the line connecting the two data points with the y-axis (b)
     *
     * @param givenLine the given line of which to calculate the interception with the y-axis
     * @return the intersection of the line that connects the two points and a point on the y-axis
     */
    public static double getIntercept(MyLine givenLine) {

        //y=m*x+b und b is the the y-axis section
        //y=(deltaY/deltaX)*x+b
        //b=y-(deltaY/deltaX)*x
        double m = getSlope(givenLine.getStartPoint(), givenLine.getEndPoint());

        //return (y1 - m * x1);
        return (givenLine.getStartPoint().getY() - m * givenLine.getStartPoint().getX());
    }

    /**
     * Calculates the interception of the line connecting the two data points with the y-axis (b)
     *
     * @param startPoint the first point representing the line
     * @param endPoint   the second point representing the line
     * @return the intersection of the line that connects the two points and a point on the y-axis
     */
    public static double getIntercept(MyPoint startPoint, MyPoint endPoint) {

        //y=m*x+b und b is the the y-axis section
        //y=(deltaY/deltaX)*x+b
        //b=y-(deltaY/deltaX)*x
        double m = getSlope(startPoint, endPoint);

        //return (y1 - m * x1);
        return (startPoint.getY() - m * startPoint.getX());
    }

    /**
     * Calculates the intercept of the line connecting the two data points with the y-axis (b)
     *
     * @param x1 x-coordinate of the first point
     * @param y1 y-coordinate of the first point
     * @param x2 x-coordinate of the second point
     * @param y2 y-coordinate of the second point
     * @return the intersection of the line that connects the two points and a point on the y-axis
     */
    public static double getIntercept(double x1, double y1, double x2, double y2) {

        //y=m*x+b und b is the the y-axis section
        //y=(deltaY/deltaX)*x+b
        //b=y-(deltaY/deltaX)*x
        double m = getSlope(x1, y1, x2, y2);
        return (y1 - m * x1);
    }

    /**
     * Checks whether two lines are parallel
     *
     * @param firstLine  the first line to compare with the second one whether parallel
     * @param secondLine the second line to compare with the first one whether parallel
     * @return true when the slopes are equal and thus the lines are parallel else false
     */
    public static boolean isParallel(MyLine firstLine, MyLine secondLine) {
        return doubleComparison(firstLine.getSlope(), secondLine.getSlope()); //parallel if slope is equal
    }

    /**
     * Checks whether two lines are parallel
     *
     * @param slope1 represents the slope of the first line
     * @param slope2 represents the slope of the second line
     * @return true when the slopes are equal and thus the lines are parallel else false
     */
    public static boolean isParallel(double slope1, double slope2) {
        return doubleComparison(slope1, slope2); //parallel if slope is equal
    }

    /**
     * Checks whether two lines are orthogonal
     *
     * @param firstLine  the first line to compare with the second one whether orthogonal
     * @param secondLine the second line to compare with the first one whether orthogonal
     * @return true when the lines are orthogonal and otherwise false
     */
    public static boolean isOrthogonal(MyLine firstLine, MyLine secondLine) {

        double result = firstLine.getSlope() * secondLine.getSlope();
        return doubleComparison(result, -1);
    }

    /**
     * Checks whether two lines are orthogonal
     *
     * @param slope1 represents the slope of the first line
     * @param slope2 represents the slope of the second line
     * @return true when the lines are orthogonal and otherwise false
     */
    public static boolean isOrthogonal(double slope1, double slope2) {

        double result = slope1 * slope2;
        return result == -1;
    }

    /**
     * Calculates the euclidean distance between two MyPoint instances
     *
     * @param point1 first point to calculate the distance to the second point
     * @param point2 second point to calculate the distance to from the first point
     * @return double value that represents the euclidean distance between point1 and point2
     */
    public static double getDistance(MyPoint point1, MyPoint point2) {
        //d = √[(x2-x1)²+(y2-y1)²]
        return Math.sqrt(square(point2.getX() - point1.getX()) + square(point2.getY() - point1.getY()));
    }

    /**
     * Calculates the intersection of two lines given by four MyPoint instances.
     *
     * @return MyPoint instance that represents the point of intersection. When the two lines are identical the x and y
     * values of the returned MyPoint instance are +infinity and when they do not intersect null.
     */
    public static MyPoint getPointOfIntersection(MyPoint firstPointLine1, MyPoint secondPointLine1, MyPoint firstPointLine2, MyPoint secondPointLine2) {

        double intersectionPointX;
        double intersectionPointY;

        //calculate slopes
        double slope1 = getSlope(firstPointLine1.getX(), firstPointLine1.getY(), secondPointLine1.getX(), secondPointLine1.getY());
        double slope2 = getSlope(firstPointLine2.getX(), firstPointLine2.getY(), secondPointLine2.getX(), secondPointLine2.getY());
        //calculate intersections with y-axis
        double b1 = getIntercept(firstPointLine1.getX(), firstPointLine1.getY(), secondPointLine1.getX(), secondPointLine1.getY());
        double b2 = getIntercept(firstPointLine2.getX(), firstPointLine2.getY(), secondPointLine2.getX(), secondPointLine2.getY());

        //calculate intersection of the two lines

        //check special cases
        if (Double.isInfinite(slope1) && Double.isInfinite(slope2)) { //both lines are vertical

            if (doubleComparison(firstPointLine1.getX(), firstPointLine2.getX())) { //lines are identical
                return new MyPoint(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY); //because we must return a point
            } else return null; //because lines do not intersect

        } else if (Double.isInfinite(slope1))
            return new MyPoint(firstPointLine1.getX(), slope2 * firstPointLine1.getX() + b2); // line 1 is vertical line 2 is not

        else if (Double.isInfinite(slope2))
            return new MyPoint(firstPointLine2.getX(), slope1 * secondPointLine1.getX() + b1);  // line 2 is vertical line 1 is not

        else { //normal case

            if (doubleComparison(slope1, slope2) && doubleComparison(firstPointLine1.getY(), slope2 * firstPointLine1.getX() + b2)) { //are identical
                return new MyPoint(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY); //because we must return a point

            } else if (doubleComparison(slope1, slope2) && !doubleComparison(firstPointLine1.getY(), slope2 * firstPointLine2.getX() + b2)) { //do not intersect
                return null; //because lines do not intersect
            }
            //calculate intersection
            intersectionPointX = (b2 - b1) / (slope1 - slope2);
            intersectionPointY = slope1 * intersectionPointX + b1;

            return new MyPoint(intersectionPointX, intersectionPointY);
        }
    }

    /**
     * Calculates the intersection of two lines given by four points
     *
     * @param point1 is the x-coordinate of line one
     * @param point2 is the y-coordinate of line one
     * @param point3 is the x-coordinate of line two
     * @param point4 is the y-coordinate of line two
     * @return array that represents the point of intersection ([0] represents x-coordinate and [1] represents y-coordinate).
     * When the two lines are identical the x and y values of the returned array are +infinity and when they do not
     * intersect null.
     */
    public static double[] getPointOfIntersection(double[] point1, double[] point2, double[] point3, double[] point4) {

        double[] intersectionPoint = new double[2];

        //Create MyPoint instances
        MyPoint firstPointLine1 = new MyPoint(point1[0], point1[1]);
        MyPoint secondPointLine1 = new MyPoint(point2[0], point2[1]);
        MyPoint firstPointLine2 = new MyPoint(point3[0], point3[1]);
        MyPoint secondPointLine2 = new MyPoint(point4[0], point4[1]);

        //Get intersection-point

        if (getPointOfIntersection(firstPointLine1, secondPointLine1, firstPointLine2, secondPointLine2) != null) { //fill the array

            MyPoint intersection = getPointOfIntersection(firstPointLine1, secondPointLine1, firstPointLine2, secondPointLine2);
            intersectionPoint[0] = intersection.getX();
            intersectionPoint[1] = intersection.getY();
        }

        return intersectionPoint;
    }

    /**
     * Calculates the intersection of two lines given by four points
     *
     * @param firstLine  the first line of the intersection
     * @param secondLine the second line of the intersection
     * @return MyPoint instance that represents the point of intersection. When the two lines are identical the x and y
     * values are +infinity and when they do not intersect both are null.
     */
    public static MyPoint getPointOfIntersection(MyLine firstLine, MyLine secondLine) {

        //calculate intersection point and return it by calling another overloaded function
        return getPointOfIntersection(firstLine.getStartPoint(), firstLine.getEndPoint(), secondLine.getStartPoint(), secondLine.getEndPoint());
    }

    /**
     * Calculates the intersection of a line and a circle and returns a MyList instance filled with the two MyPoint instances
     * that represent the intersections.
     *
     * @param line   the given line that may intersect the circle
     * @param circle the given circle that may intersect the circle
     * @return a list of zero, one or two MyPoint instances which represent the intersections. If line and circle do not intersect,
     * an empty MyList instance is returned.
     */
    public static MyList<MyPoint> getPointOfIntersection(MyLine line, MyCircle circle) {

        MyList<MyPoint> intersections = new MyList<>();
        MyList<Double> results;

        //circle variables
        double circleX = circle.getCenter().getX();
        double circleY = circle.getCenter().getY();
        //line variables
        double lineM = line.getSlope();
        double lineB = line.getIntercept();

        //construct a, b and c for midnight formula
        double a = 1 + square(lineM);
        double b = -2 * circleX + 2 * lineB * lineM - 2 * circleY * lineM;
        double c = square(circleX) + square(circleY) - 2 * circleY * lineB + square(lineB) - square(circle.getRadius());

        if (doubleComparison(discriminant(a, b, c), 0) || Double.isNaN(a) || Double.isNaN(b) || Double.isNaN(c))
            return intersections; //no intersection

        else if (doubleComparison(line.getStartPoint().getX(), line.getEndPoint().getX())) { //line is vertical

            //calculate values for a, b ,c with regard to vertical line
            double xCoordinate = line.getStartPoint().getX();
            a = 1;
            b = -2 * circleY;
            c = square(xCoordinate) - 2 * xCoordinate * circleX + square(circleX) + square(circleY) - square(circle.getRadius());

            if (doubleComparison(discriminant(a, b, c), 0)) return intersections; //no intersection
            //fill list of solutions with resulting points
            results = quadraticFormula(a, b, c);

            MyPoint first = new MyPoint(xCoordinate, results.get(0));
            MyPoint second = new MyPoint(xCoordinate, results.get(1));

            if (results.get(0) != null) intersections.add(first); //add intersections to MyList
            if (results.get(1) != null && !(first.getX() == second.getX() && first.getY() == second.getY()))
                intersections.add(second);

        } else { //standard case

            //fill list of solutions with resulting points
            results = quadraticFormula(a, b, c);

            MyPoint first = new MyPoint(results.get(0), line.getYatX(results.get(0)));
            MyPoint second = new MyPoint(results.get(1), line.getYatX(results.get(1)));

            if (results.get(0) != null) intersections.add(first); //add intersections to MyList
            if (results.get(1) != null && !(first.getX() == second.getX() && first.getY() == second.getY())) {
                intersections.add(second);
            }
        }
        return intersections;
    }

    /**
     * Calculates the intersection of a circle and another circle. When they do not intersect an empty MyList is returned.
     *
     * @param firstCircle  the first given circle that may intersect the other circle
     * @param secondCircle the second given circle that may intersect the other circle
     * @return a list of zero, one or two MyPoint instances in a MyList which represent the intersections. When they do not
     * intersect an empty MyList is returned. When they are identical a MyList of one MyPoint with x and y are +infinity is
     * returned.
     */
    public static MyList<MyPoint> getPointOfIntersection(MyCircle firstCircle, MyCircle secondCircle) {

        MyList<MyPoint> intersections;
        MyLine intersectionLine;

        //construct intersection-line
        double firstX = firstCircle.getCenter().getX();
        double firstY = firstCircle.getCenter().getY();
        double firstR = firstCircle.getRadius();
        double secondX = secondCircle.getCenter().getX();
        double secondY = secondCircle.getCenter().getY();
        double secondR = secondCircle.getRadius();

        if (doubleComparison(firstX, secondX) && doubleComparison(firstY, secondY) && doubleComparison(firstR, secondR)) { //circles are identical
            return new MyList<>(new MyPoint(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));

        } else if (doubleComparison((2 * firstY - 2 * secondY), 0)) { //circles intersect in a vertical line

            //calculate vertical intersection-line
            double xCoordinate = (-square(firstX) - square(firstY) + square(firstR) + square(secondX) + square(secondY) -
                    square(secondR)) / (-2 * firstX + 2 * secondX);

            intersectionLine = new MyLine(new MyPoint(xCoordinate, 1), new MyPoint(xCoordinate, 2));
        } else {

            //calculate intersection-line
            double m = (-2 * firstX + 2 * secondX) / (2 * firstY - 2 * secondY);
            double b = (square(firstX) + square(firstY) - square(firstR) - square(secondX) - square(secondY) +
                    square(secondR)) / (2 * firstY - 2 * secondY);

            intersectionLine = new MyLine(new MyPoint(1, m + b), new MyPoint(4, 4 * m + b));
        }
        intersections = getPointOfIntersection(intersectionLine, firstCircle);
        return intersections;
    }


    //Helping functions

    /**
     * Represents the quadratic formula to calculate intersection-points
     *
     * @param a the first variable (ax²)
     * @param b the second variable (bx)
     * @param c the third variable (c)
     * @return a MyList instance
     */
    private static MyList<Double> quadraticFormula(double a, double b, double c) {

        MyList<Double> results = new MyList<>();
        if (doubleComparison(2 * a, 0)) return results; //to avoid dividing by 0

        double firstSolution = ((-b - Math.sqrt(square(b) - 4 * a * c)) / (2 * a));
        double secondSolution = ((-b + Math.sqrt(square(b) - 4 * a * c)) / (2 * a));

        results.add(firstSolution);
        results.add(secondSolution);

        return results;
    }

    /**
     * Squares the input-value and returns it
     *
     * @param toSquare value to be squared
     * @return two times the input-value
     */
    private static double square(double toSquare) {
        return toSquare * toSquare;
    }

    /**
     * Calculates the discriminant of the midnight formula and returns 2 when the result is greater than 0, 0 if the result
     * is less than 0 and 1 when the result is exactly 0. The returned integers represent the number of solutions the
     * midnight-formula calculates with the given values a, b, c.
     *
     * @param a the first variable (ax²)
     * @param b the second variable (bx)
     * @param c the third variable (c)
     * @return whether the formula has one, two or no solutions
     */
    private static int discriminant(double a, double b, double c) {

        double discriminant = square(b) - 4 * a * c;

        if (discriminant > 0) return 2;
        else if (discriminant < 0) return 0;
        else return 1;
    }
}