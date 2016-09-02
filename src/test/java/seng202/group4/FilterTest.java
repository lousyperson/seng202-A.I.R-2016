//package seng202.group4;
//
//
//import junit.framework.TestCase;
//import org.junit.Test;
//import seng202.group4.data.dataType.Airline;
//import seng202.group4.data.dataType.Airport;
//import seng202.group4.data.dataType.Route;
//import seng202.group4.data.repository.AirlineRepository;
//import seng202.group4.data.repository.AirportRepository;
//import seng202.group4.data.repository.RouteRepository;
//
//import java.util.ArrayList;
//
///**
// * Created by psu43 on 24/08/16.
// */
//public class FilterTest extends TestCase {
//
//    @Test
//    public void filterCountryFromAirport() {
//        AirportRepository airports = new AirportRepository();
//        ArrayList<Airport> nzAirports = airports.getCountry("New Zealand");
//
//        // tests that nzAirports contains New Zealand Airports
//        for(Airport airport: nzAirports) {
//            assertEquals("New Zealand", airport.getCountry());
//        }
//    }
//
//    @Test
//    public void filterCountryFromAirline() {
//        AirlineRepository airlines = new AirlineRepository();
//        ArrayList<Airline> nzAirlines = airlines.getCountry("New Zealand");
//
//        // tests that nzAirlines contains New Zealand Airlines
//        for(Airline airline: nzAirlines) {
//            assertEquals("New Zealand", airline.getCountry());
//        }
//    }
//
//    @Test
//    public void filterActiveFromAirline() {
//        AirlineRepository airlines = new AirlineRepository();
//        ArrayList<Airline> activeAirlines=  airlines.getActive();
//
//        // tests that all the airlines in activeAirlines are active
//        for(Airline airline: activeAirlines) {
//            assertEquals(true, airline.getActive());
//        }
//    }
//
//    @Test
//    public void filterInActiveFromAirline() {
//        AirlineRepository airlines = new AirlineRepository();
//        ArrayList<Airline> inactiveAirlines=  airlines.getInActive();
//
//        // tests that all the airlines in activeAirlines are inactive
//        for(Airline airline: inactiveAirlines) {
//            assertEquals(false, airline.getActive());
//        }
//    }
//
//    @Test
//    public void filterDepartureLocation() {
//        RouteRepository routes = new RouteRepository();
//        ArrayList<Route> stuttgartRoutes = routes.getDepartureLocation("STR");
//
//        // test that all the stuttgartRoutes depart from STR
//        for (Route route : stuttgartRoutes) {
//            assertEquals("STR", route.getSrcAirport());
//        }
//    }
//
//    @Test
//    public void filterDestinationLocation() {
//        RouteRepository routes = new RouteRepository();
//        ArrayList<Route> stockholmRoutes = routes.getDestinationLocation("ARN");
//
//        // test that all the stockholmRoutes arrive at ARN
//        for (Route route : stockholmRoutes) {
//            assertEquals("ARN", route.getDestAirport());
//        }
//
//    }
//
//
//
//    @Test
//    public void filterDirectFlight() {
//        RouteRepository routes = new RouteRepository();
//        ArrayList<Route> directFlights = routes.getDirect();
//
//        // test that all the directFlights have no stops
//        for (Route route : directFlights) {
//            assertEquals(0, route.getStops());
//        }
//
//    }
//
//    @Test
//    public void filterIndirectFlight() {
//        RouteRepository routes = new RouteRepository();
//        ArrayList<Route> indirectFlights = routes.getInDirect();
//
//        // test that all the indirectFlights have no stops
//        for (Route route : indirectFlights) {
//            assertTrue(route.getStops() > 0);
//        }
//    }
//
//    @Test
//    public void filterEquipment() {
//        RouteRepository routes = new RouteRepository();
//        ArrayList<Route> filteredEquipments = routes.getEquipment("777");
//
//        // test that all the filteredEquipments are 777
//        for (Route route : filteredEquipments) {
//            assertTrue(route.getEquipment().contains("777"));
//        }
//    }
//}
