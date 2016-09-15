package seng202.group4.data.repository;

import seng202.group4.GUI.AlertPopup;
import seng202.group4.data.dataType.Airport;
import seng202.group4.data.dataType.Route;

import java.util.*;

/**
 * Created by jjg64 on 15/08/16.
 */
public class AirportRepository extends Repository{
    private HashMap<Integer, Airport> airports = new HashMap<>();
    private HashMap<String, HashSet> countryAirports = new HashMap<>();


    public void addAirport(Airport airport) {
        if (!airports.containsKey(airport.getID())) {
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

        } else {
            // add error here
            System.out.println("Error");
        }

    }

    public HashMap<Integer, Airport> getAirports() {
        return airports;
    }

    public HashMap<String, HashSet> getCountryAirports(){
        return countryAirports;
    }

    public static double findDistance(AirportRepository airports, Airport srcAirport, Airport dstAirport) {
        double distance = 0;
        Integer srcAirportID = srcAirport.getID();
        Integer dstAirportID = dstAirport.getID();
        boolean hasSrc = airports.getAirports().containsKey(srcAirportID);
        boolean hasDst = airports.getAirports().containsKey(dstAirportID);

        if (hasSrc && hasDst) {
            distance = calcDistance(srcAirport.getLatitude(), srcAirport.getLongitude(),
                    dstAirport.getLatitude(), dstAirport.getLongitude(), "K");
        }

        else {
            if (!hasSrc && !hasDst) {
                AlertPopup.makeError("Airports not found", "No airport ID was found for either airport\nPlease upload airports with the missing airport IDs");
            } else if (!hasSrc) {
                AlertPopup.makeError("Airport not found", "No airport ID was found for source airport\nPlease upload airports with the missing airport ID");
            } else {
                AlertPopup.makeError("Airport not found", "No airport ID was found for destination airport\nPlease upload airports with the missing airport ID");
            }
        }
        return distance;
    }

    private static double calcDistance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        // Kilometers
        if (unit == "K") {
            dist = dist * 1.609344;
            // Miles
        } else if (unit == "M") {
            dist = dist * 0.8684;
        }

        return (dist);
    }

    /**
     *	This function converts decimal degrees to radians
     */
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /**
     * This function converts radians to decimal degrees
     */
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
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

}
