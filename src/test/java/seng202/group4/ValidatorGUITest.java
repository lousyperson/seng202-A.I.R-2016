package seng202.group4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import seng202.group4.data.dataType.Airline;
import seng202.group4.data.dataType.Airport;
import seng202.group4.data.dataType.Route;
import seng202.group4.data.parser.validator.AirlineValidator;
import seng202.group4.data.parser.validator.AirportValidator;
import seng202.group4.data.parser.validator.RouteValidator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jjg64 on 28/08/16.
 */
public class ValidatorGUITest {

    public static void testInvalidAirlineFile(String file) throws IOException {
        AirlineValidator parser = new AirlineValidator(new File(file));
        ArrayList<Airline> airlines = parser.makeAirlines();
        assert(airlines == null);
    }

    public static void testInvalidRouteFile(String file) throws IOException {
        RouteValidator parser = new RouteValidator(new File(file));
        ArrayList<Route> routes = parser.makeroutes();
        assert(routes == null);
    }

    public static void testValidAirportFile(String file) throws IOException {
        AirportValidator parser = new AirportValidator(new File(file));
        ArrayList<Airport> airports = parser.makeAirports();
        assert(airports.size() == 8106);
    }

    public static void testInvalidAirportFile(String file) throws IOException {
        AirportValidator parser = new AirportValidator(new File(file));
        ArrayList<Airport> airports = parser.makeAirports();
        assert(airports.size() == 0);
    }
}
