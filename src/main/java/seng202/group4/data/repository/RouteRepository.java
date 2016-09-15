package seng202.group4.data.repository;

import seng202.group4.data.dataType.Airport;
import seng202.group4.data.dataType.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * Created by jjg64 on 15/08/16.
 */
public class RouteRepository {
    //private HashMap<Integer, Route> Routes = new HashMap<Integer, Route>();
    private ArrayList<Route> routes = new ArrayList<>();
    //private TreeSet Routes = new TreeSet<>();
    public void addRoute(Route route) {
        routes.add(route);
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }


    //given destination location returns the routes corresponding to this location
    public ArrayList<Route> getDepartureLocation(String location) {
        ArrayList<Route> departureLocations = new ArrayList<>();
        for (Route route : routes) {
            if (route.getSrcAirport().equals(location)) {
                departureLocations.add(route);
            }
        }
        return departureLocations;
    }

    public static double findDistance(AirportRepository airports, Route route) {
        double distance = 0;
        Integer srcAirportID = route.getSrcAirportID();
        Integer dstAirportID = route.getDestAirportID();
        Airport srcAirport = airports.getAirports().get(srcAirportID);
        Airport dstAirport = airports.getAirports().get(dstAirportID);
        boolean hasSrc = airports.getAirports().containsKey(srcAirportID);
        boolean hasDst = airports.getAirports().containsKey(dstAirportID);

        if (hasSrc && hasDst) {
            distance = calcDistance(srcAirport.getLatitude(), srcAirport.getLongitude(),
                    dstAirport.getLatitude(), dstAirport.getLongitude(), "K");
        }

        else {
            if (!hasSrc && !hasDst) {
                // Error no src and not dst airport
            } else if (!hasSrc) {
                // Error no src airport
            } else {
                // Error no dst airport
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


    //given a location, gets the routes that offer this destination
    public ArrayList<Route> getDestinationLocation(String location) {
        ArrayList<Route> destinationLocations = new ArrayList<Route>();
        for (Route route : routes) {
            if (route.getDestAirport().equals(location)) {
                destinationLocations.add(route);
            }
        }
        return destinationLocations;
    }

    //finds all direct routes
    public ArrayList<Route> getDirect() {
        ArrayList<Route> nonStopRoutes = new ArrayList<Route>();
        for (Route route : routes) {
            if( route.getStops() == 0) {
                nonStopRoutes.add(route);
            }
        }
        return nonStopRoutes;
    }


    // finds all indirect routes
    public ArrayList<Route> getInDirect() {
        ArrayList<Route> stopRoutes = new ArrayList<Route>();
        for (Route route : routes) {
            if( route.getStops() > 0) {
                stopRoutes.add(route);
            }
        }
        return stopRoutes;
    }


    // finds and returns all routes that utilise the given equipment
    public ArrayList<Route> getEquipment(String equipment) {
        ArrayList<Route> equipmentRoute = new ArrayList<Route>();
        for (Route route: routes) {
            for (String equip: route.getEquipment()) {
                if (equip.equals(equipment)) {
                    equipmentRoute.add(route);
                }
            }
        }
        return equipmentRoute;
    }

}


