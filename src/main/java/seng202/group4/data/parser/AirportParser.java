package seng202.group4.data.parser;

import seng202.group4.data.dataType.Airport;
import seng202.group4.data.dataType.DaylightSavingsTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * AirportParser.java parses and creates the list of airports for the raw data list input by the user
 * Created jjg64 on 15/08/16.
 */
public class AirportParser {
    private ArrayList<String> file;
    private Airport thisAirport;
    private String[] splitLine;
    private String splitBy = "\\s*\\,\\s*";
    private int ID;
    private HashMap<String, DaylightSavingsTime> DSTs = new HashMap<String, DaylightSavingsTime>();
    private ArrayList<Airport> airports = new ArrayList<Airport>();
    int index = 0; // Will be used to track the index corresponding to each comma

    /**Takes the array list file and utilises it to make an enum for the daylight saving time */
    public AirportParser(ArrayList file) {
        this.file = file;
        makeMap();
    }

    /**Enum for the daylight saving stuff*/
    private void makeMap() {
        DSTs.put("E", DaylightSavingsTime.E);
        DSTs.put("A", DaylightSavingsTime.A);
        DSTs.put("S", DaylightSavingsTime.S);
        DSTs.put("O", DaylightSavingsTime.O);
        DSTs.put("Z", DaylightSavingsTime.Z);
        DSTs.put("N", DaylightSavingsTime.N);
        DSTs.put("U", DaylightSavingsTime.U);

    }

    /**Reads the string, removing all unnecessary characters*/
    private void readString(int i) {
        if (splitLine[i].equals("\\N")) {
            splitLine[i] = null;
        } else {
            // Remove quotation marks
            splitLine[i] = splitLine[i].replaceAll("^\"|\"$", "");
        }
    }

    /**Reads through a string, if it contains commas where the commas is a part of the string and not a separator
    * of things within the super string, then this gets through the string
    * @return name, where name is the string without invalid characters*/
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

    /**Adds an individual airport to the data list, using readStringWithCommas so that if a comma is in the middle of an
     * attribute it is not an issue
     * @param takes in a singular line in the form of a string
     * */
    private void addAirport(String currentLine) throws IOException {
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

    /**
    *Makes a list of airports
    * @return airports, the list of all the airports from the raw input data*/
    public ArrayList<Airport> makeAirports() throws IOException {
        for(String currentLine: file){
            addAirport(currentLine);
        }
        return airports;
    }

}
