package seng202.group4;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.group4.data.dataType.Airline;
import seng202.group4.data.dataType.Airport;
import seng202.group4.data.dataType.Route;
import seng202.group4.data.parser.AirlineParser;
import seng202.group4.data.parser.validator.AirlineValidator;
import seng202.group4.data.repository.AirlineRepository;
import seng202.group4.data.repository.AirportRepository;
import seng202.group4.data.repository.RouteRepository;

import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * ValidatorTest is a JUnit test class used to test the data file validators.
 * Created by jjg64 on 25/08/16.
 */
public class ValidatorTest {

    /* Airline validator test cases */

    /* Valid files */

    /**
     * Checks if one valid airline is parsed in, one valid airline is read.
     *
     *  @throws IOException throws an IOException error
     */
    @Test
    public void oneValidAirlineSize() throws IOException {
        AirlineValidator parser = new AirlineValidator(new FileInputStream("testfiles/Airlines/oneValidAirline.txt"));
        ArrayList<Airline> airlines = parser.makeAirlines();
        assertTrue(airlines.size() == 1);

    }

    /**
     * Checks if multiple valid airlines are entered, the correct number of airlines is read.
     *
     * @throws IOException throws an IOException error
     */
    @Test
    public void multipleValidAirlinesSize() throws IOException {
        AirlineValidator parser = new AirlineValidator(new FileInputStream("testfiles/Airlines/6048Airlines.txt"));
        ArrayList<Airline> airlines = parser.makeAirlines();
        assertTrue(airlines.size() == 6048);

    }

    /**
     * Checks that if one airline is entered with a null value, that the airline data is still parsed through as a valid
     * airline.
     *
     * @throws IOException throws an IOException error
     */
    @Test
    public void oneValidAirlineWithNullSize() throws IOException {
        AirlineValidator parser = new AirlineValidator(new FileInputStream("testfiles/Airlines/oneValidAirlineWithNull.txt"));
        ArrayList<Airline> airlines = parser.makeAirlines();
        assertTrue(airlines.size() == 1);
    }

    /**
     * Checks to see if the validator handles an empty file correctly.
     *
     * @throws IOException throws an IOException error
     */
    @Test
    public void emptyFile() throws IOException {
        AirlineValidator parser = new AirlineValidator(new FileInputStream("testfiles/empty.txt"));
        ArrayList<Airline> airlines = parser.makeAirlines();
        int size = airlines.size();
        assertTrue(size == 0);
    }

    /* Invalid files */
    /* Invalid files have been moved to a GUI test as they use an alert popup */

    // Moved to ValidatorGUITest

//    @Test
//    public void missingQuotation() throws IOException {
//
//        AirlineValidator parser = new AirlineValidator(new File("testfiles/Airlines/oneDodgyAirline.txt"));
//        ArrayList<Airline> airlines = parser.makeAirlines();
//        assertTrue(airlines == null);
//    }
}
