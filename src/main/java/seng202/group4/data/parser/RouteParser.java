package seng202.group4.data.parser;


import seng202.group4.data.dataType.Airline;
import seng202.group4.data.dataType.Route;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Reads and parses through the route data from the given list. No error checking is done here.
 */
public class RouteParser {
    private final int ITEMS_PER_LINE = 9;
    private ArrayList<String> file;
    private Route thisRoute;
    private String[] splitLine = new String[ITEMS_PER_LINE];
    private String splitBy = "\\s*\\,\\s*";
    private String currentLine;
    private ArrayList<Route> routes = new ArrayList<Route>();
    private Integer[] usedInts = new Integer[4];
//    private boolean[] isInt = {false, true, false, true, false, true, false, true, false};
//    private String nullIntegerValue = "-420";    // When a null int is there

    /**
     * Initializes the route parser variables.
     *
     * @param file ArrayList
     */
    public RouteParser(ArrayList file) {
        this.file = file;
    }

    private void readString(int i) {
        if (splitLine[i].equals("\\N")) {
            splitLine[i] = null;
        }
    }

    private void readInt(int i) {
        if (splitLine[i].equals("\\N")) {
            usedInts[i / 2] = null;
        } else {
            usedInts[i / 2] = Integer.parseInt(splitLine[i]);
        }
    }

    private ArrayList<String> makeEquipment() {
        ArrayList<String> equipment = new ArrayList<String>();
        String[] splitEquipment = splitLine[8].split("[\\s]+");
        for (String item : splitEquipment) {
            equipment.add(item);
        }
        return equipment;
    }

    private void addRoute(String currentLine) throws IOException {
        splitLine = currentLine.split(splitBy, ITEMS_PER_LINE);
        for (int i = 0; i < ITEMS_PER_LINE; i+= 2) {      // Checks even indices 0 to 8 (strings)
            readString(i);
        }
        for (int i = 1; i < ITEMS_PER_LINE; i+= 2) {      // Checks even indices 1 to 7 (ints)
            readInt(i);
        }
        ArrayList<String> equipment = makeEquipment();
        thisRoute = new Route(splitLine[0], usedInts[0], splitLine[2],
                              usedInts[1], splitLine[4], usedInts[2],
                              splitLine[6], usedInts[3], equipment);

        routes.add(thisRoute);
    }

    /**
     * Makes the singular routes and adds them to the list of routes.
     *
     * @return routes
     * @throws IOException throws IOException error
     */
    public ArrayList<Route> makeRoutes() throws IOException {
        for(String currentLine: file) {
            addRoute(currentLine);
        }
        return routes;
    }

}

