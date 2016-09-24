package seng202.group4.GUI;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seng202.group4.data.dataType.Flight;
import seng202.group4.data.dataType.FlightPosition;
import seng202.group4.data.parser.validator.FlightValidator;
import seng202.group4.data.repository.Repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Pang on 24/09/16.
 */
public class FlightTabController implements Initializable{

    Controller mainController;
    //MenuBarController menuBarController;

    private Tab flightTab;
    private TabPane tabPane;

    // testing for flight
    private ObservableList<String> flightItems = FXCollections.observableArrayList();


    private ObservableList<flightTable> flightTData = FXCollections.observableArrayList();


    @FXML
    private WebView flightMap;

    @FXML
    private TitledPane instructions;

    @FXML
    private Accordion accord;

    // Flight table

    @FXML
    private TextField flightNameSearch;

    @FXML
    private TableView<flightTable> flightTableID;

    @FXML
    private TableColumn<flightTable, String> flightID;

    @FXML
    private TableColumn<flightTable, String> flightType;

    @FXML
    private TableColumn<flightTable, Integer> flightAltitude;

    @FXML
    private TableColumn<flightTable, Double> flightLatitude;

    @FXML
    private TableColumn<flightTable, Double> flightLongitude;

    @FXML
    private ListView<String> flightList;


    /**
     * Sets the main controller and retrieves private variables from the main controller
     *
     * @param controller Controller
     */
    public void setMainController(Controller controller) {
        this.mainController = controller;
        //this.menuBarController = controller.getMenuBarController();
        this.flightTab = mainController.getFlightTab();
        this.tabPane = mainController.getTabPane();



    }

    /**
     * Where the program starts, initializes things like listeners and starts running the GUI.
     *
     * @param location URL
     * @param resources ResourceBundle
     */
    public void initialize(URL location, ResourceBundle resources) {

        // Load the flight map
        flightMap.getEngine().load(getClass().getClassLoader().getResource("map.html").toExternalForm());

        // initially expand the map instructions on the side bar
        accord.setExpandedPane(instructions);

        updateFlightNameSearch();

        // listen to whats being selected in the flight list
        flightList.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            //System.out.println("Selected item from flight list: " + new_val);

            if (new_val != null) {
                // clear table and populate it again with what's selected
                updateFlightTable(new_val.toLowerCase());
                showFlightPath(new_val.toLowerCase());
            }

        });

        searchFlightNames();

        // initialise route data table resources
        flightID.setCellValueFactory(new PropertyValueFactory<>("fid"));
        flightType.setCellValueFactory(new PropertyValueFactory<>("ftype"));
        flightAltitude.setCellValueFactory(new PropertyValueFactory<>("faltitude"));
        flightLatitude.setCellValueFactory(new PropertyValueFactory<>("flatitude"));
        flightLongitude.setCellValueFactory(new PropertyValueFactory<>("flongitude"));

        flightTableID.setItems(flightTData);

        flightList.setCellFactory(listView -> {
            final ListCell<String> cell = new ListCell<>();
            final ContextMenu contextMenu = new ContextMenu();
            MenuItem removeItem = new MenuItem("Delete");

            cell.setOnMouseClicked(event -> {});
            removeItem.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Are you sure you want to delete?");
                alert.setContentText("Pressing OK will delete the row(s).\nWARNING: The action cannot be undone.\n");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Repository.flightRepository.getFlights().remove(cell.getItem());
                    flightList.getItems().remove(cell.getItem());
                    if (cell.getItem() != null) {
                        updateFlightTable(cell.getItem());
                    } else {
                        flightMap.getEngine().executeScript("initMap();");
                    }

                }
            });
            contextMenu.getItems().addAll(removeItem);

            cell.textProperty().bind(cell.itemProperty());

            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(contextMenu);
                }
            });
            return cell;
        });

        loadDefaultFlight();
        noDataCheck();

    }

    // update flight table view given the flight name
    private void updateFlightTable(String name){

        // access flight repository to get the flight positions array given the name
        Flight flight = Repository.flightRepository.getFlights().get(name);

        // clear the table then populate it with this flight
        flightTData.clear();
        // loop through array of flight positions
        for(FlightPosition position: flight.getFlightPositions()){
            flightTData.add(new flightTable(position.getID(), position.getType(), position.getAltitude(),
                    position.getLatitude(), position.getLongitude()));
        }

    }

    // update map with flight path given the flight name
    private void showFlightPath(String flightName) {
        //String flightName = "lol";

        if (flightMap.getEngine() != null) {
            flightMap.getEngine().executeScript("deleteFlights();");
            // get the flight that is selected so we can get the flight name whic is a key to the flight repo
            // so then we can get flight (info) now we have it yay its called flight
            Flight flight = Repository.flightRepository.getFlights().get(flightName);
            // flight actually has an array list of positions and positions= array listof FLIGHT POSITISONS
            // which is another object =.=

            for (int i = 0; i < flight.getFlightPositions().size(); i++) {
                // the first row would be like
                FlightPosition firstRow = flight.getFlightPositions().get(i);

                // lat long first row
                double lat = firstRow.getLatitude();
                double lon = firstRow.getLongitude();
                //flightMap.getEngine().executeScript("on();");

                flightMap.getEngine().executeScript("addFlight(" + lat + ", " + lon + ");");
            }
            flightMap.getEngine().executeScript("makePath();");
        }
    }

    /**
     * Searches through the names of the flights so that the user is able to find and select flight from the list.
     */
    private void searchFlightNames() {
        // listen to whats being typed in the search box
        flightNameSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                //clear the table
                flightTData.clear();
                // store the names that match
                ObservableList<String> tempNames = FXCollections.observableArrayList();
                for (String s : flightItems) {
                    if (s.toLowerCase().contains(newValue.toLowerCase())) {
                        tempNames.add(s);
                    }
                }
                // update the flight list with the names that match
                flightList.setItems(tempNames);
            } else {
                // update flight list with all names
                flightList.setItems(flightItems);
            }
        });
    }


    private void noDataCheck() {
        ArrayList<String> noData = new ArrayList<String>();
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

    public void refreshMap() {
        flightMap.getEngine().load(getClass().getClassLoader().getResource("map.html").toExternalForm());
        flightList.getSelectionModel().clearSelection();
    }


//    /**
//     * Allows the user to load a flight from a file
//     * @throws IOException throws IOException error
//     */
//    public void loadFlight() throws IOException {
//        try {
//            menuBarController.loadFlight();
//        } catch (NullPointerException e) {
//            // Do nothing
//        }
//    }

    /**
     * Allows the user to load a flight from a file
     *
     * @throws IOException throws IOException error
     */
    public void loadFlight() throws IOException {

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        File in = fileChooser.showOpenDialog(stage);
        if (in != null && in.exists()) {
            InputStream file = new FileInputStream(in);
            //System.out.println("oooo yah flights");
            // change tab to flight tab if its not on it already
            if (!tabPane.getSelectionModel().equals(flightTab)) {
                tabPane.getSelectionModel().select(flightTab);
            }
            insertFlightTable(file);
        }
    }

    /**Insert the flight in a given file into the flight table
     *
     * @param file InputStream
     * @throws IOException throws IOException error
     */
    public void insertFlightTable(InputStream file) throws IOException {
        FlightValidator validator = new FlightValidator(file);
        Flight flight = validator.makeFlight();
        validator = null;

        if(flight != null) {
            boolean gotName = false;
            while(!gotName){
                // ask user for flight name
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Name your flight!");
                dialog.setHeaderText("Please give a name for your flight.");
                dialog.setContentText("Flight name:");
                Optional<String> name = dialog.showAndWait();
                // if they press ok and field is not empty, check if a flight with that name exists
                if(name.isPresent() && !name.get().trim().isEmpty()) {
                    // if it does not already exist add the flight with that name to the repository
                    if(!Repository.flightRepository.getFlights().containsKey(name.get().toLowerCase())){
                        gotName = true;
                        Repository.flightRepository.addFlight(name.get().toLowerCase(), flight);
                        flightItems.add(name.get());

                        // update the listView of flight names
                        flightList.setItems(flightItems);

                        // make the listView select to the newly uploaded flight which is
                        // listened to already so it will populate the table
                        flightList.getSelectionModel().selectLast();

                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Oops!");
                        alert.setHeaderText(null);
                        alert.setContentText("Please give a different name for your flight \n" +
                                "and make sure that it does not already exist \nin the system.");
                        alert.showAndWait();
                    }
                }
                else if(!name.isPresent()){
                    gotName = true;
                }
            }

        }
        tabPane.getSelectionModel().select(1);
    }

    private void updateFlightNameSearch() {
        String text = flightNameSearch.getText();
        flightNameSearch.setText(text + " ");
        flightNameSearch.setText(text);
    }

    private void loadDefaultFlight() {
        if (Repository.flightRepository != null) {
            for (String name : Repository.flightRepository.getFlights().keySet()) {
                flightItems.add(name);
                flightList.setItems(flightItems);
            }
        }
    }

}
