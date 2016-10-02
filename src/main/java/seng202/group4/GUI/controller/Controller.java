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

    //@FXML
    //private MapTabController mapTabController;

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


//    // Used in flightAnalysis
//    private TreeSet<String> airportCountrySet = new TreeSet();
//
//    // FXML for analysis tab
//    @FXML
//    private ComboBox analysisDropdown;
//
//    @FXML
//    private ComboBox countryDropdown;
//
//    @FXML
//    private TableView<AnalysisTable> airportsAndRoutes;
//
//    @FXML
//    private TableColumn<AnalysisTable, String> column1;
//
//    @FXML
//    private TableColumn<AnalysisTable, Integer> airportCount;
//
//    @FXML
//    private PieChart pieChart;
//
//    @FXML
//    private BarChart<String,Number> barChart;
//
//    @FXML
//    private Text selectCountryText;
//
//    @FXML
//    private Text warningPieChart;
//
//    @FXML
//    private Text warningBarChart;
//
//    @FXML
//    private Text rowSize;

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

    /**
     * Initializes the controller.
     *
     * @param location URL
     * @param resources ResourceBundle
     */
    public void initialize(URL location, ResourceBundle resources) {
        // Passing MainController to the other controllers
        menuBarController.setMainController(this);
        //mapTabController.setMainController(this);
        dataTabController.setMainController(this);
        flightTabController.setMainController(this);
        analysisTabController.setMainController(this);

        doMapViewThingy();  // Gonna be the map view initialiser
    }

    private void doMapViewThingy() {  // Gonna be the map view initialiser
        mapView.getEngine().load(getClass().getClassLoader().getResource("map.html").toExternalForm());
        mapViewAccord.setExpandedPane(mapInstructions);

        // retest
        mapView.setOnMouseExited(mouseEvent -> {
            mapView.getEngine().executeScript("off();");
        });
        mapView.setOnMouseEntered(mouseEvent -> {
            mapView.getEngine().executeScript("on();");
        });

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

//    private void updateMapAirportListIATA(HashMap<Integer, Airport> airports, Pattern pattern, ObservableList<String> airportItems) {
//        if (airports != null) {
//            for (Airport airport : airports.values()) {
//                Matcher matcher = pattern.matcher(airport.getName());
//                if (matcher.matches()) {
//                    airportItems.add(airport.getName());
//                    mapAirportList.setItems(airportItems);
//                }
//            }
//        }
//    }
//
//    private void updateMapAirportListICAO(HashMap<Integer, Airport> airports, Pattern pattern, ObservableList<String> airportItems) {
//        if (airports != null) {
//            for (Airport airport : airports.values()) {
//                Matcher matcher = pattern.matcher(airport.getName());
//                if (matcher.matches()) {
//                    airportItems.add(airport.getName());
//                    mapAirportList.setItems(airportItems);
//                }
//            }
//        }
//    }

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

    public void airportRouteSearch() {
        HashMap<Integer, Airport> airports = Repository.airportRepository.getAirports();
        String inputAirportString = mapAirportRouteSearch.getText();
        Pattern pattern = Pattern.compile(".*" + inputAirportString + ".*", Pattern.CASE_INSENSITIVE);

        if (inputAirportString.length() > 4) {
            updateMapAirportRouteListString(airports, pattern);  // Only string search for now
        } else if (inputAirportString.length() > 3) {
            updateMapAirportRouteListString(airports, pattern);
//            updateMapAirportListIATA(airports, pattern, airportItems);
        } else {
            updateMapAirportRouteListString(airports, pattern);
//            updateMapAirportListIATA(airports, pattern, airportItems);
//            updateMapAirportListICAO(airports, pattern, airportItems);
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
     * Refresh map view
     */
    public void refreshMap() {
        mapView.getEngine().load(getClass().getClassLoader().getResource("map.html").toExternalForm());
    }


    /**
     * Getter for flight tab
     * @return Tab
     */
    public Tab getFlightTab() {
        return flightTab;
    }

    /**
     * Getting for tab pane
     * @return TabPane
     */
    public TabPane getTabPane() {
        return tabPane;
    }

    /**
     * Getter for data tab
     * @return Tab
     */
    public Tab getDataTab() {
        return dataTab;
    }

    /**
     * Getter for dataTabController
     * @return DataTabController
     */
    public DataTabController getDataTabController() {
        return dataTabController;
    }

    /**
     * Getter for routeAnchorController
     * @return RouteAnchorController
     */
    public RouteAnchorController getRouteAnchorController() {
        return routeAnchorController;
    }

    /**
     * Getter for flightTabController
     * @return FlightTabController
     */
    public FlightTabController getFlightTabController() {
        return flightTabController;
    }

    /**
     * Getter for airportAnchorController
     * @return AirportAnchorController
     */
    public AirportAnchorController getAirportAnchorController() {
        return airportAnchorController;
    }

    /**
     * Getter for airlineAnchorController
     * @return AirlineAnchorController
     */
    public AirlineAnchorController getAirlineAnchorController() {
        return airlineAnchorController;
    }

    /**
     * Getter for menuBarController
     * @return MenuBarController
     */
    public MenuBarController getMenuBarController() {
        return menuBarController;
    }

    /**
     * Getter for analysisTabController
     * @return AnalysisTabController
     */
    public AnalysisTabController getAnalysisTabController() {
        return analysisTabController;
    }

//    /**
//     * Getter for mapTabController
//     * @return MapTabController
//     */
//    public MapTabController getMapTabController() {
//        return mapTabController;
//    }



}