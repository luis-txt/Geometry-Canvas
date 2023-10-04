package graphics;

import controls.PointReader;
import datascructures.MyList;
import geoviz.shapes.MyPoint;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Represents the user interface and all its instances that can be interacted with
 */
public class UserInterface {


    //Object variables
    private Pane background; //pane

    private RadioButton lineButton; //UI objects
    private CheckBox fillBox;
    private Button intersectionButton;
    private ColorPicker colorPicker;
    private Button loadDataButton;
    private Button clearButton;

    private boolean intersectionButtonIsPressed = false; //other variables


    //Getter and setter

    /**
     * Returns the Pane instance of the background. The background holds the grid and is responsible for new points when
     * a click occurred on free space on the background.
     *
     * @return the Pane instance of the background.
     */
    public Pane getBackground() {
        return background;
    }

    /**
     * Returns the ColorPicker instance colorPicker from the TopBar.
     *
     * @return ColorPicker instance of colorPicker.
     */
    public ColorPicker getColorPicker() {
        return colorPicker;
    }

    /**
     * Returns a boolean whether the fillBox CheckBox is checked or not.
     *
     * @return true when fillBox is checked and else false.
     */
    public boolean getFilledIsChecked() {
        return fillBox.isSelected();
    }

    /**
     * Returns the Button instance of the load-data-button which is used to start a file-chooser to select a file from which
     * Data-Points should be read.
     *
     * @return the Button instance of the load-data-button.
     */
    public Button getLoadDataButton() {
        return loadDataButton;
    }

    /**
     * Returns the Button instance of the clear-button that clears all shapes from the surface of the grid.
     *
     * @return the Button instance of the clear-button.
     */
    public Button getClearButton() {
        return clearButton;
    }

    /**
     * Returns the Button instance of the intersection-button that handles whether intersections should be displayed or not.
     *
     * @return the Button instance of the intersection-button
     */
    public Button getIntersectionButton() {
        return intersectionButton;
    }

    /**
     * Returns a boolean whether the RadioButton lineButton is selected or not.
     *
     * @return true if the lineButton is selected and else false.
     */
    public boolean getLineIsSelected() {
        return lineButton.isSelected();
    }

    /**
     * Returns a boolean whether the intersection-button is pressed or not.
     *
     * @return true when the intersection-button was pressed and else false.
     */
    public boolean getIntersectionButtonIsPressed() {
        return intersectionButtonIsPressed;
    }


    //Object methods

    //Make UI methods

    /**
     * Creates a ToolBar instance that can be used as top-bar in the UI. It consists of two RadioButtons, a CheckBox, a
     * Button and a ColorPicker instance. Those items are aligned in the Bar. The RadioButtons are used to switch between
     * lines and circles. The CheckBox says whether circles should be filled and the button is there to show intersections.
     * The reference of the created instances is passed to the local-variables of the Graphic instance.
     *
     * @return the created ToolBar instance that can be used as top-bar.
     */
    public ToolBar makeTopBar() {

        RadioButton lineButton = new RadioButton("Line"); //add radio-buttons
        RadioButton circleButton = new RadioButton("Circle");
        lineButton.setSelected(true);

        ToggleGroup toggleShape = new ToggleGroup(); //make them affect each other
        lineButton.setToggleGroup(toggleShape);
        circleButton.setToggleGroup(toggleShape);


        CheckBox fillCheckBox = new CheckBox("Fill");
        Button intersectionButton = new Button("Show Intersection");
        ColorPicker colorPicker = new ColorPicker(Color.BLACK);

        this.lineButton = lineButton; //store references in local variables
        fillBox = fillCheckBox;
        this.intersectionButton = intersectionButton;
        this.colorPicker = colorPicker;

        //create ToolBar instance that holds the created instances and return it
        return new ToolBar(lineButton, circleButton, fillCheckBox, intersectionButton, colorPicker);
    }

    /**
     * Creates a Pane that holds a grid that can be used as background in the center of the UI. The grid is LightBlue and
     * every tenth line is slightly thicker than the other lines. Sets the background variable to the Pane instance with
     * the grid on it and adds the cicles Group and lines Group to it.
     *
     * @return a Pane instance holding the background (with the grid, the circles and the lines) and on top the points
     * and intersection points.
     */
    public Pane makeGrid() {

        Pane centerPane = new Pane();
        centerPane.setScaleY(-1);

        Pane backgroundPane = new Pane();
        Group gridLinesPane = new Group();

        for (int i = 0; i < 10000; i++) { //create grid-lines

            Line vLine = new Line(i * 10, 0, i * 10, 10000);
            vLine.setStroke(Color.LIGHTBLUE);

            Line hLine = new Line(0, i * 10, 10000, i * 10);
            hLine.setStroke(Color.LIGHTBLUE);

            if (i % 10 == 0) { //every tenth line
                vLine.setStrokeWidth(2);
                hLine.setStrokeWidth(2);
            }
            gridLinesPane.getChildren().add(vLine); //add lines to gridLinesPane Group
            gridLinesPane.getChildren().add(hLine);
        }

        //building hierarchic structure of center
        this.background = backgroundPane; //to make background clickable
        backgroundPane.getChildren().add(gridLinesPane); //creating background with grid
        //adding lines and circles on top of grid in background so a spot inside a circle can be clicked, too
        backgroundPane.getChildren().add(Graphics.getInstance().getCircles());
        backgroundPane.getChildren().add(Graphics.getInstance().getLines()); //lines on top of circles, so they can be seen better

        centerPane.getChildren().add(backgroundPane); //adding clickable background with lines, circles and grid to center
        centerPane.getChildren().add(Graphics.getInstance().getPoints()); //adding points and intersections on top
        centerPane.getChildren().add(Graphics.getInstance().getIntersections());
        return centerPane;
    }

    /**
     * Creates a ToolBar that is used as bottom-bar in the UI. It consists of a Load-button and a Clear-button.
     * The load-button is used to open a file-chooser instance and the clear-button is used to clear the created shapes
     * on the grid in the UI. The reference of the created buttons is passed to the local-variables of the Graphic instance.
     *
     * @return a ToolBar that can be used as bottom-bar.
     */
    public ToolBar makeBottomBar() {

        Button loadButton = new Button("Load Data"); //make the Button instances
        Button clearButton = new Button("Clear Window");

        loadDataButton = loadButton; //set the local-variables to the created references
        this.clearButton = clearButton;
        return new ToolBar(loadButton, clearButton); //create ToolBar instance that holds the buttons and return it
    }

    //Interactions

    /**
     * Handles the behaviour of the program when the "Show intersections" button was pressed and when it was pressed a
     * second time. It changes the text of the Button and calculates or clears the intersection-points.
     */
    public void showIntersectionsPressed() {

        if (!intersectionButtonIsPressed) { //was off before

            intersectionButton.setText("Hide Intersections");
            intersectionButtonIsPressed = true;

            Graphics.getInstance().checkForIntersection(); //calculates and displays all intersections
        } else { //was on before
            intersectionButton.setText("Show Intersections");
            intersectionButtonIsPressed = false;
            Graphics.getInstance().getIntersections().getChildren().clear(); //removes all intersections
        }
    }

    /**
     * Handles the click of the clear button. Clears the last clicked point (and in case there is a last clicked point it
     * resets its colour). The points, lines, circles and intersections - Groups are cleared.
     */
    public void clearSurfacePressed() {

        if (Graphics.getInstance().getLastClick() != null) {
            Graphics.getInstance().getLastClick().setStroke(Color.BLACK); //reset color
        }
        Graphics.getInstance().setLastClick(null);

        Graphics.getInstance().getPoints().getChildren().clear(); //clear all groups
        Graphics.getInstance().getLines().getChildren().clear();
        Graphics.getInstance().getCircles().getChildren().clear();
        Graphics.getInstance().getIntersections().getChildren().clear();
    }

    /**
     * Handles the click of the mouse on the loadData button. Adds the found MyPoint instances (of the selected file)
     * from PointReader instance to the points Group and therefore displays them.
     */
    public void loadDataPressed() {

        PointReader pointReader = new PointReader();

        String filePath = pointReader.getFilePath();
        if (filePath == null) return; //when nothing can be read

        //fills the new MyList with all found MyPoint instances of the selected file
        MyList<MyPoint> newPoints = pointReader.readPoints(filePath, colorPicker.getValue());

        int i = 0;

        while (newPoints.get(i) != null) { //for each point in the MyList
            Graphics.getInstance().getPoints().getChildren().add(newPoints.get(i)); //add the current point of the list to the points Group
            i++;
        }
    }
}
