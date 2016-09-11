package seng202.group4.data.parser;

import seng202.group4.data.dataType.Flight;
import seng202.group4.data.dataType.FlightPosition;
import seng202.group4.data.dataType.Route;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jjg64 on 15/08/16.
 */
public class FlightParser {
    private BufferedReader file;
    private final int ITEMS_PER_LINE = 5;
    private FlightPosition thisPosition;
    private String[] splitLine = new String[ITEMS_PER_LINE];
    private String splitBy = "\\s*\\,\\s*";
    private String currentLine;
    private ArrayList<FlightPosition> positions = new ArrayList<FlightPosition>();


    public FlightParser(BufferedReader file) {
        this.file = file;
    }

    private void readString(int i) {
        if (splitLine[i].equals("\\N")) {
            splitLine[i] = null;
        }
    }

    private void addFlightPosition() throws IOException {
        splitLine = currentLine.split(splitBy, ITEMS_PER_LINE);
        readString(0);  // Type
        readString(1);  // ID

        thisPosition = new FlightPosition(splitLine[0], splitLine[1], Double.parseDouble(splitLine[2]),
                Double.parseDouble(splitLine[3]), Double.parseDouble(splitLine[4]));
        positions.add(thisPosition);
    }

    public Flight makeFlight() throws IOException {
        while ((currentLine = file.readLine()) != null) {
            currentLine = currentLine.trim();
            if (!currentLine.matches("\\w") && !currentLine.matches("")) {
                addFlightPosition();
            }
        }
        Flight flight = new Flight(positions);
        return flight;
    }

}

