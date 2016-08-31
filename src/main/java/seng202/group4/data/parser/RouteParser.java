package seng202.group4.data.parser;


import seng202.group4.data.dataType.Airline;
import seng202.group4.data.dataType.Route;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jjg64 on 15/08/16.
 */
public class RouteParser {
    private final int ITEMS_PER_LINE = 9;
    private BufferedReader file;
    private Route thisRoute;
    private String[] splitLine = new String[ITEMS_PER_LINE];
    private String splitBy = "\\s*\\,\\s*";
    private boolean active;
    private String currentLine;
    private ArrayList<Route> routes = new ArrayList<Route>();

    RouteParser(BufferedReader file) {
        this.file = file;
    }

    private void readString(int i) {
        if (splitLine[i].equals("\\N")) {
            splitLine[i] = null;
        }
    }

    private ArrayList<String> makeEquipment() {
        ArrayList<String> equipment = new ArrayList<String>();
        String[] splitEquipment = splitLine[8].split("\\s");
        for (String item: splitEquipment) {
            equipment.add(item);
        }
        return equipment;
    }

    private void addRoute() throws IOException {
        splitLine = currentLine.split(splitBy, ITEMS_PER_LINE);
        for (int i = 1; i < ITEMS_PER_LINE; i++) {      // Checks indices 1 to 7
            readString(i);
        }
        ArrayList<String> equipment = makeEquipment();
        thisRoute = new Route(splitLine[0], Integer.parseInt(splitLine[1]), splitLine[2],
                              Integer.parseInt(splitLine[3]), splitLine[4], Integer.parseInt(splitLine[5]),
                              splitLine[6], Integer.parseInt(splitLine[7]), equipment);

        routes.add(thisRoute);
    }

    public ArrayList<Route> makeAirlines() throws IOException {
        while ((currentLine = file.readLine()) != null) {
            currentLine = currentLine.trim();
            if (!currentLine.matches("\\w") && !currentLine.matches("")) {
                addRoute();
            }
        }
        return routes;
    }

}

