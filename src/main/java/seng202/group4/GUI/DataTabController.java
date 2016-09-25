package seng202.group4.GUI;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group4.data.dataType.Airline;
import seng202.group4.data.dataType.Airport;
import seng202.group4.data.dataType.Flight;
import seng202.group4.data.dataType.Route;
import seng202.group4.data.parser.validator.AirlineValidator;
import seng202.group4.data.parser.validator.AirportValidator;
import seng202.group4.data.parser.validator.FlightValidator;
import seng202.group4.data.parser.validator.RouteValidator;
import seng202.group4.data.repository.AirlineRepository;
import seng202.group4.data.repository.AirportRepository;
import seng202.group4.data.repository.Repository;
import seng202.group4.data.repository.RouteRepository;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Pang on 22/09/16.
 */
public class DataTabController implements Initializable{
    private Controller mainController;
    private FlightTabController flightTabController;

    private Tab flightTab;
    private TabPane tabPane;
    private Tab dataTab;

    @FXML
    private MenuBarController menuBarController;

    // search field
    @FXML
    private TextField airlineSearch;

    // airlines inactive and active check boxes
    @FXML
    private CheckBox active;

    @FXML
    private CheckBox inactive;

    // airline country filter
    @FXML
    private ComboBox airlineCountryFilter;

    // airport FXML
    @FXML
    private TextField airportSearch;

    @FXML
    private ComboBox airportCountryFilter;



    @FXML
    private TextField calcdDistance;

    @FXML
    private Button calc;

    // route FXML
    @FXML
    private TextField routeSearch;

    @FXML
    private ComboBox routeDepCountryFilter;

    @FXML
    private ComboBox routeDestCountryFilter;

    @FXML
    private CheckBox direct;

    @FXML
    private CheckBox indirect;

    @FXML
    private ComboBox routeEquipFilter;

    // StackPane for search
    @FXML
    private StackPane searchPanes;

    public AnchorPane getAirlinePane() {
        return airlinePane;
    }

    public AnchorPane getAirportPane() {
        return airportPane;
    }

    public AnchorPane getRoutePane() {
        return routePane;
    }



    @FXML
    private TextField airportA;

    @FXML
    private TextField airportB;


    private Double pointALat;
    private Double pointALon;
    private Double pointBLat;
    private Double pointBLon;
    private String pointAICAO;
    private String pointBICAO;

    // initial combobox names
    private String allCountriesTag = " --ALL COUNTRIES-- ";
    private String allEquipmentsTag = " --ALL EQUIPMENTS-- ";
    private String departureCountryTag = " --DEPART FROM-- ";
    private String destinationCountryTag = " --ARRIVE TO-- ";

    @FXML
    private ListView<String> datalist;

    // Airline Table
    @FXML
    private TableView<airlineTable> airlineTableID;
    @FXML
    private TableColumn<airlineTable, String> aid;

    @FXML
    private TableColumn<airlineTable, String> aname;

    @FXML
    private TableColumn<airlineTable, String> aalias;

    @FXML
    private TableColumn<airlineTable, String> aiata;

    @FXML
    private TableColumn<airlineTable, String> aicao;

    @FXML
    private TableColumn<airlineTable, String> acallsign;

    @FXML
    private TableColumn<airlineTable, String> acountry;

    @FXML
    private TableColumn<airlineTable, String> aactive;

    // Airport Table
    @FXML
    private TableView<airportTable> airportTableID;

    @FXML
    private TableColumn<airportTable, String> apid;

    @FXML
    private TableColumn<airportTable, String> apname;

    @FXML
    private TableColumn<airportTable, String> apcity;

    @FXML
    private TableColumn<airportTable, String> apcountry;

    @FXML
    private TableColumn<airportTable, String> apiata;

    @FXML
    private TableColumn<airportTable, String> apicao;

    @FXML
    private TableColumn<airportTable, String> aplat;

    @FXML
    private TableColumn<airportTable, String> aplong;

    @FXML
    private TableColumn<airportTable, String> apalt;

    @FXML
    private TableColumn<airportTable, String> aptimezone;

    @FXML
    private TableColumn<airportTable, String> apdst;

    @FXML
    private TableColumn<airportTable, String> aptz;

    // Route table

    @FXML
    private TableView<routeTable> routeTableID;

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




    @FXML
    private AnchorPane airlinePane;

    @FXML
    private AnchorPane airportPane;

    @FXML
    private AnchorPane routePane;

    private String airlineLabel = "Airlines";
    private String airportLabel = "Airports";
    private String routeLabel = "Routes";

    // create table data
    private ObservableList<airlineTable> airlineTData = FXCollections.observableArrayList();

    private ObservableList<airportTable> airportTData = FXCollections.observableArrayList();

    private ObservableList<routeTable> routeTData = FXCollections.observableArrayList();

    private ObservableList<flightTable> flightTData = FXCollections.observableArrayList();

    private ObservableList<String> items = FXCollections.observableArrayList(airlineLabel, airportLabel, routeLabel);



    public TreeSet getAirlineCountrySet() {
        return airlineCountrySet;
    }

    public TreeSet getAirportCountrySet() {
        return airportCountrySet;
    }

    public TreeSet getDepSet() {
        return depSet;
    }

    public TreeSet getDestSet() {
        return destSet;
    }

    public TreeSet getEquipmentSet() {
        return equipmentSet;
    }

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


    /**
     * Where the program starts, initializes things like listeners and starts running the GUI.
     *
     * @param location URL
     * @param resources ResourceBundle
     */
    public void initialize(URL location, ResourceBundle resources) {
       // menuBarController.setDataTabController(this);
        datalist.setItems(items);
        // select first data type (airline) on the side bar
        datalist.getSelectionModel().clearAndSelect(0);
        // show airline table
        airlineTableID.toFront();


        datalist.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            //System.out.println("Selected item: " + new_val);
            if (new_val.equals(airlineLabel)) {
                airlineTableID.toFront();
                airlinePane.setVisible(true);
                airportPane.setVisible(false);
                routePane.setVisible(false);
            } else if (new_val.equals(airportLabel)) {
                airportTableID.toFront();
                airlinePane.setVisible(false);
                airportPane.setVisible(true);
                routePane.setVisible(false);
            } else if (new_val.equals(routeLabel)) {
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

        searchAirlines();

        airlineTableID.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        airlineTableID.setRowFactory(tableView -> {
            final TableRow<airlineTable> row = new TableRow<>();
            final ContextMenu rowMenu = new ContextMenu();
            MenuItem removeItem = new MenuItem("Delete");
            row.setOnMouseClicked(event -> {
            });
            removeItem.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Are you sure you want to delete?");
                alert.setContentText("Pressing OK will delete the row(s).\nWARNING: The action cannot be undone.\n");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    ObservableList<airlineTable> selectedItems = airlineTableID.getSelectionModel().getSelectedItems();
                    for (airlineTable airline : selectedItems) {
                        Repository.airlineRepository.getAirlines().remove(airline.getRid());
                    }
                    airlineTData.removeAll(selectedItems);
                    airlineTableID.getSelectionModel().clearSelection();
                }
            });
            rowMenu.getItems().addAll(removeItem);
            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
                            .then(rowMenu)
                            .otherwise((ContextMenu) null)
            );
            return row;
        });

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

        airportTableID.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        airportTableID.setRowFactory(tableView -> {
            final TableRow<airportTable> row = new TableRow<>();
            final ContextMenu rowMenu = new ContextMenu();
            MenuItem addA = new MenuItem("Add to airport A");
            MenuItem addB = new MenuItem("Add to airport B");
            MenuItem removeItem = new MenuItem("Delete");
            row.setOnMouseClicked(event -> {
            });
            addA.setOnAction(event -> {
                airportA.setText(row.getItem().getAtname());
                pointALat = row.getItem().getAtlatitude();
                pointALon = row.getItem().getAtlongitude();
                pointAICAO = row.getItem().getAticao();
            });
            addB.setOnAction(event -> {
                airportB.setText(row.getItem().getAtname());
                pointBLat = row.getItem().getAtlatitude();
                pointBLon = row.getItem().getAtlongitude();
                pointBICAO = row.getItem().getAticao();
            });
            removeItem.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Are you sure you want to delete?");
                alert.setContentText("Pressing OK will delete the row(s).\nWARNING: The action cannot be undone.\n");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    ObservableList<airportTable> selectedItems = airportTableID.getSelectionModel().getSelectedItems();
                    for (airportTable airport : selectedItems) {
                        Repository.airportRepository.getAirports().remove(airport.getAtid());
                    }
                    airportTData.removeAll(selectedItems);
                    airportTableID.getSelectionModel().clearSelection();
                }
            });
            rowMenu.getItems().addAll(addA, addB, removeItem);
            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
                            .then(rowMenu)
                            .otherwise((ContextMenu) null)
            );
            return row;
        });

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

        routeTableID.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        routeTableID.setRowFactory(tableView -> {
            final TableRow<routeTable> row = new TableRow<>();
            final ContextMenu rowMenu = new ContextMenu();
            MenuItem removeItem = new MenuItem("Delete");
            row.setOnMouseClicked(event -> {
            });
            removeItem.setOnAction(event -> {
                if (routeTableID.getSelectionModel().getSelectedItems().size() > 5000) {
                    Alert warning = new Alert(Alert.AlertType.WARNING);
                    warning.setTitle("Warning Dialog");
                    warning.setHeaderText("Cannot delete more than 5000 lines at a time.");
                    warning.setContentText("You selected " + routeTableID.getSelectionModel().getSelectedItems().size() + " lines.");

                    warning.showAndWait();

                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("Are you sure you want to delete?");
                    alert.setContentText("Pressing OK will delete the row(s).\nWARNING: The action cannot be undone.\n");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        ObservableList<routeTable> selectedItems = routeTableID.getSelectionModel().getSelectedItems();
                        for (routeTable route : selectedItems) {
                            String key = route.getRairline() + route.getRid() + route.getRsource() + route.getRsourceid()
                                    + route.getRdest() + route.getRdestid() + route.getRcodeshare() + route.getRstops();
                            String[] equipment = route.getRequipment().split(", ");
                            for (String item : equipment) {
                                key += item;
                            }
                            Repository.routeRepository.getRoutes().remove(key);
                        }
                        routeTData.removeAll(selectedItems);
                        routeTableID.getSelectionModel().clearSelection();
                    }
                }

            });
            rowMenu.getItems().addAll(removeItem);
            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
                            .then(rowMenu)
                            .otherwise((ContextMenu) null)
            );
            return row;
        });

        // listen for route search queries
        searchRoutes();

    }

    /**
     * Sets the main controller and retrieves private variables from the main controller
     *
     * @param controller Controller
     */
    public void setMainController(Controller controller) {
        this.mainController = controller;
        //this.menuBarController = controller.getMenuBarController();
        this.flightTabController = mainController.getFlightTabController();
        this.flightTab = mainController.getFlightTab();
        this.airlineLabel = mainController.getAirlineLabel();
        this.airportLabel = mainController.getAirportLabel();
        this.routeLabel = mainController.getRouteLabel();
        this.tabPane = mainController.getTabPane();
        this.dataTab = mainController.getDataTab();
    }

    /**
     * Gets the main controller
     * @return Controller
     */
    public Controller getMainController() {
        return mainController;
    }


    private void searchRoutes() {
        // searching for route
        FilteredList<routeTable> routeTableFiltered = new FilteredList<>(routeTData, p -> true);

        routeSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            routeTableFiltered.setPredicate(route -> {
                // Some columns in route table (rt)
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
                if (routeDepCountryFilter.getValue() != null) {
                    selectedDepartCountry = routeDepCountryFilter.getValue().toString();
                }

                // set up for destination country drop down box
                boolean emptyDestFilter = routeDestCountryFilter.getSelectionModel().getSelectedItem() == null;
                // selectedAirportCountry is a string to hold whats selected in the dropdown
                // set to null unless a dropdown is selected
                String selectedDestCountry = null;
                if (routeDestCountryFilter.getValue() != null) {
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
                        (rtEquipment != null && rtEquipment.toLowerCase().contains(lowerCaseFilter)))) {
                    toggled = true;
                }

                // For the following cases, return true if the equipment dropdown is empty, --ALL EQUIPMENTS-- or
                // matches the equipment in the table
                if ((toggled && !direct.isSelected() && !indirect.isSelected()) ||
                        (direct.isSelected() && rtStops != null && rtStops.equals("0") && toggled) ||
                        (indirect.isSelected() && rtStops != null && !rtStops.equals("0") && toggled)) {
                    boolean filterChecked = filterEquipDepDest(emptyEquipFilter, emptyDepartFilter, emptyDestFilter,
                            selectedRouteEquip, selectedDepartCountry, selectedDestCountry, rtEquipmentArray,
                            rtDestAirportId, rtSrcAirportId);
                    if (filterChecked) {
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
                                       String rtDestAirportId, String rtSrcAirportId) {
        // empty cases when no filters are selected
        if ((emptyEquipFilter && emptyDepartFilter && emptyDestFilter) || (selectedRouteEquip != null
                && selectedRouteEquip.equals(allEquipmentsTag) && selectedDepartCountry != null &&
                selectedDepartCountry.equals(departureCountryTag) && selectedDestCountry != null
                && selectedDestCountry.equals(destinationCountryTag))) {
            return true;
        }

        // if only one filter is selected
        // filter only equipment
        if (((emptyDepartFilter && emptyDestFilter) || (selectedDepartCountry != null &&
                selectedDepartCountry.equals(departureCountryTag) && selectedDestCountry != null
                && selectedDestCountry.equals(destinationCountryTag))) && selectedRouteEquip != null) {
            if (selectedRouteEquip.equals(allEquipmentsTag)) {
                return true;
            } else if (rtEquipmentArray.contains(selectedRouteEquip.toLowerCase())) {
                return true;
            }
        }
        // filter only destination country
        else if (((emptyEquipFilter && emptyDepartFilter) || (selectedRouteEquip != null
                && selectedRouteEquip.equals(allEquipmentsTag) && selectedDepartCountry != null &&
                selectedDepartCountry.equals(departureCountryTag))) &&
                selectedDestCountry != null) {
            if (selectedDestCountry.equals(destinationCountryTag)) {
                return true;
            } else if (!rtDestAirportId.equals("null") &&
                    Repository.airportRepository.airportIDsOfCountry(selectedDestCountry.toLowerCase()).contains(Integer.parseInt(rtDestAirportId))) {
                return true;
            }
        }
        // filter only departure country
        else if (((emptyEquipFilter && emptyDestFilter) || (selectedRouteEquip != null
                && selectedRouteEquip.equals(allEquipmentsTag) && selectedDestCountry != null &&
                selectedDestCountry.equals(destinationCountryTag))) &&
                selectedDepartCountry != null) {
            if (selectedDepartCountry.equals(departureCountryTag)) {
                return true;
            } else if (!rtSrcAirportId.equals("null") &&
                    Repository.airportRepository.airportIDsOfCountry(selectedDepartCountry.toLowerCase()).contains(Integer.parseInt(rtSrcAirportId))) {
                return true;
            }
        }

        // if two filters are selected
        // filter equipment and destination country
        else if ((emptyDepartFilter || (selectedDepartCountry != null &&
                selectedDepartCountry.equals(departureCountryTag))) && selectedDestCountry != null &&
                selectedRouteEquip != null) {
            if (selectedDestCountry.equals(destinationCountryTag) && selectedRouteEquip.equals(allEquipmentsTag)) {
                return true;
            } else if (selectedDestCountry.equals(destinationCountryTag) &&
                    rtEquipmentArray.contains(selectedRouteEquip.toLowerCase())) {
                return true;
            } else if (selectedRouteEquip.equals(allEquipmentsTag) && !rtDestAirportId.equals("null") &&
                    Repository.airportRepository.airportIDsOfCountry(selectedDestCountry.toLowerCase()).contains(Integer.parseInt(rtDestAirportId))) {
                return true;
            } else if (rtEquipmentArray.contains(selectedRouteEquip.toLowerCase()) &&
                    !rtDestAirportId.equals("null") &&
                    Repository.airportRepository.airportIDsOfCountry(selectedDestCountry.toLowerCase()).contains(Integer.parseInt(rtDestAirportId))) {
                return true;
            }
        }
        //filter equipment and departure country
        else if ((emptyDestFilter || (selectedDestCountry != null &&
                selectedDestCountry.equals(destinationCountryTag))) && selectedDepartCountry != null &&
                selectedRouteEquip != null) {
            if (selectedDepartCountry.equals(departureCountryTag) && selectedRouteEquip.equals(allEquipmentsTag)) {
                return true;
            } else if (selectedDepartCountry.equals(departureCountryTag) &&
                    rtEquipmentArray.contains(selectedRouteEquip.toLowerCase())) {
                return true;
            } else if (selectedRouteEquip.equals(allEquipmentsTag) && !rtSrcAirportId.equals("null") &&
                    Repository.airportRepository.airportIDsOfCountry(selectedDepartCountry.toLowerCase()).contains(Integer.parseInt(rtSrcAirportId))) {
                return true;
            } else if (rtEquipmentArray.contains(selectedRouteEquip.toLowerCase()) &&
                    !rtSrcAirportId.equals("null") &&
                    Repository.airportRepository.airportIDsOfCountry(selectedDepartCountry.toLowerCase()).contains(Integer.parseInt(rtSrcAirportId))) {
                return true;
            }
        }
        //filter departure country and destination country
        else if ((emptyEquipFilter || (selectedRouteEquip != null &&
                selectedRouteEquip.equals(allEquipmentsTag))) && selectedDepartCountry != null &&
                selectedDestCountry != null) {
            if (selectedDepartCountry.equals(departureCountryTag) &&
                    selectedDestCountry.equals(destinationCountryTag)) {
                return true;
            } else if (selectedDepartCountry.equals(departureCountryTag) && !rtDestAirportId.equals("null") &&
                    Repository.airportRepository.airportIDsOfCountry(selectedDestCountry.toLowerCase()).contains(Integer.parseInt(rtDestAirportId))) {
                return true;
            } else if (selectedDestCountry.equals(destinationCountryTag) && !rtSrcAirportId.equals("null") &&
                    Repository.airportRepository.airportIDsOfCountry(selectedDepartCountry.toLowerCase()).contains(Integer.parseInt(rtSrcAirportId))) {
                return true;
            } else if (!rtDestAirportId.equals("null") &&
                    Repository.airportRepository.airportIDsOfCountry(selectedDestCountry.toLowerCase()).contains(Integer.parseInt(rtDestAirportId))
                    && !rtSrcAirportId.equals("null") &&
                    Repository.airportRepository.airportIDsOfCountry(selectedDepartCountry.toLowerCase()).contains(Integer.parseInt(rtSrcAirportId))) {
                return true;
            }
        }

        // if three filters are selected
        // filter destination/departure country and equipment
        else if (selectedDepartCountry != null && selectedDestCountry != null && selectedRouteEquip != null) {
            if (!rtDestAirportId.equals("null") &&
                    Repository.airportRepository.airportIDsOfCountry(selectedDestCountry.toLowerCase()).contains(Integer.parseInt(rtDestAirportId))
                    && !rtSrcAirportId.equals("null") &&
                    Repository.airportRepository.airportIDsOfCountry(selectedDepartCountry.toLowerCase()).contains(Integer.parseInt(rtSrcAirportId))
                    && rtEquipmentArray.contains(selectedRouteEquip.toLowerCase())) {
                return true;
            }

        }
        return false;
    }

    /**
     * Searches through the airports in the table dependent on a specific search entry by the user.
     */
    private void searchAirports() {
        String intPattern = "[-]?[0-9]*[.]?[0-9]+";
        // searching for airline
        FilteredList<airportTable> airportTableFiltered = new FilteredList<>(airportTData, p -> true);

        airportSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            airportTableFiltered.setPredicate(airport -> {
                // Columns in airport table (at)
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
                boolean toggled = false;    // toggle to see if anything was matched in the search box

                // set up for country drop down box
                boolean emptyCountryFilter = airportCountryFilter.getSelectionModel().getSelectedItem() == null;
                // selectedAirportCountry is a string to hold whats selected in the dropdown
                // set to null unless a dropdown is selected
                String selectedAirportCountry = null;
                if (airportCountryFilter.getValue() != null) {
                    selectedAirportCountry = airportCountryFilter.getValue().toString();
                }

                // hold the string that's typed in the search bar in lowercase
                String lowerCaseFilter = newValue.toLowerCase();

                // The following returns true if the filter matches
                if (newValue.isEmpty() && selectedAirportCountry != null && selectedAirportCountry.equals(allCountriesTag)) {
                    return true; // display all data
                }

                // Check if the search criteria matches the following columns
                if ((lowerCaseFilter.matches("[0-9]+") && atID == Integer.parseInt(lowerCaseFilter)) ||
                        (atName != null && atName.toLowerCase().contains(lowerCaseFilter)) ||
                        (atCity != null && atCity.toLowerCase().contains(lowerCaseFilter)) ||
                        (atCountry != null && atCountry.toLowerCase().contains(lowerCaseFilter)) ||
                        (atIATA != null && atIATA.toLowerCase().contains(lowerCaseFilter)) ||
                        (atICAO != null && atICAO.toLowerCase().contains(lowerCaseFilter)) ||
                        (lowerCaseFilter.matches(intPattern) && atLatitude == Double.parseDouble(lowerCaseFilter)) || // CAN BE IMPROVED
                        (lowerCaseFilter.matches(intPattern) && atLongitude == Double.parseDouble(lowerCaseFilter)) || // CAN BE IMPROVED
                        (lowerCaseFilter.matches(intPattern) && atAltitude == Double.parseDouble(lowerCaseFilter)) || // CAN BE IMPROVED
                        (lowerCaseFilter.matches(intPattern) && atTimezone == Float.parseFloat(lowerCaseFilter))) {
                    toggled = true;
                }

                // For the following case, return true if the country dropdown is empty, --ALL COUNTRIES-- or
                // matches the country in the table
                if (toggled) {
                    if (emptyCountryFilter) {
                        return true;
                    }
                    if (selectedAirportCountry != null && selectedAirportCountry.equals(allCountriesTag)) {
                        return true;
                    } else if (selectedAirportCountry != null && atCountry != null &&
                            atCountry.toLowerCase().equals(selectedAirportCountry.toLowerCase())) {
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

    private void searchAirlines() {
        // searching for airline
        FilteredList<airlineTable> airlineTableFiltered = new FilteredList<>(airlineTData, p -> true);

        airlineSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            airlineTableFiltered.setPredicate(airline -> {
                // Some columns in airline table (at)
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
                if (airlineCountryFilter.getValue() != null) {
                    selectedAirlineCountry = airlineCountryFilter.getValue().toString();
                }

                // hold the string that's typed in the search bar in lowercase
                String lowerCaseFilter = newValue.toLowerCase();

                // The following returns true if the filter matches
                if (newValue.isEmpty() && !active.isSelected() && !inactive.isSelected()
                        && selectedAirlineCountry != null && selectedAirlineCountry.equals(allCountriesTag)) {
                    return true; // display all data
                }

                // Check if the search criteria matches the following columns
                if ((lowerCaseFilter.matches("[0-9]+") && atID == Integer.parseInt(lowerCaseFilter)) ||
                        (atName != null && atName.toLowerCase().contains(lowerCaseFilter)) ||
                        (atAlias != null && atAlias.toLowerCase().contains(lowerCaseFilter)) ||
                        (atIATA != null && atIATA.toLowerCase().contains(lowerCaseFilter)) ||
                        (atICAO != null && atICAO.toLowerCase().contains(lowerCaseFilter)) ||
                        (atCallsign != null && atCallsign.toLowerCase().contains(lowerCaseFilter)) ||
                        (atCountry != null && atCountry.toLowerCase().contains(lowerCaseFilter))) {
                    toggled = true;
                }

                // For the following cases, return true if the country dropdown is empty, --ALL COUNTRIES-- or
                // matches the country in the table
                if ((!active.isSelected() && toggled && !inactive.isSelected()) ||
                        (toggled && active.isSelected() && atActive) ||
                        (toggled && inactive.isSelected() && !atActive)) {
                    if (emptyCountryFilter) {
                        return true;
                    }
                    if (selectedAirlineCountry != null && selectedAirlineCountry.equals(allCountriesTag)) {
                        return true;
                    } else if (selectedAirlineCountry != null && atCountry != null &&
                            atCountry.toLowerCase().equals(selectedAirlineCountry.toLowerCase())) {
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

    private void loadDefaultAirline() throws IOException, URISyntaxException {
        if (Repository.airlineRepository != null) {
            loadSerializedAirline();
        } else {
            Repository.airlineRepository = new AirlineRepository();
            InputStream file = getClass().getResourceAsStream("/airlines.dat");
            //File file = new File(getClass().getClassLoader().getResource("airlines.dat").toURI());
            if (file != null) {
                insertEmptyAirlineTable(file);
            }
        }
    }

    private void loadSerializedAirline() {
        Collection<Airline> airlines = Repository.airlineRepository.getAirlines().values();
        for (Airline airline : airlines) {
            airlineTData.add(new airlineTable(airline.getID(), airline.getName(),
                    airline.getAlias(), airline.getIATA(),
                    airline.getICAO(), airline.getCallsign(),
                    airline.getCountry(), airline.getActive()));
            if (airline.getCountry() != null) {
                airlineCountrySet.add(airline.getCountry());
            }
        }
        updateAirlineCountryBox();
    }

    // Insert the airlines in a given file into the airline table GUI
    private void insertEmptyAirlineTable(InputStream file) throws IOException {

        AirlineValidator validator = new AirlineValidator(file);
        ArrayList<Airline> airlines = validator.makeAirlines();
        validator = null;
        if (airlines != null) {
            for (int i = 0; i < airlines.size(); i++) {
                Airline airline = airlines.get(i);
                Repository.airlineRepository.addAirline(airline);
                airlineTData.add(new airlineTable(airline.getID(), airline.getName(),
                        airline.getAlias(), airline.getIATA(),
                        airline.getICAO(), airline.getCallsign(),
                        airline.getCountry(), airline.getActive()));
                if (airline.getCountry() != null) {
                    airlineCountrySet.add(airline.getCountry());
                }
            }
            updateAirlineCountryBox();
        }

    }


    private void loadDefaultRoute() throws IOException, URISyntaxException {
        if (Repository.routeRepository != null) {
            loadSerializedRoute();
        } else {
            Repository.routeRepository = new RouteRepository();
            InputStream file = getClass().getResourceAsStream("/routes.dat");
            if (file != null) {
                //System.out.println("file opened oh yeah~");
                insertEmptyRouteTable(file);
            }
        }
    }


    // insert the given routes in a file into route table that's empty so dont check for duplicates
    private void insertEmptyRouteTable(InputStream file) throws IOException {
        RouteValidator validator = new RouteValidator(file);
        ArrayList<Route> routes = validator.makeroutes();
        validator = null;
        if (routes != null) {
            for (int i = 0; i < routes.size(); i++) {
                Route route = routes.get(i);
                Repository.routeRepository.addRoute(route);
                routeTData.add(new routeTable(route.getAirline(), String.valueOf(route.getAirlineID()),
                        route.getSrcAirport(), String.valueOf(route.getSrcAirportID()),
                        route.getDestAirport(), String.valueOf(route.getDestAirportID()),
                        route.getCodeshare(), String.valueOf(route.getStops()),
                        route.getEquipment().stream().collect(Collectors.joining(", "))));

                // if destination country id is not null then add it to destSet
                if (route.getDestAirportID() != null) {
                    // also check that the airport of that id exists in the repository
                    if (Repository.airportRepository.getAirports().get(route.getDestAirportID()) != null) {
                        destSet.add(Repository.airportRepository.getAirports().get(route.getDestAirportID()).getCountry());
                    }
                }
                // if departure country id is not null then add it to depSet
                if (route.getSrcAirportID() != null) {
                    // also check that the airport of that id exists in the repository
                    if (Repository.airportRepository.getAirports().get(route.getSrcAirportID()) != null) {
                        depSet.add(Repository.airportRepository.getAirports().get(route.getSrcAirportID()).getCountry());
                    }
                }

                for (String r : route.getEquipment()) {
                    if (!r.isEmpty()) {
                        equipmentSet.add(r);
                    }
                }
            }
            updateEquipBox();
            updateDepCountryBox();
            updateDestCountryBox();
        }


    }

    private void loadSerializedRoute() {
        Collection<Route> routes = Repository.routeRepository.getRoutes().values();
        for (Route route : routes) {
            routeTData.add(new routeTable(route.getAirline(), String.valueOf(route.getAirlineID()),
                    route.getSrcAirport(), String.valueOf(route.getSrcAirportID()),
                    route.getDestAirport(), String.valueOf(route.getDestAirportID()),
                    route.getCodeshare(), String.valueOf(route.getStops()),
                    route.getEquipment().stream().collect(Collectors.joining(", "))));

            // if destination country id is not null then add it to destSet
            if (route.getDestAirportID() != null) {
                // also check that the airport of that id exists in the repository
                if (Repository.airportRepository.getAirports().get(route.getDestAirportID()) != null) {
                    destSet.add(Repository.airportRepository.getAirports().get(route.getDestAirportID()).getCountry());
                }
            }
            // if departure country id is not null then add it to depSet
            if (route.getSrcAirportID() != null) {
                // also check that the airport of that id exists in the repository
                if (Repository.airportRepository.getAirports().get(route.getSrcAirportID()) != null) {
                    depSet.add(Repository.airportRepository.getAirports().get(route.getSrcAirportID()).getCountry());
                }
            }

            // loop through the equipments given and add it to the equipmentSet
            for (String r : route.getEquipment()) {
                if (!r.isEmpty()) {
                    equipmentSet.add(r);
                }
            }
        }
        updateEquipBox();
        updateDepCountryBox();
        updateDestCountryBox();
    }

    private void updateAirlineSearch() {
        String text = airlineSearch.getText();
        airlineSearch.setText(text + " ");
        airlineSearch.setText(text);

    }

    private void updateAirportSearch() {
        String text = airportSearch.getText();
        airportSearch.setText(text + " ");
        airportSearch.setText(text);

    }

    private void updateRouteSearch() {
        String text = routeSearch.getText();
        routeSearch.setText(text + " ");
        routeSearch.setText(text);

    }

    private void loadDefaultAirports() throws IOException, URISyntaxException {
        if (Repository.airportRepository != null) {
            loadSerializedAirport();
        } else {
            Repository.airportRepository = new AirportRepository();
            InputStream file = getClass().getResourceAsStream("/airports.dat");
            //File file = new File(getClass().getClassLoader().getResource("airports.dat").toURI());
            if (file != null) {
                //System.out.println("ooooooooh yyyyyyyyyyahhhhhh");
                insertEmptyAirportTable(file);
            }
        }
    }

    private void insertEmptyAirportTable(InputStream file) throws IOException {
        AirportValidator validator = new AirportValidator(file);
        ArrayList<Airport> airports = validator.makeAirports();
        validator = null;
        if (airports != null) {
            for (int i = 0; i < airports.size(); i++) {
                Airport airport = airports.get(i);
                Repository.airportRepository.addAirport(airport);
                airportTData.add(new airportTable(airport.getID(), airport.getName(), airport.getCity(),
                        airport.getCountry(), airport.getIATA(), airport.getICAO(), airport.getLatitude(),
                        airport.getLongitude(), airport.getAltitude(), airport.getTimezone(), airport.getDST().toText(),
                        airport.getTz()));
                if (airport.getCountry() != null) {
                    airportCountrySet.add(airport.getCountry());
                }
                updateAirportCountryBox();
            }
        }

    }

    private void loadSerializedAirport() {
        Collection<Airport> airports = Repository.airportRepository.getAirports().values();
        for (Airport airport : airports) {
            airportTData.add(new airportTable(airport.getID(), airport.getName(), airport.getCity(),
                    airport.getCountry(), airport.getIATA(), airport.getICAO(), airport.getLatitude(),
                    airport.getLongitude(), airport.getAltitude(), airport.getTimezone(), airport.getDST().toText(),
                    airport.getTz()));
            if (airport.getCountry() != null) {
                airportCountrySet.add(airport.getCountry());
            }
            updateAirportCountryBox();
        }
    }


    /**
     * Allows the user to select the direct flight filter option.
     *
     * @throws IOException throws IOException error
     */
    public void selectDirect() throws IOException {
        updateRouteSearch();
    }

    /**
     * Allows the user to select the indirect flight filter option.
     *
     * @throws IOException throws IOException error
     */
    public void selectIndirect() throws IOException {
        updateRouteSearch();
    }

    /**
     * Filters the list of airlines by country, leaving only airlines from the selected county.
     *
     * @throws IOException throws IOException error
     */
    public void filterAirlineCountry() throws IOException {
        updateAirlineSearch();
    }

    /**
     * Filters the list of airports by country, leaving only airports from the selected county.
     *
     * @throws IOException throws IOException error
     */
    public void filterAirportCountry() throws IOException {
        updateAirportSearch();
    }

    /**
     * Filters by departure country, only showing routes departing from the selected country.
     *
     * @throws IOException throws IOException error
     */
    public void filterDepCountry() throws IOException {
        updateRouteSearch();
    }

    /**
     * Filters by destination country, only showing routes that arrive in the selected country.
     *
     * @throws IOException throws IOException error
     */
    public void filterDestCountry() throws IOException {
        updateRouteSearch();
    }

    /**
     * Filters by the equipment in a route, only showing routes that utilize that equipment.
     *
     * @throws IOException throws IOException error
     */
    public void filterEquipment() throws IOException {
        updateRouteSearch();
    }

    /**
     * Filters by whether or not the airline is active, only showing active airlines.
     *
     * @throws IOException throws IOException error
     */
    public void selectActiveAirlines() throws IOException {
        updateAirlineSearch();
    }

    /**
     * Filters by whether or not the airline is active, only showing inactive airlines.
     *
     * @throws IOException throws IOException error
     */
    public void selectInactiveAirlines() throws IOException {
        updateAirlineSearch();
    }

//    /**
//     * Allows the user to load airline data from a file
//     *
//     * @throws IOException throws IOException error
//     */
//    public void loadAirline() throws IOException {
//        try {
//            menuBarController.loadAirline();
//        } catch (NullPointerException e) {
//            // Do Nothing
//
//        }
//    }

    /**
     * Allows the user to load airline data from a file
     *
     * @throws IOException throws IOException error
     */
    public void loadAirline() throws IOException {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        File in = fileChooser.showOpenDialog(stage);
        if (in != null && in.exists()) {
            InputStream file = new FileInputStream(in);
            goToDataTab(airlineLabel);
            insertAirlineTable(file);
        }
    }


    /**Insert the airlines in a given file into the airline table GUI checking for duplicates
     *
     * @param file InputStream
     * @throws IOException throws IOException error
     */
    public void insertAirlineTable(InputStream file) throws IOException {
        AirlineValidator validator = new AirlineValidator(file);
        ArrayList<Airline> airlines = validator.makeAirlines();
        validator = null;
        if (airlines != null) {
            for (int i = 0; i < airlines.size(); i++) {
                Airline airline = airlines.get(i);
                // if the airline ID already exists in the repository, warn the user
                if (!Repository.airlineRepository.getAirlines().containsKey(airline.getID())) {
                    Repository.airlineRepository.addAirline(airline);
                    airlineTData.add(new airlineTable(airline.getID(), airline.getName(),
                            airline.getAlias(), airline.getIATA(),
                            airline.getICAO(), airline.getCallsign(),
                            airline.getCountry(), airline.getActive()));
                    if (airline.getCountry() != null) {
                        airlineCountrySet.add(airline.getCountry());
                    }
                } else {
                    duplicateIDAlert("Please fix the conflict and reupload the file.", airline.getID());
                    break;
                }
                updateAirlineCountryBox();
            }
        }
    }


    private void duplicateIDAlert(String message, Integer id) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("ID: " + id + " already exists in the system.");
        alert.setContentText(message);
        alert.showAndWait();
    }

//
//    /**
//     * Allows the user to load route data from a file
//     *
//     * @throws IOException throws IOException error
//     */
//    public void loadRoute() throws IOException {
//        try {
//            menuBarController.loadRoute();
//        } catch (NullPointerException e) {
//            // Do nothing
//        }
//    }

    /**
     * Allows the user to load route data from a file
     *
     * @throws IOException throws IOException error
     */
    public void loadRoute() throws IOException {

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        File in = fileChooser.showOpenDialog(stage);
        if (in != null && in.exists()) {
            InputStream file = new FileInputStream(in);
            //System.out.println("file opneeeedddd");
            goToDataTab(routeLabel);
            insertRouteTable(file);
        }
    }

    // change tab to data tab if its not on it already and switch the selection tab to the given name
    private void goToDataTab(String name) {
        if (!tabPane.getSelectionModel().equals(dataTab)) {
            tabPane.getSelectionModel().select(dataTab);
        }
        datalist.getSelectionModel().select(name);
    }


//    /**
//     * Allows the user to load airport data from a file
//     *
//     * @throws IOException throws IOException error
//     */
//    public void loadAirport() throws IOException {
//        try {
//            menuBarController.loadAirport();
//        } catch (NullPointerException e) {
//            // Do nothing
//        }
//    }


    /**
     * Allows the user to load airport data from a file
     *
     * @throws IOException throws IOException error
     */
    public void loadAirport() throws IOException {

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        File in = fileChooser.showOpenDialog(stage);
        if (in != null && in.exists()) {
            InputStream file = new FileInputStream(in);
            //System.out.println("file opened oh ye bb~");
            goToDataTab(airportLabel);
            insertAirportTable(file);
        }
    }

    /**Insert the airports in a given file into the airport table GUI checking for duplicates
     *
     * @param file InputStream
     * @throws IOException throws IOException error
     */
    public void insertAirportTable(InputStream file) throws IOException {
        AirportValidator validator = new AirportValidator(file);
        ArrayList<Airport> airports = validator.makeAirports();
        validator = null;
        if (airports != null) {
            for (int i = 0; i < airports.size(); i++) {
                Airport airport = airports.get(i);
                if (!Repository.airportRepository.getAirports().containsKey(airport.getID())) {
                    Repository.airportRepository.addAirport(airport);
                    airportTData.add(new airportTable(airport.getID(), airport.getName(), airport.getCity(),
                            airport.getCountry(), airport.getIATA(), airport.getICAO(), airport.getLatitude(),
                            airport.getLongitude(), airport.getAltitude(), airport.getTimezone(), airport.getDST().toText(),
                            airport.getTz()));
                    if (airport.getCountry() != null) {
                        airportCountrySet.add(airport.getCountry());
                    }
                } else {
                    duplicateIDAlert("Please fix the conflict and reupload the file.", airport.getID());
                    break;
                }

            }
            updateAirportCountryBox();
        }
    }


    /**
     * Calculates the distance between two coordinates on the map.
     */
    public void calcDistance() {
        if (pointALat != null && pointALon != null &&
                pointBLat != null && pointBLon != null) {

            final int R = 6371; // Radius of the earth in km

            Double latDistance = Math.toRadians(pointBLat - pointALat);
            Double lonDistance = Math.toRadians(pointBLon - pointALon);
            Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                    + Math.cos(Math.toRadians(pointALat)) * Math.cos(Math.toRadians(pointBLat))
                    * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
            Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

            double distance = R * c; // convert to meters
            calcdDistance.setText(String.format("%.2f", distance));
        }
    }


    public void getFlightPath() throws UnirestException, IOException, InterruptedException {
        if (!(pointAICAO == null) && !(pointBICAO == null)) {
            HttpResponse<JsonNode> getID = Unirest.get("https://api.flightplandatabase.com/search/plans?fromICAO=" + pointAICAO + "&toICAO=" + pointBICAO + "&limit=1")
                    .asJson();
            try {
                Integer id = getID.getBody().getArray().getJSONObject(0).getInt("id");
                String id2 = Integer.toString(id);

                String something = Unirest.get("https://api.flightplandatabase.com/plan/" + id2).header("Accept", "text/csv").asString().getBody();

                if (!something.contains("Bad Request")) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("Flight plan found!");
                    alert.setContentText("There was a matching plan (id: " + id2 + ")\nfrom FlightPlanDatabase.com." +
                            "\n\nPress 'Simple path' to load point-to-point path." +
                            "\nPress 'Online path' to load plan "+id2+".");

                    ButtonType buttonTypeOne = new ButtonType("Simple path");
                    ButtonType buttonTypeTwo = new ButtonType("Online path");
                    ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                    alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == buttonTypeOne){
                        String makeCSV = "APT,"+pointAICAO+",0,"+pointALat+","+pointALon+"\n" +
                                "APT,"+pointBICAO+",0,"+pointBLat+","+pointBLon;
                        loadFlight2(makeCSV);
                    } else if (result.get() == buttonTypeTwo) {
                        loadFlight2(something);
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("Look, an Error Dialog");
                    alert.setContentText("There is no flight path from " + pointAICAO + " to " + pointBICAO + "\nat FlightPlanDatabase.com.");

                    alert.showAndWait();
                }

            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("There is no flight plan found on\nFlightPlanDatabase.com.");
                alert.setContentText("Press OK to load a simple path.\nPress CANCEL to return to airport table.");

                ButtonType buttonTypeOne = new ButtonType("OK");
                ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonTypeOne){
                    String makeCSV = "APT,"+pointAICAO+",0,"+pointALat+","+pointALon+"\n" +
                            "APT,"+pointBICAO+",0,"+pointBLat+","+pointBLon;
                    loadFlight2(makeCSV);
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Woops");
            alert.setContentText("Please select Airport A and Airport B\nbefore continuing.");

            alert.showAndWait();
        }
    }

    private void loadFlight2(String someCSV) throws IOException {
        InputStream file = new ByteArrayInputStream(someCSV.getBytes(StandardCharsets.UTF_8));
        flightTabController.insertFlightTable(file);
    }

    public void updateAirlineCountryBox() {
        // clear the current combo box
        airlineCountryFilter.getItems().clear();
        // if the combo box doesn't have --ALL COUNTRIES-- then add one
        if (!airlineCountryFilter.getItems().contains(allCountriesTag)) {
            airlineCountryFilter.getItems().add(allCountriesTag);
        }

        // loop through the current airlineCountrySet
        Iterator itr = airlineCountrySet.iterator();
        while (itr.hasNext()) {
            airlineCountryFilter.getItems().add(itr.next());
        }
    }

    public void updateAirportCountryBox() {
        airportCountryFilter.getItems().clear();
        if (!airportCountryFilter.getItems().contains(allCountriesTag)) {
            airportCountryFilter.getItems().add(allCountriesTag);
        }
        Iterator itr = airportCountrySet.iterator();
        while (itr.hasNext()) {
            airportCountryFilter.getItems().add(itr.next());
        }
    }


    public void updateDepCountryBox() {
        routeDepCountryFilter.getItems().clear();
        if (!routeDepCountryFilter.getItems().contains(departureCountryTag)) {
            routeDepCountryFilter.getItems().add(departureCountryTag);
        }
        Iterator itr = depSet.iterator();
        while (itr.hasNext()) {
            routeDepCountryFilter.getItems().add(itr.next());
        }
    }

    public void updateDestCountryBox() {
        routeDestCountryFilter.getItems().clear();
        if (!routeDestCountryFilter.getItems().contains(destinationCountryTag)) {
            routeDestCountryFilter.getItems().add(destinationCountryTag);
        }
        Iterator itr = destSet.iterator();
        while (itr.hasNext()) {
            routeDestCountryFilter.getItems().add(itr.next());
        }
    }


    public void updateEquipBox() {
        // clear the current combo box
        routeEquipFilter.getItems().clear();
        // if the combo box doesn't have --ALL EQUIPMENTS-- then add one
        if (!routeEquipFilter.getItems().contains(allEquipmentsTag)) {
            routeEquipFilter.getItems().add(allEquipmentsTag);
        }

        // loop through the current airlineCountrySet
        Iterator itr = equipmentSet.iterator();
        while (itr.hasNext()) {
            routeEquipFilter.getItems().add(itr.next());
        }
    }

//        /**
//     * Allows the user to load a flight from a file
//     *
//     * @throws IOException throws IOException error
//     */
//    public void loadFlight() throws IOException {
//        flightTabController.loadFlight();
//    }

//    /**
//     * Allows the user to load a flight from a file
//     *
//     * @throws IOException throws IOException error
//     */
//    public void loadFlight() throws IOException {
//
//        Stage stage = new Stage();
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Open file");
//        File in = fileChooser.showOpenDialog(stage);
//        if (in != null && in.exists()) {
//            InputStream file = new FileInputStream(in);
//            //System.out.println("oooo yah flights");
//            // change tab to flight tab if its not on it already
//            if (!tabPane.getSelectionModel().equals(flightTab)) {
//                tabPane.getSelectionModel().select(flightTab);
//            }
//            insertFlightTable(file);
//        }
//    }



    /**
     * Clears the airline table and AirlineRepository then replaces them with the default airlines
     * @throws IOException when default airline file cannot be read
     */
    public void resetAirline() throws IOException {
        boolean result = resetConformation();
        if (result) {
            clearAirlineTable();
            Repository.airlineRepository = new AirlineRepository();
            InputStream file = getClass().getResourceAsStream("/airlines.dat");
            //File file = new File(getClass().getClassLoader().getResource("airlines.dat").toURI());
            if (file != null) {
                insertAirlineTable(file);
            }
            Repository.serializeObject(Repository.airlineRepository, "airline");
        }
    }

    /**
     * Clears the airport table and AirportRepository then replaces them with the default airports
     * @throws IOException when default airport file cannot be read
     */
    public void resetAirport() throws IOException {
        boolean result = resetConformation();
        if (result) {
            clearAirportTable();
            Repository.airportRepository = new AirportRepository();
            InputStream file = getClass().getResourceAsStream("/airports.dat");
            //File file = new File(getClass().getClassLoader().getResource("airlines.dat").toURI());
            if (file != null) {
                insertAirportTable(file);
            }
            Repository.serializeObject(Repository.airportRepository, "airport");
        }
    }

    /**
     * Clears the route table and routeRepository then replaces them with the default routes
     * @throws IOException when default route file cannot be read
     */
    public void resetRoute() throws IOException {
        boolean result = resetConformation();
//        boolean done = false;

        if (result) {
            Label label = new Label();
            label.setText("loading: ");
            ProgressBar pb = new ProgressBar();
            ProgressIndicator pin = new ProgressIndicator();
            Text text = new Text();
            text.setText("\nThis action might take longer usual.\nPlease wait :)\n");
            Button toClose = new Button();
            toClose.setText("Click to close!");
            toClose.setVisible(false);

            Task<Void> task = new Task<Void>() {
                @Override
                public Void call() {
                    // process long-running computation, data retrieval, etc...
                    clearRouteTable();
                    Repository.routeRepository = new RouteRepository();
                    InputStream file = getClass().getResourceAsStream("/routes.dat");
                    //File file = new File(getClass().getClassLoader().getResource("airlines.dat").toURI());
                    if (file != null) {
                        try {
                            insertRouteTable(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Repository.serializeObject(Repository.routeRepository, "route");
                    return null;
                }
            };

            task.setOnSucceeded(e -> {
                pb.setProgress(1.0f);
                pin.setProgress(1.0f);
                toClose.setVisible(true);
            });

            new Thread(task).start();

            pb.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);

            pin.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
            HBox hb = new HBox();
            hb.setSpacing(5);
            hb.setAlignment(Pos.CENTER);
            hb.getChildren().addAll(label, pb, pin);

            Stage stage = new Stage();
            Scene scene = new Scene(VBoxBuilder.create()
                    .children(hb, text, toClose)
                    .alignment(Pos.CENTER)
                    .padding(new Insets(10))
                    .build(), 300, 170);
            stage.setScene(scene);
            stage.setTitle("Progress Controls");

            stage.show();
            toClose.setOnAction( event ->
                    stage.close()
            );
        }
    }

    private boolean resetConformation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure you want to reset your selected data?");
        alert.setContentText("This will replace the data with the default data.\nThis may take a few moments" +
                "\n\nWARNING: The action cannot be undone and may take a while.\n");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

//    /**
//     * Shows Aviation Information Reader's help page
//     */
//    public void getHelp() {
//        //System.out.println("help");
//        try {
//            FXMLLoader fxml = new FXMLLoader();
//            fxml.setLocation(getClass().getClassLoader().getResource("help.fxml"));
//            Parent root = fxml.load();
//            //Parent root = FXMLLoader.load(getClass().getResource("help.fxml"));
//            Stage stage = new Stage();
//            stage.initModality(Modality.APPLICATION_MODAL);
//            stage.setResizable(false);
//            stage.setTitle("Aviation Information Reader Help");
//            stage.setScene(new Scene(root, 600, 400));
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    /**Insert the routes in a given file into the route table GUI checking for duplicates
     *
     * @param file InputStream
     * @throws IOException throws IOException error
     */
    public void insertRouteTable(InputStream file) throws IOException {
        RouteValidator validator = new RouteValidator(file);
        ArrayList<Route> routes = validator.makeroutes();
        validator = null;
        if (routes != null) {
            for (int i = 0; i < routes.size(); i++) {
                Route route = routes.get(i);
                if (diffRoutes(route)) {
                    Repository.routeRepository.addRoute(route);
                    routeTData.add(new routeTable(route.getAirline(), String.valueOf(route.getAirlineID()),
                            route.getSrcAirport(), String.valueOf(route.getSrcAirportID()),
                            route.getDestAirport(), String.valueOf(route.getDestAirportID()),
                            route.getCodeshare(), String.valueOf(route.getStops()),
                            route.getEquipment().stream().collect(Collectors.joining(", "))));

                    // if destination country id is not null then add it to destSet
                    if (route.getDestAirportID() != null) {
                        // also check that the airport of that id exists in the repository
                        if (Repository.airportRepository.getAirports().get(route.getDestAirportID()) != null) {
                            destSet.add(Repository.airportRepository.getAirports().get(route.getDestAirportID()).getCountry());
                        }
                    }
                    // if departure country id is not null then add it to depSet
                    if (route.getSrcAirportID() != null) {
                        // also check that the airport of that id exists in the repository
                        if (Repository.airportRepository.getAirports().get(route.getSrcAirportID()) != null) {
                            depSet.add(Repository.airportRepository.getAirports().get(route.getSrcAirportID()).getCountry());
                        }
                    }

                    // loop through the equipments given and add it to the equipmentSet
                    for (String r : route.getEquipment()) {
                        if (!r.isEmpty()) {
                            equipmentSet.add(r);
                        }
                    }
                } else {
                    duplicateAlert("The system has read " + i + " route(s) from your file.\nPlease upload a file with different routes.");
                    break;
                }
                updateEquipBox();
                updateDepCountryBox();
                updateDestCountryBox();
                //updateMapCountryBox(); // add this back once mapp allgood
            }
        }

    }

    private void duplicateAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("The route already exists in the system.");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Compare routes, return true if the routes are different otherwise false
    private boolean diffRoutes(Route route) {
        if (Repository.routeRepository.getRoutes().containsKey(RouteRepository.getKey(route))) {
            return false;
        } else {
            return true;
        }
    }

    public void clearRouteTable() {
        routeTData.removeAll(routeTData);
    }

    public void clearAirlineTable() {
        airlineTData.removeAll(airlineTData);
    }

    public void clearAirportTable() {
        airportTData.removeAll(airportTData);
    }

}
