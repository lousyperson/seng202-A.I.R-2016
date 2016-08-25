package seng202.group4.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seng202.group4.data.dataType.Airline;
import seng202.group4.data.parser.AirlineParser;
import seng202.group4.data.parser.AirportParser;
import seng202.group4.data.parser.FlightParser;
import seng202.group4.data.parser.RouteParser;
import seng202.group4.data.parser.validator.AirlineValidator;


import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Controller implements Initializable{

    // DEFINE TABLE
    @FXML
    TableView<airlineTable> tableID;

    @FXML
    TableColumn<airlineTable, String> id;

    @FXML
    TableColumn<airlineTable, String> name;

    @FXML
    TableColumn<airlineTable, String> alias;

    @FXML
    TableColumn<airlineTable, String> iata;

    @FXML
    TableColumn<airlineTable, String> icao;

    @FXML
    TableColumn<airlineTable, String> callsign;

    @FXML
    TableColumn<airlineTable, String> country;

    @FXML
    TableColumn<airlineTable, String> active;


    //create table data

    final ObservableList<airlineTable> data = FXCollections.observableArrayList();



    public void initialize(URL location, ResourceBundle resources) {
        id.setCellValueFactory(new PropertyValueFactory<airlineTable, String>("rid"));
        name.setCellValueFactory(new PropertyValueFactory<airlineTable, String>("rname"));
        alias.setCellValueFactory(new PropertyValueFactory<airlineTable, String>("ralias"));
        iata.setCellValueFactory(new PropertyValueFactory<airlineTable, String>("riata"));
        icao.setCellValueFactory(new PropertyValueFactory<airlineTable, String>("ricao"));
        callsign.setCellValueFactory(new PropertyValueFactory<airlineTable, String>("rcallsign"));
        country.setCellValueFactory(new PropertyValueFactory<airlineTable, String>("rcountry"));
        active.setCellValueFactory(new PropertyValueFactory<airlineTable, String>("ractive"));

        tableID.setItems(data);




    }




    public void loadAirline() throws IOException {

            Stage stage = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open file");
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                System.out.println("file opneedd");

                AirlineValidator validator = new AirlineValidator(file);
                ArrayList<Airline> airlines = validator.makeAirlines();

            }
    }

    public void loadAirport() throws FileNotFoundException {

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            AirportParser parser = new AirportParser(br);
        }
    }


    public void loadRoute() throws FileNotFoundException {

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            //TODO
        }
    }

    public void loadFlight() throws FileNotFoundException {

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            //TODO
        }
    }




}
