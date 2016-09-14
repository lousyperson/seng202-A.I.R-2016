package seng202.group4.data.repository;

import seng202.group4.data.dataType.Flight;

import java.util.ArrayList;

/**
 * Created by jjg64 on 15/08/16.
 */
public class FlightRepository extends Repository{
    private ArrayList<Flight> flights = new ArrayList<>();


    public void addFlight(Flight flight) {
        flights.add(flight);
    }


    public ArrayList<Flight> getFlights() {
        return flights;
    }
}
