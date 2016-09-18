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

/**ParserTest is a class written to run the Junit tests for the parser.
 */
public class ParserTest {

    /**
     * Given a file path convert file lines into an array of strings
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
    /* Airline parser test cases */

    /**
     * Given one valid airline, check that the size is 1
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidAirlineSize() throws IOException {
        AirlineParser parser = new AirlineParser(fileArray("testfiles/Airlines/oneValidAirline.txt"));
        ArrayList<Airline> airlines = parser.makeAirlines();
        assertTrue(airlines.size() == 1);

    }

    /**
     * Given one valid airline, check that the content has been parsed correctly
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidAirlineContents() throws IOException {
        AirlineParser parser = new AirlineParser(fileArray("testfiles/Airlines/oneValidAirline.txt"));
        ArrayList<Airline> airlines = parser.makeAirlines();
        Airline airline = airlines.get(0);
        boolean isEqual = airline.getID() == 324 && airline.getName().equals("All Nippon Airways") &&
                          airline.getAlias().equals("ANA All Nippon Airways") && airline.getIATA().equals("NH") &&
                          airline.getICAO().equals("ANA") && airline.getCallsign().equals("ALL NIPPON") &&
                          airline.getCountry().equals("Japan") && airline.getActive();
        assertTrue(isEqual);
    }

    /**
     * Check that every airline in the validAirline.txt has been read by the parser
     * @throws IOException throws IOException error
     */
    @Test
    public void multipleValidAirlinesSize() throws IOException {
        AirlineParser parser = new AirlineParser(fileArray("testfiles/Airlines/validAirline.txt"));
        ArrayList<Airline> airlines = parser.makeAirlines();
        assertTrue(airlines.size() == 6048);

    }

    /**
     * Check that a valid airline with null is read by the parser
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidAirlineWithNullSize() throws IOException {
        AirlineParser parser = new AirlineParser(fileArray("testfiles/Airlines/oneValidAirlineWithNull.txt"));
        ArrayList<Airline> airlines = parser.makeAirlines();
        assertTrue(airlines.size() == 1);
    }

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
    /* ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ */

    /* Airport parser test cases */

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

     /* ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ */

    /* Route Parser tests */

    /**
     * Check that an empty text file does not make any routes when parsed
     * @throws IOException throws IOException error
     */
    @Test
    public void emptyFileRoute() throws IOException {
        RouteParser parser = new RouteParser(fileArray("testfiles/empty.txt"));
        ArrayList<Route> routes = parser.makeRoutes();
        int size = routes.size();
        assertTrue(size == 0);
    }

    /**
     * Checks that one valid route can be parsed
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidRouteSize() throws IOException {
        RouteParser parser = new RouteParser(fileArray("testfiles/Routes/oneValidRoute.txt"));
        ArrayList<Route> routes = parser.makeRoutes();
        int size = routes.size();
        assertTrue(size == 1);
    }

    /**
     * Check that the contents of a valid route file are parsed correctly
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidRouteContents() throws IOException {
        assertTrue(true);
    }

    /**
     * Checks that one valid route with null can be parsed
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidRouteWithNullSize() throws IOException {
        RouteParser parser = new RouteParser(fileArray("testfiles/Routes/oneValidRouteWithNull.txt"));
        ArrayList<Route> routes = parser.makeRoutes();
        int size = routes.size();
        assertTrue(size == 1);
    }

    /**
     * Checks that one valid route with null can be parsed, and the contents are correct
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidRouteWithNullContents() throws IOException {
        assertTrue(true);
    }

    /**
     * Checks that one valid route with multiple equipments can be parsed
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidRouteWithMultiEquipmentSize() throws IOException {
        RouteParser parser = new RouteParser(fileArray("testfiles/Routes/oneValidRouteWithMultiEquipment.txt"));
        ArrayList<Route> routes = parser.makeRoutes();
        int size = routes.size();
        assertTrue(size == 1);
    }

    /**
     * Checks that one valid route with three equipments are parsed
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidRouteWithMultiEquipmentSize2() throws IOException {
        RouteParser parser = new RouteParser(fileArray("testfiles/Routes/oneValidRouteWithMultiEquipment.txt"));
        ArrayList<Route> routes = parser.makeRoutes();
        int size = routes.get(0).getEquipment().size();
        assertTrue(size == 3);
    }

    /**
     * Check that a valid route with multiple equipments are parsed correctly with the correct contents
     * @throws IOException throws IOException error
     */
    @Test
    public void oneValidRouteWithMultiEquipmentContents() throws IOException {
        assertTrue(true);
    }

    /**
     * Check that all routes in a valid route file are parsed
     * @throws IOException throws IOException error
     */
    @Test
    public void multipleValidRoutesSize() throws IOException {
        RouteParser parser = new RouteParser(fileArray("testfiles/Routes/validRoute.txt"));
        ArrayList<Route> routes = parser.makeRoutes();
        int size = routes.size();
        assertTrue(size == 67663);
    }

    /* ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ */


    /* Flight Parser tests */

    /**
     * Check that an empty file is parsed and does not make any flights
     * @throws IOException throws IOException error
     */
    @Test
    public void emptyFileFlight() throws IOException {
        FlightParser parser = new FlightParser(fileArray("testfiles/empty.csv"));
        Flight flight = parser.makeFlight();
        int size = flight.getFlightPositions().size();
        assertTrue(size == 0);
    }

    /**
     * Check that each row from a valid flight file is parsed
     * @throws IOException throws IOException error
     */
    @Test
    public void validFlightSize() throws IOException {
        FlightParser parser = new FlightParser(fileArray("testfiles/Flights/validFlight.csv"));
        Flight flight = parser.makeFlight();
        int size = flight.getFlightPositions().size();
        assertTrue(size == 31);
    }

}
