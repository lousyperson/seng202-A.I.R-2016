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
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Ensures that the validator GUI responses are working as expected.
 * Created by jjg64 on 28/08/16.
 */
public class ValidatorGUITest {

    /**
     * Tests an invalid airline file to ensure the GUI responds as anticipated, by producing a pop-up dialog.
     *
     * @param file the filename
     * @throws IOException when file cannot be read
     */
    public static void testInvalidAirlineFile(String file) throws IOException {
        AirlineValidator parser = new AirlineValidator(new FileInputStream(file));
        ArrayList<Airline> airlines = parser.makeAirlines();
        assert(airlines == null);
    }

    /**
     * Tests an invalid route file to ensure the GUI acts as anticipated, by producing a pop-up dialog.
     *
     * @param file the name of the file
     * @throws IOException when file cannot be read
     */
    public static void testInvalidRouteFile(String file) throws IOException {
        RouteValidator parser = new RouteValidator(new FileInputStream(file));
        ArrayList<Route> routes = parser.makeroutes();
        assert(routes == null);
    }

    /**
     * Tests a valid airport file to see that the GUI responds as anticipated, by happily accepting the file.
     *
     * @param file the filename
     * @throws IOException when file cannot be read
     */
    public static void testValidAirportFile(String file) throws IOException {
        AirportValidator parser = new AirportValidator(new FileInputStream(file));
        ArrayList<Airport> airports = parser.makeAirports();
        assert(airports.size() == 8106);
    }

    /**
     * Tests an invalid aiport file to see that the GUI responds appropriately, by producing a pop-up dialog.
     *
     * @param file Filepath in string format
     * @throws IOException when file cannot be read
     */
    public static void testInvalidAirportFile(String file) throws IOException {
        AirportValidator parser = new AirportValidator(new FileInputStream(file));
        ArrayList<Airport> airports = parser.makeAirports();
        assert(airports.size() == 0);
    }
}
