package seng202.group4.GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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

        // initialise data list
        datalist.setItems(items);

        datalist.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends  String> ov, String old_val, String new_val) -> {
            System.out.println("Selected item: " + new_val);
            if (new_val.equals("Default Airlines")) {
                airlineTableID.toFront();
            } else if (new_val.equals("Default Airports")) {
                airportTableID.toFront();
            } else if (new_val.equals("Default Routes")) {
                routeTableID.toFront();
            }

        });

        // initialise airline table resources
        aid.setCellValueFactory(new PropertyValueFactory<>("rid"));
        aname.setCellValueFactory(new PropertyValueFactory<>("rname"));
        aalias.setCellValueFactory(new PropertyValueFactory<>("ralias"));
        aiata.setCellValueFactory(new PropertyValueFactory<>("riata"));
        aicao.setCellValueFactory(new PropertyValueFactory<>("ricao"));
        acallsign.setCellValueFactory(new PropertyValueFactory<>("rcallsign"));
        acountry.setCellValueFactory(new PropertyValueFactory<>("rcountry"));
        aactive.setCellValueFactory(new PropertyValueFactory<>("ractive"));

        airlineTableID.setItems(airlineTData);

        // searching for airline
        FilteredList<airlineTable> airlineTableFiltered = new FilteredList<>(airlineTData, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            airlineTableFiltered.setPredicate(airline -> {
                // All the columns in airline table (at)
                Integer atID = airline.getRid();
                String atName = airline.getRname();
                String atAlias = airline.getRalias();
                String atIATA = airline.getRiata();
                String atICAO = airline.getRicao();
                String atCallsign = airline.getRcallsign();
                String atCountry = airline.getRcountry();
                //Boolean atActive = airline.getRactive();

                String lowerCaseFilter = newValue.toLowerCase();

                // If filter text is empty, display all data.
                if (newValue.isEmpty()) {
                    return true;
                }

                if (lowerCaseFilter.matches("[0-9]+") && atID == Integer.parseInt(lowerCaseFilter)) {
                    return true; // Filter matches
                }

                if (atName != null && atName.toLowerCase().contains(lowerCaseFilter)){
                    return true; // Filter matches
                }
                if (atAlias != null && atAlias.toLowerCase().contains(lowerCaseFilter)){
                    return true; // Filter matches
                }
                if (atIATA != null && atIATA.toLowerCase().contains(lowerCaseFilter)){
                    return true; // Filter matches
                }
                if (atICAO != null &&  atICAO.toLowerCase().contains(lowerCaseFilter)){
                    return true; // Filter matches
                }
                if (atCallsign != null && atCallsign.toLowerCase().contains(lowerCaseFilter)){
                    return true; // Filter matches
                }
                if (atCountry != null && atCountry.toLowerCase().contains(lowerCaseFilter)){
                    return true; // Filter matches
                }

                return false; // Does not match.
            });

        });

        // Wrap the filtered list in a SortedList
        SortedList<airlineTable> airlineTableSorted = new SortedList<>(airlineTableFiltered);

        // Bind the SortedList comparator to the TableView comparator
        airlineTableSorted.comparatorProperty().bind(airlineTableID.comparatorProperty());

        // Add sorted (and filtered) data to the table
        airlineTableID.setItems(airlineTableSorted);

        System.out.println("added!");









        // initialise airport table resources
        apid.setCellValueFactory(new PropertyValueFactory<>("atid"));
        apname.setCellValueFactory(new PropertyValueFactory<>("atname"));
        apcity.setCellValueFactory(new PropertyValueFactory<>("atcity"));
        apcountry.setCellValueFactory(new PropertyValueFactory<>("atcountry"));
        apiata.setCellValueFactory(new PropertyValueFactory<>("atiata"));
        apicao.setCellValueFactory(new PropertyValueFactory<>("aticao"));
        aplat.setCellValueFactory(new PropertyValueFactory<>("atlatitude"));
        aplong.setCellValueFactory(new PropertyValueFactory<>("atlongitude"));
        apalt.setCellValueFactory(new PropertyValueFactory<>("ataltitude"));
        aptimezone.setCellValueFactory(new PropertyValueFactory<>("attimezone"));
        apdst.setCellValueFactory(new PropertyValueFactory<>("atdst"));
        aptz.setCellValueFactory(new PropertyValueFactory<>("attzdatabase"));

        airportTableID.setItems(airportTData);

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

        routeTableID.setItems(routeTData);





    }

    public void search() throws IOException {
        String query = searchField.getText();
        System.out.println("GO!" + query);

        if (searchField == null || query.isEmpty()) {
            System.out.println("nothng search");
            airlineTableID.setItems(airlineTData);
        }
        else {
            System.out.println("better search");
            String queryLower = query.toLowerCase();

            for (airlineTable airline: airlineTableID.getItems()) {

                String filterCountry = airline.getRcountry();
                System.out.println(airline.getRid() + filterCountry);
                if (filterCountry == null || filterCountry.equals("null")) {
                    System.out.println("NULL COUNTRY");
                }
                else if(filterCountry.toLowerCase().contains(queryLower)) {
                    System.out.println("inside seachlol");
                    System.out.println(airline.getRname() + " " + filterCountry);
                }

            }
        }
    }

    public void searchAirline(String oldValue, String newValue) {
        System.out.println("GO!" + searchField.getText());

        // searching for airline
        ObservableList<airlineTable> filteredList = FXCollections.observableArrayList();
        if(searchField == null || (newValue.length() < oldValue.length()) || newValue.isEmpty()) {
            airlineTableID.setItems(airlineTData);
        }
        else {
            newValue = newValue.toUpperCase();
            for(airlineTable airline : airlineTableID.getItems()) {
                String filterFirstName = airline.getRcountry();
                String filterLastName = airline.getRname();
                if(filterFirstName.toUpperCase().contains(newValue) || filterLastName.toUpperCase().contains(newValue)) {
                    filteredList.add(airline);
                }
            }
            airlineTableID.setItems(filteredList);
        }
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
