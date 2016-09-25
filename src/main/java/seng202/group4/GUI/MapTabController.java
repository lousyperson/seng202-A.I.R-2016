package seng202.group4.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.web.WebView;
import seng202.group4.data.dataType.Airport;
import seng202.group4.data.repository.Repository;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by psu43 on 25/09/16.
 */
public class MapTabController implements Initializable{

    Controller mainController;

    @FXML
    private Accordion accord1;

    @FXML
    private TitledPane instructions1;

    @FXML
    private TextField flightNameSearch1;


    // Map view
    @FXML
    private WebView mapView;


    @FXML
    private ComboBox chooseAirport;

    @FXML
    private CheckBox allAirports;

    @FXML
    private CheckBox allRoutes;



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

    public void refreshMap() {
        mapView.getEngine().load(getClass().getClassLoader().getResource("map.html").toExternalForm());
    }

}
