package seng202.group4.data.parser.validator;

import javafx.scene.control.Alert;
import seng202.group4.GUI.AlertPopup;
import seng202.group4.data.dataType.Flight;
import seng202.group4.data.dataType.FlightPosition;
import seng202.group4.data.parser.FlightParser;

import java.io.*;
import java.util.ArrayList;

/**
 * The FlightValidator class checks to make sure that the flight data file that is being parsed in is correctly formatted
 * so that all data is represented accurately. Error checks the file and uses FlightParser if valid.
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

    /**
     * Parses in the flight data to be validated so that it is can be read and checked.
     *
     * @param filepath InputStream
     * @throws FileNotFoundException Throws error when file is not found
     */
    public FlightValidator(InputStream filepath) throws FileNotFoundException {
        this.filepath = filepath;
        this.file = new BufferedReader(new InputStreamReader(filepath));
    }

    /**
     * Creates the flights one by one, checking that each is valid as it is created.
     *
     * @return Flight
     * @throws IOException Throws IOException error
     */
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

    /**
     * Checks to see if a single line is valid.
     *
     * @throws IOException
     */
    private void validateLine() throws IOException {
        splitLine = currentLine.split(splitBy, ITEMS_PER_LINE + 1);
        if (splitLine.length != ITEMS_PER_LINE) {
            makeAlert("Expected " + ITEMS_PER_LINE + " comma separated variables.");
        } else {
            checkLine();
        }
    }

    /**
     * Ensures that every individual part of the line is valid.
     *
     * @throws IOException
     */
    private void checkLine() throws IOException {
        // Strings do not need to be checked, as quotation marks is no longer a constraint

        if (!checkNumber(2)) {
            makeAlert("Altitude must be a number in feet");
            return;
        } else if (!checkNumber(3, -90 , 90)) {
            makeAlert("Latitude must be a number in degrees");
            return;
        } else if (!checkNumber(4, -180, 180)) {
            makeAlert("Longitude must be a number in degrees");
            return;
        }

    }

    /**
     * Checks that was is pointed o is a string.
     *
     * @param i int
     * @return if a string, returns true, else, false.
     */
    private boolean checkString(int i) {
        boolean isValid = true;
        if (splitLine[i].contains(" ")) {
            isValid = false;
        }
        return isValid;
    }

    private boolean checkNumber(int i, int lowerBound, int upperBound) {
        try {
            Double number = Double.parseDouble(splitLine[i]);
            if (number >= lowerBound && number <= upperBound) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean checkNumber(int i) {
        try {
            Double.parseDouble(splitLine[i]);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Makes an alert to the user if there is an error in the formatting of the raw data.
     * @param message String
     */
    private void makeAlert(String message) {
        hasError = true;
        AlertPopup.makeError("There is an error in your file on line " + lineNumber,
                message + "\n\nFlight was not added.\n\nPlease go to help drop down for file formatting help.");
    }
}
