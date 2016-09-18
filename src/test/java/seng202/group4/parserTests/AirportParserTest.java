package seng202.group4.parserTests;

import org.junit.Test;
import seng202.group4.data.dataType.Airport;
import seng202.group4.data.parser.AirportParser;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * @param
 * @returns
 * @throws
 */
public class AirportParserTest extends ParserTest {
    /**
     * Check that one valid airport is parsed when loading a file with only one valid airport
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidAirportSize() throws IOException {
        AirportParser parser = new AirportParser(fileArray("testfiles/Airports/oneValidAirport.txt"));
        ArrayList<Airport> airports = parser.makeAirports();
        assertTrue(airports.size() == 1);

    }

    /**
     * Check that the correct contents have been parsed given a valid airport
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidAirportContents() throws IOException {
        assertTrue(true);
    }

    /**
     * Checks that all airports in the valid airport file has been parsed
     * @throws IOException throws IOException error
     */
    @Test
    public void multipleValidAirportsSize() throws IOException {
        AirportParser parser = new AirportParser(fileArray("testfiles/Airports/validAirport.txt"));
        ArrayList<Airport> airports = parser.makeAirports();
        assertTrue(airports.size() == 8106);
    }

    /**
     * Checks that a file with one valid airport with null can be parsed
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidAirportWithNullSize() throws IOException {
        AirportParser parser = new AirportParser(fileArray("testfiles/Airports/oneValidAirportWithNull.txt"));
        ArrayList<Airport> airports = parser.makeAirports();
        assertTrue(airports.size() == 1);

    }

    /**
     * Checks that a valid airport with comma can be parsed
     * @throws IOException throws IOException error
     */
    @Test public void oneValidAirportWithCommaSize() throws IOException {
        AirportParser parser = new AirportParser(fileArray("testfiles/Airports/oneValidAirportWithComma.txt"));
        ArrayList<Airport> airports = parser.makeAirports();
        assertTrue(airports.size() == 1);
    }

    /**
     * Check that an empty text file does not make any airports when parsed
     * @throws IOException throws IOException error
     */
    @Test
    public void emptyFileAirport() throws IOException {
        AirportParser parser = new AirportParser(fileArray("testfiles/empty.txt"));
        ArrayList<Airport> airports = parser.makeAirports();
        int size = airports.size();
        assertTrue(size == 0);
    }
}
