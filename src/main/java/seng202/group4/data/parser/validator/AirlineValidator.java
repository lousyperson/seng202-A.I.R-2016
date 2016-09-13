package seng202.group4.data.parser.validator;

import javafx.scene.control.Alert;
import seng202.group4.data.dataType.Airline;
import seng202.group4.data.parser.AirlineParser;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by jjg64 on 25/08/16.
 */
public class AirlineValidator {
    private final int ITEMS_PER_LINE = 8;
    private InputStream filepath;
    private BufferedReader file;
    private String[] splitLine = new String[ITEMS_PER_LINE + 1];
    private String splitBy = "\\s*\\,\\s*";
    private String currentLine;
    private int lineNumber = 0;
    private Alert alert;
    private boolean hasError = false;
    private ArrayList<String> stringArray = new ArrayList<>();

    public AirlineValidator(InputStream filepath) throws FileNotFoundException {
        this.filepath = filepath;
        this.file = new BufferedReader(new InputStreamReader(filepath));
        System.out.println("yo" + file.toString());
    }

    public ArrayList<Airline> makeAirlines() throws IOException {
        System.out.println("in make airlines ");

//        BufferedReader mine = new BufferedReader(new InputStreamReader(filepath));
//        if (mine.readLine() != null){
//            System.out.println("send mine: " + mine.readLine());
//        }
        while ((currentLine = file.readLine()) != null) {
            lineNumber++;
            currentLine = currentLine.trim();
            //System.out.println(currentLine);
            if (!currentLine.matches("\\w") && !currentLine.equals("")) {
                validateLine();
                stringArray.add(currentLine);
            }
            if (hasError) {
                System.out.println("ERROR");
                return null;
            }
        }
        // no errors so continue parsing
        AirlineParser parser = new AirlineParser(stringArray);
        System.out.println("sent to parser");
        return parser.makeAirlines();
    }

    private void validateLine() throws IOException {
        //System.out.println("in validate");
        splitLine = currentLine.split(splitBy, ITEMS_PER_LINE + 1);
        if (splitLine.length != ITEMS_PER_LINE) {
            makeAlert("Expected " + ITEMS_PER_LINE + " comma separated variables.");
        } else {
            checkLine();
        }
    }

    private void checkLine() throws IOException {
        // Error if ID is invalid (ID must be an int)
        try {
            Integer.parseInt(splitLine[0]);
        } catch (NumberFormatException e) {
            makeAlert("Airline ID should be a number with no decimal points.");
            return;
        }

        // Error if anything is invalid
        if (!checkString(1)) {                                                                                          // Name
            makeAlert("Airline Name should only contain letters, spaces and hyphens.");
            return;
        } else if (!checkString(2)) {                                                                                   // Alias
            makeAlert("Airline alias should be a string.");
            return;
        } else if (!checkString(3)) {                                                                                   // IATA
            makeAlert("Airline IATA should be letters with a length two.");
            return;
        } else if (!checkString(4)) {                                                                                   // ICAO
            makeAlert("Airline ICAO should be letters with a length of three.");
            return;
        } else if (!checkString(5)) {                                                                                   // Callsign
            makeAlert("Airline Callsign should only contain letters, spaces and hyphens.");
            return;
        } else if (!checkString(6)) {                                                                                   // Country
            makeAlert("Airline Country should only contain letters, spaces and hyphens.");
        } else if (!(splitLine[7].toUpperCase().equals("\"Y\"")) && !(splitLine[7].toUpperCase().equals("\"N\""))) {    // Active
            makeAlert("Airline activity should only be either \"Y\" or \"N\".");
            return;
        }
    }

    // Returns true if the string is valid
    private boolean checkString(int i) {
        boolean isNull = (splitLine[i].equals("\\N"));
        if (!isNull) {
            // Checks if the string starts and ends with " and contains no numbers
            return splitLine[i].startsWith("\"") && splitLine[i].endsWith("\"");
        } else {
            return true;
        }
    }

    private void makeAlert(String message) {
        hasError = true;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("There is an error in your file on line " + lineNumber);
        alert.setContentText(message + "\nNo airlines were added.\nPlease go to help drop down for file formatting help.");
        alert.showAndWait();
    }
}
