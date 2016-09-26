package seng202.group4.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.web.WebView;
import seng202.group4.data.dataType.Airport;
import seng202.group4.data.dataType.Route;
import seng202.group4.data.repository.Repository;

import java.net.URL;
import java.util.*;

/**
 * Created by psu43 on 25/09/16.
 */
public class MapTabController implements Initializable{

    @FXML
    private WebView mapView;

    @FXML
    private ComboBox<String> chooseAirportFilter;

    @FXML
    private CheckBox allAirports;

    @FXML
    private CheckBox allRoutes;

    // main controller
    private Controller mainController;

    // airportCountrySet holds all the airport countries imported
    private TreeSet<String> airportCountrySet = new TreeSet();

    /**
     * Sets the main controller and retrieves private variables from the main controller
     *
     * @param controller Controller
     */
    public void setMainController(Controller controller) {
        this.mainController = controller;
    }

    /**
     * Where the program starts, initializes things like listeners and starts running the GUI.
     *
     * @param location URL
     * @param resources ResourceBundle
     */
    public void initialize(URL location, ResourceBundle resources) {
        mapView.getEngine().load(getClass().getClassLoader().getResource("map.html").toExternalForm());

        HashMap<Integer, Airport> airports = Repository.airportRepository.getAirports();
        if (airports != null) {
            for (Airport airport : airports.values()) {
                if (airport != null && airport.getCountry() != null) {
                    airportCountrySet.add(airport.getCountry());
                }
            }
        }

        updateChooseAirportFilter();
    }

    private void updateChooseAirportFilter() {
        // clear the current combo box
        chooseAirportFilter.getItems().clear();

        // if the combo box doesn't have --CHOOSE AIRPORT-- then add one
        if (!chooseAirportFilter.getItems().contains("--CHOOSE AIRPORT--")) {
            chooseAirportFilter.getItems().add("--CHOOSE AIRPORT--");
        }
        // add countries from TreeSet as combobox options
        Iterator<String> itr = airportCountrySet.iterator();
        while (itr.hasNext()) {
            chooseAirportFilter.getItems().add(itr.next());
        }
    }

    private String selectedCountry() {
        String selectedAirportCountry = null;
        if (chooseAirportFilter.getValue() != null) {
            selectedAirportCountry = chooseAirportFilter.getValue();
        }
        return selectedAirportCountry;
    }

    /**
     * Show all country routes in the map view
     */
    public void showCountryRoutes() {
        refreshMap();
        String country = selectedCountry();
        ArrayList<Airport> airports = Repository.airportRepository.getAirportsFromCountry(country);
        for (Airport airport : airports) {
            double srcLat = airport.getLatitude();
            double srcLon = airport.getLongitude();
            HashMap<String, Route> routes = Repository.routeRepository.getRoutes();
            for (Route route : routes.values()) {
                if (route != null) {
                    if (route.getDestAirportID() != null && route.getSrcAirportID() != null && route.getSrcAirportID() == airport.getID()) {
                        int destID = route.getDestAirportID();
                        Airport destPort = Repository.airportRepository.getAirports().get(destID);
                        if (destPort != null) {
                            double destLat = destPort.getLatitude();
                            double destLon = destPort.getLongitude();
                            mapView.getEngine().executeScript("addRoute(" + srcLat + ", " + srcLon + ", " + destLat + ", " + destLon + ");");
                        }
                    }
                }
            }
        }
    }

    /**
     * Show all airports on the map view
     */
    public void showAllAirports() {
        if (mapView.getEngine() != null) {
            HashMap<Integer, Airport> airports = Repository.airportRepository.getAirports();
            for (Map.Entry<Integer, Airport> entry : airports.entrySet()) {
                double lat = entry.getValue().getLatitude();
                double lon = entry.getValue().getLongitude();
                mapView.getEngine().executeScript("addAirport(" + lat + ", " + lon + ");");
            }
            mapView.getEngine().executeScript("showAllAirports();");
        }
        if (allAirports.isSelected() == false) {
            mapView.getEngine().executeScript("hideAllAirports();");
        }
    }

    /**
     * Show all routes on the map view
     */
    public void showAllRoutes() {
        if (mapView.getEngine() != null) {
            mapView.getEngine().executeScript("showAllRoutes();");
        }
        if (allRoutes.isSelected() == false) {
            mapView.getEngine().executeScript("hideAllRoutes();");
        }
    }

    /**
     * Refresh map view
     */
    public void refreshMap() {
        mapView.getEngine().load(getClass().getClassLoader().getResource("map.html").toExternalForm());
    }

}
