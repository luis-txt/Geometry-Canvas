package geoviz.shapes;

import geoviz.Utilities;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Represents a circle with a specific center and a radius
 */
public class MyCircle extends Circle {


    //Object variables
    private final MyPoint center;
    private final boolean isFilled;


    //Constructor
    public MyCircle(MyPoint center, MyPoint radiusPoint, Color color, boolean isFilled) {

        this.center = center;
        double radius = Utilities.getDistance(center, radiusPoint); //set MyPoint variables
        this.isFilled = isFilled;

        //set parent variables
        setStrokeWidth(3); //set visuals
        setStroke(color);
        if (!isFilled) setFill(Color.TRANSPARENT);
        else setFill(color);

        setCenterX(center.getX()); //set parent variables
        setCenterY(center.getY());
        setRadius(radius);

        //add tooltip to get information when hovering over instance
        Tooltip.install(this, new Tooltip("x: " + Utilities.round2(center.getX()) + "\ny: " + Utilities.round2(center.getY()) + "\nradius: " + Utilities.round2(radius)));
    }


    //Getter & Setter

    /**
     * Returns the center-point of the circle
     *
     * @return center of the circle instance as MyPoint instance
     */
    public MyPoint getCenter() {
        return center;
    }


    /**
     * Returns whether the current MyCircle instance is filled or just the border.
     *
     * @return true if the current instance is filled and else false.
     */
    public boolean getIsFilled() {
        return isFilled;
    }
}
