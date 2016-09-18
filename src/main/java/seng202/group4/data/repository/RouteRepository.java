package seng202.group4.data.repository;

import seng202.group4.data.dataType.Route;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * Allows for the serialization and searching/filtering of routes.
 */
public class RouteRepository extends Repository implements Serializable {
    private HashMap<String, Route> routes = new HashMap<String, Route>();

    public void addRoute(Route route) {
        String key = getKey(route);
        routes.put(key, route);
    }

    public HashMap<String, Route> getRoutes() {
        return routes;
    }

    public static String getKey(Route route) {
        String key = "";
        key += route.getAirline() + route.getAirlineID() + route.getSrcAirport() + route.getSrcAirportID()
                + route.getDestAirport() + route.getDestAirportID() + route.getCodeshare() + route.getStops();
        for (String item : route.getEquipment()) {
            key += item;
        }
        return key;
    }


    /**
     * Given a destination location, returns routes that leave the given location.
     * @param location String
     * @return departureLocations
     */
    //given destination location returns the routes corresponding to this location
    public ArrayList<Route> getDepartureLocation(String location) {
        ArrayList<Route> departureLocations = new ArrayList<>();
        for (Route route : routes.values()) {
            if (route.getSrcAirport().equals(location)) {
                departureLocations.add(route);
            }
        }
        return departureLocations;
    }

    /**
     * Given a location, returns the routes that arrive at the given location.
     * @param location String
     * @return destinationLocations
     */
    //given a location, gets the routes that offer this destination
    public ArrayList<Route> getDestinationLocation(String location) {
        ArrayList<Route> destinationLocations = new ArrayList<Route>();
        for (Route route : routes.values()) {
            if (route.getDestAirport().equals(location)) {
                destinationLocations.add(route);
            }
        }
        return destinationLocations;
    }

    /**
     * Finds all direct routes.
     * @return nonStopRoutes
     */
    //finds all direct routes
    public ArrayList<Route> getDirect() {
        ArrayList<Route> nonStopRoutes = new ArrayList<Route>();
        for (Route route : routes.values()) {
            if( route.getStops() == 0) {
                nonStopRoutes.add(route);
            }
        }
        return nonStopRoutes;
    }

    /**
     * Finds all indirecct routes.
     * @return stopRoutes
     */
    // finds all indirect routes
    public ArrayList<Route> getInDirect() {
        ArrayList<Route> stopRoutes = new ArrayList<Route>();
        for (Route route : routes.values()) {
            if( route.getStops() > 0) {
                stopRoutes.add(route);
            }
        }
        return stopRoutes;
    }

    /**
     * Finds and returns all routes that utilise the user specified equipment.
     * @param equipment String
     * @return equipmentRoute
     */
    // finds and returns all routes that utilise the given equipment
    public ArrayList<Route> getEquipment(String equipment) {
        ArrayList<Route> equipmentRoute = new ArrayList<Route>();
        for (Route route: routes.values()) {
            for (String equip: route.getEquipment()) {
                if (equip.equals(equipment)) {
                    equipmentRoute.add(route);
                }
            }
        }
        return equipmentRoute;
    }

}


