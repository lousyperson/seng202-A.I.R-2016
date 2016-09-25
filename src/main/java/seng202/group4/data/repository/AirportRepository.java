package seng202.group4.data.repository;

import seng202.group4.data.dataType.Airport;

import java.io.Serializable;
import java.util.*;

/**
 * Stores all the Airports in exactly one HashMap and has methods that Airports will use.
 */
public class AirportRepository extends Repository implements Serializable {
    private HashMap<Integer, Airport> airports = new HashMap<>();
    private HashMap<String, HashSet> countryAirports = new HashMap<>();

    /**
     * Adds an airport to the list of airports.
     * @param airport Airport
     */
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

    /**
     * Gets a list of all the airports.
     * @return airports
     */
    public HashMap<Integer, Airport> getAirports() {
        return airports;
    }

    /**
     * Gets the list of airports in a country.
     * @return countryAirports
     */
    public HashMap<String, HashSet> getCountryAirports(){
        return countryAirports;
    }

    /**
     * Takes a country and returns all the airport identification numbers of that country.
     * @param country String
     * @return Set
     */
    // takes a country and returns all the airports IDs of that country
    public Set airportIDsOfCountry(String country){
        if(countryAirports.get(country) == null){
            return Collections.emptySet();
        }
        return countryAirports.get(country);
    }

    /**
     * Finds and returns all airports in a given country.
     * @param country String
     * @return AirportCountry
     */
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

    /**
     * Finds the country of an airport with a given IATA.
     * @param id String
     * @return airport country if it exists otherwise null
     */
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
