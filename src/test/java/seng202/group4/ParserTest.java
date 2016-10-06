package seng202.group4;


import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.group4.data.dataType.Airline;
import seng202.group4.data.dataType.Airport;
import seng202.group4.data.dataType.Flight;
import seng202.group4.data.dataType.Route;
import seng202.group4.data.parser.AirlineParser;
import seng202.group4.data.parser.AirportParser;
import seng202.group4.data.parser.FlightParser;
import seng202.group4.data.parser.RouteParser;
import seng202.group4.data.repository.AirlineRepository;
import seng202.group4.data.repository.AirportRepository;
import seng202.group4.data.repository.RouteRepository;

import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * ParserTest contains the fileArray method which is needed for each distinct type of parser test.
 */
public class ParserTest {

    /**
     * Given a file path convert file lines into an array of strings.
     *
     * @param path String
     * @return stringArray if the file exists otherwise null
     * @throws IOException throws IOException error
     */
    // given a file path convert file lines into an array of strings
    public ArrayList<String> fileArray(String path) throws IOException {
        File in = new File(path);
        // check if file exists
        if(in.exists()){
            String currentLine;
            ArrayList<String> stringArray = new ArrayList<>(); // array to store each line
            BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(in)));
            while((currentLine = file.readLine()) != null){
                currentLine.trim();
                stringArray.add(currentLine); // add the line to the string array
            }
            return stringArray;
        }
        else{
            return null;
        }
    }
}
