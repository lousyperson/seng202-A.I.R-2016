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
import seng202.group4.data.parser.AirportParser;
import seng202.group4.data.parser.validator.AirlineValidator;


import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Controller implements Initializable{

    // DEFINE TABLES

    // Airline Table
    @FXML
    TableView<airlineTable> airlineTableID;

    @FXML
    TableColumn<airlineTable, String> aid;

    @FXML
    TableColumn<airlineTable, String> aname;

    @FXML
    TableColumn<airlineTable, String> aalias;

    @FXML
    TableColumn<airlineTable, String> aiata;

    @FXML
    TableColumn<airlineTable, String> aicao;

    @FXML
    TableColumn<airlineTable, String> acallsign;

    @FXML
    TableColumn<airlineTable, String> acountry;

    @FXML
    TableColumn<airlineTable, String> aactive;

    // Airport Table
    @FXML
    TableView<airportTable> airportTableID;

    @FXML
    TableColumn<airportTable, String> apid;

    @FXML
    TableColumn<airportTable, String> apname;

    @FXML
    TableColumn<airportTable, String> apcity;

    @FXML
    TableColumn<airportTable, String> apcountry;

    @FXML
    TableColumn<airportTable, String> apiata;

    @FXML
    TableColumn<airportTable, String> apicao;

    @FXML
    TableColumn<airportTable, String> aplat;

    @FXML
    TableColumn<airportTable, String> aplong;

    @FXML
    TableColumn<airportTable, String> apalt;

    @FXML
    TableColumn<airportTable, String> aptimezone;

    @FXML
    TableColumn<airportTable, String> apdst;

    @FXML
    TableColumn<airportTable, String> aptz;






    //create table data
    final ObservableList<airlineTable> airlineTData = FXCollections.observableArrayList();

    final ObservableList<airlineTable> airportTData = FXCollections.observableArrayList();


    public void initialize(URL location, ResourceBundle resources) {
        aid.setCellValueFactory(new PropertyValueFactory<airlineTable, String>("rid"));
        aname.setCellValueFactory(new PropertyValueFactory<airlineTable, String>("rname"));
        aalias.setCellValueFactory(new PropertyValueFactory<airlineTable, String>("ralias"));
        aiata.setCellValueFactory(new PropertyValueFactory<airlineTable, String>("riata"));
        aicao.setCellValueFactory(new PropertyValueFactory<airlineTable, String>("ricao"));
        acallsign.setCellValueFactory(new PropertyValueFactory<airlineTable, String>("rcallsign"));
        acountry.setCellValueFactory(new PropertyValueFactory<airlineTable, String>("rcountry"));
        aactive.setCellValueFactory(new PropertyValueFactory<airlineTable, String>("ractive"));

        airlineTableID.setItems(airlineTData);




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
