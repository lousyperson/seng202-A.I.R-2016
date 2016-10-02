package seng202.group4.GUI.controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import seng202.group4.GUI.controller.Controller;
import seng202.group4.data.repository.Repository;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Pang on 22/09/16.
 */
public class DataTabController implements Initializable{

    @FXML
    AnchorPane dataSelector;

    @FXML
    AnchorPane airlineAnchor;

    @FXML
    AnchorPane airportAnchor;

    @FXML
    AnchorPane routeAnchor;

    @FXML
    private ListView<String> datalist;

    @FXML
    private AirlineAnchorController airlineAnchorController;

    @FXML
    private AirportAnchorController airportAnchorController;

    @FXML
    private RouteAnchorController routeAnchorController;

    // controllers
    private Controller mainController;
    private FlightTabController flightTabController;


    // variables from other controllers
    private TabPane tabPane;
    private Tab dataTab;


    // initial combobox names
    private String allCountriesTag = " --ALL COUNTRIES-- ";

    // user data selection labels
    private String airlineLabel = "Airlines";
    private String airportLabel = "Airports";
    private String routeLabel = "Routes";

    private ObservableList<String> items = FXCollections.observableArrayList(airlineLabel, airportLabel, routeLabel);


    /**
     * Where the program starts, initializes things like listeners and starts running the GUI.
     *
     * @param location URL
     * @param resources ResourceBundle
     */
    public void initialize(URL location, ResourceBundle resources) {
        // Passing main controller to other controllers
        airlineAnchorController.setMainController(this);
        airportAnchorController.setMainController(this);
        routeAnchorController.setMainController(this);

        datalist.setItems(items);
        // select first data type (airline) on the side bar
        datalist.getSelectionModel().clearAndSelect(0);

        datalist.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            if (new_val.equals(airlineLabel)) {
                airlineAnchor.toFront();
                airlineAnchor.setVisible(true);
                routeAnchor.setVisible(false);
                airportAnchor.setVisible(false);
            } else if (new_val.equals(airportLabel)) {
                airportAnchor.toFront();
                airportAnchor.setVisible(true);
                airlineAnchor.setVisible(false);
                routeAnchor.setVisible(false);
            } else if (new_val.equals(routeLabel)) {
                routeAnchor.toFront();
                routeAnchor.setVisible(true);
                airlineAnchor.setVisible(false);
                airportAnchor.setVisible(false);
            }
            dataSelector.toFront();
        });

        // check if there is data in the tables
        noDataCheck();
    }

    /**
     * Sets the main controller and retrieves private variables from the main controller.
     *
     * @param controller Controller
     */
    public void setMainController(Controller controller) {
        this.mainController = controller;
        this.flightTabController = mainController.getFlightTabController();
        this.tabPane = mainController.getTabPane();
        this.dataTab = mainController.getDataTab();
    }

    private void noDataCheck() {
        ArrayList<String> noData = new ArrayList<>();
        if (Repository.airlineRepository != null && Repository.airlineRepository.getAirlines().size() == 0) {
            noData.add("airline");
        }
        if (Repository.airportRepository != null && Repository.airportRepository.getAirports().size() == 0) {
            noData.add("airport");
        }
        if (Repository.routeRepository != null && Repository.routeRepository.getRoutes().size() == 0) {
            noData.add("route");
        }
        if (noData.size() > 0) {
            noDataWarning(noData);
        }
    }

    private void noDataWarning(ArrayList<String> noData) {
        String message = "";
        String context = "Please go to \"Reset to Default\" under the file menu\nif you wish to load the default data.";
        if (noData.size() == 1) {
            message = noData.get(0);
        } else if (noData.size() == 2) {
            message = noData.get(0) + " or " + noData.get(1);
        } else {
            message = noData.get(0) + ", " + noData.get(1) + " or " + noData.get(2);
        }
        Alert warning = new Alert(Alert.AlertType.WARNING);
        warning.setTitle("No " + message + " data found.");
        warning.setHeaderText("You are about to load with empty data");
        warning.setContentText(context);
        warning.showAndWait();
    }

    /**
     * Change tab to data tab if its not on it already and switch the selection tab to the given name.
     */
    public void goToDataTab(String name) {
        if (!tabPane.getSelectionModel().equals(dataTab)) {
            tabPane.getSelectionModel().select(dataTab);
        }
        datalist.getSelectionModel().select(name);
    }

    /**
     * Calls the dialog box to confirm that the user wants to reset their selected data type.
     *
     * @return true if they select OK otherwise false
     */
    public boolean resetConformation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure you want to reset your select data type?");
        alert.setContentText("This will replace the data with the default data.\nThis may take a few moments" +
                "\n\nWARNING: The action cannot be undone and may take a while.\n");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Calls the dialog box to confirm that the user wants to delete their selected data type.
     *
     * @return true if they select OK otherwise false
     */
    public boolean deleteAllConformation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure you want to delete your selected data type?");
        alert.setContentText("This will DELETE ALL OF YOUR DATA\nof the selected data type" +
                "\n\nWARNING: The action cannot be undone.\n");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Getter for airline label.
     *
     * @return String
     */
    public String getAirlineLabel() {
        return airlineLabel;
    }

    /**
     * Getter for airport label.
     *
     * @return String
     */
    public String getAirportLabel() {
        return airportLabel;
    }

    /**
     * Getter for route label.
     *
     * @return String
     */
    public String getRouteLabel() {
        return routeLabel;
    }

    /**
     * Getter for all countries tag.
     *
     * @return String
     */
    public String getAllCountriesTag() {
        return allCountriesTag;
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
     * Getter for airportAnchorController.
     *
     * @return AirportAnchorController
     */
    public AirportAnchorController getAirportAnchorController() {
        return airportAnchorController;
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

}