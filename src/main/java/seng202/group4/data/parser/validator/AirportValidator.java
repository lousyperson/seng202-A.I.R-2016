package seng202.group4.data.parser.validator;

import javafx.scene.control.Alert;
import seng202.group4.data.dataType.Airport;
import seng202.group4.data.parser.AirportParser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Checks that the raw input from the user is valid.
 * Created by jjg64 on 25/08/16.
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

<<<<<<< HEAD
    /**
     * Makes the Daylight Saving time enum.
     */
=======
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

>>>>>>> cd71ee9bc1b23d43e6f666b8aecc670be18fc607
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
        } else if (!checkNumber(2)) {
            makeAlert("Latitude must be a number");
            return;
        } else if (!checkNumber(3)) {
            makeAlert("Longitude must be a number");
            return;
        } else if (!checkNumber(4)) {
            makeAlert("Altitude must be a number");
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

    /**
     * Checks to see if the given index is a number.
     * @param i
     * @return a boolean, true if it is a number, false if not.
     */
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
     * @param i
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
     * Takes the filepath and builds a new buffered reader so that the file can be parsed.
     * @param filepath
     * @throws FileNotFoundException
     */
    public AirportValidator(InputStream filepath) throws FileNotFoundException {
        this.filepath = filepath;
        this.file = new BufferedReader(new InputStreamReader(filepath));
    }

<<<<<<< HEAD
    /**
     * Makes a list of airports.
     * @return The list of airports, null otherwise.
     * @throws IOException
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
                return null;
            }
        }

        // no errors so continue parsing
        AirportParser parser = new AirportParser(stringArray);
        return parser.makeAirports();
    }

    /**
     * Makes an alert in case there is an error in formatting so that the user can alter their raw data.
     * @param message
     */
=======
>>>>>>> cd71ee9bc1b23d43e6f666b8aecc670be18fc607
    private void makeAlert(String message) {
        hasError = true;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("There is an error in your file on line " + lineNumber);
        alert.setContentText(message + "\nNo airports were added.\nPlease go to help drop down for file formatting help.");
        alert.showAndWait();
    }
}
