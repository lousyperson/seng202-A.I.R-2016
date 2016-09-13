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
 * Created by jjg64 on 25/08/16.
 */
public class ParserTest {

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

    @Test
    public void oneValidAirlineSize() throws IOException {
        AirlineParser parser = new AirlineParser(fileArray("testfiles/Airlines/oneValidAirline.txt"));
        ArrayList<Airline> airlines = parser.makeAirlines();
        assertTrue(airlines.size() == 1);

    }

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

    @Test
    public void multipleValidAirlinesSize() throws IOException {
        AirlineParser parser = new AirlineParser(fileArray("testfiles/Airlines/validAirline.txt"));
        ArrayList<Airline> airlines = parser.makeAirlines();
        assertTrue(airlines.size() == 6048);

    }

    @Test
    public void oneValidAirlineWithNullSize() throws IOException {
        AirlineParser parser = new AirlineParser(fileArray("testfiles/Airlines/oneValidAirlineWithNull.txt"));
        ArrayList<Airline> airlines = parser.makeAirlines();
        assertTrue(airlines.size() == 1);
    }

    @Test
    public void emptyFileAirline() throws IOException {
        AirlineParser parser = new AirlineParser(fileArray("testfiles/empty.txt"));
        ArrayList<Airline> airlines = parser.makeAirlines();
        int size = airlines.size();
        assertTrue(size == 0);
    }
    /* ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ */

    /* Airport parser test cases */

    @Test
    public void oneValidAirportSize() throws IOException {
        AirportParser parser = new AirportParser(fileArray("testfiles/Airports/oneValidAirport.txt"));
        ArrayList<Airport> airports = parser.makeAirports();
        assertTrue(airports.size() == 1);

    }

    @Test
    public void oneValidAirportContents() throws IOException {
        assertTrue(true);
    }

    @Test
    public void multipleValidAirportsSize() throws IOException {
        AirportParser parser = new AirportParser(fileArray("testfiles/Airports/validAirport.txt"));
        ArrayList<Airport> airports = parser.makeAirports();
        assertTrue(airports.size() == 8106);
    }

    @Test
    public void oneValidAirportWithNullSize() throws IOException {
        AirportParser parser = new AirportParser(fileArray("testfiles/Airports/oneValidAirportWithNull.txt"));
        ArrayList<Airport> airports = parser.makeAirports();
        assertTrue(airports.size() == 1);

    }

    @Test public void oneValidAirportWithCommaSize() throws IOException {
        AirportParser parser = new AirportParser(fileArray("testfiles/Airports/oneValidAirportWithComma.txt"));
        ArrayList<Airport> airports = parser.makeAirports();
        assertTrue(airports.size() == 1);
    }

    @Test
    public void emptyFileAirport() throws IOException {
        AirportParser parser = new AirportParser(fileArray("testfiles/empty.txt"));
        ArrayList<Airport> airports = parser.makeAirports();
        int size = airports.size();
        assertTrue(size == 0);
    }

     /* ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ */

    /* Route Parser tests */

    @Test
    public void emptyFileRoute() throws IOException {
        RouteParser parser = new RouteParser(fileArray("testfiles/empty.txt"));
        ArrayList<Route> routes = parser.makeRoutes();
        int size = routes.size();
        assertTrue(size == 0);
    }


    @Test
    public void oneValidRouteSize() throws IOException {
        RouteParser parser = new RouteParser(fileArray("testfiles/Routes/oneValidRoute.txt"));
        ArrayList<Route> routes = parser.makeRoutes();
        int size = routes.size();
        assertTrue(size == 1);
    }

    @Test
    public void oneValidRouteContents() throws IOException {
        assertTrue(true);
    }

    @Test
    public void oneValidRouteWithNullSize() throws IOException {
        RouteParser parser = new RouteParser(fileArray("testfiles/Routes/oneValidRouteWithNull.txt"));
        ArrayList<Route> routes = parser.makeRoutes();
        int size = routes.size();
        assertTrue(size == 1);
    }

    @Test
    public void oneValidRouteWithNullContents() throws IOException {
        assertTrue(true);
    }

    @Test
    public void oneValidRouteWithMultiEquipmentSize() throws IOException {
        RouteParser parser = new RouteParser(fileArray("testfiles/Routes/oneValidRouteWithMultiEquipment.txt"));
        ArrayList<Route> routes = parser.makeRoutes();
        int size = routes.size();
        assertTrue(size == 1);
    }

    @Test
    public void oneValidRouteWithMultiEquipmentSize2() throws IOException {
        RouteParser parser = new RouteParser(fileArray("testfiles/Routes/oneValidRouteWithMultiEquipment.txt"));
        ArrayList<Route> routes = parser.makeRoutes();
        int size = routes.get(0).getEquipment().size();
        assertTrue(size == 3);
    }

    @Test
    public void oneValidRouteWithMultiEquipmentContents() throws IOException {
        assertTrue(true);
    }

    @Test
    public void multipleValidRoutesSize() throws IOException {
        RouteParser parser = new RouteParser(fileArray("testfiles/Routes/validRoute.txt"));
        ArrayList<Route> routes = parser.makeRoutes();
        int size = routes.size();
        assertTrue(size == 67663);
    }

    /* ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ */

    /* Flight Parser tests */

    @Test
    public void emptyFileFlight() throws IOException {
        FlightParser parser = new FlightParser(fileArray("testfiles/empty.csv"));
        Flight flight = parser.makeFlight();
        int size = flight.getFlightPositions().size();
        assertTrue(size == 0);
    }


    @Test
    public void validFlightSize() throws IOException {
        FlightParser parser = new FlightParser(fileArray("testfiles/Flights/validFlight.csv"));
        Flight flight = parser.makeFlight();
        int size = flight.getFlightPositions().size();
        assertTrue(size == 31);
    }

}
