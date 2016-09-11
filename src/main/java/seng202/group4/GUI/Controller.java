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
import seng202.group4.data.dataType.Airline;
import seng202.group4.data.dataType.Route;
import seng202.group4.data.parser.validator.AirlineValidator;
import seng202.group4.data.parser.validator.RouteValidator;
import seng202.group4.data.repository.AirlineRepository;
import seng202.group4.data.repository.RouteRepository;


import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;


public class Controller implements Initializable{

    // Map view
    @FXML
    WebView mapView;

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

    // data repositories
    private AirlineRepository airlineRepository = new AirlineRepository();
    private RouteRepository routeRepository = new RouteRepository();

    // countrySet holds all the countries uploaded to airline
    private TreeSet countrySet = new TreeSet();

    // equipmentSet holds all the equipment uploaded to route
    private TreeSet equipmentSet = new TreeSet();

    public void initialize(URL location, ResourceBundle resources) {
        mapView.getEngine().load(getClass().getClassLoader().getResource("map.html").toExternalForm());
        // initialise data list
        datalist.setItems(items);

        // setting up view
        updateAirlineSearch();
        updateRouteSearch();
        // select airline table on the side bar
        datalist.getSelectionModel().clearAndSelect(0);
        // show airline table
        airlineTableID.toFront();

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

        // listen for route search queries
        searchRoutes();

    }




    public void searchRoutes(){
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

                // hold the string that's typed in the search bar in lowercase
                String lowerCaseFilter = newValue.toLowerCase();

                // The following returns true if the filter matches
                if (newValue.isEmpty() && !direct.isSelected() && !indirect.isSelected()
                        && selectedRouteEquip != null && selectedRouteEquip.equals(" --ALL EQUIPMENTS-- ")) {
                    return true;    // display all data.
                }

                // Check if the search criteria matches the following columns
                if (rtAirID != null && rtAirID.toLowerCase().contains(lowerCaseFilter)){
                    toggled = true;
                }
                if (rtID != null && rtID.toLowerCase().contains(lowerCaseFilter)) {
                    toggled = true;
                }
                if (rtSrcAirport != null && rtSrcAirport.toLowerCase().contains(lowerCaseFilter)){
                    toggled = true;
                }
                if (rtSrcAirportId != null && rtSrcAirportId.toLowerCase().contains(lowerCaseFilter)) {
                    toggled = true;
                }
                if (rtDestAirport != null && rtDestAirport.toLowerCase().contains(lowerCaseFilter)){
                    toggled = true;
                }
                if (rtDestAirportId != null && rtDestAirportId.toLowerCase().contains(lowerCaseFilter)) {
                    toggled = true;
                }
                if (rtCodeshare != null && rtCodeshare.toLowerCase().contains(lowerCaseFilter)){
                    toggled = true;
                }
                if (rtStops != null && rtStops.toLowerCase().contains(lowerCaseFilter)) {
                    toggled = true;
                }
                if (rtEquipment != null && rtEquipment.toLowerCase().contains(lowerCaseFilter)){
                    toggled = true;
                }

                // For the following cases, return true if the equipment dropdown is empty, --ALL EQUIPMENTS-- or
                // matches the equipment in the table
                if (toggled && !direct.isSelected() && !indirect.isSelected()){
                    if (emptyEquipFilter){
                        return true;
                    }
                    if (selectedRouteEquip != null && selectedRouteEquip.equals(" --ALL EQUIPMENTS-- ")) {
                        return true;
                    }
                    else if(selectedRouteEquip != null && rtEquipmentArray.contains(selectedRouteEquip.toLowerCase())){
                        return true;
                    }
                }
                if (direct.isSelected() && rtStops != null && rtStops.equals("0") && toggled){
                    if (emptyEquipFilter){
                        return true;
                    }
                    if (selectedRouteEquip != null && selectedRouteEquip.equals(" --ALL EQUIPMENTS-- ")) {
                        return true;
                    }
                    else if(selectedRouteEquip != null && rtEquipmentArray.contains(selectedRouteEquip.toLowerCase())){
                        return true;
                    }
                }
                if (indirect.isSelected() && rtStops != null && !rtStops.equals("0") && toggled){
                    if (emptyEquipFilter){
                        return true;
                    }
                    if (selectedRouteEquip != null && selectedRouteEquip.equals(" --ALL EQUIPMENTS-- ")) {
                        return true;
                    }
                    else if(selectedRouteEquip != null && rtEquipmentArray.contains(selectedRouteEquip.toLowerCase())){
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
                        && selectedAirlineCountry != null && selectedAirlineCountry.equals(" --ALL COUNTRIES-- ")){
                    return true; // display all data
                }

                // Check if the search criteria matches the following columns
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

                // For the following cases, return true if the country dropdown is empty, --ALL COUNTRIES-- or
                // matches the country in the table
                if (!active.isSelected() && toggled && !inactive.isSelected()){
                    if (emptyCountryFilter){
                        return true;
                    }
                    if (selectedAirlineCountry != null && selectedAirlineCountry.equals(" --ALL COUNTRIES-- ")) {
                        return true;
                    }
                    else if(selectedAirlineCountry != null && atCountry != null &&
                            atCountry.toLowerCase().equals(selectedAirlineCountry.toLowerCase())){
                        return true;
                    }
                }
                if (toggled && active.isSelected() && atActive){
                    if (emptyCountryFilter){
                        return true;
                    }
                    if (selectedAirlineCountry != null && selectedAirlineCountry.equals(" --ALL COUNTRIES-- ")) {
                        return true;

                    }
                    else if(selectedAirlineCountry != null && atCountry != null &&
                            atCountry.toLowerCase().equals(selectedAirlineCountry.toLowerCase())){
                        return true;
                    }
                }
                if (toggled && inactive.isSelected() && !atActive){
                    if (emptyCountryFilter){
                        return true;
                    }
                    if (selectedAirlineCountry != null && selectedAirlineCountry.equals(" --ALL COUNTRIES-- ")) {
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



    private void updateCountryBox(){
        // clear the current combo box
        airlineCountryFilter.getItems().clear();
        // if the combo box doesn't have --ALL COUNTRIES-- then add one
        if(!airlineCountryFilter.getItems().contains(" --ALL COUNTRIES-- ")){
            airlineCountryFilter.getItems().add(" --ALL COUNTRIES-- ");
        }

        // loop through the current countrySet
        Iterator itr = countrySet.iterator();
        while(itr.hasNext()){
            airlineCountryFilter.getItems().add(itr.next());
        }
    }

    private void updateEquipBox(){
        // clear the current combo box
        routeEquipFilter.getItems().clear();
        // if the combo box doesn't have --ALL EQUIPMENTS-- then add one
        if(!routeEquipFilter.getItems().contains(" --ALL EQUIPMENTS-- ")){
            routeEquipFilter.getItems().add(" --ALL EQUIPMENTS-- ");
        }

        // loop through the current countrySet
        Iterator itr = equipmentSet.iterator();
        while(itr.hasNext()){
            routeEquipFilter.getItems().add(itr.next());
        }
    }

    public void updateAirlineSearch(){
        String text = airlineSearch.getText();
        airlineSearch.setText(text + " ");
        airlineSearch.setText(text);

    }

    public void updateRouteSearch(){
        String text = routeSearch.getText();
        routeSearch.setText(text + " ");
        routeSearch.setText(text);

    }


    public void selectDirect() throws IOException {
        updateRouteSearch();
    }

    public void selectIndirect() throws IOException {
        updateRouteSearch();
    }

    public void filterCountry() throws IOException {
        updateAirlineSearch();
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
    private void insertAirlineTable(File file) throws IOException {
        AirlineValidator validator = new AirlineValidator(file);
        ArrayList<Airline> airlines = validator.makeAirlines();
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
                    countrySet.add(airline.getCountry());
                }
            }
            else{
                duplicateIDAlert("Please fix the conflict and reupload the file.", airline.getID());
                break;
            }
            updateCountryBox();
        }
    }

    // Insert the airlines in a given file into the airline table GUI
    private void insertEmptyAirlineTable(File file) throws IOException {
        AirlineValidator validator = new AirlineValidator(file);
        ArrayList<Airline> airlines = validator.makeAirlines();
        for(int i = 0; i < airlines.size(); i++) {
            Airline airline = airlines.get(i);
            airlineRepository.addAirline(airline);
            airlineTData.add(new airlineTable(airline.getID(), airline.getName(),
                    airline.getAlias(), airline.getIATA(),
                    airline.getICAO(), airline.getCallsign(),
                    airline.getCountry(), airline.getActive()));
            if(airline.getCountry() != null){
                countrySet.add(airline.getCountry());
            }
        }
        updateCountryBox();
    }



    public void loadAirline() throws IOException {
            Stage stage = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open file");
            File file = fileChooser.showOpenDialog(stage);
            if (file != null && file.exists()) {
                System.out.println("file opneedd");
                insertAirlineTable(file);
            }
    }

    private void loadDefaultAirline() throws IOException, URISyntaxException {
        File file = new File(getClass().getClassLoader().getResource("airlines.dat").toURI());
        if (file.exists()) {
            System.out.println("file opened yeah~");
            insertEmptyAirlineTable(file);
        }
    }

    public void loadAirport() throws IOException {

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        File file = fileChooser.showOpenDialog(stage);
    }

    // insert the given routes in a file into route table and checks for duplicates
    public void insertRouteTable(File file) throws IOException{
        RouteValidator validator = new RouteValidator(file);
        ArrayList<Route> routes = validator.makeroutes();
        for(int i = 0; i < routes.size(); i++) {
            Route route = routes.get(i);
            if(diffRoutes(route, routeRepository.getRoutes())) {
                routeRepository.addRoute(route);
                routeTData.add(new routeTable(route.getAirline(), String.valueOf(route.getAirlineID()),
                        route.getSrcAirport(), String.valueOf(route.getSrcAirportID()),
                        route.getDestAirport(), String.valueOf(route.getDestAirportID()),
                        route.getCodeshare(), String.valueOf(route.getStops()),
                        route.getEquipment().stream().collect(Collectors.joining(", "))));
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
        }
    }

    // insert the given routes in a file into route table that's empty so dont check for duplicates
    public void insertEmptyRouteTable(File file) throws IOException{
        RouteValidator validator = new RouteValidator(file);
        ArrayList<Route> routes = validator.makeroutes();
        for(int i = 0; i < routes.size(); i++) {
            Route route = routes.get(i);
            routeRepository.addRoute(route);
            routeTData.add(new routeTable(route.getAirline(), String.valueOf(route.getAirlineID()),
                    route.getSrcAirport(), String.valueOf(route.getSrcAirportID()),
                    route.getDestAirport(), String.valueOf(route.getDestAirportID()),
                    route.getCodeshare(), String.valueOf(route.getStops()),
                    route.getEquipment().stream().collect(Collectors.joining(", "))));
            for(String r: route.getEquipment()){
                if(!r.isEmpty()) {
                    equipmentSet.add(r);
                }
            }
        }
        updateEquipBox();

    }


    public void loadRoute() throws IOException {

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            System.out.println("file opneeeedddd");
            insertRouteTable(file);
        }
    }

    public void loadDefaultRoute() throws IOException, URISyntaxException {

        File file = new File(getClass().getClassLoader().getResource("routes.dat").toURI());
        if (file.exists()) {
            System.out.println("file opened oh yeah~");
            insertEmptyRouteTable(file);
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