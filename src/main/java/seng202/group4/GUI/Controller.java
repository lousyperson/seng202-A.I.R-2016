package seng202.group4.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.net.URL;
import java.util.ResourceBundle;

//import com.aquafx_project.AquaFx;

/**
 * The main controller implementing GUI functions.
 */
public class Controller implements Initializable {

    @FXML
    private MenuBarController menuBarController;

    @FXML
    private RouteAnchorController routeAnchorController;

    @FXML
    private DataTabController dataTabController;

    @FXML
    private FlightTabController flightTabController;

    @FXML
    private MapTabController mapTabController;

    @FXML
    private AirportAnchorController airportAnchorController;

    @FXML
    private AirlineAnchorController airlineAnchorController;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Tab mapTab;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab dataTab;

    @FXML
    private Tab flightTab;

    // labels for the user's data selection
    private String airlineLabel = "Airlines";
    private String airportLabel = "Airports";
    private String routeLabel = "Routes";


    /**
     * Initializes the controller
     *
     * @param location URL
     * @param resources ResourceBundle
     */
    public void initialize(URL location, ResourceBundle resources) {
        // Passing MainController to the other controllers
        menuBarController.setMainController(this);
        mapTabController.setMainController(this);
        dataTabController.setMainController(this);
        flightTabController.setMainController(this);
    }


    public String getAirlineLabel() {
        return airlineLabel;
    }

    public String getAirportLabel() {
        return airportLabel;
    }

    public String getRouteLabel() {
        return routeLabel;
    }

    public Tab getFlightTab() {
        return flightTab;
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    public Tab getDataTab() {
        return dataTab;
    }

    public DataTabController getDataTabController() {
        return dataTabController;
    }

    public RouteAnchorController getRouteAnchorController() {
        return routeAnchorController;
    }

    public FlightTabController getFlightTabController() {
        return flightTabController;
    }

    public AirportAnchorController getAirportAnchorController() {
        return airportAnchorController;
    }

    public AirlineAnchorController getAirlineAnchorController() {
        return airlineAnchorController;
    }

    public MenuBarController getMenuBarController() {
        return menuBarController;
    }

    public MapTabController getMapTabController() {
        return mapTabController;
    }

}