package seng202.group4.GUI;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group4.App;
import seng202.group4.data.dataType.Airline;
import seng202.group4.data.repository.AirlineRepository;
import seng202.group4.data.repository.AirportRepository;
import seng202.group4.data.repository.Repository;
import seng202.group4.data.repository.RouteRepository;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Optional;

/**
 * The MenuBarController with functions for importing data
 */
public class MenuBarController {



    private Controller mainController;
    private DataTabController dataTabController;
    private FlightTabController flightTabController;

    /**
     * Sets the main controller and retrieves variables from the main controller
     *
     * @param controller Controller
     */
    public void setMainController(Controller controller) {
        this.mainController = controller;
        this.dataTabController = mainController.getDataTabController();
        this.flightTabController = mainController.getFlightTabController();
    }

    /**
     * Sets the data tab controller to retrieve functions and variables from it
     * @param dataTabController
     */
    public void setDataTabController(DataTabController dataTabController) {
        this.dataTabController = dataTabController;
    }
    /**
     * Getter for the main controller
     *
     * @return mainController
     */
    public Controller getMainController() {
        return mainController;
    }

    /**
     * Allows the user to load airline data from a file
     *
     * @throws IOException throws IOException error
     */
    public void loadAirline() throws IOException {
        dataTabController.loadAirline();
    }

    /**
     * Allows the user to load airport data from a file
     *
     * @throws IOException throws IOException error
     */
    public void loadAirport() throws IOException {
        dataTabController.loadAirport();
    }

    /**
     * Allows the user to load route data from a file
     *
     * @throws IOException throws IOException error
     */
    public void loadRoute() throws IOException {
        dataTabController.loadRoute();
    }

    /**
     * Allows the user to load a flight from a file
     *
     * @throws IOException throws IOException error
     */
    public void loadFlight() throws IOException {
        flightTabController.loadFlight();
    }


    /**
     * Clears the airline table and AirlineRepository then replaces them with the default airlines
     * @throws IOException when default airline file cannot be read
     */
    public void resetAirline() throws IOException {
        dataTabController.resetAirline();
    }

    /**
     * Clears the airport table and AirportRepository then replaces them with the default airports
     * @throws IOException when default airport file cannot be read
     */
    public void resetAirport() throws IOException {
        dataTabController.resetAirport();
    }

    /**
     * Clears the route table and routeRepository then replaces them with the default routes
     * @throws IOException when default route file cannot be read
     */
    public void resetRoute() throws IOException {
        dataTabController.resetRoute();
    }

    /**
     * Shows Aviation Information Reader's help page
     */
    public void getHelp() {
        //System.out.println("help");
        try {
            FXMLLoader fxml = new FXMLLoader();
            fxml.setLocation(getClass().getClassLoader().getResource("help.fxml"));
            Parent root = fxml.load();
            //Parent root = FXMLLoader.load(getClass().getResource("help.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setTitle("Aviation Information Reader Help");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}