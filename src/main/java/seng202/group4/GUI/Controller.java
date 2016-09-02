package seng202.group4.GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seng202.group4.data.dataType.Airline;
import seng202.group4.data.dataType.Route;
import seng202.group4.data.parser.AirportParser;
import seng202.group4.data.parser.RouteParser;
import seng202.group4.data.parser.validator.AirlineValidator;
import seng202.group4.data.parser.validator.RouteValidator;
import seng202.group4.data.repository.AirlineRepository;


import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;


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
    TextField airlineSearch;

    // airlines inactive and active check boxes
    @FXML
    CheckBox active;

    @FXML
    CheckBox inactive;

    // airline country filter
    @FXML
    ComboBox airlineCountryFilter;

    // airport FXML
    @FXML
    TextField airportSearch;

    @FXML
    ComboBox airportCountryFilter;

    // route FXML
    @FXML
    TextField routeSearch;

    @FXML
    ComboBox routeDepCountryFilter;

    @FXML
    ComboBox routeDestCountryFilter;

    @FXML
    CheckBox direct;

    @FXML
    CheckBox indirect;

    @FXML
    ComboBox routeEquipFilter;

    // StackPane for search
    @FXML
    StackPane searchPanes;

    @FXML
    AnchorPane airlinePane;

    @FXML
    AnchorPane airportPane;

    @FXML
    AnchorPane routePane;

    // create table data
    private ObservableList<airlineTable> airlineTData = FXCollections.observableArrayList();

    private ObservableList<airportTable> airportTData = FXCollections.observableArrayList();

    private ObservableList<routeTable> routeTData = FXCollections.observableArrayList();

    private ObservableList<String> items = FXCollections.observableArrayList("Default Airlines", "Default Airports", "Default Routes");

    // airline repository
    private Set<String> countrySet = new HashSet<>();

    public void initialize(URL location, ResourceBundle resources) {

        // initialise data list
        datalist.setItems(items);
        // defaults airport list
        datalist.getSelectionModel().clearAndSelect(0);

        datalist.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends  String> ov, String old_val, String new_val) -> {
            System.out.println("Selected item: " + new_val);
            if (new_val.equals("Default Airlines")) {
                airlineTableID.toFront();
                airlinePane.setVisible(true);
                airportPane.setVisible(false);
                routePane.setVisible(false);
            } else if (new_val.equals("Default Airports")) {
                airportTableID.toFront();
                airlinePane.setVisible(false);
                airportPane.setVisible(true);
                routePane.setVisible(false);
            } else if (new_val.equals("Default Routes")) {
                routeTableID.toFront();
                airlinePane.setVisible(false);
                airportPane.setVisible(false);
                routePane.setVisible(true);
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

        searchAirlines();
        //fillCountryBox();

        // loads default airline list
        try {
            loadDefaultAirline();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

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

        //loads default route list
        try {
            loadDefaultRoute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void searchAirlines(){
        // searching for airline
        FilteredList<airlineTable> airlineTableFiltered = new FilteredList<>(airlineTData, p -> true);

        airlineSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            airlineTableFiltered.setPredicate(airline -> {
                // All the columns in airline table (at)
                Integer atID = airline.getRid();
                String atName = airline.getRname();
                String atAlias = airline.getRalias();
                String atIATA = airline.getRiata();
                String atICAO = airline.getRicao();
                String atCallsign = airline.getRcallsign();
                String atCountry = airline.getRcountry();
                Boolean atActive = airline.getRactive();
                Boolean toggled = false;

                String selectedAirlineCountry = null;
                if (airlineCountryFilter.getValue() != null){
                    selectedAirlineCountry= airlineCountryFilter.getValue().toString();
                }


                String lowerCaseFilter = newValue.toLowerCase();

                // Return true if filter matches
                if (newValue.isEmpty() && !active.isSelected() && !inactive.isSelected() &&
                        selectedAirlineCountry != null &&
                        selectedAirlineCountry.equals(" --ALL COUNTRIES-- ")) {
                    return true;                 // If filter text is empty, display all data.
                }

                if (lowerCaseFilter.matches("[0-9]+") && atID == Integer.parseInt(lowerCaseFilter)) {
                    toggled = true;
                }

                if (atName != null && atName.toLowerCase().contains(lowerCaseFilter)){
                    toggled = true;
                }
                if (atAlias != null && atAlias.toLowerCase().contains(lowerCaseFilter)){
                    toggled = true;
                }
                if (atIATA != null && atIATA.toLowerCase().contains(lowerCaseFilter)){
                    toggled = true;
                }
                if (atICAO != null &&  atICAO.toLowerCase().contains(lowerCaseFilter)){
                    toggled = true;
                }
                if (atCallsign != null && atCallsign.toLowerCase().contains(lowerCaseFilter)){
                    toggled = true;
                }
                if (atCountry != null && atCountry.toLowerCase().contains(lowerCaseFilter)){
                    toggled = true;
                }
                if (!active.isSelected() && toggled && !inactive.isSelected()){
                    if (selectedAirlineCountry != null && selectedAirlineCountry.equals(" --ALL COUNTRIES-- ")) {
                        return true;

                    }
                    else if(selectedAirlineCountry != null && atCountry != null &&
                            atCountry.toLowerCase().equals(selectedAirlineCountry.toLowerCase())){
                        return true;
                    }
                    //return true;
                }
                if (active.isSelected() && atActive && toggled){
                    if (selectedAirlineCountry != null && selectedAirlineCountry.equals(" --ALL COUNTRIES-- ")) {
                        return true;

                    }
                    else if(selectedAirlineCountry != null && atCountry != null &&
                            atCountry.toLowerCase().equals(selectedAirlineCountry.toLowerCase())){
                        return true;
                    }

                }
                if (toggled && inactive.isSelected() && !atActive){
                    if (selectedAirlineCountry != null && selectedAirlineCountry.equals(" --ALL COUNTRIES-- ")) {
                        return true;

                    }
                    else if(selectedAirlineCountry != null && atCountry != null &&
                            atCountry.toLowerCase().equals(selectedAirlineCountry.toLowerCase())){
                        return true;
                    }
                }
//                if(atCountry!=null && !selectedAirlineCountry.equals(" --ALL COUNTRIES-- ")){
//                    System.out.println(atCountry.toLowerCase() + " " + (selectedAirlineCountry.toLowerCase()));
//                    if(atCountry.toLowerCase().equals(selectedAirlineCountry.toLowerCase())){
//                        System.out.println("MATCHES!!");
//                    }
//                    //System.out.println(selectedAirlineCountry.toString());
//                    return true;
//                }



//                if (atCountry != null && !airlineCountryFilter.getValue().equals("")){
//                    System.out.println(airlineCountryFilter.getValue() + " " + atCountry);
//                    if (airlineCountryFilter.getValue().equals(atCountry)){
//                        System.out.println("matches " + atCountry);
//                    }
//                    return true;
//                }
                return false;
                //return toggled && inactive.isSelected() && !atActive; // Does not match. false
            });

        });

        // Wrap the filtered list in a SortedList
        SortedList<airlineTable> airlineTableSorted = new SortedList<>(airlineTableFiltered);

        // Bind the SortedList comparator to the TableView comparator
        airlineTableSorted.comparatorProperty().bind(airlineTableID.comparatorProperty());

        // Add sorted (and filtered) data to the table
        airlineTableID.setItems(airlineTableSorted);

    }

    public void updateCountryBox(){
        ArrayList<String> countryArray = new ArrayList<>();
        for (String country: countrySet) {
            if(!airlineCountryFilter.getItems().contains(" --ALL COUNTRIES-- ")){
                airlineCountryFilter.getItems().add(" --ALL COUNTRIES-- ");
            }
            if (country != null && !country.equals("") && !airlineCountryFilter.getItems().contains(country)) {
                airlineCountryFilter.getItems().add(country);
            }
        }

        ObservableList<String> countryItems = airlineCountryFilter.getItems();
        for(String item : countryItems){
            if(item != null) {
                countryArray.add(item.toString());
            }
        }
        airlineCountryFilter.getItems().clear();
        Collections.sort(countryArray);
        for(String c : countryArray){
            airlineCountryFilter.getItems().add(c);
        }

    }

    public void updateAirlineSearch(){
        String text = airlineSearch.getText();
        airlineSearch.setText(text + " ");
        airlineSearch.setText(text);

    }

    public void filterCountry() throws IOException {
        updateAirlineSearch();
    }

    public void selectActiveAirlines() throws IOException {
        updateAirlineSearch();
    }

    public void selectInactiveAirlines() throws IOException {
        updateAirlineSearch();
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
                    countrySet.add(airline.getCountry());
                    updateCountryBox();
                }

            }
    }

    public void loadDefaultAirline() throws IOException, URISyntaxException {

        File file = new File(getClass().getClassLoader().getResource("airlines.dat").toURI());
        if (file != null) {
            System.out.println("file opened yeah~");

            AirlineValidator validator = new AirlineValidator(file);
            ArrayList<Airline> airlines = validator.makeAirlines();
            for(int i = 0; i < airlines.size(); i++) {
                Airline airline = airlines.get(i);
                airlineTData.add(new airlineTable(airline.getID(), airline.getName(),
                        airline.getAlias(), airline.getIATA(),
                        airline.getICAO(), airline.getCallsign(),
                        airline.getCountry(), airline.getActive()));
                countrySet.add(airline.getCountry());
                updateCountryBox();
            }

        }
    }

    public void loadAirport() throws IOException {

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        File file = fileChooser.showOpenDialog(stage);
//        if (file != null) {
//            System.out.println("file opneeeddd");
//
//            AirportValidator validator = new AirportValidator(file);
//            ArrayList<Airport> airports = validator.makeAirports();
//            for(int i = 0; i < airports.size(); i++) {
//                Airport airport = airports.get(i);
//                airportTData.add(new airportTable(airport.getID(), airport.getName(),
//                        airport.getCity(), airport.getCountry(),
//                        airport.getIATA(), airport.getICAO(),
//                        airport.getLatitude(), airport.getLongitude(),
//                        airport.getAltitude(), airport.getTimezone(),
//                        airport.getDST(), airport.getTz()));
//            }
//        }
    }


    public void loadRoute() throws IOException {

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            System.out.println("file opneeeedddd");

            RouteValidator validator = new RouteValidator(file);
            ArrayList<Route> routes = validator.makeroutes();
            for(int i = 0; i < routes.size(); i++) {
                Route route = routes.get(i);
                routeTData.add(new routeTable(route.getAirline(), route.getAirlineID(),
                        route.getSrcAirport(), route.getSrcAirportID(),
                        route.getDestAirport(), route.getDestAirportID(),
                        route.getCodeshare(), route.getStops(),
                        route.getEquipment().stream().collect(Collectors.joining(", "))));
            }
        }
    }

    public void loadDefaultRoute() throws IOException, URISyntaxException {

        File file = new File(getClass().getClassLoader().getResource("routes.dat").toURI());
        if (file != null) {
            System.out.println("file opened oh yeah~");

            RouteValidator validator = new RouteValidator(file);
            ArrayList<Route> routes = validator.makeroutes();
            for(int i = 0; i < routes.size(); i++) {
                Route route = routes.get(i);
                routeTData.add(new routeTable(route.getAirline(), route.getAirlineID(),
                        route.getSrcAirport(), route.getSrcAirportID(),
                        route.getDestAirport(), route.getDestAirportID(),
                        route.getCodeshare(), route.getStops(),
                        route.getEquipment().stream().collect(Collectors.joining(", "))));
            }
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