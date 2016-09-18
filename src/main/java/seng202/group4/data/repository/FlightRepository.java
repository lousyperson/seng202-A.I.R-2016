package seng202.group4.data.repository;

import seng202.group4.data.dataType.Flight;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Allows the for the serialization off flights.
 */
public class FlightRepository extends Repository implements Serializable {
    //private ArrayList<Flight> flights = new ArrayList<>();
    private HashMap<String, Flight> flights = new HashMap<>();

    /**
     * Adds a flight to the list of flights.
     * @param name String
     * @param flight Flight
     */
    public void addFlight(String name, Flight flight) {
        //flights.add(flight);
        flights.put(name, flight);
    }


    /**
     * Gets a list of all the flights.
     * @return flights
     */
    public HashMap<String, Flight> getFlights() {
        return flights;
    }
}
