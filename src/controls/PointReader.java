package controls;

import datascructures.MyList;
import geoviz.shapes.MyPoint;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

/**
 * Reads data form files and returns MyPoint instances represented by coordinates in the read files
 */
public class PointReader {


    //Object methods

    /**
     * Creates a File-Chooser that only can select plain text documents. When an acceptable file is selected and the
     * open-button is pressed it returns the absolute file-path of the file. Else it returns null.
     *
     * @return a String instance that represents the absolute file-path of the selected file. If the selection was not a
     * plain-text file it returns null.
     */
    public String getFilePath() {

        JFileChooser jfc = new JFileChooser("src" + File.separator + "load"); //new file-chooser instance
        jfc.setDialogTitle("Choose file that holds your points");

        jfc.setAcceptAllFileFilterUsed(false); //disable select-all possibility so only plain text documents can be selected
        jfc.addChoosableFileFilter(new FileNameExtensionFilter("plain text document", "txt")); //add plain text to make it the only thing selectable

        if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { //if the selection fits the criteria
            File selectedFile = jfc.getSelectedFile(); //get selected file as File instance
            return selectedFile.getAbsolutePath();
        }
        return null; //in case the section did not fit the criteria
    }

    /**
     * Reads the file from the given filepath. Converts double-pairs in the file (for example divided by a ",")
     * (those represent the x and y coordinates) to MyPoint instances and returns a MyList instance filled with
     * the new created MyPoint instances. Each Point has to be written in a separate line (for example: 13.5, 6.22).
     * When an unexpected character is found and can not be read, the rest of the file is not read and an error-dialog
     * is shown. After this dialog is closed, the correct parsed points are returned. The returned MyPoint instances get
     * the passed color.
     *
     * @param color the color of the new created MyPoint instances
     * @param path  the path of the file from with to read the coordinates of the new MyPoint instances.
     * @return a MyList instance that holds all new created MyPoints from the file.
     */
    public MyList<MyPoint> readPoints(String path, Color color) {

        MyList<MyPoint> addedPoints = new MyList<>();
        File points = new File(path); //get File instance of given path

        if (points.exists() && points.canRead()) {

            try (BufferedReader in = new BufferedReader(new FileReader(points))) {

                String line;
                while ((line = in.readLine()) != null) { //read the input line by line

                    if (!line.equals("\n") && !line.isEmpty()) { //when line can be read

                        String first = line.replaceAll("(\\d+\\.?\\d*)[^\\d]+\\d+\\.?\\d*", "$1"); //get first number
                        String second = line.replaceAll("\\d+\\.?\\d*[^\\d]+(\\d+\\.?\\d*)", "$1"); //get second number

                        if (!first.equals("") && !second.equals("")) {

                            try {
                                //convert numbers to double-values and add them to the list
                                addedPoints.add(new MyPoint(Double.parseDouble(first), Double.parseDouble(second), color));

                            } catch (NumberFormatException numberFormatException) {

                                //Show error-dialog
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Unexpected character");
                                alert.setContentText("Found unexpected character in the selected file!");
                                alert.showAndWait();
                                numberFormatException.printStackTrace();
                            }
                        }
                    }
                }
                //in is automatically being closed
            } catch (IOException ignored) {}
        }
        return addedPoints;
    }
}
