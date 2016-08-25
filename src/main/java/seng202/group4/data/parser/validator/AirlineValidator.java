package seng202.group4.data.parser.validator;

import seng202.group4.data.dataType.Airline;
import seng202.group4.data.parser.AirlineParser;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by jjg64 on 25/08/16.
 */
public class AirlineValidator {
    private final int ITEMS_PER_LINE = 8;
    private File filepath;
    private BufferedReader file;
    private String[] splitLine = new String[ITEMS_PER_LINE + 1];
    private String splitBy = ",";
    private String currentLine;
    private boolean isInvalidString;
    private int lineNumber = 0;

    public AirlineValidator(File filepath) throws FileNotFoundException {
        this.filepath = filepath;
        this.file = new BufferedReader(new FileReader(filepath));
    }

    public ArrayList<Airline> makeAirlines() throws IOException {
        while ((currentLine = file.readLine()) != null) {
            lineNumber++;
            if (!currentLine.matches("\\w")) {
                validateLine();
            }
        }

        AirlineParser parser = new AirlineParser(new BufferedReader(new FileReader(filepath)));
        return parser.makeAirlines();
    }

    private void validateLine() throws IOException {
        splitLine = currentLine.split(splitBy, ITEMS_PER_LINE + 1);
        if (splitLine.length != ITEMS_PER_LINE) {
            System.out.println(splitLine.length);
            throw new IOException();
        } else {
            checkLine();
        }
    }

    private void checkLine() throws IOException {
        try {
            Integer.parseInt(splitLine[0]);
        } catch (NumberFormatException e) {
            System.out.println(0);
            throw new IOException();
        }


        if (checkString(1)) {   // Name
            System.out.println(1);
            throw new IOException();
        } else if (checkString(2)) {    // Alias
            System.out.println(2);
            throw new IOException();
        } else if (checkString(3) && splitLine[4].length() != 4 && !splitLine[4].equals("\\N")) {  // IATA must be length 2 (inlcudes quotation)
            System.out.println(3);
            throw new IOException();
        } else if (checkString(4) && splitLine[4].length() != 5 && !splitLine[4].equals("\\N")) {  // ICAO must be length 3 (includes quoation)
            System.out.println(4);
            throw new IOException();
        } else if (checkString(5)) {    // Callsign
            System.out.println(5);
            throw new IOException();
        } else if (checkString(6)) {    // Country
            System.out.println(6);
            throw new IOException();
        } else if (!(splitLine[7].toUpperCase().equals("\"Y\"")) && !(splitLine[7].toUpperCase().equals("\"N\""))) {
            System.out.println(7);
            throw new IOException();
        }
    }

    private boolean checkString(int i) {
        isInvalidString = !(splitLine[i].equals("\\N"));
        isInvalidString = isInvalidString && !(splitLine[i].startsWith("\"") && splitLine[i].endsWith("\""));
        return isInvalidString;
    }
}