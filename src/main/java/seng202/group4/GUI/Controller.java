package seng202.group4.GUI;


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
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seng202.group4.data.dataType.*;
import seng202.group4.data.parser.FlightParser;
import seng202.group4.data.parser.validator.AirlineValidator;
import seng202.group4.data.parser.validator.AirportValidator;
import seng202.group4.data.parser.validator.FlightValidator;
import seng202.group4.data.parser.validator.RouteValidator;
import seng202.group4.data.repository.AirlineRepository;
import seng202.group4.data.repository.AirportRepository;
import seng202.group4.data.repository.FlightRepository;
import seng202.group4.data.repository.RouteRepository;


import javax.swing.*;
import javax.swing.text.html.Option;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;


public class Controller implements Initializable{

    // Map view
    @FXML
    WebView mapView;

    @FXML
    WebView flightMap;

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

    // Flight table

    @FXML
    TextField flightNameSearch;

    @FXML
    TableView<flightTable> flightTableID;

    @FXML
    TableColumn<flightTable, String> flightID;

    @FXML
    TableColumn<flightTable, String> flightType;

    @FXML
    TableColumn<flightTable, Integer> flightAltitude;

    @FXML
    TableColumn<flightTable, Double> flightLatitude;

    @FXML
    TableColumn<flightTable, Double> flightLongitude;

    @FXML
    ListView<String> flightList;



    // create table data
    private ObservableList<airlineTable> airlineTData = FXCollections.observableArrayList();

    private ObservableList<airportTable> airportTData = FXCollections.observableArrayList();

    private ObservableList<routeTable> routeTData = FXCollections.observableArrayList();

    private ObservableList<flightTable> flightTData = FXCollections.observableArrayList();

    private ObservableList<String> items = FXCollections.observableArrayList("Default Airlines", "Default Airports", "Default Routes");

    // testing for flight
    private ObservableList<String> flightItems = FXCollections.observableArrayList();

    // data repositories
    private AirlineRepository airlineRepository = new AirlineRepository();
    private AirportRepository airportRepository = new AirportRepository();
    private RouteRepository routeRepository = new RouteRepository();
    private FlightRepository flightRepository = new FlightRepository();

    // initial combobox names
    private String allCountriesTag = " --ALL COUNTRIES-- ";
    private String allEquipmentsTag = " --ALL EQUIPMENTS-- ";
    private String departureCountryTag = " --DEPART FROM-- ";
    private String destinationCountryTag = " --ARRIVE TO-- ";

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

    public void initialize(URL location, ResourceBundle resources) {


        flightMap.getEngine().load(getClass().getClassLoader().getResource("map.html").toExternalForm());
        // initialise data list
        datalist.setItems(items);

        // setting up view
        updateAirlineSearch();
        updateAirportSearch();
        updateRouteSearch();
        updateFlightNameSearch();
        // select airline table on the side bar
        datalist.getSelectionModel().clearAndSelect(0);
        // show airline table
        airlineTableID.toFront();

        // listen to whats being selected in the flight list
        flightList.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends  String> ov, String old_val, String new_val) -> {
            System.out.println("Selected item from flight list: " + new_val);

            if(new_val != null){
                // clear table and populate it again with what's selected
                updateFlightTable(new_val.toLowerCase());
                showFlightPath(new_val.toLowerCase());
            }

        });

        searchFlightNames();

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

        // loads default airline list
        try {
            loadDefaultAirline();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // listen for airline search queries
        searchAirlines();

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

        try {
            loadDefaultAirports();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // listen for airports search queries
        searchAirports();

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

        // initialise route data table resources
        flightID.setCellValueFactory(new PropertyValueFactory<>("fid"));
        flightType.setCellValueFactory(new PropertyValueFactory<>("ftype"));
        flightAltitude.setCellValueFactory(new PropertyValueFactory<>("faltitude"));
        flightLatitude.setCellValueFactory(new PropertyValueFactory<>("flatitude"));
        flightLongitude.setCellValueFactory(new PropertyValueFactory<>("flongitude"));

        flightTableID.setItems(flightTData);

        // listen for route search queries
        searchRoutes();


    }

    private void searchFlightNames(){
        //String intPattern = "[-]?[0-9]*[.]?[0-9]+";
        // searching for airline
        FilteredList<flightTable> flightTableFiltered = new FilteredList<>(flightTData, p -> true);

        flightNameSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            if(!newValue.isEmpty()){
                //clear the table
                flightTData.clear();
                //update the data list if it matches the string
                ObservableList<String> tempNames = FXCollections.observableArrayList();
                //ArrayList<String> tempNames = new ArrayList<>();
                for(String s: flightItems){
                    if(s.toLowerCase().contains(newValue.toLowerCase())){
                        tempNames.add(s);
                    }
                }
                flightList.setItems(tempNames);
            }
            else{
                flightList.setItems(flightItems);
            }
        });
    }

    private void searchAirports(){
        String intPattern = "[-]?[0-9]*[.]?[0-9]+";
        // searching for airline
        FilteredList<airportTable> airportTableFiltered = new FilteredList<>(airportTData, p -> true);

        airportSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            airportTableFiltered.setPredicate(airport -> {
                // All the columns in airport table (at)
                int atID = airport.getAtid();
                String atName = airport.getAtname();
                String atCity = airport.getAtcity();
                String atCountry = airport.getAtcountry();
                String atIATA = airport.getAtiata(); // FAA if the country is USA
                String atICAO = airport.getAticao();
                Double atLatitude = airport.getAtlatitude();
                Double atLongitude = airport.getAtlongitude();
                Double atAltitude = airport.getAtaltitude();
                Float atTimezone = airport.getAttimezone();
                String atDST = airport.getAtdst();
                String atTz = airport.getAttzdatabase();
                boolean toggled = false;    // toggle to see if anything was matched in the search box

                // set up for country drop down box
                boolean emptyCountryFilter = airportCountryFilter.getSelectionModel().getSelectedItem() == null;
                // selectedAirportCountry is a string to hold whats selected in the dropdown
                // set to null unless a dropdown is selected
                String selectedAirportCountry = null;
                if (airportCountryFilter.getValue() != null){
                    selectedAirportCountry = airportCountryFilter.getValue().toString();
                }

                // hold the string that's typed in the search bar in lowercase
                String lowerCaseFilter = newValue.toLowerCase();

                // The following returns true if the filter matches
                if (newValue.isEmpty() && selectedAirportCountry != null && selectedAirportCountry.equals(allCountriesTag)){
                    return true; // display all data
                }

                // Check if the search criteria matches the following columns
                if ((lowerCaseFilter.matches("[0-9]+") && atID == Integer.parseInt(lowerCaseFilter)) ||
                        (atName != null && atName.toLowerCase().contains(lowerCaseFilter)) ||
                        (atCity != null && atCity.toLowerCase().contains(lowerCaseFilter)) ||
                        (atCountry != null && atCountry.toLowerCase().contains(lowerCaseFilter)) ||
                        (atIATA != null && atIATA.toLowerCase().contains(lowerCaseFilter)) ||
                        (atICAO != null &&  atICAO.toLowerCase().contains(lowerCaseFilter)) ||
                        (lowerCaseFilter.matches(intPattern) && atLatitude == Double.parseDouble(lowerCaseFilter)) || // CAN BE IMPROVED
                        (lowerCaseFilter.matches(intPattern) && atLongitude == Double.parseDouble(lowerCaseFilter)) || // CAN BE IMPROVED
                        (lowerCaseFilter.matches(intPattern) && atAltitude == Double.parseDouble(lowerCaseFilter)) || // CAN BE IMPROVED
                        (lowerCaseFilter.matches(intPattern) && atTimezone == Float.parseFloat(lowerCaseFilter)) ||
                        (atDST != null &&  atDST.toLowerCase().contains(lowerCaseFilter)) ||
                        (atTz != null &&  atTz.toLowerCase().contains(lowerCaseFilter))) {
                    toggled = true;
                }

                // For the following case, return true if the country dropdown is empty, --ALL COUNTRIES-- or
                // matches the country in the table
                if (toggled) {
                    if (emptyCountryFilter){
                        return true;
                    }
                    if (selectedAirportCountry != null && selectedAirportCountry.equals(allCountriesTag)) {
                        return true;
                    }
                    else if(selectedAirportCountry != null && atCountry != null &&
                            atCountry.toLowerCase().equals(selectedAirportCountry.toLowerCase())){
                        return true;
                    }
                }
                return false;
            });

        });

        // Wrap the filtered list in a SortedList
        SortedList<airportTable> airportTableSorted = new SortedList<>(airportTableFiltered);

        // Bind the SortedList comparator to the TableView comparator
        airportTableSorted.comparatorProperty().bind(airportTableID.comparatorProperty());

        // Add sorted (and filtered) data to the table
        airportTableID.setItems(airportTableSorted);

    }

    // update map with flight path given the flight name
    private void showFlightPath(String flightName){
        //String flightName = "lol";

        if (flightMap.getEngine() != null) {
            System.out.println("flgihthhhs!!!");
            flightMap.getEngine().executeScript("deleteFlights();");
            // get the flight that is selected so we can get the flight name whic is a key to the flight repo
            // so then we can get flight (info) now we have it yay its called flight
            Flight flight = flightRepository.getFlights().get(flightName);
            // flight actually has an array list of positions and positions= array listof FLIGHT POSITISONS
            // which is another object =.=

            for (int i = 0; i < flight.getFlightPositions().size(); i++) {
                // the first row would be like
                FlightPosition firstRow = flight.getFlightPositions().get(i);

                // lat long first row
                double lat = firstRow.getLatitude();
                double lon = firstRow.getLongitude();

                flightMap.getEngine().executeScript("addFlight(" + lat + ", " + lon + ");");
            }
            flightMap.getEngine().executeScript("makePath();");
            System.out.println("i survived");
        }
    }

    private void searchRoutes(){
        // searching for route
        FilteredList<routeTable> routeTableFiltered = new FilteredList<>(routeTData, p -> true);

        routeSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            routeTableFiltered.setPredicate(route -> {
                // All the columns in route table (rt)
                String rtAirID = route.getRairline();
                String rtID = route.getRid();
                String rtSrcAirport = route.getRsource();
                String rtSrcAirportId = route.getRsourceid();
                String rtDestAirport = route.getRdest();
                String rtDestAirportId = route.getRdestid();
                String rtCodeshare = route.getRcodeshare();
                String rtStops = route.getRstops();
                String rtEquipment = route.getRequipment();
                List<String> rtEquipmentArray = null;
                Boolean toggled = false; // toggle to see if anything was matched in the search box

                // set up for equipment drop down box
                boolean emptyEquipFilter = routeEquipFilter.getSelectionModel().getSelectedItem() == null;
                // selectedRouteEquip is a string to hold whats selected in the dropdown
                // set to null unless a dropdown is selected
                String selectedRouteEquip = null;
                if (routeEquipFilter.getValue() != null) {
                    rtEquipmentArray = Arrays.asList(rtEquipment.toLowerCase().split("\\s*,\\s*"));
                    selectedRouteEquip = routeEquipFilter.getValue().toString();
                }

                // set up for departure country drop down box
                boolean emptyDepartFilter = routeDepCountryFilter.getSelectionModel().getSelectedItem() == null;
                // selectedAirportCountry is a string to hold whats selected in the dropdown
                // set to null unless a dropdown is selected
                String selectedDepartCountry = null;
                if (routeDepCountryFilter.getValue() != null){
                    selectedDepartCountry = routeDepCountryFilter.getValue().toString();
                }

                // set up for destination country drop down box
                boolean emptyDestFilter = routeDestCountryFilter.getSelectionModel().getSelectedItem() == null;
                // selectedAirportCountry is a string to hold whats selected in the dropdown
                // set to null unless a dropdown is selected
                String selectedDestCountry = null;
                if (routeDestCountryFilter.getValue() != null){
                    selectedDestCountry = routeDestCountryFilter.getValue().toString();
                }

                // hold the string that's typed in the search bar in lowercase
                String lowerCaseFilter = newValue.toLowerCase();

                // The following returns true if the filter matches
                // base case
                if (newValue.isEmpty() && !direct.isSelected() && !indirect.isSelected()
                        && selectedRouteEquip != null && selectedRouteEquip.equals(allCountriesTag)
                        && selectedDepartCountry.equals(departureCountryTag)
                        && selectedDestCountry.equals(destinationCountryTag)) {
                    return true;    // display all data.
                }

                // Check if the search criteria matches the following columns
                if ((rtAirID != null && rtAirID.toLowerCase().contains(lowerCaseFilter) ||
                        (rtID != null && rtID.toLowerCase().contains(lowerCaseFilter)) ||
                        (rtSrcAirport != null && rtSrcAirport.toLowerCase().contains(lowerCaseFilter)) ||
                        (rtSrcAirportId != null && rtSrcAirportId.toLowerCase().contains(lowerCaseFilter)) ||
                        (rtDestAirport != null && rtDestAirport.toLowerCase().contains(lowerCaseFilter)) ||
                        (rtDestAirportId != null && rtDestAirportId.toLowerCase().contains(lowerCaseFilter)) ||
                        (rtCodeshare != null && rtCodeshare.toLowerCase().contains(lowerCaseFilter)) ||
                        (rtStops != null && rtStops.toLowerCase().contains(lowerCaseFilter)) ||
                        (rtEquipment != null && rtEquipment.toLowerCase().contains(lowerCaseFilter)))){
                    toggled = true;
                }

                // For the following cases, return true if the equipment dropdown is empty, --ALL EQUIPMENTS-- or
                // matches the equipment in the table
                if ((toggled && !direct.isSelected() && !indirect.isSelected()) ||
                        (direct.isSelected() && rtStops != null && rtStops.equals("0") && toggled) ||
                        (indirect.isSelected() && rtStops != null && !rtStops.equals("0") && toggled)){
                    boolean filterChecked = filterEquipDepDest(emptyEquipFilter, emptyDepartFilter, emptyDestFilter,
                            selectedRouteEquip, selectedDepartCountry, selectedDestCountry, rtEquipmentArray,
                             rtDestAirportId,  rtSrcAirportId);
                    if(filterChecked){
                        return true;
                    }
                }
                return false;   // does not match
            });

        });

        // Wrap the filtered list in a SortedList
        SortedList<routeTable> routeTableSorted = new SortedList<>(routeTableFiltered);

        // Bind the SortedList comparator to the TableView comparator
        routeTableSorted.comparatorProperty().bind(routeTableID.comparatorProperty());

        // Add sorted (and filtered) data to the table
        routeTableID.setItems(routeTableSorted);

    }

    private boolean filterEquipDepDest(boolean emptyEquipFilter, boolean emptyDepartFilter, boolean emptyDestFilter,
                                       String selectedRouteEquip, String selectedDepartCountry,
                                       String selectedDestCountry, List<String> rtEquipmentArray,
                                       String rtDestAirportId, String rtSrcAirportId){
        // empty cases when no filters are selected
        if ((emptyEquipFilter && emptyDepartFilter && emptyDestFilter) || (selectedRouteEquip != null
                && selectedRouteEquip.equals(allEquipmentsTag) && selectedDepartCountry != null &&
                selectedDepartCountry.equals(departureCountryTag) && selectedDestCountry != null
                && selectedDestCountry.equals(destinationCountryTag))){
            return true;
        }

        // if only one filter is selected
        // filter only equipment
        if(((emptyDepartFilter && emptyDestFilter) || (selectedDepartCountry != null &&
                selectedDepartCountry.equals(departureCountryTag) && selectedDestCountry != null
                && selectedDestCountry.equals(destinationCountryTag))) && selectedRouteEquip != null){
            if(selectedRouteEquip.equals(allEquipmentsTag)){
                return true;
            }
            else if(rtEquipmentArray.contains(selectedRouteEquip.toLowerCase())){
                return true;
            }
        }
        // filter only destination country
        else if(((emptyEquipFilter && emptyDepartFilter) || (selectedRouteEquip != null
                && selectedRouteEquip.equals(allEquipmentsTag) && selectedDepartCountry != null &&
                selectedDepartCountry.equals(departureCountryTag))) &&
                selectedDestCountry != null){
            if(selectedDestCountry.equals(destinationCountryTag)){
                return true;
            }
            else if(!rtDestAirportId.equals("null") &&
                    airportRepository.airportIDsOfCountry(selectedDestCountry.toLowerCase()).contains(Integer.parseInt(rtDestAirportId))){
                return true;
            }
        }
        // filter only departure country
        else if(((emptyEquipFilter && emptyDestFilter) || (selectedRouteEquip != null
                && selectedRouteEquip.equals(allEquipmentsTag) && selectedDestCountry != null &&
                selectedDestCountry.equals(destinationCountryTag))) &&
                selectedDepartCountry != null){
            if(selectedDepartCountry.equals(departureCountryTag)){
                return true;
            }
            else if(!rtSrcAirportId.equals("null") &&
                    airportRepository.airportIDsOfCountry(selectedDepartCountry.toLowerCase()).contains(Integer.parseInt(rtSrcAirportId))){
                return true;
            }
        }

        // if two filters are selected
        // filter equipment and destination country
        else if((emptyDepartFilter || (selectedDepartCountry != null &&
                selectedDepartCountry.equals(departureCountryTag))) && selectedDestCountry != null &&
                selectedRouteEquip != null){
            if(selectedDestCountry.equals(destinationCountryTag) && selectedRouteEquip.equals(allEquipmentsTag)){
                return true;
            }
            else if(selectedDestCountry.equals(destinationCountryTag) &&
                    rtEquipmentArray.contains(selectedRouteEquip.toLowerCase())){
                return true;
            }
            else if(selectedRouteEquip.equals(allEquipmentsTag) && !rtDestAirportId.equals("null") &&
                    airportRepository.airportIDsOfCountry(selectedDestCountry.toLowerCase()).contains(Integer.parseInt(rtDestAirportId))){
                return true;
            }
            else if(rtEquipmentArray.contains(selectedRouteEquip.toLowerCase()) &&
                    !rtDestAirportId.equals("null") &&
                    airportRepository.airportIDsOfCountry(selectedDestCountry.toLowerCase()).contains(Integer.parseInt(rtDestAirportId))){
                return true;
            }
        }
        //filter equipment and departure country
        else if((emptyDestFilter || (selectedDestCountry != null &&
                selectedDestCountry.equals(destinationCountryTag))) && selectedDepartCountry != null &&
                selectedRouteEquip != null){
            if(selectedDepartCountry.equals(departureCountryTag) && selectedRouteEquip.equals(allEquipmentsTag)){
                return true;
            }
            else if(selectedDepartCountry.equals(departureCountryTag) &&
                    rtEquipmentArray.contains(selectedRouteEquip.toLowerCase())){
                return true;
            }
            else if(selectedRouteEquip.equals(allEquipmentsTag) && !rtSrcAirportId.equals("null") &&
                    airportRepository.airportIDsOfCountry(selectedDepartCountry.toLowerCase()).contains(Integer.parseInt(rtSrcAirportId))){
                return true;
            }
            else if(rtEquipmentArray.contains(selectedRouteEquip.toLowerCase()) &&
                    !rtSrcAirportId.equals("null") &&
                    airportRepository.airportIDsOfCountry(selectedDepartCountry.toLowerCase()).contains(Integer.parseInt(rtSrcAirportId))){
                return true;
            }
        }
        //filter departure country and destination country
        else if((emptyEquipFilter || (selectedRouteEquip != null &&
                selectedRouteEquip.equals(allEquipmentsTag))) && selectedDepartCountry != null &&
                selectedDestCountry != null){
            if(selectedDepartCountry.equals(departureCountryTag) &&
                    selectedDestCountry.equals(destinationCountryTag)){
                return true;
            }
            else if(selectedDepartCountry.equals(departureCountryTag) && !rtDestAirportId.equals("null") &&
                    airportRepository.airportIDsOfCountry(selectedDestCountry.toLowerCase()).contains(Integer.parseInt(rtDestAirportId))){
                return true;
            }
            else if(selectedDestCountry.equals(destinationCountryTag) && !rtSrcAirportId.equals("null") &&
                    airportRepository.airportIDsOfCountry(selectedDepartCountry.toLowerCase()).contains(Integer.parseInt(rtSrcAirportId))){
                return true;
            }
            else if(!rtDestAirportId.equals("null") &&
                    airportRepository.airportIDsOfCountry(selectedDestCountry.toLowerCase()).contains(Integer.parseInt(rtDestAirportId))
                    && !rtSrcAirportId.equals("null") &&
                    airportRepository.airportIDsOfCountry(selectedDepartCountry.toLowerCase()).contains(Integer.parseInt(rtSrcAirportId))){
                return true;
            }
        }

        // if three filters are selected
        // filter destination/departure country and equipment
        else if(selectedDepartCountry != null && selectedDestCountry != null && selectedRouteEquip != null){
            if(!rtDestAirportId.equals("null") &&
                    airportRepository.airportIDsOfCountry(selectedDestCountry.toLowerCase()).contains(Integer.parseInt(rtDestAirportId))
                    && !rtSrcAirportId.equals("null") &&
                    airportRepository.airportIDsOfCountry(selectedDepartCountry.toLowerCase()).contains(Integer.parseInt(rtSrcAirportId))
                    && rtEquipmentArray.contains(selectedRouteEquip.toLowerCase())){
                return true;
            }

        }
        return false;
    }


    private void searchAirlines(){
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
                boolean toggled = false;    // toggle to see if anything was matched in the search box

                // set up for country drop down box
                boolean emptyCountryFilter = airlineCountryFilter.getSelectionModel().getSelectedItem() == null;
                // selectedAirlineCountry is a string to hold whats selected in the dropdown
                // set to null unless a dropdown is selected
                String selectedAirlineCountry = null;
                if (airlineCountryFilter.getValue() != null){
                    selectedAirlineCountry = airlineCountryFilter.getValue().toString();
                }

                // hold the string that's typed in the search bar in lowercase
                String lowerCaseFilter = newValue.toLowerCase();

                // The following returns true if the filter matches
                if (newValue.isEmpty() && !active.isSelected() && !inactive.isSelected()
                        && selectedAirlineCountry != null && selectedAirlineCountry.equals(allCountriesTag)){
                    return true; // display all data
                }

                // Check if the search criteria matches the following columns
                if ((lowerCaseFilter.matches("[0-9]+") && atID == Integer.parseInt(lowerCaseFilter)) ||
                        (atName != null && atName.toLowerCase().contains(lowerCaseFilter)) ||
                        (atAlias != null && atAlias.toLowerCase().contains(lowerCaseFilter)) ||
                        (atIATA != null && atIATA.toLowerCase().contains(lowerCaseFilter)) ||
                        (atICAO != null &&  atICAO.toLowerCase().contains(lowerCaseFilter)) ||
                        (atCallsign != null && atCallsign.toLowerCase().contains(lowerCaseFilter)) ||
                        (atCountry != null && atCountry.toLowerCase().contains(lowerCaseFilter))) {
                    toggled = true;
                }

                // For the following cases, return true if the country dropdown is empty, --ALL COUNTRIES-- or
                // matches the country in the table
                if ((!active.isSelected() && toggled && !inactive.isSelected()) ||
                        (toggled && active.isSelected() && atActive) ||
                        (toggled && inactive.isSelected() && !atActive)){
                    if (emptyCountryFilter){
                        return true;
                    }
                    if (selectedAirlineCountry != null && selectedAirlineCountry.equals(allCountriesTag)) {
                        return true;
                    }
                    else if(selectedAirlineCountry != null && atCountry != null &&
                            atCountry.toLowerCase().equals(selectedAirlineCountry.toLowerCase())){
                        return true;
                    }
                }
                return false; // does not match
            });

        });

        // Wrap the filtered list in a SortedList
        SortedList<airlineTable> airlineTableSorted = new SortedList<>(airlineTableFiltered);

        // Bind the SortedList comparator to the TableView comparator
        airlineTableSorted.comparatorProperty().bind(airlineTableID.comparatorProperty());

        // Add sorted (and filtered) data to the table
        airlineTableID.setItems(airlineTableSorted);

    }



    private void updateAirlineCountryBox(){
        // clear the current combo box
        airlineCountryFilter.getItems().clear();
        // if the combo box doesn't have --ALL COUNTRIES-- then add one
        if(!airlineCountryFilter.getItems().contains(allCountriesTag)){
            airlineCountryFilter.getItems().add(allCountriesTag);
        }

        // loop through the current airlineCountrySet
        Iterator itr = airlineCountrySet.iterator();
        while(itr.hasNext()){
            airlineCountryFilter.getItems().add(itr.next());
        }
    }

    private void updateAirportCountryBox() {
        airportCountryFilter.getItems().clear();
        if (!airportCountryFilter.getItems().contains(allCountriesTag)) {
            airportCountryFilter.getItems().add(allCountriesTag);
        }
        Iterator itr = airportCountrySet.iterator();
        while(itr.hasNext()) {
            airportCountryFilter.getItems().add(itr.next());
        }
    }


    private void updateDepCountryBox() {
        routeDepCountryFilter.getItems().clear();
        if (!routeDepCountryFilter.getItems().contains(departureCountryTag)) {
            routeDepCountryFilter.getItems().add(departureCountryTag);
        }
        Iterator itr = depSet.iterator();
        while(itr.hasNext()) {
            routeDepCountryFilter.getItems().add(itr.next());
        }
    }

    private void updateDestCountryBox() {
        routeDestCountryFilter.getItems().clear();
        if (!routeDestCountryFilter.getItems().contains(destinationCountryTag)) {
            routeDestCountryFilter.getItems().add(destinationCountryTag);
        }
        Iterator itr = destSet.iterator();
        while(itr.hasNext()) {
            routeDestCountryFilter.getItems().add(itr.next());
        }
    }


    private void updateEquipBox(){
        // clear the current combo box
        routeEquipFilter.getItems().clear();
        // if the combo box doesn't have --ALL EQUIPMENTS-- then add one
        if(!routeEquipFilter.getItems().contains(allEquipmentsTag)){
            routeEquipFilter.getItems().add(allEquipmentsTag);
        }

        // loop through the current airlineCountrySet
        Iterator itr = equipmentSet.iterator();
        while(itr.hasNext()){
            routeEquipFilter.getItems().add(itr.next());
        }
    }

    private void updateAirlineSearch(){
        String text = airlineSearch.getText();
        airlineSearch.setText(text + " ");
        airlineSearch.setText(text);

    }

    private void updateAirportSearch(){
        String text = airportSearch.getText();
        airportSearch.setText(text + " ");
        airportSearch.setText(text);

    }

    private void updateRouteSearch(){
        String text = routeSearch.getText();
        routeSearch.setText(text + " ");
        routeSearch.setText(text);

    }

    private void updateFlightNameSearch(){
        String text = flightNameSearch.getText();
        flightNameSearch.setText(text + " ");
        flightNameSearch.setText(text);
    }


    public void selectDirect() throws IOException {
        updateRouteSearch();
    }

    public void selectIndirect() throws IOException {
        updateRouteSearch();
    }

    public void filterAirlineCountry() throws IOException {
        updateAirlineSearch();
    }

    public void filterAirportCountry() throws IOException {
        updateAirportSearch();
    }

    public void filterDepCountry() throws IOException {
        updateRouteSearch();
    }

    public void filterDestCountry() throws IOException {
        updateRouteSearch();
    }

    public void filterEquipment() throws IOException {
        updateRouteSearch();
    }

    public void selectActiveAirlines() throws IOException {
        updateAirlineSearch();
    }

    public void selectInactiveAirlines() throws IOException {
        updateAirlineSearch();
    }

    // Insert the airlines in a given file into the airline table GUI checking for duplicates
    private void insertAirlineTable(InputStream file) throws IOException {
        AirlineValidator validator = new AirlineValidator(file);
        ArrayList<Airline> airlines = validator.makeAirlines();
        validator = null;
        if(airlines != null){
            for(int i = 0; i < airlines.size(); i++) {
                Airline airline = airlines.get(i);
                // if the airline ID already exists in the repository, warn the user
                if(!airlineRepository.getAirlines().containsKey(airline.getID())){
                    airlineRepository.addAirline(airline);
                    airlineTData.add(new airlineTable(airline.getID(), airline.getName(),
                            airline.getAlias(), airline.getIATA(),
                            airline.getICAO(), airline.getCallsign(),
                            airline.getCountry(), airline.getActive()));
                    if(airline.getCountry() != null){
                        airlineCountrySet.add(airline.getCountry());
                    }
                }
                else{
                    duplicateIDAlert("Please fix the conflict and reupload the file.", airline.getID());
                    break;
                }
                updateAirlineCountryBox();
            }
        }
    }

    // Insert the airlines in a given file into the airline table GUI
    private void insertEmptyAirlineTable(InputStream file) throws IOException {

        AirlineValidator validator = new AirlineValidator(file);
        ArrayList<Airline> airlines = validator.makeAirlines();
        validator = null;
        if(airlines != null){
            for(int i = 0; i < airlines.size(); i++) {
                Airline airline = airlines.get(i);
                airlineRepository.addAirline(airline);
                airlineTData.add(new airlineTable(airline.getID(), airline.getName(),
                        airline.getAlias(), airline.getIATA(),
                        airline.getICAO(), airline.getCallsign(),
                        airline.getCountry(), airline.getActive()));
                if(airline.getCountry() != null){
                    airlineCountrySet.add(airline.getCountry());
                }
            }
            updateAirlineCountryBox();
        }

    }



    public void loadAirline() throws IOException {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        File in = fileChooser.showOpenDialog(stage);
        InputStream file = new FileInputStream(in);
        if (in.exists()) {
            System.out.println("file opneedd");
            insertAirlineTable(file);
        }
    }

    private void loadDefaultAirline() throws IOException, URISyntaxException {
        InputStream file = getClass().getResourceAsStream("/airlines.dat");
        //File file = new File(getClass().getClassLoader().getResource("airlines.dat").toURI());
        if (file != null) {
            System.out.println("file opened yeah~");
            insertEmptyAirlineTable(file);
        }
    }



    private void insertAirportTable(InputStream file) throws IOException {
        AirportValidator validator = new AirportValidator(file);
        ArrayList<Airport> airports = validator.makeAirports();
        validator = null;
        if(airports != null){
            for(int i = 0; i < airports.size(); i++) {
                Airport airport = airports.get(i);
                if(!airportRepository.getAirports().containsKey(airport.getID())){
                    airportRepository.addAirport(airport);
                    airportTData.add(new airportTable(airport.getID(), airport.getName(), airport.getCity(),
                            airport.getCountry(), airport.getIATA(), airport.getICAO(), airport.getLatitude(),
                            airport.getLongitude(), airport.getAltitude(), airport.getTimezone(), airport.getDST().toText(),
                            airport.getTz()));
                    if(airport.getCountry() != null) {
                        airportCountrySet.add(airport.getCountry());
                    }
                }
                else{
                    duplicateIDAlert("Please fix the conflict and reupload the file.", airport.getID());
                    break;
                }
                updateAirportCountryBox();
            }
        }
    }

    private void insertEmptyAirportTable(InputStream file) throws IOException {
        AirportValidator validator = new AirportValidator(file);
        ArrayList<Airport> airports = validator.makeAirports();
        validator = null;
        if(airports != null){
            for(int i = 0; i < airports.size(); i++) {
                Airport airport = airports.get(i);
                airportRepository.addAirport(airport);
                airportTData.add(new airportTable(airport.getID(), airport.getName(), airport.getCity(),
                        airport.getCountry(), airport.getIATA(), airport.getICAO(), airport.getLatitude(),
                        airport.getLongitude(), airport.getAltitude(), airport.getTimezone(), airport.getDST().toText(),
                        airport.getTz()));
                if(airport.getCountry() != null) {
                    airportCountrySet.add(airport.getCountry());
                }
                updateAirportCountryBox();
            }
        }

    }


    public void loadAirport() throws IOException {

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        File in = fileChooser.showOpenDialog(stage);
        InputStream file = new FileInputStream(in);
        if (in.exists()) {
            System.out.println("file opened oh ye bb~");
            insertAirportTable(file);
        }
    }

    private void loadDefaultAirports() throws IOException, URISyntaxException {
        InputStream file = getClass().getResourceAsStream("/airports.dat");
        //File file = new File(getClass().getClassLoader().getResource("airports.dat").toURI());
        if (file != null) {
            System.out.println("ooooooooh yyyyyyyyyyahhhhhh");
            insertEmptyAirportTable(file);
            //System.out.println("size of re" + airportRepository.getAirports().size());
        }
    }

    // insert the given routes in a file into route table and checks for duplicates
    private void insertRouteTable(InputStream file) throws IOException{
        RouteValidator validator = new RouteValidator(file);
        ArrayList<Route> routes = validator.makeroutes();
        validator = null;
        if(routes != null){
            for(int i = 0; i < routes.size(); i++) {
                Route route = routes.get(i);
                if(diffRoutes(route, routeRepository.getRoutes())) {
                    routeRepository.addRoute(route);
                    routeTData.add(new routeTable(route.getAirline(), String.valueOf(route.getAirlineID()),
                            route.getSrcAirport(), String.valueOf(route.getSrcAirportID()),
                            route.getDestAirport(), String.valueOf(route.getDestAirportID()),
                            route.getCodeshare(), String.valueOf(route.getStops()),
                            route.getEquipment().stream().collect(Collectors.joining(", "))));

                    // loop through the equipments given and add it to the equipmentSet
                    for(String r: route.getEquipment()){
                        if(!r.isEmpty()) {
                            equipmentSet.add(r);
                        }
                    }
                }
                else{
                    duplicateAlert("The system has read " + i + " route(s) from your file.\nPlease upload a file with different routes.");
                    break;
                }
                updateEquipBox();
                updateDepCountryBox();
            }
        }

    }

    // insert the given routes in a file into route table that's empty so dont check for duplicates
    private void insertEmptyRouteTable(InputStream file) throws IOException{
        RouteValidator validator = new RouteValidator(file);
        ArrayList<Route> routes = validator.makeroutes();
        validator = null;
        if(routes != null){
            for(int i = 0; i < routes.size(); i++) {
                Route route = routes.get(i);
                routeRepository.addRoute(route);
                routeTData.add(new routeTable(route.getAirline(), String.valueOf(route.getAirlineID()),
                        route.getSrcAirport(), String.valueOf(route.getSrcAirportID()),
                        route.getDestAirport(), String.valueOf(route.getDestAirportID()),
                        route.getCodeshare(), String.valueOf(route.getStops()),
                        route.getEquipment().stream().collect(Collectors.joining(", "))));

                // if destination country id is not null then add it to destSet
                if (route.getDestAirportID() != null){
                    // also check that the airport of that id exists in the repository
                    if(airportRepository.getAirports().get(route.getDestAirportID()) != null) {
                        destSet.add(airportRepository.getAirports().get(route.getDestAirportID()).getCountry());
                    }
                }
                // if departure country id is not null then add it to depSet
                if (route.getSrcAirportID() != null){
                    // also check that the airport of that id exists in the repository
                    if(airportRepository.getAirports().get(route.getSrcAirportID()) != null) {
                        depSet.add(airportRepository.getAirports().get(route.getSrcAirportID()).getCountry());
                    }
                }

                for(String r: route.getEquipment()){
                    if(!r.isEmpty()) {
                        equipmentSet.add(r);
                    }
                }
            }
            updateEquipBox();
            updateDepCountryBox();
            updateDestCountryBox();
        }


    }


    public void loadRoute() throws IOException {

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        File in = fileChooser.showOpenDialog(stage);
        InputStream file = new FileInputStream(in);
        if (in.exists()) {
            System.out.println("file opneeeedddd");
            insertRouteTable(file);
        }
    }

    private void loadDefaultRoute() throws IOException, URISyntaxException {
        InputStream file = getClass().getResourceAsStream("/routes.dat");
        //File file = new File(getClass().getClassLoader().getResource("routes.dat").toURI());
        if (file != null) {
            System.out.println("file opened oh yeah~");
            insertEmptyRouteTable(file);


        }
    }

    private void insertFlightTable(InputStream file) throws IOException {
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
                    if(!flightRepository.getFlights().containsKey(name.get().toLowerCase())){
                        gotName = true;
                        flightRepository.addFlight(name.get().toLowerCase(), flight);
                        flightItems.add(name.get());

                        // update the listView of flight names
                        flightList.setItems(flightItems);

//                    //populate flight table with this flight
//                    updateFlightTable(name.get());

                        // make the listView select to the newly uploaded flight which is
                        // listened to already so it will populate the table
                        flightList.getSelectionModel().selectLast();

                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Oops!");
                        alert.setHeaderText(null);
                        alert.setContentText("Please give a different name for your flight \n" +
                                "and make sure that it does not already exist \n in the system.");
                        alert.showAndWait();
                    }
                }
                else if(!name.isPresent()){
                    gotName = true;
                }
            }

        }

        // updateSomething();
    }

    // update flight table view given the flight name
    private void updateFlightTable(String name){

        // access flight repository to get the flight positions array given the name
        Flight flight = flightRepository.getFlights().get(name);

        // clear the table then populate it with this flight
        flightTData.clear();
        // loop through array of flight positions
        for(FlightPosition position: flight.getFlightPositions()){
            flightTData.add(new flightTable(position.getID(), position.getType(), position.getAltitude(),
                    position.getLatitude(), position.getLongitude()));
        }

    }

    public void loadFlight() throws IOException {

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        File in = fileChooser.showOpenDialog(stage);
        InputStream file = new FileInputStream(in);
        if (in.exists()) {
            System.out.println("oooo yah flights");
            insertFlightTable(file);
        }
    }

    private void duplicateIDAlert(String message, Integer id) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("ID: " + id + " already exists in the system.");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void duplicateAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("The route already exists in the system.");
        alert.setContentText(message);
        alert.showAndWait();
    }


    // Compare routes, return true if the routes are different otherwise false
    private boolean diffRoutes(Route route, ArrayList<Route> routeArray) {
        for(Route r: routeArray){
            if (r.getAirline().equals(route.getAirline()) &&
                    r.getSrcAirport().equals(route.getSrcAirport()) &&
                    r.getDestAirport().equals(route.getDestAirport()) &&
                    r.getCodeshare().equals(route.getCodeshare()) &&
                    String.valueOf(r.getStops()).equals(String.valueOf(route.getStops())) &&
                    r.getEquipment().equals(route.getEquipment())) {
                return false;
            }
        }
        return true;
    }




}