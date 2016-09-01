package seng202.group4.data.repository;

import seng202.group4.data.dataType.Route;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jjg64 on 15/08/16.
 */
public class RouteRepository {
    private HashMap<Integer, Route> Routes = new HashMap<Integer, Route>();


    public void addRoute(Route route) {
        Routes.put(route.getDestAirportID(), route);
    }

    public HashMap<Integer, Route> getRoutes() {
        return Routes;
    }


    //given destination location returns the routes corresponding to this location
    public ArrayList<Route> getDepartureLocation(String location) {
        ArrayList<Route> departureLocations = new ArrayList<Route>();
        for (Route route : Routes.values()) {
            if (route.getSrcAirport().equals(location)) {
                departureLocations.add(route);
            }
        }
        return departureLocations;
    }


    //given a location, gets the routes that offer this destination
    public ArrayList<Route> getDestinationLocation(String location) {
        ArrayList<Route> destinationLocations = new ArrayList<Route>();
        for (Route route : Routes.values()) {
            if (route.getDestAirport().equals(location)) {
                destinationLocations.add(route);
            }
        }
        return destinationLocations;
    }

    //finds all direct routes
    public ArrayList<Route> getDirect() {
        ArrayList<Route> nonStopRoutes = new ArrayList<Route>();
        for (Route route : Routes.values()) {
            if( route.getStops() == 0) {
                nonStopRoutes.add(route);
            }
        }
        return nonStopRoutes;
    }


    // finds all indirect routes
    public ArrayList<Route> getInDirect() {
        ArrayList<Route> stopRoutes = new ArrayList<Route>();
        for (Route route : Routes.values()) {
            if( route.getStops() > 0) {
                stopRoutes.add(route);
            }
        }
        return stopRoutes;
    }


    // finds and returns all routes that utilise the given equipment
    public ArrayList<Route> getEquipment(String equipment) {
        ArrayList<Route> equipmentRoute = new ArrayList<Route>();
        for (Route route: Routes.values()) {
            for (String equip: route.getEquipment()) {
                if (equip.equals(equipment)) {
                    equipmentRoute.add(route);
                }
            }
        }
        return equipmentRoute;
    }

}


