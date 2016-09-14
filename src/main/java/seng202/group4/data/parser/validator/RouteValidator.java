package seng202.group4.data.parser.validator;

import javafx.scene.control.Alert;
import seng202.group4.data.dataType.Route;
import seng202.group4.data.parser.RouteParser;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by jjg64 on 25/08/16.
 */
public class RouteValidator {
    private final int ITEMS_PER_LINE = 9;
    private InputStream filepath;
    private BufferedReader file;
    private String[] splitLine = new String[ITEMS_PER_LINE + 1];
    private String splitBy = "\\s*\\,\\s*";
    private String currentLine;
    private int lineNumber = 0;
    private Alert alert;
    private boolean hasError = false;
    private ArrayList<String> stringArray = new ArrayList<>();

    public RouteValidator(InputStream filepath) throws FileNotFoundException {
        this.filepath = filepath;
        this.file = new BufferedReader(new InputStreamReader(filepath));
    }

    public ArrayList<Route> makeroutes() throws IOException {
        while ((currentLine = file.readLine()) != null) {
            lineNumber++;
            currentLine = currentLine.trim();
            if (!currentLine.matches("\\w") && !currentLine.equals("")) {
                validateLine();
                stringArray.add(currentLine);
            }
            if (hasError) {
                return null;
            }
        }
        // no errors so continue parsing
        RouteParser parser = new RouteParser(stringArray);
        return parser.makeRoutes();
    }

    private void validateLine() throws IOException {
        splitLine = currentLine.split(splitBy, ITEMS_PER_LINE + 1);
        if (splitLine.length != ITEMS_PER_LINE) {
            makeAlert("Expected " + ITEMS_PER_LINE + " comma separated variables.");
        } else {
            checkLine();
        }
    }

    private void checkLine() throws IOException {

        // Error if anything is invalid (int checker)
        if (!checkInt(1)) {                                                     // Airline ID
            makeAlert("Airline ID should be a number or \"\\N\"");
            return;
        } else if (!checkInt(3)) {                                              // Source airport ID
            makeAlert("Source airport ID should be a number or \"\\N\"");
            return;
        } else if (!checkInt(5)) {                                              // Destination airport ID
            makeAlert("Destination airport ID should be a number or \"\\N\"");
            return;
        } else if (!checkInt(7)) {                                              // Number of stops
            makeAlert("Number of stops should be a number or \"\\N\"");
            return;
        }

        else if (splitLine[6].length() > 0 && !(splitLine[6].toUpperCase().equals("Y") || splitLine[6].equals("\\N"))) { // Codeshare
            makeAlert("Codeshare must be \"Y\", empty or \"\\N\"");
            return;
        }
    }


    // Returns true if integer is valid
    private boolean checkInt(int i) {
        if (splitLine[i].equals("\\N")) {
            return true;
        } else {
            try {
                Integer.parseInt(splitLine[i]);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }


    private void makeAlert(String message) {
        hasError = true;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("There is an error in your file on line " + lineNumber);
        alert.setContentText(message + "\nNo routes were added.\nPlease go to help drop down for file formatting help.");
        alert.showAndWait();
    }
}
