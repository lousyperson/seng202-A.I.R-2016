package seng202.group4.data.parser;

import seng202.group4.data.dataType.Airport;
import seng202.group4.data.dataType.DaylightSavingsTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jjg64 on 15/08/16.
 */
public class AirportParser {
    private BufferedReader file;
    private Airport thisAirport;
    private String[] splitLine;
    private String splitBy = "\\s*\\,\\s*";
    private int ID;
    private String currentLine;
    private HashMap<String, DaylightSavingsTime> DSTs = new HashMap<String, DaylightSavingsTime>();
    private ArrayList<Airport> airports = new ArrayList<Airport>();
    int index = 0; // Will be used to track the index corresponding to each comma

    public AirportParser(BufferedReader file) {
        this.file = file;
        makeMap();
    }

    private void makeMap() {
        DSTs.put("E", DaylightSavingsTime.E);
        DSTs.put("A", DaylightSavingsTime.A);
        DSTs.put("S", DaylightSavingsTime.S);
        DSTs.put("O", DaylightSavingsTime.O);
        DSTs.put("Z", DaylightSavingsTime.Z);
        DSTs.put("N", DaylightSavingsTime.N);
        DSTs.put("U", DaylightSavingsTime.U);

    }

    private void readString(int i) {
        if (splitLine[i].equals("\\N")) {
            splitLine[i] = null;
        } else {
            // Remove quotation marks
            splitLine[i] = splitLine[i].replaceAll("^\"|\"$", "");
        }
    }

    private String readStringWithCommas() {
        String name = "";
        while (!splitLine[index].endsWith("\"")) {
            name += splitLine[index] + ", ";
            index++;
        }
        name += splitLine[index];
        index++;
        name = name.replaceAll("^\"|\"$", "");  // Removes quotation mark
        return name;
    }

    private void addAirport() throws IOException {
        splitLine = currentLine.split(splitBy);
        index = 1;

        ID = Integer.parseInt(splitLine[0]);
        String name = readStringWithCommas();
        String city = readStringWithCommas();
        String country = readStringWithCommas();

        for (int i = index; i < splitLine.length; i++) {   // Reads rest of variables that cannot have commas
            readString(i);
        }

        thisAirport = new Airport(ID, name, city,
                country, splitLine[index], splitLine[index + 1],
                Double.parseDouble(splitLine[index + 2]), Double.parseDouble(splitLine[index + 3]),
                Double.parseDouble(splitLine[index + 4]), Float.parseFloat(splitLine[index + 5]),
                DSTs.get(splitLine[index + 6]), splitLine[index + 7]);

        airports.add(thisAirport);
    }

    public ArrayList<Airport> makeAirports() throws IOException {
        while ((currentLine = file.readLine()) != null) {
            currentLine = currentLine.trim();
            if (!currentLine.matches("\\w") && !currentLine.matches("")) {
                addAirport();
            }
        }
        return airports;
    }

}
