package geoviz.shapes;

import geoviz.Utilities;
import graphics.Graphics;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Represents a point with x and y coordinates in two-dimensional space
 */
public class MyPoint extends Circle {


    //Object variables
    private double xPos;
    private final double yPos;
    private Color standardColor = Color.BLACK;


    //Constructor
    public MyPoint(double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        setAll();
    }

    public MyPoint(double xPos, double yPos, Color color) {

        this.xPos = xPos;
        this.yPos = yPos;
        this.standardColor = color;
        setFill(color);
        setAll();
    }


    //Getter and Setter

    /**
     * Returns x-coordinate of this point
     *
     * @return the x-coordinate
     */
    public double getX() {
        return xPos;
    }

    /**
     * Returns y-coordinate of this point
     *
     * @return the y-coordinate
     */
    public double getY() {
        return yPos;
    }

    /**
     * Sets the x-coordinate of this point (used to prevent vertical lines)
     *
     * @param x the new x-coordinate of this MyPoint instance
     */
    public void setX(double x) {
        xPos = x;
    }

    /**
     * Returns the originally given colour of the current instance
     *
     * @return the standard-color the MyPoint was instanced with
     */
    public Color getStandardColor() {
        return standardColor;
    }


    //Helping methods

    /**
     * Sets all attributes of the current MyPoint instance. Also sets the OnMouseClicked behaviour.
     */
    private void setAll() {

        setCenterX(xPos); //set parent variables
        setCenterY(yPos);
        setRadius(2);
        setRadius(5);

        //add tooltip to get information when hovering over instance
        Tooltip.install(this, new Tooltip("x: " + Utilities.round2(xPos) + "\ny: " + Utilities.round2(yPos)));

        //handle click on current MyPoint instance
        this.setOnMouseClicked(mouseEvent -> Graphics.getInstance().handlePointClicked(this));
    }
}
