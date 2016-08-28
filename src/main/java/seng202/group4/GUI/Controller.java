package seng202.group4.GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seng202.group4.data.dataType.Airline;
import seng202.group4.data.parser.AirportParser;
import seng202.group4.data.parser.RouteParser;
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

    // Route table

    @FXML
    TableView<routeTable> routeTableID;

    @FXML
    TableColumn<routeTable, String> airline;

    @FXML
    TableColumn<routeTable, Integer> airlineID;

    @FXML
    TableColumn<routeTable, String> source;

    @FXML
    TableColumn<routeTable, Integer> sourceID;

    @FXML
    TableColumn<routeTable, String> dest;

    @FXML
    TableColumn<routeTable, Integer> destID;

    @FXML
    TableColumn<routeTable, String> codeshare;

    @FXML
    TableColumn<routeTable, Integer> stops;

    @FXML
    TableColumn<routeTable, String> equipment;


    // data list
    @FXML
    ListView<String> datalist;

    // search field
    @FXML
    TextField searchField;

    // create table data
    final ObservableList<airlineTable> airlineTData = FXCollections.observableArrayList();

    final ObservableList<airportTable> airportTData = FXCollections.observableArrayList();

    final ObservableList<routeTable> routeTData = FXCollections.observableArrayList();

    final ObservableList<String> items = FXCollections.observableArrayList("Default Airlines", "Default Airports", "Default Routes");



    public void initialize(URL location, ResourceBundle resources) {

        datalist.setItems(items);

        datalist.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
                // action goes here.
                System.out.println("Selected item: " + new_val);
                if (new_val.equals("Default Airlines")) {
                    airlineTableID.toFront();
                } else if (new_val.equals("Default Airports")) {
                    airportTableID.toFront();
                } else if (new_val.equals("Default Routes")) {
                    routeTableID.toFront();
                }

            }
        });

        // initiliase airline table resources
        aid.setCellValueFactory(new PropertyValueFactory<airlineTable, String>("rid"));
        aname.setCellValueFactory(new PropertyValueFactory<airlineTable, String>("rname"));
        aalias.setCellValueFactory(new PropertyValueFactory<airlineTable, String>("ralias"));
        aiata.setCellValueFactory(new PropertyValueFactory<airlineTable, String>("riata"));
        aicao.setCellValueFactory(new PropertyValueFactory<airlineTable, String>("ricao"));
        acallsign.setCellValueFactory(new PropertyValueFactory<airlineTable, String>("rcallsign"));
        acountry.setCellValueFactory(new PropertyValueFactory<airlineTable, String>("rcountry"));
        aactive.setCellValueFactory(new PropertyValueFactory<airlineTable, String>("ractive"));

        airlineTableID.setItems(airlineTData);

        // initiliase airport table resources
        apid.setCellValueFactory(new PropertyValueFactory<airportTable, String>("atid"));
        apname.setCellValueFactory(new PropertyValueFactory<airportTable, String>("atname"));
        apcity.setCellValueFactory(new PropertyValueFactory<airportTable, String>("atcity"));
        apcountry.setCellValueFactory(new PropertyValueFactory<airportTable, String>("atcountry"));
        apiata.setCellValueFactory(new PropertyValueFactory<airportTable, String>("atiata"));
        apicao.setCellValueFactory(new PropertyValueFactory<airportTable, String>("aticao"));
        aplat.setCellValueFactory(new PropertyValueFactory<airportTable, String>("atlatitude"));
        aplong.setCellValueFactory(new PropertyValueFactory<airportTable, String>("atlongitude"));
        apalt.setCellValueFactory(new PropertyValueFactory<airportTable, String>("ataltitude"));
        aptimezone.setCellValueFactory(new PropertyValueFactory<airportTable, String>("attimezone"));
        apdst.setCellValueFactory(new PropertyValueFactory<airportTable, String>("atdst"));
        aptz.setCellValueFactory(new PropertyValueFactory<airportTable, String>("attzdatabase"));

        airportTableID.setItems(airportTData);

        // initiliase route data table resources
        airline.setCellValueFactory(new PropertyValueFactory<routeTable, String>("rairline"));
        airlineID.setCellValueFactory(new PropertyValueFactory<routeTable, Integer>("rid"));
        source.setCellValueFactory(new PropertyValueFactory<routeTable, String>("rsource"));
        sourceID.setCellValueFactory(new PropertyValueFactory<routeTable, Integer>("rsourceid"));
        dest.setCellValueFactory(new PropertyValueFactory<routeTable, String>("rdest"));
        destID.setCellValueFactory(new PropertyValueFactory<routeTable, Integer>("rdestid"));
        codeshare.setCellValueFactory(new PropertyValueFactory<routeTable, String>("rcodeshare"));
        stops.setCellValueFactory(new PropertyValueFactory<routeTable, Integer>("rstops"));
        equipment.setCellValueFactory(new PropertyValueFactory<routeTable, String>("requipment"));

        routeTableID.setItems(routeTData);

        // filtering for airline table
        FilteredList<airlineTable> airlineFiltered = new FilteredList<airlineTable>(airlineTData, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            airlineFiltered.setPredicate(data -> {
                // If filter text is empty, display all data.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every data with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (data.getRcountry().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else if (data.getRname().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });


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
                for(int i = 0; i < airlines.size(); i++) {
                    Airline airline = airlines.get(i);
                    airlineTData.add(new airlineTable(airline.getID(), airline.getName(),
                                     airline.getAlias(), airline.getIATA(),
                                     airline.getICAO(), airline.getCallsign(),
                                     airline.getCountry(), airline.getActive()));
                }

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
            //RouteParser parser = new RouteParser(br);


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
