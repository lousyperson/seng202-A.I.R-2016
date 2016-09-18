package seng202.group4.data.repository;

import seng202.group4.data.dataType.Airline;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;

/**
 * Connects all of the data type repositories together allowing for simplified serialization.
 */
public class Repository implements Serializable {
    public static AirlineRepository airlineRepository;
    public static AirportRepository airportRepository;
    public static RouteRepository routeRepository;
    public static FlightRepository flightRepository;

    /**
     * Initializes the repository variables.
     */
    public static void initRepository() {
        deserialize();
        if (airlineRepository == null) {
            airlineRepository = new AirlineRepository();
        }
        if (airportRepository == null) {
            airportRepository = new AirportRepository();
        }
        if (routeRepository == null) {
            routeRepository = new RouteRepository();
        }
        if (flightRepository == null) {
            flightRepository = new FlightRepository();
        }
    }

    /**
     * Serializes the objects.
     */
    public static void serialize() {
        serializeObject(airlineRepository, "airline");
        serializeObject(airportRepository, "airport");
        serializeObject(routeRepository, "route");
        serializeObject(flightRepository, "flight");
    }

    protected static void serializeObject(Repository repository, String type) {
        try {
            FileOutputStream fileOut = new FileOutputStream(type + "s.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(repository);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in " + type + "s.ser");
        }catch(IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Deserializes the objects.
     */
    public static void deserialize() {
        checkSerFile(airlineRepository, "airline");
        checkSerFile(airportRepository, "airport");
        checkSerFile(routeRepository, "route");
        checkSerFile(flightRepository, "flight");
        airlineRepository = (AirlineRepository) deserializeObject("airline");
        airportRepository = (AirportRepository) deserializeObject("airport");
        routeRepository = (RouteRepository) deserializeObject("route");
        flightRepository = (FlightRepository) deserializeObject("flight");
    }

    private static void checkSerFile(Repository repository, String type) {
        if (!new File(type + "s.ser").exists()) {
            serializeObject(repository, type);
        }
    }

    protected static Object deserializeObject(String type) {
        try {
            Object repository;
            FileInputStream fileIn = new FileInputStream(type + "s.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            repository = in.readObject();
            in.close();
            fileIn.close();
            return repository;
        }catch(IOException i) {
            i.printStackTrace();
            return null;
        }catch(ClassNotFoundException c) {
            System.out.println(type + " class not found");
            c.printStackTrace();
            return null;
        }
    }
}
