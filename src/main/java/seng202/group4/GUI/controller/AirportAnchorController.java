package seng202.group4.GUI.controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import seng202.group4.App;
import seng202.group4.GUI.table.AirportTable;
import seng202.group4.GUI.ButtonResult;
import seng202.group4.data.dataType.Airport;
import seng202.group4.data.parser.validator.AirportValidator;
import seng202.group4.data.repository.AirportRepository;
import seng202.group4.data.repository.Repository;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by Pang on 25/09/16.
 */
public class AirportAnchorController implements Initializable{

    @FXML
    private TableView airportTableID;

    @FXML
    private AnchorPane airportPane;

    // airport FXML
    @FXML
    private TextField airportSearch;

    @FXML
    private ComboBox airportCountryFilter;

    @FXML
    private TextField airportA;

    @FXML
    private TextField airportB;

    @FXML
    private TextField calcdDistance;

    @FXML
    private Button createFlightButton;

    @FXML
    private TableColumn<AirportTable, String> apid;

    @FXML
    private TableColumn<AirportTable, String> apname;

    @FXML
    private TableColumn<AirportTable, String> apcity;

    @FXML
    private TableColumn<AirportTable, String> apcountry;

    @FXML
    private TableColumn<AirportTable, String> apiata;

    @FXML
    private TableColumn<AirportTable, String> apicao;

    @FXML
    private TableColumn<AirportTable, String> aplat;

    @FXML
    private TableColumn<AirportTable, String> aplong;

    @FXML
    private TableColumn<AirportTable, String> apalt;

    @FXML
    private TableColumn<AirportTable, String> aptimezone;

    @FXML
    private TableColumn<AirportTable, String> apdst;

    @FXML
    private TableColumn<AirportTable, String> aptz;

    // controllers
    private DataTabController mainController;
    private FlightTabController flightTabController;

    // the default string for country combo box
    private String allCountriesTag;

    // create table data
    private ObservableList<AirportTable> airportTData = FXCollections.observableArrayList();

    // create parallel hash map
    private HashMap<Integer, AirportTable> airportTableMap = new HashMap<Integer, AirportTable>();

    // airportCountrySet holds all the countries uploaded to airport
    private TreeSet airportCountrySet = new TreeSet();

    // create set of IATAs
    private HashSet<String> IATASet = new HashSet<String>();

    private Double pointALat;
    private Double pointALon;
    private Double pointBLat;
    private Double pointBLon;
    private String pointAICAO;
    private String pointBICAO;

    /**
     * Where the program starts, initializes things like listeners and starts running the GUI.
     *
     * @param location  URL
     * @param resources ResourceBundle
     */
    public void initialize(URL location, ResourceBundle resources) {
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
            final TableRow<AirportTable> row = new TableRow<>();
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
                calcDistance();
            });
            addB.setOnAction(event -> {
                airportB.setText(row.getItem().getAtname());
                pointBLat = row.getItem().getAtlatitude();
                pointBLon = row.getItem().getAtlongitude();
                pointBICAO = row.getItem().getAticao();
                calcDistance();
            });
            removeItem.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Are you sure you want to delete?");
                alert.setContentText("Pressing OK will delete the row(s).\nWARNING: The action cannot be undone.\n");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    ObservableList<AirportTable> selectedItems = airportTableID.getSelectionModel().getSelectedItems();
                    for (AirportTable airport : selectedItems) {
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
    }

    /**
     * Sets the main controller and retrieves private variables from the main controller.
     *
     * @param controller Controller
     */
    public void setMainController(DataTabController controller) {
        this.mainController = controller;
        //this.flightTabController = mainController.getFlightTabController();
        this.allCountriesTag = mainController.getAllCountriesTag();
    }

    /**
     * Allows the user to load airport data from a file.
     *
     * @throws IOException throws IOException error
     */
    public void loadAirport() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        File in = fileChooser.showOpenDialog(App.primaryStage);
        if (in != null && in.exists()) {
            InputStream file = new FileInputStream(in);
            mainController.goToDataTab(mainController.getAirportLabel());
            insertAirportTable(file);
        }
    }

    private void loadDefaultAirports() throws IOException, URISyntaxException {
        if (Repository.airportRepository != null) {
            loadSerializedAirport();
        } else {
            Repository.airportRepository = new AirportRepository();
            InputStream file = getClass().getResourceAsStream("/airports.dat");
            if (file != null) {
                insertAirportTable(file);
            }
        }
    }

    private void addNewAirport(Airport airport) {
        Repository.airportRepository.addAirport(airport);
        AirportTable airportDataEntry = new AirportTable(airport.getID(), airport.getName(), airport.getCity(),
                airport.getCountry(), airport.getIATA(), airport.getICAO(), airport.getLatitude(),
                airport.getLongitude(), airport.getAltitude(), airport.getTimezone(), airport.getDST().toText(),
                airport.getTz());
        airportTData.add(airportDataEntry);
        airportTableMap.put(airport.getID(), airportDataEntry);
        if (!(airport.getIATA() == null || airport.getIATA().equals(""))) {
            IATASet.add(airport.getIATA());
        }
        if (airport.getCountry() != null) {
            airportCountrySet.add(airport.getCountry());
        }
    }

    /* -------------------------------------------------------------------------------------------------------------------------------------------------------------- */
    /* Airport table methods below*/

    private void loadSerializedAirport() {
        Collection<Airport> airports = Repository.airportRepository.getAirports().values();
        for (Airport airport : airports) {
            addNewAirport(airport);
        }
        updateAirportCountryBox();
    }

    /**Insert the airports in a given file into the airport table GUI checking for duplicates
     *
     * @param file InputStream
     * @throws IOException throws IOException error
     */
    private void insertAirportTable(InputStream file) throws IOException {
        AirportValidator validator = new AirportValidator(file);
        ArrayList<Airport> airports = validator.makeAirports();
        ButtonResult buttonResult = null;
        for (Airport airport : airports) {
            if (!Repository.airportRepository.getAirports().containsKey(airport.getID()) &&
                    (!IATASet.contains(airport.getIATA()))) {
                addNewAirport(airport);
            } else {
                // Override data option
                if (buttonResult == ButtonResult.OVERRIDEALL) {
                    overrideAirport(airport);
                } else if (buttonResult != ButtonResult.IGNOREALL) {
                    if (!Repository.airportRepository.getAirports().containsKey(airport.getID())) {
                        // Not clashing ID => only IATA clashing
                        buttonResult = dataOverridePopup(airport.getIATA());
                    } else if (!IATASet.contains(airport.getIATA())) {
                        // Not clashing IATA => only ID clashing
                        buttonResult = dataOverridePopup(airport.getID());
                    } else {
                        // Both clashing
                        buttonResult = dataOverridePopup(airport.getID(), airport.getIATA());
                    }
                    if (buttonResult == ButtonResult.OVERRIDE || buttonResult == ButtonResult.OVERRIDEALL) {
                        overrideAirport(airport);
                    } else if (buttonResult == ButtonResult.CANCEL) {
                        break;
                    }

                }
            }
        }
        updateAirportCountryBox();
    }

    private void overrideAirport(Airport airport) {
        Repository.airportRepository.getAirports().put(airport.getID(), airport);
        AirportTable oldAirport = airportTableMap.get(airport.getID());
        oldAirport.setAtname(airport.getName());
        oldAirport.setAtcity(airport.getCity());
        oldAirport.setAtcountry(airport.getCountry());
        oldAirport.setAtiata(airport.getIATA());
        oldAirport.setAticao(airport.getICAO());
        oldAirport.setAtlatitude(airport.getLatitude());
        oldAirport.setAtlongitude(airport.getLongitude());
        oldAirport.setAtaltitude(airport.getAltitude());
        oldAirport.setAttimezone(airport.getTimezone());
        oldAirport.setAtdst(airport.getDST().toText());
        oldAirport.setAttzdatabase(airport.getTz());
    }

    // Popup with clashing airport ID
    private ButtonResult dataOverridePopup(int ID) {
        return OverrideDataController.getPopUpResult("Airport with ID " + ID + " already exists in the system");
    }

    // Popup with clashing airport ID and IATA
    private ButtonResult dataOverridePopup(int ID, String IATA) {
        return OverrideDataController.getPopUpResult("Airport with ID " + ID + " and IATA " + IATA + " already exists in the system");
    }

    // Popup with clashing IATA
    private ButtonResult dataOverridePopup(String IATA) {
        return OverrideDataController.getPopUpResult("Airport with IATA " + IATA + " already exists in the system");
    }

    /**
     * Clears the airport table and AirportRepository then replaces them with the default airports.
     *
     * @throws IOException when default airport file cannot be read
     */
    public void resetAirport() throws IOException {
        boolean result = mainController.resetConformation();
        if (result) {
            clearAirportTable();
            InputStream file = getClass().getResourceAsStream("/airports.dat");
            //File file = new File(getClass().getClassLoader().getResource("airlines.dat").toURI());
            if (file != null) {
                insertAirportTable(file);
            }
            Repository.serializeObject(Repository.airportRepository, "airport");
        }
    }

    /**
     * Clears the airport table and AirportRepository.
     */
    public void deleteAllAirports() {
        boolean result = mainController.deleteAllConformation();
        if (result) {
            clearAirportTable();
            Repository.serializeObject(Repository.airportRepository, "airport");
        }
    }

    private void clearAirportTable() {
        airportTData.clear();
        airportTableMap = new HashMap<>();
        IATASet = new HashSet<>();
        Repository.airportRepository = new AirportRepository();
    }

    /* Airport table methods above*/
    /* -------------------------------------------------------------------------------------------------------------------------------------------------------------- */

    /**
     * Calculates the distance between two coordinates on the map.
     */
    public void calcDistance() {
        if (pointALat != null && pointALon != null &&
                pointBLat != null && pointBLon != null) {
            createFlightButton.setDisable(false);
            String distanceText = AirportRepository.calculateDistance(pointALat, pointALon, pointBLat, pointBLon);
            calcdDistance.setText(distanceText);
        }
    }

    /**
     * Gets flight path from flightplandatabase.
     *
     * @throws UnirestException when an error occurs
     * @throws IOException when an error occurs
     * @throws InterruptedException when an error occurs
     */
    public void getFlightPath() throws UnirestException, IOException, InterruptedException {
        // get the flight tab controller
        flightTabController = mainController.getFlightTabController();

        if (!(pointAICAO == null) && !(pointBICAO == null)) {
            HttpResponse<JsonNode> getID = Unirest.get("https://api.flightplandatabase.com/search/plans?fromICAO=" + pointAICAO + "&toICAO=" + pointBICAO + "&limit=1").asJson();
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
            alert.setHeaderText("Whoops");
            alert.setContentText("Please select Airport A and Airport B\nbefore continuing.");

            alert.showAndWait();
        }
    }

    /**
     * Filters the list of airports by country, leaving only airports from the selected county.
     *
     * @throws IOException throws IOException error
     */
    public void filterAirportCountry() throws IOException {
        updateAirportSearch();
    }

    private void updateAirportCountryBox() {
        airportCountryFilter.getItems().clear();
        if (!airportCountryFilter.getItems().contains(allCountriesTag)) {
            airportCountryFilter.getItems().add(allCountriesTag);
        }
        Iterator itr = airportCountrySet.iterator();
        while (itr.hasNext()) {
            airportCountryFilter.getItems().add(itr.next());
        }
    }

    private void updateAirportSearch() {
        String text = airportSearch.getText();
        airportSearch.setText(text + " ");
        airportSearch.setText(text);
    }


    /**
     * Searches through the airports in the table dependent on a specific search entry by the user.
     */
    private void searchAirports() {
        String intPattern = "[-]?[0-9]*[.]?[0-9]+";
        // searching for airline
        FilteredList<AirportTable> airportTableFiltered = new FilteredList<>(airportTData, p -> true);

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
        SortedList<AirportTable> airportTableSorted = new SortedList<>(airportTableFiltered);

        // Bind the SortedList comparator to the TableView comparator
        airportTableSorted.comparatorProperty().bind(airportTableID.comparatorProperty());

        // Add sorted (and filtered) data to the table
        airportTableID.setItems(airportTableSorted);

    }

    private void loadFlight2(String someCSV) throws IOException {
        InputStream file = new ByteArrayInputStream(someCSV.getBytes(StandardCharsets.UTF_8));
        flightTabController.insertFlightTable(file);
    }

    /**
     * Returns airportTData to be used on other class.
     *
     * @return ObservableList<AirportTable>
     */
    public ObservableList<AirportTable> getAirportTData() {
        return airportTData;
    }

}