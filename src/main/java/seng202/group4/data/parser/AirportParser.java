package seng202.group4.data.parser;

import seng202.group4.data.dataType.Airport;
import seng202.group4.data.dataType.DaylightSavingsTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by jjg64 on 15/08/16.
 */
public class AirportParser {
    private final int MAX_ITEMS_PER_LINE = 12;
    private BufferedReader file;
    private Airport thisAirport;
    private String[] splitLine = new String[MAX_ITEMS_PER_LINE];
    private String splitBy = ",";
    private int ID;
    private String currentLine;
    private HashMap<String, DaylightSavingsTime> DSTs = new HashMap<String, DaylightSavingsTime>();

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
            splitLine[i] = splitLine[i].replaceAll("^\"|\"$", "");      // Remove quotation marks
        }
    }

    private void addAirport() throws IOException {
        splitLine = currentLine.split(splitBy, MAX_ITEMS_PER_LINE);
        ID = Integer.parseInt(splitLine[0]);
        for (int i = 1; i <= 11; i++) {      // Checks indices 1 to 11
            readString(i);
        }
        thisAirport = new Airport(ID, splitLine[1], splitLine[2],
                splitLine[3], splitLine[4], splitLine[5],
                Double.parseDouble(splitLine[6]), Double.parseDouble(splitLine[7]),
                Double.parseDouble(splitLine[8]), Float.parseFloat(splitLine[9]),
                DSTs.get(splitLine[10]), splitLine[11]);
    }

    public void readFile() throws IOException {
        while ((currentLine = file.readLine()) != null) {
            addAirport();
        }
    }

}

