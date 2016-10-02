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

/**
 * Created by psu43 on 25/09/16.
 */
public class MapTabController implements Initializable{



    // Map View
    @FXML
    private Accordion mapViewAccord;

    @FXML
    private TitledPane mapInstructions;

    @FXML
    private ListView<String> viewSelect;

    @FXML
    private AnchorPane mapAirportAnchor;

    @FXML
    private AnchorPane mapRouteAnchor;

    @FXML
    private ComboBox mapAirportFilter;

    @FXML
    private WebView mapView;

    @FXML
    private TextField mapAirportSearch;

    @FXML
    private ListView<String> mapAirportList;

    @FXML
    private TextField mapAirportRouteSearch;

    @FXML
    private ListView<String> mapAirportRouteList;

    // Used in Map View
    private TreeSet<String> mapCountrySet = new TreeSet();
    private int mapAirportListIndex = -1;
    private int mapAirportRouteListIndex = -1;

    private Controller mainController;


    /**
     * Sets the main controller.
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
        initialiseMap();
    }


    /**
     *  Map view initializer.
     */
    private void initialiseMap() {
        mapView.getEngine().load(getClass().getClassLoader().getResource("map.html").toExternalForm());
        mapViewAccord.setExpandedPane(mapInstructions);

        // draggable on and off
//        mapView.setOnMouseExited(mouseEvent -> {
//            mapView.getEngine().executeScript("off();");
//        });
//        mapView.setOnMouseEntered(mouseEvent -> {
//            mapView.getEngine().executeScript("on();");
//        });

        // selection listview
        ObservableList<String> items = FXCollections.observableArrayList("Airports", "Airport routes");
        viewSelect.setItems(items);
        viewSelect.getSelectionModel().clearAndSelect(0);

        viewSelect.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            if (new_val.equals("Airports")) {
                mapAirportAnchor.toFront();
                mapAirportAnchor.setVisible(true);
                mapRouteAnchor.setVisible(false);
            } else if (new_val.equals("Airport routes")) {
                mapRouteAnchor.toFront();
                mapRouteAnchor.setVisible(true);
                mapAirportAnchor.setVisible(false);
            }
        });

        // populate airportCountrySet for combobox
        HashMap<Integer, Airport> airports = Repository.airportRepository.getAirports();
        if (airports != null) {
            for (Airport airport : airports.values()) {
                if (airport != null && airport.getCountry() != null) {
                    mapCountrySet.add(airport.getCountry());
                }
            }
        }

        // if the combo box doesn't have --SELECT COUNTRIES-- then add one
        if (!mapAirportFilter.getItems().contains("--SELECT COUNTRIES--")) {
            mapAirportFilter.getItems().add("--SELECT COUNTRIES--");
        }

        // if the combo box doesn't have --ALL COUNTRIES-- then add one
        if (!mapAirportFilter.getItems().contains("--ALL COUNTRIES--")) {
            mapAirportFilter.getItems().add("--ALL COUNTRIES--");
        }

        // add countries from TreeSet as combobox options
        for (String aMapCountrySet : mapCountrySet) {
            mapAirportFilter.getItems().add(aMapCountrySet);
        }
        mapAirportFilter.getSelectionModel().select(0);

        // listen to whats being selected in the mapAirportList
        mapAirportList.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> ov,
                                                                               String old_val, String new_val) -> {
            if (new_val != null) {
                // clear table and populate it again with what's selected
                showAirport(new_val.toLowerCase());
            }
        });

        // unselects upon clicking again
        mapAirportList.setOnMouseClicked(event -> {
            final int index = mapAirportList.getSelectionModel().getSelectedIndex();
            if (mapAirportListIndex == index) {
                mapAirportList.getSelectionModel().clearSelection();
                mapAirportListIndex = -1;
                refreshMap();
            } else {
                mapAirportListIndex = index;
            }
        });

        // listen to whats being selected in the mapAirportRouteList
        mapAirportRouteList.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> ov,
                                                                                    String old_val, String new_val) -> {
            if (new_val != null) {
                // clear table and populate it again with what's selected
                showRoutes(new_val.toLowerCase());
            }
        });

        // unselects upon clicking again
        mapAirportRouteList.setOnMouseClicked(event -> {
            final int index = mapAirportRouteList.getSelectionModel().getSelectedIndex();
            if (mapAirportRouteListIndex == index) {
                mapAirportRouteList.getSelectionModel().clearSelection();
                mapAirportRouteListIndex = -1;
                refreshMap();
            } else {
                mapAirportRouteListIndex = index;
            }
        });
    }

    private void showAirport(String inputAirportString) {  // Reposition not quite right yet
        Airport airport = Repository.airportRepository.getAirport(inputAirportString);
        mapView.getEngine().executeScript("initMap()");
        double lat = airport.getLatitude();
        double lon = airport.getLongitude();
        mapView.getEngine().executeScript("addFlight(" + lat + ", " + lon + ");");
        mapView.getEngine().executeScript("repositionMap()");
    }

    /**
     * Reads text from mapAirportSearch and finds the matching airports then updates mapAirportList with the airports found.
     */
    public void airportSearch() {
        HashMap<Integer, Airport> airports = Repository.airportRepository.getAirports();
        String inputAirportString = mapAirportSearch.getText();
        Pattern pattern = Pattern.compile(".*" + inputAirportString + ".*", Pattern.CASE_INSENSITIVE);

        if (inputAirportString.length() > 4) {
            updateMapAirportListString(airports, pattern);  // Only string search for now
        } else if (inputAirportString.length() > 3) {
            updateMapAirportListString(airports, pattern);
//            updateMapAirportListIATA(airports, pattern, airportItems);
        } else {
            updateMapAirportListString(airports, pattern);
//            updateMapAirportListIATA(airports, pattern, airportItems);
//            updateMapAirportListICAO(airports, pattern, airportItems);
        }
    }

    private void updateMapAirportListString(HashMap<Integer, Airport> airports, Pattern pattern) {
        // List View items
        ObservableList<String> airportItems = FXCollections.observableArrayList();

        if (airports != null) {
            for (Airport airport : airports.values()) {
                Matcher matcher = pattern.matcher(airport.getName());
                if (matcher.matches()) {
                    airportItems.add(airport.getName());
                }
            }
            mapAirportList.setItems(airportItems);
        }
    }

    /**
     * Updates Map View with every airports in the country selected.
     */
    public void showCountryAirports() {
        String country = mapAirportFilter.getSelectionModel().getSelectedItem().toString();  // Not yet check for all countries
        mapView.getEngine().executeScript("initMap()");
        if (!country.equals("--ALL COUNTRIES--") && !country.equals("Antarctica")) {
            ArrayList<Airport> airports = Repository.airportRepository.getAirportsFromCountry(country);
            if (airports.size() > 0) {
                for (Airport airport : airports) {
                    double lat = airport.getLatitude();
                    double lon = airport.getLongitude();
                    mapView.getEngine().executeScript("addFlight(" + lat + ", " + lon + ");");
                }
                mapView.getEngine().executeScript("mapClusterer()");
                mapView.getEngine().executeScript("repositionMap()");
            }
        } else if (country.equals("Antarctica")) {  // Special case where reposition doesn't work
            mapView.getEngine().executeScript("refreshMap(" + -50 + "," + 0 + ")");  // Make nicer map position
            ArrayList<Airport> airports = Repository.airportRepository.getAirportsFromCountry(country);
            for (Airport airport : airports) {
                double lat = airport.getLatitude();
                double lon = airport.getLongitude();
                mapView.getEngine().executeScript("addFlight(" + lat + ", " + lon + ");");
            }
            mapView.getEngine().executeScript("mapClusterer()");
        } else {
            HashMap<Integer, Airport> airports = Repository.airportRepository.getAirports();
            for (Airport airport : airports.values()) {
                double lat = airport.getLatitude();
                double lon = airport.getLongitude();
                mapView.getEngine().executeScript("addFlight(" + lat + ", " + lon + ");");
            }
            mapView.getEngine().executeScript("mapClusterer()");
        }
    }

    /**
     * Reads text from mapAirportRouteSearch and finds the matching airports then updates mapAirportRouteList with the airports found.
     */
    public void airportRouteSearch() {
        HashMap<Integer, Airport> airports = Repository.airportRepository.getAirports();
        String inputAirportString = mapAirportRouteSearch.getText();
        Pattern pattern = Pattern.compile(".*" + inputAirportString + ".*", Pattern.CASE_INSENSITIVE);

        if (inputAirportString.length() > 4) {
            updateMapAirportRouteListString(airports, pattern);  // Only string search for now
        } else if (inputAirportString.length() > 3) {
            updateMapAirportRouteListString(airports, pattern);
        } else {
            updateMapAirportRouteListString(airports, pattern);

        }
    }

    private void updateMapAirportRouteListString(HashMap<Integer, Airport> airports, Pattern pattern) {
        // List View items
        ObservableList<String> airportItems = FXCollections.observableArrayList();

        if (airports != null) {
            for (Airport airport : airports.values()) {
                Matcher matcher = pattern.matcher(airport.getName());
                if (matcher.matches()) {
                    airportItems.add(airport.getName());
                }
            }
            mapAirportRouteList.setItems(airportItems);
        }
    }

    private void showRoutes(String inputAirportString) {
        Airport aAirport = Repository.airportRepository.getAirport(inputAirportString);
        Integer inputAirportID = aAirport.getID();
        HashMap<String, Route> routes = Repository.routeRepository.getRoutes();
        mapView.getEngine().executeScript("initMap()");
        for (Route route : routes.values()) {
            if (Objects.equals(route.getSrcAirportID(), inputAirportID)) {
                int destID = route.getDestAirportID();
                Airport airport = Repository.airportRepository.getAirports().get(destID);
                double lat = airport.getLatitude();
                double lon = airport.getLongitude();
                mapView.getEngine().executeScript("makeOnePath(" + aAirport.getLatitude() + ", " + aAirport.getLongitude()
                        + ", " + lat + ", " + lon + ");");
            }
        }
        mapView.getEngine().executeScript("addFlight(" + aAirport.getLatitude() + ", " + aAirport.getLongitude() + ");");
        mapView.getEngine().executeScript("repositionMap()");
    }

    /**
     * Refresh map view.
     */
    public void refreshMap() {
        mapView.getEngine().load(getClass().getClassLoader().getResource("map.html").toExternalForm());
    }

}
