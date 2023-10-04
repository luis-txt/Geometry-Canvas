package graphics;

import geoviz.Utilities;
import geoviz.shapes.MyCircle;
import geoviz.shapes.MyLine;
import geoviz.shapes.MyPoint;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Singleton that creates new MyPoint, MyLine and MyCircle instances and holds their respective Group instances.
 * Holds methods for click on coordinate system.
 */
public class Graphics {


    //Static variables
    private static final Graphics graphics = new Graphics();


    //Object variables
    private UserInterface userInterface;

    private MyPoint lastClick; //internal additional variables

    private final Group points = new Group(); //Groups
    private final Group lines = new Group();
    private final Group circles = new Group();
    private final Group intersections = new Group();


    //Constructor
    private Graphics() {
    }


    //Static methods

    /**
     * Returns the reference of the Graphics singleton.
     *
     * @return Graphics singleton reference.
     */
    public static Graphics getInstance() {
        return graphics;
    }


    //Getter and setter

    /**
     * Sets the UI for to work with
     *
     * @param userInterface the User Interface to get variables form
     */
    public void setUserInterface(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    /**
     * Returns the intersection Group that holds the intersections of the lines and circles displayed.
     *
     * @return the intersections Group instance.
     */
    public Group getIntersections() {
        return intersections;
    }

    /**
     * Returns the points Group that holds all MyPoint instances that are displayed.
     *
     * @return the points Group instance.
     */
    public Group getPoints() {
        return points;
    }

    /**
     * Returns the lines Group that holds all MyLines instances that are displayed.
     *
     * @return the lines Group instance.
     */
    public Group getLines() {
        return lines;
    }

    /**
     * Returns the circles Group that holds all MyCircle instances that are displayed.
     *
     * @return the circles Group instance.
     */
    public Group getCircles() {
        return circles;
    }

    /**
     * Returns the last-clicked MyPoint instance.
     *
     * @return the last-clicked MyPoint instance. If there is no null is returned.
     */
    public MyPoint getLastClick() {
        return lastClick;
    }

    /**
     * Updates the local-variable lastClick of the Graphics instance with the given MyPoint instance.
     *
     * @param lastClick the new last-clicked point that should be saved.
     */
    public void setLastClick(MyPoint lastClick) {
        this.lastClick = lastClick;
    }


    //Object methods

    /**
     * Handles the click of the mouse on the background pane. Creates a new MyPoint instance at the specific position of the mouse
     * and adds it to the points Group. It therefore is displayed on the screen. Clears the last-clicked point.
     *
     * @param x the x-coordinate of the mouse to create the MyPoint at this specific x position.
     * @param y the y-coordinate of the mouse to create the MyPoint at this specific y position.
     */
    public void mouseClicked(double x, double y) {

        MyPoint additionalCircle = new MyPoint(x, y, userInterface.getColorPicker().getValue()); //create new circle at mouse position

        if (Graphics.getInstance().getLastClick() != null) {
            Graphics.getInstance().getLastClick().setFill(Graphics.getInstance().getLastClick().getStandardColor()); //reset last-click
        }

        Graphics.getInstance().setLastClick(null); //reset last clicked point
        Graphics.getInstance().getPoints().getChildren().add(additionalCircle);
    }

    /**
     * Creates a new MyLine with the given point as startPoint and the last clicked point as endPoint. The new line is
     * added to the circles Group and therefore displayed on the screen.
     *
     * @param point the given MyPoint instance to create the MyLine with the last clicked MyPoint.
     */
    public void constructLine(MyPoint point) {
        MyLine newLine = new MyLine(point, Graphics.getInstance().getLastClick(), userInterface.getColorPicker().getValue()); //instantiate new MyLine instance
        lines.getChildren().add(newLine); //add new line

        if (userInterface.getIntersectionButtonIsPressed()) {
            checkForIntersection();
        }
    }

    /**
     * Handles the click on a MyPoint instance. Sets the lastClick instance to the selected point and changes its color
     * in order to make the selection visible. When a point was selected before, depending on the selection of the RadioButton
     * (Line or Circle) it creates a MyCircle or a MyLine instance at the locations of the last selected point and the current
     * selected point (it also resets the colors of the points) and sets the lastClicked instance to null.
     *
     * @param clickedPoint the currently clicked MyPoint instance.
     */
    public void handlePointClicked(MyPoint clickedPoint) {

        if (getLastClick() != null) { //when there was clicked a MyPoint instance before

            if (userInterface.getLineIsSelected()) { //line was selected
                constructLine(clickedPoint); //if there was clicked a point before
            } else constructCircle(clickedPoint); //circle was selected

            getLastClick().setFill(getLastClick().getStandardColor()); //reset colours
            clickedPoint.setFill(clickedPoint.getStandardColor());

            setLastClick(null); //reset last-clicked
        } else {

            setLastClick(clickedPoint); //update last-clicked point to current instance

            if (clickedPoint.getStandardColor().equals(Color.DARKRED)) clickedPoint.setFill(Color.GREEN); //change color of current instance
            else clickedPoint.setFill(Color.DARKRED);
        }
    }

    /**
     * Creates a new MyCircle with the given point as radiusPoint and the last clicked point as center. The new circle is
     * added to the circles Group and therefore displayed on the screen.
     *
     * @param point the radiusPoint of the circle to be created.
     */
    public void constructCircle(MyPoint point) {
        MyCircle newMyCircle = new MyCircle(Graphics.getInstance().getLastClick(), point, userInterface.getColorPicker().getValue(), userInterface.getFilledIsChecked());
        circles.getChildren().add(newMyCircle); //add new circle
        if (circles.getChildren().size() > 1) sortCircles(); //sorting the circles according to their radius

        if (userInterface.getIntersectionButtonIsPressed()) checkForIntersection();
    }

    /**
     * Clears the previous found intersections and fills the intersections Group with all new intersections.
     */
    public void checkForIntersection() {
        intersections.getChildren().clear();
        for (MyPoint intersection : calculateAllIntersections()) { //fill up intersections Group
            intersections.getChildren().add(intersection);
        }
    }

    /**
     * Calculates the intersections of all lines and circles displayed. Returns an ArrayList that holds all intersection
     * points as green MyPoint instances.
     *
     * @return ArrayList that holds all intersection points as green MyPoint instances. When there are no intersections
     * an empty ArrayList is returned.
     */
    public ArrayList<MyPoint> calculateAllIntersections() {

        ArrayList<MyPoint> resultPoints = new ArrayList<>();
        if (lines.getChildren().size() + circles.getChildren().size() < 2) return resultPoints; //if there cannot be any intersections

        //lines with lines and circles
        for (int i = 0; i < lines.getChildren().size(); i++) {

            for (int j = i + 1; j < lines.getChildren().size(); j++) { //check all lines
                addIntersectionPointToList(Utilities.getPointOfIntersection((MyLine) lines.getChildren().get(i), (MyLine) lines.getChildren().get(j)), resultPoints);
            }

            for (int j = 0; j < circles.getChildren().size(); j++) { //check all circles with lines
                addIntersectionPointToList(Utilities.getPointOfIntersection((MyLine) lines.getChildren().get(i), (MyCircle) circles.getChildren().get(j)).get(0), resultPoints);
                addIntersectionPointToList(Utilities.getPointOfIntersection((MyLine) lines.getChildren().get(i), (MyCircle) circles.getChildren().get(j)).get(1), resultPoints);
            }
        }

        if (circles.getChildren().size() < 2) return resultPoints; //when there are at least two circles

        //circles with circles
        for (int i = 0; i < circles.getChildren().size() - 1; i++) {

            for (int j = i + 1; j < circles.getChildren().size(); j++) { //check all circles with circles

                addIntersectionPointToList(Utilities.getPointOfIntersection((MyCircle) circles.getChildren().get(j), (MyCircle) circles.getChildren().get(i)).get(0), resultPoints);
                addIntersectionPointToList(Utilities.getPointOfIntersection((MyCircle) circles.getChildren().get(j), (MyCircle) circles.getChildren().get(i)).get(1), resultPoints);
            }

        }
        return resultPoints;
    }


    //Helping methods

    /**
     * Checks if the given MyPoint instance is null. When not it adds the given instance to the given ArrayList and changes
     * the color of the instance to green (in order to make it visible that it is an intersection point).
     *
     * @param point      the given intersection-point to add to the list
     * @param resultList the given ArrayList to add the new MyPoint instance to
     */
    private void addIntersectionPointToList(MyPoint point, ArrayList<MyPoint> resultList) {

        if (point != null) {
            point.setFill(Color.GREEN);
            resultList.add(point);
        }
    }

    /**
     * Sorts the circle instances. The filled circles are prioritised (they are placed further back). When both circles are
     * filled, they are sorted according to their radius.
     */
    private void sortCircles() {

        List<Node> sored = circles.getChildren().stream().sorted((c1, c2) -> {

            MyCircle circle1 = (MyCircle) c1; //hard cast to MyCircle because only such instances are saved in this Group
            MyCircle circle2 = (MyCircle) c2;

            if (circle1.getIsFilled() && !circle2.getIsFilled()) return -1; //if only one circle is filled the other one should be on top
            else if (!circle1.getIsFilled() && circle2.getIsFilled()) return 1;
            else return Double.compare(circle2.getRadius(), circle1.getRadius()); //if both are filled sort according to radius
        }).collect(Collectors.toList());

        circles.getChildren().clear();
        circles.getChildren().setAll(sored); //remove previous entries and add sorted ones
    }
}
