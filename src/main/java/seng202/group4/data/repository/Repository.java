package seng202.group4.data.repository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by jjg64 on 15/08/16.
 */
public class Repository implements Serializable {
    public static AirlineRepository airlineRepository;
    public static AirportRepository airportRepository;
    public static RouteRepository routeRepository;
    public static FlightRepository flightRepository;

    public static void initRepository() {
        airlineRepository = new AirlineRepository();
        airportRepository = new AirportRepository();
        routeRepository = new RouteRepository();
        flightRepository = new FlightRepository();
    }

    public static void serialize() {
//        serializeObject(airlineRepository, "airline");
//        serializeObject(airportRepository, "airport");
//        serializeObject(routeRepository, "route");
//        serializeObject(flightRepository, "flight");
    }

    protected static void serializeObject(Object object, String type) {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("/tmp/employee.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(object);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /tmp/employee.ser");
        }catch(IOException i) {
            i.printStackTrace();
        }
    }
}
