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
     *
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
     *
     * @return airports
     */
    public HashMap<Integer, Airport> getAirports() {
        return airports;
    }

    /**
     * Gets the list of airports in a country.
     *
     * @return countryAirports
     */
    public HashMap<String, HashSet> getCountryAirports(){
        return countryAirports;
    }

    /**
     * Takes a country and returns all the airport identification numbers of that country.
     *
     * @param country String
     * @return Set
     */
    public Set airportIDsOfCountry(String country){
        if(countryAirports.get(country) == null){
            return Collections.emptySet();
        }
        return countryAirports.get(country);
    }

    /**
     * Finds and returns all airports in a given country.
     *
     * @param country String
     * @return AirportCountry
     */
    public ArrayList<Airport> getAirportsFromCountry(String country) {
        ArrayList<Airport> AirportCountry = new ArrayList<Airport>();
        for (Airport airport : airports.values()) {
            if (airport.getCountry().equals(country)) {
                AirportCountry.add(airport);
            }
        }
        return AirportCountry;
    }

    /**
     * Finds the country of an airport with a given airport ID.
     *
     * @param id Integer
     * @return airport country if it exists otherwise null
     */
    public String findCountry(Integer id) {
        for (Airport airport : airports.values()) {
            if (airport.getID() == id) {
                return airport.getCountry();
            }
        }
        return null;
    }

    /**
     * Calculates the distance given two points (with latitude and longitudes).
     *
     * @param pointALat Double: Point A latitude
     * @param pointALon Double: Point A longitude
     * @param pointBLat Double: Point B latitude
     * @param pointBLon Double: Point B longitude
     * @return String
     */
    public static String calculateDistance(Double pointALat, Double pointALon, Double pointBLat, Double pointBLon) {
        final int R = 6371; // Radius of the earth in km

        Double latDistance = Math.toRadians(pointBLat - pointALat);
        Double lonDistance = Math.toRadians(pointBLon - pointALon);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(pointALat)) * Math.cos(Math.toRadians(pointBLat))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = R * c; // convert to meters
        return String.format("%.2f", distance);
    }

    /**
     * Finds an airport IATA with a given airport ID.
     *
     * @param id Integer
     * @return airport IATA if it exists otherwise null
     */
    public String findAirportIATA(Integer id) {
        for (Airport airport : airports.values()) {
            if (airport.getID() == id) {
                return airport.getIATA();
            }
        }
        return null;
    }

    /**
     * Returns airport given airport name in string.
     *
     * @param airportName String
     * @return airport if it exists otherwise null
     */
    public Airport getAirport(String airportName) {
        for (Airport airport : airports.values()) {
            if (airport.getName().toLowerCase().equals(airportName)) {
                return airport;
            }
        }
        return null;
    }
}
