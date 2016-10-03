package seng202.group4.data.parser.validator;

import javafx.scene.control.Alert;
import seng202.group4.GUI.AlertPopup;
import seng202.group4.data.dataType.Airport;
import seng202.group4.data.parser.AirportParser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Ensures that the airport data from the file is valid by reading and checking to ensure it meets formatting
 * expectations. Error checks the file and uses the AirportParser if valid.
 */
public class AirportValidator {
    private final int MIN_ITEMS_PER_LINE = 12;
    private InputStream filepath;
    private BufferedReader file;
    private String[] splitLine;
    private String splitBy = "\\s*\\,\\s*";
    private String currentLine;
    private int lineNumber = 0;
    private Alert alert;
    private boolean hasError = false;
    private Set<String> DSTs = new HashSet<String>();
    int index;
    private ArrayList<String> stringArray = new ArrayList<>();

    /**
     * Builds the daylight saving time enum and checks that the line meets the minimum number of items per line.
     *
     * @throws IOException
     */
    private void validateLine() throws IOException {
        makeMap();
        splitLine = currentLine.split(splitBy);
        if (splitLine.length < MIN_ITEMS_PER_LINE) {
            makeAlert("Expected " + MIN_ITEMS_PER_LINE + " comma separated variables.");
        } else {
            checkLine();
        }
    }

    /**
     * Makes a list of airports, by making the airports and checking that each is a valid airport along the way.
     *
     * @return Array list of airports
     * @throws IOException Throws IOException error
     */
    public ArrayList<Airport> makeAirports() throws IOException {
        while ((currentLine = file.readLine()) != null) {
            lineNumber++;
            currentLine = currentLine.trim();
            if (!currentLine.matches("\\w") && !currentLine.equals("")) {
                validateLine();
                stringArray.add(currentLine);
            }
            if (hasError) {
                return new ArrayList<Airport>();
            }
        }

        // no errors so continue parsing
        AirportParser parser = new AirportParser(stringArray);
        return parser.makeAirports();
    }

    private void makeMap() {
        DSTs.add("\"E\"");
        DSTs.add("\"A\"");
        DSTs.add("\"S\"");
        DSTs.add("\"O\"");
        DSTs.add("\"Z\"");
        DSTs.add("\"N\"");
        DSTs.add("\"U\"");
    }

    /**
     * Checks that the singular line contains data of the expected format.
     *
     * @throws IOException
     */
    private void checkLine() throws IOException {
        index = 1;
        try {
            Integer.parseInt(splitLine[0]);
        } catch (NumberFormatException e) {
            makeAlert("Airport ID should be a number with no decimal points.");
        }

        if (!checkStringWithCommas()) {
            makeAlert("Airport name must be in quotations");
            return;
        } else if (!checkStringWithCommas()) {
            makeAlert("Airport city must be in quotations");
            return;
        } else if (!checkStringWithCommas()) {
            makeAlert("Airport country must be in quotations");
            return;
        } else if (!checkString(0) && !(splitLine[index].length() == 5 || splitLine[index].equals("\\N"))) {
            makeAlert("IATA must be letters of length 3 in quotations or \\N");
            return;
        } else if (!checkString(0) && !(splitLine[index].length() == 6 || splitLine[index].equals("\\N"))) {
            makeAlert("ICAO must be letters of length 4 in quotations");
            return;
        } else if (!checkNumber(2, -90, 90)) {
            makeAlert("Latitude must be a number in degrees");
            return;
        } else if (!checkNumber(3, -180, 180)) {
            makeAlert("Longitude must be a number in degrees");
            return;
        } else if (!checkNumber(4)) {
            makeAlert("Altitude must be a number in feet");
            return;
        } else if (!checkNumber(5)) {
            makeAlert("Timezone must be a number");
            return;
        } else if (!DSTs.contains(splitLine[index + 6])) {
            makeAlert("DST must be E, A, S, O, Z, N or U in quotations\nUse U for Unknown or N for None");
            return;
        } else if (!checkString(7)) {
            makeAlert("Database time zone name must be in quotations");
            return;
        }
    }

    /**
     * Checks that the string with commas is valid.
     *
     * @return isValid, a boolean, depending on whether on not the string is valid, true if valid, false if not.
     */
    private boolean checkStringWithCommas() {
        boolean isValid = true;
        if (splitLine[index].startsWith("\"")) {
            while (!splitLine[index].endsWith("\"") && index < splitLine.length) {
                index++;
            }
            if (!splitLine[index].endsWith("\"")) {
                isValid = false;
            }
            index++;
        } else {
            isValid  = false;
        }
        return isValid;
    }

    private boolean checkNumber(int i, int lowerBound, int upperBound) {
        try {
            Double number = Double.parseDouble(splitLine[index + i]);
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
            Double.parseDouble(splitLine[index + i]);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks to see if the given index points to a string.
     *
     * @param i int
     * @return a boolean, true if it points to a string, false if not.
     */
    private boolean checkString(int i) {
        boolean isValid = true;
        if (!splitLine[index + i].equals("\\N")) {
            isValid = splitLine[index + i].startsWith("\"") && splitLine[index + i].endsWith("\"");
        }
        return isValid;
    }

    /**
     * Builds the reader so that each line can be read and an airport bult for that line.
     *
     * @param filepath InputStream
     * @throws FileNotFoundException Throws error when file is not found
     */
    public AirportValidator(InputStream filepath) throws FileNotFoundException {
        this.filepath = filepath;
        this.file = new BufferedReader(new InputStreamReader(filepath));
    }

    private void makeAlert(String message) {
        hasError = true;
        AlertPopup.makeError("There is an error in your file on line " + lineNumber,
                message + "\n\nNo airports were added.\n\nPlease go to help drop down for file formatting help.");
    }
}
