package seng202.group4.data.repository;

import seng202.group4.data.dataType.Airport;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jjg64 on 15/08/16.
 */
public class AirportRepository extends Repository{
    private HashMap<Integer, Airport> Airports = new HashMap<Integer, Airport>();

    public void addAirport(Airport airport) {
        Airports.put(airport.getID(), airport);
    }

    public HashMap<Integer, Airport> getAirports() {
        return Airports;
    }

    // finds and returns all airports in a given country
    public ArrayList<Airport> getCountry(String country) {
        ArrayList<Airport> AirportCountry = new ArrayList<Airport>();
        for (Airport airport : Airports.values()) {
            if (airport.getCountry().equals(country)) {
                AirportCountry.add(airport);
            }
        }
        return AirportCountry;
    }



}
