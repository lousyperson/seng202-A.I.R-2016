package seng202.group4.GUI;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import seng202.group4.data.dataType.*;
import seng202.group4.data.parser.validator.AirlineValidator;
import seng202.group4.data.parser.validator.AirportValidator;
import seng202.group4.data.parser.validator.FlightValidator;
import seng202.group4.data.parser.validator.RouteValidator;
import seng202.group4.data.repository.AirlineRepository;
import seng202.group4.data.repository.AirportRepository;
import seng202.group4.data.repository.Repository;
import seng202.group4.data.repository.RouteRepository;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

//import com.aquafx_project.AquaFx;

/**
 * The main controller implementing GUI functions.
 */
public class Controller implements Initializable {

    public MenuBarController getMenuBarController() {
        return menuBarController;
    }

    @FXML
    private MenuBarController menuBarController;

    public DataTabController getDataTabController() {
        return dataTabController;
    }

    @FXML
    private DataTabController dataTabController;

    public FlightTabController getFlightTabController() {
        return flightTabController;
    }

    @FXML
    private FlightTabController flightTabController;


//    public SearchPanesController getSearchPanesController() {
//        return searchPanesController;
//    }
//
//    @FXML
//    private SearchPanesController searchPanesController;

    // Map view
    @FXML
    private WebView mapView;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Tab mapTab;

    @FXML
    private Accordion accord1;

    @FXML
    private TitledPane instructions1;

    @FXML
    private TextField flightNameSearch1;





    public TabPane getTabPane() {
        return tabPane;
    }

    @FXML
    private TabPane tabPane;

    public Tab getDataTab() {
        return dataTab;
    }

    @FXML
    private Tab dataTab;

    @FXML
    private Tab flightTab;



    @FXML
    private ComboBox chooseAirport;

    @FXML
    private CheckBox allAirports;

    @FXML
    private CheckBox allRoutes;



    public String getAirlineLabel() {
        return airlineLabel;
    }

    public String getAirportLabel() {
        return airportLabel;
    }

    public String getRouteLabel() {
        return routeLabel;
    }

    private String airlineLabel = "Airlines";
    private String airportLabel = "Airports";
    private String routeLabel = "Routes";



    /**
     * Where the program starts, initializes things like listeners and starts running the GUI.
     *
     * @param location URL
     * @param resources ResourceBundle
     */
    public void initialize(URL location, ResourceBundle resources) {

        // Passing MainController to the other controllers
        menuBarController.setMainController(this);
        dataTabController.setMainController(this);
        flightTabController.setMainController(this);
        mapView.getEngine().load(getClass().getClassLoader().getResource("map.html").toExternalForm());

    }



    public void showAllAirports() {
        if (mapView.getEngine() != null) {
            HashMap<Integer, Airport> airports = Repository.airportRepository.getAirports();
            for (Map.Entry<Integer, Airport> entry : airports.entrySet()) {
                double lat = entry.getValue().getLatitude();
                double lon = entry.getValue().getLongitude();
                String name = entry.getValue().getName();
                mapView.getEngine().executeScript("addAirport(" + lat + ", " + lon + ");");
            }
            mapView.getEngine().executeScript("showAllAirports();");
        }
        if (allAirports.isSelected() == false) {
            mapView.getEngine().executeScript("hideAllAirports();");
        }
    }


    public void showAllRoutes() {
        if (mapView.getEngine() != null) {
            mapView.getEngine().executeScript("showAllRoutes();");
        }
        if (allRoutes.isSelected() == false) {
            mapView.getEngine().executeScript("hideAllRoutes();");
        }
    }




    public Tab getFlightTab() {
        return flightTab;
    }




    public void refreshMap() {
        mapView.getEngine().load(getClass().getClassLoader().getResource("map.html").toExternalForm());
    }

}