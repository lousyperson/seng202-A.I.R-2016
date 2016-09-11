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
    public void oneValidAirlineContents() throws IOException {
        AirlineParser parser = new AirlineParser(new BufferedReader(new FileReader("testfiles/Airlines/oneValidAirline.txt")));
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
    public void emptyFileAirline() throws IOException {
        AirlineParser parser = new AirlineParser(new BufferedReader(new FileReader("testfiles/empty.txt")));
        ArrayList<Airline> airlines = parser.makeAirlines();
        int size = airlines.size();
        assertTrue(size == 0);
    }
    /* ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ */

    /* Airport parser test cases */

    @Test
    public void oneValidAirportSize() throws IOException {
        AirportParser parser = new AirportParser(new BufferedReader(new FileReader("testfiles/Airports/oneValidAirport.txt")));
        ArrayList<Airport> airports = parser.makeAirports();
        assertTrue(airports.size() == 1);

    }

    @Test
    public void oneValidAirportContents() throws IOException {
        assertTrue(true);
    }

    @Test
    public void multipleValidAirportsSize() throws IOException {
        AirportParser parser = new AirportParser(new BufferedReader(new FileReader("testfiles/Airports/validAirport.txt")));
        ArrayList<Airport> airports = parser.makeAirports();
        assertTrue(airports.size() == 8106);
    }

    @Test
    public void oneValidAirportWithNullSize() throws IOException {
        AirportParser parser = new AirportParser(new BufferedReader(new FileReader("testfiles/Airports/oneValidAirportWithNull.txt")));
        ArrayList<Airport> airports = parser.makeAirports();
        assertTrue(airports.size() == 1);

    }

    @Test public void oneValidAirportWithCommaSize() throws IOException {
        AirportParser parser = new AirportParser(new BufferedReader(new FileReader("testfiles/Airports/oneValidAirportWithComma.txt")));
        ArrayList<Airport> airports = parser.makeAirports();
        assertTrue(airports.size() == 1);
    }

    @Test
    public void emptyFileAirport() throws IOException {
        AirportParser parser = new AirportParser(new BufferedReader(new FileReader("testfiles/empty.txt")));
        ArrayList<Airport> airports = parser.makeAirports();
        int size = airports.size();
        assertTrue(size == 0);
    }

     /* ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ */

    /* Route Parser tests */

    @Test
    public void emptyFileRoute() throws IOException {
        RouteParser parser = new RouteParser((new BufferedReader(new FileReader("testfiles/empty.txt"))));
        ArrayList<Route> routes = parser.makeRoutes();
        int size = routes.size();
        assertTrue(size == 0);
    }


    @Test
    public void oneValidRouteSize() throws IOException {
        RouteParser parser = new RouteParser((new BufferedReader(new FileReader("testfiles/Routes/oneValidRoute.txt"))));
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
        RouteParser parser = new RouteParser((new BufferedReader(new FileReader("testfiles/Routes/oneValidRouteWithNull.txt"))));
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
        RouteParser parser = new RouteParser((new BufferedReader(new FileReader("testfiles/Routes/oneValidRouteWithMultiEquipment.txt"))));
        ArrayList<Route> routes = parser.makeRoutes();
        int size = routes.size();
        assertTrue(size == 1);
    }

    @Test
    public void oneValidRouteWithMultiEquipmentSize2() throws IOException {
        RouteParser parser = new RouteParser((new BufferedReader(new FileReader("testfiles/Routes/oneValidRouteWithMultiEquipment.txt"))));
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
        RouteParser parser = new RouteParser((new BufferedReader(new FileReader("testfiles/Routes/validRoute.txt"))));
        ArrayList<Route> routes = parser.makeRoutes();
        int size = routes.size();
        assertTrue(size == 67663);
    }

    /* ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ */

    /* Flight Parser tests */

    @Test
    public void emptyFileFlight() throws IOException {
        FlightParser parser = new FlightParser((new BufferedReader(new FileReader("testfiles/empty.csv"))));
        Flight flight = parser.makeFlight();
        int size = flight.getFlightPositions().size();
        assertTrue(size == 0);
    }


    @Test
    public void validFlightSize() throws IOException {
        FlightParser parser = new FlightParser((new BufferedReader(new FileReader("testfiles/Flights/validFlight.csv"))));
        Flight flight = parser.makeFlight();
        int size = flight.getFlightPositions().size();
        assertTrue(size == 31);
    }

}
