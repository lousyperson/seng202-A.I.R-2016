package seng202.group4;

import org.junit.Test;
import seng202.group4.data.dataType.Airport;
import seng202.group4.data.parser.AirportParser;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * Tests the AirportParser class with valid Airport files.
 */
public class AirportParserTest extends ParserTest {
    String path = "testfiles/Airports/";

    /**
     * Gets the string of the flight position that is used for testing.
     */
    private String getString(Airport airport) {
        String s = "";
        s += airport.getID() + "," + airport.getName() + "," + airport.getCity() + "," + airport.getCountry() + "," +
                airport.getIATA() + "," + airport.getICAO() + "," + airport.getLatitude() + "," +
                airport.getLongitude() + "," + airport.getAltitude() + "," + airport.getTimezone() + "," +
                airport.getDST() + "," + airport.getTz();
        return s;
    }

    /**
     * Check that an empty text file does not make any airports when parsed.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void emptyFileAirport() throws IOException {
        AirportParser parser = new AirportParser(fileArray("testfiles/empty.txt"));
        ArrayList<Airport> airports = parser.makeAirports();
        int size = airports.size();
        assertTrue(size == 0);
    }

    /**
     * Check that one valid airport is parsed when loading a file with two valid airports.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void twoValidAirportsSize() throws IOException {
        AirportParser parser = new AirportParser(fileArray(path + "twoValidAirports.txt"));
        ArrayList<Airport> airports = parser.makeAirports();
        assertTrue(airports.size() == 2);

    }

    /**
     * Check that one valid airport is parsed when loading a file with three valid airports.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void threeValidAirportsSize() throws IOException {
        AirportParser parser = new AirportParser(fileArray(path + "threeValidAirports.txt"));
        ArrayList<Airport> airports = parser.makeAirports();
        assertTrue(airports.size() == 3);

    }

    /**
     * Check that one valid airport is parsed when loading a file with only one valid airport.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidAirportSize() throws IOException {
        AirportParser parser = new AirportParser(fileArray(path + "oneValidAirport.txt"));
        ArrayList<Airport> airports = parser.makeAirports();
        assertTrue(airports.size() == 1);

    }

    /**
     * Checks that a file with one valid airport with null can be parsed.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidAirportWithNullSize() throws IOException {
        AirportParser parser = new AirportParser(fileArray(path + "oneValidAirportWithNull.txt"));
        ArrayList<Airport> airports = parser.makeAirports();
        assertTrue(airports.size() == 1);

    }

    /**
     * Checks that a valid airport with comma can be parsed.
     *
     * @throws IOException throws IOException error
     */
    @Test public void oneValidAirportWithCommaSize() throws IOException {
        AirportParser parser = new AirportParser(fileArray(path + "oneValidAirportWithComma.txt"));
        ArrayList<Airport> airports = parser.makeAirports();
        assertTrue(airports.size() == 1);
    }

    /**
     * Checks that all airports in the valid airport file has been parsed, checks if all 8106 airports are parsed.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void Airports8016Size() throws IOException {
        AirportParser parser = new AirportParser(fileArray(path + "8106Airports.txt"));
        ArrayList<Airport> airports = parser.makeAirports();
        assertTrue(airports.size() == 8106);
    }

    /**
     * Check that the correct contents have been parsed given a valid airport.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidAirportContents() throws IOException {
        AirportParser parser = new AirportParser(fileArray(path + "oneValidAirport.txt"));
        ArrayList<Airport> airports = parser.makeAirports();
        String airport0 = getString(airports.get(0));
        assertTrue(airport0.equals("1,Goroka,Goroka,Papua New Guinea,GKA,AYGA,-6.081689,145.391881,5282.0,10.0,U,Pacific/Port_Moresby"));
    }

    /**
     * Check that the correct contents have been parsed given a consecutive valid airport.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void twoValidAirportsContents() throws IOException {
        AirportParser parser = new AirportParser(fileArray(path + "twoValidAirports.txt"));
        ArrayList<Airport> airports = parser.makeAirports();
        String airport0 = getString(airports.get(0));
        String airport1 = getString(airports.get(1));
        assertTrue(airport0.equals("92,Maniwaki,Maniwaki,Canada,YMW,CYMW,46.272778,-75.990556,656.0,-5.0,A,America/Toronto") &&
                airport1.equals("93,Montreal Intl Mirabel,Montreal,Canada,YMX,CYMX,45.681944,-74.005278,270.0,-5.0,A,America/Toronto"));
    }

    /**
     * Check that the correct contents have been parsed given multiple valid airport.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void threeValidAirportsContents() throws IOException {
        AirportParser parser = new AirportParser(fileArray(path + "threeValidAirports.txt"));
        ArrayList<Airport> airports = parser.makeAirports();
        String airport0 = getString(airports.get(0));
        String airport1 = getString(airports.get(1));
        String airport2 = getString(airports.get(2));
        assertTrue(airport0.equals("119,Comox,Comox,Canada,YQQ,CYQQ,49.710833,-124.886667,84.0,-8.0,A,America/Vancouver") &&
                airport1.equals("177,North Bay,North Bay,Canada,YYB,CYYB,46.363611,-79.422778,1215.0,-5.0,A,America/Toronto") &&
                airport2.equals("5794,Banja Luka International Airport,Banja Luka,Bosnia and Herzegovina,BNX,LQBK,44.941444,17.297501,400.0,1.0,E,Europe/Sarajevo"));
    }

    /**
     * Check that the correct contents have been parsed given a valid airport with a null.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidAirportWithNullContents() throws IOException {
        AirportParser parser = new AirportParser(fileArray(path + "oneValidAirportWithNull.txt"));
        ArrayList<Airport> airports = parser.makeAirports();
        String airport0 = getString(airports.get(0));
        assertTrue(airport0.equals("6891,Putnam County Airport,Greencastle,United States,4I7,null,39.6335556,-86.8138056,842.0,-5.0,U,America/New_York"));
    }

    /**
     * Check that the correct contents have been parsed given a valid airport with a comma.
     *
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidAirportWithCommaContents() throws IOException {
        AirportParser parser = new AirportParser(fileArray(path + "oneValidAirportWithComma.txt"));
        ArrayList<Airport> airports = parser.makeAirports();
        String airport0 = getString(airports.get(0));
        assertTrue(airport0.equals("1,Goroka,Goroka, Memes,Papua New Guinea,GKA,AYGA,-6.081689,145.391881,5282.0,10.0,U,Pacific/Port_Moresby"));
    }
}
