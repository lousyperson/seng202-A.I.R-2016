package seng202.group4.GUI.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng202.group4.App;

import java.io.IOException;

/**
 * The MenuBarController with functions for importing data.
 */
public class MenuBarController {

    private Controller mainController;
    private DataTabController dataTabController;
    private FlightTabController flightTabController;
    private AirlineAnchorController airlineAnchorController;
    private AirportAnchorController airportAnchorController;
    private RouteAnchorController routeAnchorController;

    /**
     * Sets the main controller and retrieves variables from other controllers.
     *
     * @param controller Controller
     */
    public void setMainController(Controller controller) {
        this.mainController = controller;
        this.dataTabController = mainController.getDataTabController();
        this.flightTabController = mainController.getFlightTabController();
        this.airportAnchorController = dataTabController.getAirportAnchorController();
        this.airlineAnchorController = dataTabController.getAirlineAnchorController();
        this.routeAnchorController = dataTabController.getRouteAnchorController();
    }

    /**
     * Allows the user to load airline data from a file.
     *
     * @throws IOException throws IOException error
     */
    public void loadAirline() throws IOException {
        airlineAnchorController.loadAirline();
    }

    /**
     * Allows the user to load airport data from a file.
     *
     * @throws IOException throws IOException error
     */
    public void loadAirport() throws IOException {
        airportAnchorController.loadAirport();
    }

    /**
     * Allows the user to load route data from a file.
     *
     * @throws IOException throws IOException error
     */
    public void loadRoute() throws IOException {
        routeAnchorController.loadRoute();
    }

    /**
     * Allows the user to load a flight from a file.
     *
     * @throws IOException throws IOException error
     */
    public void loadFlight() throws IOException {
        flightTabController.loadFlight();
    }


    /**
     * Clears the airline table and AirlineRepository then replaces them with the default airlines.
     * @throws IOException when default airline file cannot be read
     */
    public void resetAirline() throws IOException {
        airlineAnchorController.resetAirline();
    }

    /**
     * Clears the airport table and AirportRepository then replaces them with the default airports.
     * @throws IOException when default airport file cannot be read
     */
    public void resetAirport() throws IOException {
        airportAnchorController.resetAirport();
    }

    /**
     * Clears the route table and routeRepository then replaces them with the default routes.
     * @throws IOException when default route file cannot be read
     */
    public void resetRoute() throws IOException {
        routeAnchorController.resetRoute();
    }

    /**
     * Deletes all entries from the airline table and airlineRepository.
     */
    public void deleteAllAirlines() {
        airlineAnchorController.deleteAllAirlines();
    }

    /**
     * Deletes all entries from the airline table and airportRepository.
     */
    public void deleteAllAirports() {
        airportAnchorController.deleteAllAirports();
    }

    /**
     * Deletes all entries from the airline table and routeRepository.
     */
    public void deleteAllRoutes() {
        routeAnchorController.deleteAllRoutes();
    }

    /**
     * Deletes all entries from the airline table and flightRepository.
     */
    public void deleteAllFlights() {
        flightTabController.deleteAllFlights();
    }

    /**
     * Shows Aviation Information Reader's help page.
     */
    public void getHelp() {
        try {
            FXMLLoader fxml = new FXMLLoader();
            fxml.setLocation(getClass().getClassLoader().getResource("help.fxml"));
            Parent root = fxml.load();
            Stage stage = new Stage();
            stage.initOwner(App.primaryStage);
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setTitle("Aviation Information Reader Help");
            stage.setScene(new Scene(root, 743, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}