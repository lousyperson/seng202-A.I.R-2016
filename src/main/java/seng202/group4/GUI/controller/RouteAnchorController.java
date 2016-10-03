package seng202.group4.GUI.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group4.App;
import seng202.group4.GUI.table.RouteTable;
import seng202.group4.data.dataType.Route;
import seng202.group4.data.parser.validator.RouteValidator;
import seng202.group4.data.repository.Repository;
import seng202.group4.data.repository.RouteRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Pang on 26/09/16.
 */
public class RouteAnchorController implements Initializable {

    @FXML
    private TableView routeTableID;

    @FXML
    private AnchorPane routePane;

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

    @FXML
    private TableColumn<RouteTable, String> airline;

    @FXML
    private TableColumn<RouteTable, Integer> airlineID;

    @FXML
    private TableColumn<RouteTable, String> source;

    @FXML
    private TableColumn<RouteTable, Integer> sourceID;

    @FXML
    private TableColumn<RouteTable, String> dest;

    @FXML
    private TableColumn<RouteTable, Integer> destID;

    @FXML
    private TableColumn<RouteTable, String> codeshare;

    @FXML
    private TableColumn<RouteTable, Integer> stops;

    @FXML
    private TableColumn<RouteTable, String> equipment;

    // controllers
    private DataTabController mainController;

    // variables from other controllers
    private String allCountriesTag;
    private String routeLabel;

    // initial text in the combo boxes
    private String allEquipmentsTag = " --ALL EQUIPMENTS-- ";
    private String departureCountryTag = " --DEPART FROM-- ";
    private String destinationCountryTag = " --ARRIVE TO-- ";


    // depSet holds all the departure country names uploaded to routes
    private TreeSet depSet = new TreeSet();

    // dest holds all the destination country names uploaded to routes
    private TreeSet destSet = new TreeSet();

    // equipmentSet holds all the equipment uploaded to route
    private TreeSet equipmentSet = new TreeSet();

    // create route data
    private ObservableList<RouteTable> routeTData = FXCollections.observableArrayList();

    /**
     * Sets the main controller and retrieves private variables from the main controller.
     *
     * @param controller Controller
     */
    public void setMainController(DataTabController controller) {
        this.mainController = controller;
        this.allCountriesTag = mainController.getAllCountriesTag();
        this.routeLabel = mainController.getRouteLabel();
    }

    /**
     * Where the program starts, initializes things like listeners and starts running the GUI.
     *
     * @param location URL
     * @param resources ResourceBundle
     */
    public void initialize(URL location, ResourceBundle resources) {
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

        // set items to route table
        routeTableID.setItems(routeTData);

        // load default route list
        try {
            loadDefaultRoute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // enable multi select on table
        routeTableID.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // enable delete on table
        enableDeleteRoute();

        // listen for route search queries
        searchRoutes();
    }


    private void enableDeleteRoute(){
        routeTableID.setRowFactory(tableView -> {
            final TableRow<RouteTable> row = new TableRow<>();
            final ContextMenu rowMenu = new ContextMenu();
            MenuItem removeItem = new MenuItem("Delete");
            row.setOnMouseClicked(event -> {
            });
            removeItem.setOnAction(event -> {
                if (routeTableID.getSelectionModel().getSelectedItems().size() > 5000) {
                    Alert warning = new Alert(Alert.AlertType.WARNING);
                    warning.setTitle("Warning Dialog");
                    warning.setHeaderText("Cannot delete more than 5000 routes at a time.");
                    warning.setContentText("You selected " + routeTableID.getSelectionModel().getSelectedItems().size() + " lines.");

                    warning.showAndWait();

                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("Are you sure you want to delete?");
                    alert.setContentText("Pressing OK will delete the row(s).\nWARNING: The action cannot be undone.\n");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        ObservableList<RouteTable> selectedItems = routeTableID.getSelectionModel().getSelectedItems();
                        for (RouteTable route : selectedItems) {
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
    }

    /**
     * Allows the user to load route data from a file.
     *
     * @throws IOException throws IOException error
     */
    public void loadRoute() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        File in = fileChooser.showOpenDialog(App.primaryStage);
        if (in != null && in.exists()) {
            InputStream file = new FileInputStream(in);
            mainController.goToDataTab(routeLabel);
            insertRouteTable(file);
        }
    }

    /**
     * Insert the routes in a given file into the route table GUI checking for duplicates.
     *
     * @param file InputStream
     * @throws IOException throws IOException error
     */
    private void insertRouteTable(InputStream file) throws IOException {
        RouteValidator validator = new RouteValidator(file);
        ArrayList<Route> routes = validator.makeroutes();
        validator = null;
        if (routes != null) {
            for (int i = 0; i < routes.size(); i++) {
                Route route = routes.get(i);
                if (diffRoutes(route)) {
                    Repository.routeRepository.addRoute(route);
                    routeTData.add(new RouteTable(route.getAirline(), String.valueOf(route.getAirlineID()),
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

    private void updateEquipBox() {
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

    private void updateDepCountryBox() {
        routeDepCountryFilter.getItems().clear();
        if (!routeDepCountryFilter.getItems().contains(departureCountryTag)) {
            routeDepCountryFilter.getItems().add(departureCountryTag);
        }
        Iterator itr = depSet.iterator();
        while (itr.hasNext()) {
            routeDepCountryFilter.getItems().add(itr.next());
        }
    }

    private void updateDestCountryBox() {
        routeDestCountryFilter.getItems().clear();
        if (!routeDestCountryFilter.getItems().contains(destinationCountryTag)) {
            routeDestCountryFilter.getItems().add(destinationCountryTag);
        }
        Iterator itr = destSet.iterator();
        while (itr.hasNext()) {
            routeDestCountryFilter.getItems().add(itr.next());
        }
    }


    private void searchRoutes() {
        // searching for route
        FilteredList<RouteTable> routeTableFiltered = new FilteredList<>(routeTData, p -> true);

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
        SortedList<RouteTable> routeTableSorted = new SortedList<>(routeTableFiltered);

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
     * Clears the route table and routeRepository then replaces them with the default routes.
     *
     * @throws IOException when default route file cannot be read
     */
    public void resetRoute() throws IOException {
        boolean result = mainController.resetConformation();

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
                    InputStream file = getClass().getResourceAsStream("/routes.dat");
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
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(App.primaryStage);

            stage.show();
            toClose.setOnAction( event ->
                    stage.close()
            );
        }
    }

    /**
     * Clears the route table and routeRepository.
     */
    public void deleteAllRoutes() {
        boolean result = mainController.deleteAllConformation();
        if (result) {
            clearRouteTable();
            Repository.serializeObject(Repository.routeRepository, "route");
        }
    }

    // Compare routes, return true if the routes are different otherwise false
    private boolean diffRoutes(Route route) {
        if (Repository.routeRepository.getRoutes().containsKey(RouteRepository.getKey(route))) {
            return false;
        } else {
            return true;
        }
    }

    private void clearRouteTable() {
        routeTData.clear();
        Repository.routeRepository = new RouteRepository();
    }

    private void loadDefaultRoute() throws IOException, URISyntaxException {
        if (Repository.routeRepository != null) {
            loadSerializedRoute();
        } else {
            Repository.routeRepository = new RouteRepository();
            InputStream file = getClass().getResourceAsStream("/routes.dat");
            if (file != null) {
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
                routeTData.add(new RouteTable(route.getAirline(), String.valueOf(route.getAirlineID()),
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
            routeTData.add(new RouteTable(route.getAirline(), String.valueOf(route.getAirlineID()),
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

    private void updateRouteSearch() {
        String text = routeSearch.getText();
        routeSearch.setText(text + " ");
        routeSearch.setText(text);
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
     * Returns routeTData to be used on other class.
     *
     * @return ObservableList<RouteTable>
     */
    public ObservableList<RouteTable> getRouteTData() {
        return routeTData;
    }

}
