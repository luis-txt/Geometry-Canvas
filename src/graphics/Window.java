package graphics;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Represents the opened window, the application
 */
public class Window extends Application {


    //Object variables
    private final UserInterface userInterface = new UserInterface(); //create UI and the shown objects

    
    //Object methods

    /**
     * Initialization of the window
     */
    public void init() {
        Graphics.getInstance().setUserInterface(userInterface); //set the association between UserInterface and Graphics
    }

    /**
     * Setting up stage (window) with title and size. Also sets interaction associations
     *
     * @param primaryStage the given stage for start of window
     */
    public void start(Stage primaryStage) {

        //initialize pane, set TopBar, BottomBar, grid and add shapes Group
        BorderPane root = new BorderPane();

        root.setCenter(userInterface.makeGrid());
        root.setTop(userInterface.makeTopBar());
        root.setBottom(userInterface.makeBottomBar());

        Scene scene = new Scene(root, 700, 475); //set size of window

        //A coordinate in the grid is clicked
        userInterface.getBackground().setOnMouseClicked(mouseEvent -> Graphics.getInstance().mouseClicked(mouseEvent.getX(), mouseEvent.getY()));

        //Buttons are clicked
        userInterface.getIntersectionButton().setOnAction(actionEvent -> userInterface.showIntersectionsPressed());
        userInterface.getLoadDataButton().setOnAction(actionEvent -> userInterface.loadDataPressed());
        userInterface.getClearButton().setOnAction(actionEvent -> userInterface.clearSurfacePressed());

        //Set primaryStage attributes and show it
        primaryStage.setTitle("Graphic Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
