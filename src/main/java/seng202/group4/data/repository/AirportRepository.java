package seng202.group4.data.repository;

import seng202.group4.data.dataType.Airport;

import java.io.Serializable;
import java.util.*;

/**
 * Created by jjg64 on 15/08/16.
 */
public class AirportRepository implements Serializable {
    private HashMap<Integer, Airport> airports = new HashMap<>();
    private HashMap<String, HashSet> countryAirports = new HashMap<>();


    public void addAirport(Airport airport) {
        airports.put(airport.getID(), airport);

        HashSet ids = countryAirports.get(airport.getCountry().toLowerCase());
        // add airport to the countryAirports to keep track of airports in each country
        if(ids == null){
            ids = new HashSet();
            ids.add(airport.getID());
        }
        else{
            ids.add(airport.getID());
        }
        countryAirports.put(airport.getCountry().toLowerCase(), ids);

    }

    public HashMap<Integer, Airport> getAirports() {
        return airports;
    }

    public HashMap<String, HashSet> getCountryAirports(){
        return countryAirports;
    }

    // takes a country and returns all the airports IDs of that country
    public Set airportIDsOfCountry(String country){
        if(countryAirports.get(country) == null){
            return Collections.emptySet();
        }
        return countryAirports.get(country);
    }

    // finds and returns all airports in a given country
    public ArrayList<Airport> getCountry(String country) {
        ArrayList<Airport> AirportCountry = new ArrayList<Airport>();
        for (Airport airport : airports.values()) {
            if (airport.getCountry().equals(country)) {
                AirportCountry.add(airport);
            }
        }
        return AirportCountry;
    }

    // finds the country of an airport given IATA
    public String findCountry(String id) {
        for (Airport airport : airports.values()) {
            if (airport.getIATA().equals(id)) {
                return airport.getCountry();
            }
        }
        return null;
    }
}
