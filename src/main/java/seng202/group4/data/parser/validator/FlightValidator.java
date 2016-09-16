package seng202.group4.data.parser.validator;

import javafx.scene.control.Alert;
import seng202.group4.data.dataType.Flight;
import seng202.group4.data.dataType.FlightPosition;
import seng202.group4.data.parser.FlightParser;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by jjg64 on 25/08/16.
 */
public class FlightValidator {
    private final int ITEMS_PER_LINE = 5;
    private InputStream filepath;
    private BufferedReader file;
    private String[] splitLine = new String[ITEMS_PER_LINE + 1];
    private String splitBy = "\\s*\\,\\s*";
    private String currentLine;
    private int lineNumber = 0;
    private Alert alert;
    private boolean hasError = false;
    private ArrayList<String> stringArray = new ArrayList<>();

    public FlightValidator(InputStream filepath) throws FileNotFoundException {
        this.filepath = filepath;
        this.file = new BufferedReader(new InputStreamReader(filepath));
    }

    public Flight makeFlight() throws IOException {
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
        FlightParser parser = new FlightParser(stringArray);
        return parser.makeFlight();
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
        // Strings do not need to be checked, as quotation marks is no longer a constraint

        if (!checkNumber(2)) {
            makeAlert("Altitude must be a number.");
            return;
        } else if (!checkNumber(3)) {
            makeAlert("Longitude must be a number.");
            return;
        } else if (!checkNumber(4)) {
            makeAlert("Latitude must be a number.");
            return;
        }

    }

    private boolean checkString(int i) {
        boolean isValid = true;
        if (splitLine[i].contains(" ")) {
            isValid = false;
        }
        return isValid;
    }

    private boolean checkNumber(int i) {
        try {
            Double.parseDouble(splitLine[i]);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void makeAlert(String message) {
        hasError = true;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("There is an error in your file on line " + lineNumber);
        alert.setContentText(message + "\nFlight was not added.\nPlease go to help drop down for file formatting help.");
        alert.showAndWait();
    }
}
