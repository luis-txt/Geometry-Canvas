package geoviz.shapes;

import geoviz.Utilities;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Represents a line between two points
 */
public class MyLine extends Line {


    //Object variables
    private final MyPoint startPoint;
    private final MyPoint endPoint;
    private final double slopeOfLine;
    private final double intercept;


    //Constructors
    public MyLine(MyPoint startPoint, MyPoint endPoint) { //for utilities

        this.startPoint = startPoint;
        this.endPoint = endPoint;

        slopeOfLine = Utilities.getSlope(startPoint, endPoint);
        intercept = Utilities.getIntercept(this);
    }

    public MyLine(MyPoint startPoint, MyPoint endPoint, Color color) { //for visuals

        //when line is vertical
        if (Utilities.getSlope(startPoint, endPoint) == Double.POSITIVE_INFINITY || Utilities.getSlope(startPoint, endPoint) == Double.NEGATIVE_INFINITY) {
            startPoint.setX(startPoint.getX() + 0.01); //move first point of line slightly to prevent real vertical line
            startPoint.setCenterX(startPoint.getCenterX() + 0.01);
        }

        slopeOfLine = Utilities.getSlope(startPoint, endPoint); //set MyLine variables
        intercept = Utilities.getIntercept(startPoint, endPoint);

        this.startPoint = startPoint;
        this.endPoint = endPoint;

        setStartX(0); //set parent variables
        setStartY(intercept);
        setEndX(10000000);
        setEndY(slopeOfLine * 10000000 + intercept);

        setStrokeWidth(3); //set visuals
        setStroke(color);

        //add tooltip to get information when hovering over instance
        String slopeText = Double.toString(Utilities.round2(slopeOfLine)); //normal information
        String interceptText = Double.toString(Utilities.round2(intercept));

        //information for vertical slope
        if (0 > Double.compare(50, slopeOfLine) || 0 < Double.compare(-50, slopeOfLine)) {
            slopeText = "Infinity";
            interceptText = "No interception";
        }

        //create and install tooltip
        Tooltip.install(this, new Tooltip("slope: " + slopeText + "\nintercept: " + interceptText));
    }


    //Getter and Setter

    /**
     * Returns the first point of the MyLine instance
     *
     * @return the first point
     */
    public MyPoint getStartPoint() {
        return startPoint;
    }

    /**
     * Returns the second point of the MyLine instance
     *
     * @return the second point
     */
    public MyPoint getEndPoint() {
        return endPoint;
    }

    /**
     * Returns the slope of the MyLine instance
     *
     * @return the slope
     */
    public double getSlope() {
        return slopeOfLine;
    }

    /**
     * Returns the interception of the MyLine instance
     *
     * @return the interception
     */
    public double getIntercept() {
        return intercept;
    }

    /**
     * Calculates and returns the y-coordinate to the given x-coordinate of the MyLine instance
     *
     * @param x the x-coordinate from which the corresponding y coordinate is to be calculated
     * @return the corresponding y-coordinate
     */
    public double getYatX(double x) {
        return ((slopeOfLine * x) + intercept);
    }
}
