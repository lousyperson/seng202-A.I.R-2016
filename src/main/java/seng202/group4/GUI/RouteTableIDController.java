package seng202.group4.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by psu43 on 25/09/16.
 */
public class RouteTableIDController implements Initializable {
    Controller mainController;


    @FXML
    private TableColumn<routeTable, String> airline;

    @FXML
    private TableColumn<routeTable, Integer> airlineID;

    @FXML
    private TableColumn<routeTable, String> source;

    @FXML
    private TableColumn<routeTable, Integer> sourceID;

    @FXML
    private TableColumn<routeTable, String> dest;

    @FXML
    private TableColumn<routeTable, Integer> destID;

    @FXML
    private TableColumn<routeTable, String> codeshare;

    @FXML
    private TableColumn<routeTable, Integer> stops;

    @FXML
    private TableColumn<routeTable, String> equipment;

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
        System.out.println("routetable id init");
        // initialise route data table resources
        airline.setCellValueFactory(new PropertyValueFactory<>("rairline"));
        airlineID.setCellValueFactory(new PropertyValueFactory<>("rid"));
        source.setCellValueFactory(new PropertyValueFactory<>("rsource"));
        sourceID.setCellValueFactory(new PropertyValueFactory<>("rsourceid"));
        dest.setCellValueFactory(new PropertyValueFactory<>("rdest"));
        destID.setCellValueFactory(new PropertyValueFactory<>("rdestid"));
        codeshare.setCellValueFactory(new PropertyValueFactory<>("rcodeshare"));
        stops.setCellValueFactory(new PropertyValueFactory<>("rstops"));
        equipment.setCellValueFactory(new PropertyValueFactory<>("requipment"));

    }
}
