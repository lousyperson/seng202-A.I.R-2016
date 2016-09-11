package seng202.group4.data.parser.validator;

import seng202.group4.data.dataType.Airport;
import seng202.group4.data.parser.AirportParser;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by jjg64 on 25/08/16.
 */
public class AirportValidator {
    private File filepath;
    private BufferedReader file;

    public AirportValidator(File filepath) throws FileNotFoundException {
        this.filepath = filepath;
        this.file = new BufferedReader(new FileReader(filepath));
    }

    public ArrayList<Airport> makeAirports() throws IOException {
        AirportParser parser = new AirportParser(new BufferedReader(new FileReader(filepath)));
        return parser.makeAirports();
    }
}
