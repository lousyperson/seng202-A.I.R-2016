package seng202.group4.data.parser;

import seng202.group4.data.dataType.Flight;
import seng202.group4.data.dataType.FlightPosition;
import seng202.group4.data.dataType.Route;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Reads through the flight data from the given list. No error checking is done here.
 */
public class FlightParser {
    private ArrayList<String> file;
    private final int ITEMS_PER_LINE = 5;
    private FlightPosition thisPosition;
    private String[] splitLine = new String[ITEMS_PER_LINE];
    private String splitBy = "\\s*\\,\\s*";
    private ArrayList<FlightPosition> positions = new ArrayList<>();

    /**
     * Initializes the FlightParser variables.
     *
     * @param file ArrayList
     */
    public FlightParser(ArrayList file) {
        this.file = file;
    }

    private void readString(int i) {
        if (splitLine[i].equals("\\N")) {
            splitLine[i] = null;
        }
    }

    private void addFlightPosition(String currentLine) throws IOException {
        splitLine = currentLine.split(splitBy, ITEMS_PER_LINE);
        readString(0);  // Type
        readString(1);  // ID

        thisPosition = new FlightPosition(splitLine[0], splitLine[1], Double.parseDouble(splitLine[2]),
                Double.parseDouble(splitLine[3]), Double.parseDouble(splitLine[4]));
        positions.add(thisPosition);
    }

    /**
     * Makes a singular flight from the current line in the flight data.
     *
     * @return Flight
     * @throws IOException throws IOException error
     */
    public Flight makeFlight() throws IOException {
        for(String currentLine: file){
            addFlightPosition(currentLine);
        }
        Flight flight = new Flight(positions);
        return flight;
    }

}

