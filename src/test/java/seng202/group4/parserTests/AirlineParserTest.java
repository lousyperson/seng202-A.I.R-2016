package seng202.group4.parserTests;

import org.junit.Test;
import seng202.group4.data.dataType.Airline;
import seng202.group4.data.parser.AirlineParser;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * @param
 * @returns
 * @throws
 */
public class AirlineParserTest extends ParserTest {
    String path = "testfiles/Airlines/";

    /**
     * Check that if the airline parser parses an empty file, 0 airlines are parsed
     * @throws IOException throws IOException error
     */
    @Test
    public void emptyFileAirline() throws IOException {
        AirlineParser parser = new AirlineParser(fileArray("testfiles/empty.txt"));
        ArrayList<Airline> airlines = parser.makeAirlines();
        int size = airlines.size();
        assertTrue(size == 0);
    }

    /**
     * Given one valid airline, check that the size is 1
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidAirlineSize() throws IOException {
        AirlineParser parser = new AirlineParser(fileArray(path + "oneValidAirline.txt"));
        ArrayList<Airline> airlines = parser.makeAirlines();
        assertTrue(airlines.size() == 1);
    }

    /**
     * Given two valid airlines, check that the size is 2
     * @throws IOException throws IOException error
     */
    @Test
    public void twoValidAirlineSize() throws IOException {
        AirlineParser parser = new AirlineParser(fileArray(path + "twoValidAirlines.txt"));
        ArrayList<Airline> airlines = parser.makeAirlines();
        assertTrue(airlines.size() == 2);
    }

    /**
     * Given three valid airlines, check that the size is 3
     * @throws IOException throws IOException error
     */
    @Test
    public void threeValidAirlineSize() throws IOException {
        AirlineParser parser = new AirlineParser(fileArray(path + "threeValidAirlines.txt"));
        ArrayList<Airline> airlines = parser.makeAirlines();
        assertTrue(airlines.size() == 3);
    }

    /**
     * Check that a valid airline with null is read by the parser
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidAirlineWithNullSize() throws IOException {
        AirlineParser parser = new AirlineParser(fileArray(path + "oneValidAirlineWithNull.txt"));
        ArrayList<Airline> airlines = parser.makeAirlines();
        assertTrue(airlines.size() == 1);
    }

    /**
     * Check that every airline in the 6048Airlines.txt has been read by the parser, check the size is 6048
     * @throws IOException throws IOException error
     */
    @Test
    public void Airlines6048Size() throws IOException {
        AirlineParser parser = new AirlineParser(fileArray(path + "6048Airlines.txt"));
        ArrayList<Airline> airlines = parser.makeAirlines();
        assertTrue(airlines.size() == 6048);

    }

    /**
     * Given one valid airline, check that the content has been parsed correctly
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidAirlineContents() throws IOException {
        AirlineParser parser = new AirlineParser(fileArray(path + "oneValidAirline.txt"));
        ArrayList<Airline> airlines = parser.makeAirlines();
        Airline airline = airlines.get(0);
        boolean isEqual = airline.getID() == 324 && airline.getName().equals("All Nippon Airways") &&
                airline.getAlias().equals("ANA All Nippon Airways") && airline.getIATA().equals("NH") &&
                airline.getICAO().equals("ANA") && airline.getCallsign().equals("ALL NIPPON") &&
                airline.getCountry().equals("Japan") && airline.getActive();
        assertTrue(isEqual);
    }
}
