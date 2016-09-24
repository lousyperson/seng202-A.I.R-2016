package seng202.group4.GUI;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TreeSet;

/**
 * Created by Pang on 22/09/16.
 */
public class SearchPanesController implements Initializable{

    Controller mainController;
    MenuBarController menuBarController;

    // search field
    @FXML
    private TextField airlineSearch;

    // airlines inactive and active check boxes
    @FXML
    private CheckBox active;

    @FXML
    private CheckBox inactive;

    // airline country filter
    @FXML
    private ComboBox airlineCountryFilter;

    // airport FXML
    @FXML
    private TextField airportSearch;

    @FXML
    private ComboBox airportCountryFilter;



    @FXML
    private TextField calcdDistance;

    @FXML
    private Button calc;

    // route FXML
    @FXML
    private TextField routeSearch;

    @FXML
    private ComboBox routeDepCountryFilter;

    @FXML
    private ComboBox routeDestCountryFilter;

    @FXML
    private CheckBox direct;

    @FXML
    private CheckBox indirect;

    @FXML
    private ComboBox routeEquipFilter;

    // StackPane for search
    @FXML
    private StackPane searchPanes;

    public AnchorPane getAirlinePane() {
        return airlinePane;
    }

    public AnchorPane getAirportPane() {
        return airportPane;
    }

    public AnchorPane getRoutePane() {
        return routePane;
    }

    @FXML
    private AnchorPane airlinePane;

    @FXML
    private AnchorPane airportPane;

    @FXML
    private AnchorPane routePane;

    @FXML
    private TextField airportA;

    @FXML
    private TextField airportB;


    private Double pointALat;
    private Double pointALon;
    private Double pointBLat;
    private Double pointBLon;
    private String pointAICAO;
    private String pointBICAO;

    // initial combobox names
    private String allCountriesTag = " --ALL COUNTRIES-- ";
    private String allEquipmentsTag = " --ALL EQUIPMENTS-- ";
    private String departureCountryTag = " --DEPART FROM-- ";
    private String destinationCountryTag = " --ARRIVE TO-- ";
//
//    public TreeSet getAirlineCountrySet() {
//        return airlineCountrySet;
//    }
//
//    public TreeSet getAirportCountrySet() {
//        return airportCountrySet;
//    }
//
//    public TreeSet getDepSet() {
//        return depSet;
//    }
//
//    public TreeSet getDestSet() {
//        return destSet;
//    }
//
//    public TreeSet getEquipmentSet() {
//        return equipmentSet;
//    }

    // airlineCountrySet holds all the countries uploaded to airline
    private TreeSet airlineCountrySet = new TreeSet();

    // airportCountrySet holds all the countries uploaded to airport
    private TreeSet airportCountrySet = new TreeSet();

    // depSet holds all the departure country names uploaded to routes
    private TreeSet depSet = new TreeSet();

    // dest holds all the destination country names uploaded to routes
    private TreeSet destSet = new TreeSet();

    // equipmentSet holds all the equipment uploaded to route
    private TreeSet equipmentSet = new TreeSet();


    /**
     * Where the program starts, initializes things like listeners and starts running the GUI.
     *
     * @param location URL
     * @param resources ResourceBundle
     */
    public void initialize(URL location, ResourceBundle resources) {
        updateAirlineSearch();
        updateAirportSearch();
        updateRouteSearch();
    }

    private void updateAirlineSearch() {
        String text = airlineSearch.getText();
        airlineSearch.setText(text + " ");
        airlineSearch.setText(text);

    }

    private void updateAirportSearch() {
        String text = airportSearch.getText();
        airportSearch.setText(text + " ");
        airportSearch.setText(text);

    }

    private void updateRouteSearch() {
        String text = routeSearch.getText();
        routeSearch.setText(text + " ");
        routeSearch.setText(text);

    }

    /**
     * Sets the main controller and retrieves private variables from the main controller
     *
     * @param controller Controller
     */
    public void setMainController(Controller controller) {
        this.mainController = controller;
        this.menuBarController = mainController.getMenuBarController();


    }

    /**
     * Gets the main controller
     * @return Controller
     */
    public Controller getMainController() {
        return mainController;
    }



    /**
     * Allows the user to select the direct flight filter option.
     *
     * @throws IOException throws IOException error
     */
    public void selectDirect() throws IOException {
        updateRouteSearch();
    }

    /**
     * Allows the user to select the indirect flight filter option.
     *
     * @throws IOException throws IOException error
     */
    public void selectIndirect() throws IOException {
        updateRouteSearch();
    }

    /**
     * Filters the list of airlines by country, leaving only airlines from the selected county.
     *
     * @throws IOException throws IOException error
     */
    public void filterAirlineCountry() throws IOException {
        updateAirlineSearch();
    }

    /**
     * Filters the list of airports by country, leaving only airports from the selected county.
     *
     * @throws IOException throws IOException error
     */
    public void filterAirportCountry() throws IOException {
        updateAirportSearch();
    }

    /**
     * Filters by departure country, only showing routes departing from the selected country.
     *
     * @throws IOException throws IOException error
     */
    public void filterDepCountry() throws IOException {
        updateRouteSearch();
    }

    /**
     * Filters by destination country, only showing routes that arrive in the selected country.
     *
     * @throws IOException throws IOException error
     */
    public void filterDestCountry() throws IOException {
        updateRouteSearch();
    }

    /**
     * Filters by the equipment in a route, only showing routes that utilize that equipment.
     *
     * @throws IOException throws IOException error
     */
    public void filterEquipment() throws IOException {
        updateRouteSearch();
    }

    /**
     * Filters by whether or not the airline is active, only showing active airlines.
     *
     * @throws IOException throws IOException error
     */
    public void selectActiveAirlines() throws IOException {
        updateAirlineSearch();
    }

    /**
     * Filters by whether or not the airline is active, only showing inactive airlines.
     *
     * @throws IOException throws IOException error
     */
    public void selectInactiveAirlines() throws IOException {
        updateAirlineSearch();
    }

    /**
     * Allows the user to load airline data from a file
     *
     * @throws IOException throws IOException error
     */
    public void loadAirline() throws IOException {
        try {
            menuBarController.loadAirline();
        } catch (NullPointerException e) {
            // Do Nothing

        }
    }


    /**
     * Allows the user to load route data from a file
     *
     * @throws IOException throws IOException error
     */
    public void loadRoute() throws IOException {
        try {
            menuBarController.loadRoute();
        } catch (NullPointerException e) {
            // Do nothing
        }
    }


    /**
     * Allows the user to load airport data from a file
     *
     * @throws IOException throws IOException error
     */
    public void loadAirport() throws IOException {
        try {
            menuBarController.loadAirport();
        } catch (NullPointerException e) {
            // Do nothing
        }
    }

    /**
     * Calculates the distance between two coordinates on the map.
     */
    public void calcDistance() {
        if (pointALat != null && pointALon != null &&
                pointBLat != null && pointBLon != null) {

            final int R = 6371; // Radius of the earth in km

            Double latDistance = Math.toRadians(pointBLat - pointALat);
            Double lonDistance = Math.toRadians(pointBLon - pointALon);
            Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                    + Math.cos(Math.toRadians(pointALat)) * Math.cos(Math.toRadians(pointBLat))
                    * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
            Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

            double distance = R * c; // convert to meters
            calcdDistance.setText(String.format("%.2f", distance));
        }
    }


    public void getFlightPath() throws UnirestException, IOException, InterruptedException {
        if (!(pointAICAO == null) && !(pointBICAO == null)) {
            HttpResponse<JsonNode> getID = Unirest.get("https://api.flightplandatabase.com/search/plans?fromICAO=" + pointAICAO + "&toICAO=" + pointBICAO + "&limit=1")
                    .asJson();
            try {
                Integer id = getID.getBody().getArray().getJSONObject(0).getInt("id");
                String id2 = Integer.toString(id);

                String something = Unirest.get("https://api.flightplandatabase.com/plan/" + id2).header("Accept", "text/csv").asString().getBody();

                if (!something.contains("Bad Request")) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("Flight plan found!");
                    alert.setContentText("There was a matching plan (id: " + id2 + ")\nfrom FlightPlanDatabase.com." +
                            "\n\nPress 'Simple path' to load point-to-point path." +
                            "\nPress 'Online path' to load plan "+id2+".");

                    ButtonType buttonTypeOne = new ButtonType("Simple path");
                    ButtonType buttonTypeTwo = new ButtonType("Online path");
                    ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                    alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == buttonTypeOne){
                        String makeCSV = "APT,"+pointAICAO+",0,"+pointALat+","+pointALon+"\n" +
                                "APT,"+pointBICAO+",0,"+pointBLat+","+pointBLon;
                        menuBarController.loadFlight2(makeCSV);
                    } else if (result.get() == buttonTypeTwo) {
                        menuBarController.loadFlight2(something);
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("Look, an Error Dialog");
                    alert.setContentText("There is no flight path from " + pointAICAO + " to " + pointBICAO + "\nat FlightPlanDatabase.com.");

                    alert.showAndWait();
                }

            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("There is no flight plan found on\nFlightPlanDatabase.com.");
                alert.setContentText("Press OK to load a simple path.\nPress CANCEL to return to airport table.");

                ButtonType buttonTypeOne = new ButtonType("OK");
                ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonTypeOne){
                    String makeCSV = "APT,"+pointAICAO+",0,"+pointALat+","+pointALon+"\n" +
                            "APT,"+pointBICAO+",0,"+pointBLat+","+pointBLon;
                    menuBarController.loadFlight2(makeCSV);
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Woops");
            alert.setContentText("Please select Airport A and Airport B\nbefore continuing.");

            alert.showAndWait();
        }
    }


    public void updateAirlineCountryBox() {
        // clear the current combo box
        airlineCountryFilter.getItems().clear();
        // if the combo box doesn't have --ALL COUNTRIES-- then add one
        if (!airlineCountryFilter.getItems().contains(allCountriesTag)) {
            airlineCountryFilter.getItems().add(allCountriesTag);
        }

        // loop through the current airlineCountrySet
        Iterator itr = airlineCountrySet.iterator();
        while (itr.hasNext()) {
            airlineCountryFilter.getItems().add(itr.next());
        }
    }

    public void updateAirportCountryBox() {
        airportCountryFilter.getItems().clear();
        if (!airportCountryFilter.getItems().contains(allCountriesTag)) {
            airportCountryFilter.getItems().add(allCountriesTag);
        }
        Iterator itr = airportCountrySet.iterator();
        while (itr.hasNext()) {
            airportCountryFilter.getItems().add(itr.next());
        }
    }


    public void updateDepCountryBox() {
        routeDepCountryFilter.getItems().clear();
        if (!routeDepCountryFilter.getItems().contains(departureCountryTag)) {
            routeDepCountryFilter.getItems().add(departureCountryTag);
        }
        Iterator itr = depSet.iterator();
        while (itr.hasNext()) {
            routeDepCountryFilter.getItems().add(itr.next());
        }
    }

    public void updateDestCountryBox() {
        routeDestCountryFilter.getItems().clear();
        if (!routeDestCountryFilter.getItems().contains(destinationCountryTag)) {
            routeDestCountryFilter.getItems().add(destinationCountryTag);
        }
        Iterator itr = destSet.iterator();
        while (itr.hasNext()) {
            routeDestCountryFilter.getItems().add(itr.next());
        }
    }


    public void updateEquipBox() {
        // clear the current combo box
        routeEquipFilter.getItems().clear();
        // if the combo box doesn't have --ALL EQUIPMENTS-- then add one
        if (!routeEquipFilter.getItems().contains(allEquipmentsTag)) {
            routeEquipFilter.getItems().add(allEquipmentsTag);
        }

        // loop through the current airlineCountrySet
        Iterator itr = equipmentSet.iterator();
        while (itr.hasNext()) {
            routeEquipFilter.getItems().add(itr.next());
        }
    }


}
