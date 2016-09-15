package seng202.group4.data.repository;

import seng202.group4.data.dataType.Flight;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jjg64 on 15/08/16.
 */
public class FlightRepository extends Repository{
    //private ArrayList<Flight> flights = new ArrayList<>();
    private HashMap<String, Flight> flights = new HashMap<>();

    public void addFlight(String name, Flight flight) {
        //flights.add(flight);
        flights.put(name, flight);
    }


    public HashMap<String, Flight> getFlights() {
        return flights;
    }
}
