package seng202.group4;


import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.group4.data.dataType.Airline;
import seng202.group4.data.dataType.Airport;
import seng202.group4.data.dataType.Route;
import seng202.group4.data.parser.AirlineParser;
import seng202.group4.data.repository.AirlineRepository;
import seng202.group4.data.repository.AirportRepository;
import seng202.group4.data.repository.RouteRepository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by jjg64 on 25/08/16.
 */
public class ParserTest {

    /* Airline parser test cases */

    @Test
    public void oneValidAirlineSize() throws IOException {
        AirlineParser parser = new AirlineParser(new BufferedReader(new FileReader("testfiles/Airlines/oneValidAirline.txt")));
        ArrayList<Airline> airlines = parser.makeAirlines();
        assertTrue(airlines.size() == 1);

    }

    @Test
    public void multipleValidAirlinesSize() throws IOException {
        AirlineParser parser = new AirlineParser(new BufferedReader(new FileReader("testfiles/Airlines/validAirline.txt")));
        ArrayList<Airline> airlines = parser.makeAirlines();
        assertTrue(airlines.size() == 6048);

    }

    @Test
    public void oneValidAirlineWithNullSize() throws IOException {
        AirlineParser parser = new AirlineParser(new BufferedReader(new FileReader("testfiles/Airlines/oneValidAirlineWithNull.txt")));
        ArrayList<Airline> airlines = parser.makeAirlines();
        assertTrue(airlines.size() == 1);
    }

    @Test
    public void emptyFile() throws IOException {
        AirlineParser parser = new AirlineParser(new BufferedReader(new FileReader("testfiles/empty.txt")));
        ArrayList<Airline> airlines = parser.makeAirlines();
        int size = airlines.size();
        assertTrue(size == 0);
    }
}
