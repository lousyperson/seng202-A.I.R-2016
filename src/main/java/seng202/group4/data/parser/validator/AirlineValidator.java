package seng202.group4.data.parser.validator;

import seng202.group4.data.dataType.Airline;
import seng202.group4.data.parser.AirlineParser;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by jjg64 on 25/08/16.
 */
public class AirlineValidator {
    private final int MAX_ITEMS_PER_LINE = 8;
    private File filepath;
    private BufferedReader file;
    private String[] splitLine = new String[MAX_ITEMS_PER_LINE];
    private String splitBy = ",";
    private int ID;
    private boolean active;
    private String currentLine;

    public AirlineValidator(File filepath) throws FileNotFoundException {
        this.filepath = filepath;
        this.file = new BufferedReader(new FileReader(filepath));
    }

    public ArrayList<Airline> makeAirlines() throws IOException {


        AirlineParser parser = new AirlineParser(new BufferedReader(new FileReader(filepath)));
        return parser.makeAirlines();
    }
}
