package seng202.group4.data.parser.validator;

import seng202.group4.data.dataType.Flight;
import seng202.group4.data.parser.FlightParser;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by jjg64 on 25/08/16.
 */
public class FlightValidator {
    private File filepath;
    private BufferedReader file;

    public FlightValidator(File filepath) throws FileNotFoundException {
        this.filepath = filepath;
        this.file = new BufferedReader(new FileReader(filepath));
    }

    public Flight makeAirports() throws IOException {
        FlightParser parser = new FlightParser(new BufferedReader(new FileReader(filepath)));
        return parser.makeFlight();
    }
}
