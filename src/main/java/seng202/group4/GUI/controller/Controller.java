package seng202.group4.GUI.controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import seng202.group4.data.dataType.Airport;
import seng202.group4.data.dataType.Route;
import seng202.group4.data.repository.Repository;

import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private AnalysisTabController analysisTabController;

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

    @FXML
    private Tab analysisTab;


    /**
     * Initializes the controller.
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
        analysisTabController.setMainController(this);
    }

    /**
     * Getter for flight tab.
     *
     * @return Tab
     */
    public Tab getFlightTab() {
        return flightTab;
    }

    /**
     * Getting for tab pane.
     *
     * @return TabPane
     */
    public TabPane getTabPane() {
        return tabPane;
    }

    /**
     * Getter for data tab.
     *
     * @return Tab
     */
    public Tab getDataTab() {
        return dataTab;
    }

    /**
     * Getter for dataTabController.
     *
     * @return DataTabController
     */
    public DataTabController getDataTabController() {
        return dataTabController;
    }

    /**
     * Getter for routeAnchorController.
     *
     * @return RouteAnchorController
     */
    public RouteAnchorController getRouteAnchorController() {
        return routeAnchorController;
    }

    /**
     * Getter for flightTabController.
     *
     * @return FlightTabController
     */
    public FlightTabController getFlightTabController() {
        return flightTabController;
    }

    /**
     * Getter for airportAnchorController.
     *
     * @return AirportAnchorController
     */
    public AirportAnchorController getAirportAnchorController() {
        return airportAnchorController;
    }

    /**
     * Getter for airlineAnchorController.
     *
     * @return AirlineAnchorController
     */
    public AirlineAnchorController getAirlineAnchorController() {
        return airlineAnchorController;
    }

    /**
     * Getter for menuBarController.
     *
     * @return MenuBarController
     */
    public MenuBarController getMenuBarController() {
        return menuBarController;
    }

    /**
     * Getter for analysisTabController.
     *
     * @return AnalysisTabController
     */
    public AnalysisTabController getAnalysisTabController() {
        return analysisTabController;
    }

    /**
     * Getter for mapTabController.
     *
     * @return MapTabController
     */
    public MapTabController getMapTabController() {
        return mapTabController;
    }

}