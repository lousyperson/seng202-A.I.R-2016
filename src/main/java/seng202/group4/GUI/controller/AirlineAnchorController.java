package seng202.group4.GUI.controller;

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
import seng202.group4.GUI.table.AirlineTable;
import seng202.group4.GUI.ButtonResult;
import seng202.group4.data.dataType.Airline;
import seng202.group4.data.parser.validator.AirlineValidator;
import seng202.group4.data.repository.AirlineRepository;
import seng202.group4.data.repository.Repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

/**
 * Created by Pang on 25/09/16.
 */
public class AirlineAnchorController implements Initializable{

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

    @FXML
    private AnchorPane airlinePane;

    // airline table view and columns
    @FXML
    private TableView airlineTableID;

    @FXML
    private TableColumn<AirlineTable, String> aid;

    @FXML
    private TableColumn<AirlineTable, String> aname;

    @FXML
    private TableColumn<AirlineTable, String> aalias;

    @FXML
    private TableColumn<AirlineTable, String> aiata;

    @FXML
    private TableColumn<AirlineTable, String> aicao;

    @FXML
    private TableColumn<AirlineTable, String> acallsign;

    @FXML
    private TableColumn<AirlineTable, String> acountry;

    @FXML
    private TableColumn<AirlineTable, String> aactive;

    // controllers
    private DataTabController mainController;

    // the default string for country combo box
    private String allCountriesTag;

    // create table data
    private ObservableList<AirlineTable> airlineTData = FXCollections.observableArrayList();

    // create parallel hash map
    private HashMap<Integer, AirlineTable> airlineTableMap = new HashMap<Integer, AirlineTable>();

    // airlineCountrySet holds all the countries uploaded to airline
    private TreeSet airlineCountrySet = new TreeSet();

    /**
     * Where the program starts, initializes things like listeners and starts running the GUI.
     *
     * @param location  URL
     * @param resources ResourceBundle
     */
    public void initialize(URL location, ResourceBundle resources) {
        // bring airline table to front initially
        airlineTableID.toFront();

        // initialise airline table resources
        aid.setCellValueFactory(new PropertyValueFactory<>("rid"));
        aname.setCellValueFactory(new PropertyValueFactory<>("rname"));
        aalias.setCellValueFactory(new PropertyValueFactory<>("ralias"));
        aiata.setCellValueFactory(new PropertyValueFactory<>("riata"));
        aicao.setCellValueFactory(new PropertyValueFactory<>("ricao"));
        acallsign.setCellValueFactory(new PropertyValueFactory<>("rcallsign"));
        acountry.setCellValueFactory(new PropertyValueFactory<>("rcountry"));
        aactive.setCellValueFactory(new PropertyValueFactory<>("ractive"));

        // set items into airline table
        airlineTableID.setItems(airlineTData);

        // loads default airline list
        try {
            loadDefaultAirline();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // enable search box listener
        searchAirlines();

        // enable multi select on table
        airlineTableID.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // enable deleting on table
        enableDeleteAirlines();
    }

    /**
     * Sets the main controller and retrieves variables from other controllers.
     *
     * @param controller Controller
     */
    public void setMainController(DataTabController controller) {
        this.mainController = controller;
        this.allCountriesTag = mainController.getAllCountriesTag();
    }

    private void enableDeleteAirlines(){
        airlineTableID.setRowFactory(tableView -> {
            final TableRow<AirlineTable> row = new TableRow<>();
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
                    ObservableList<AirlineTable> selectedItems = airlineTableID.getSelectionModel().getSelectedItems();
                    for (AirlineTable airline : selectedItems) {
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
    }

    private void updateAirlineSearch() {
        String text = airlineSearch.getText();
        airlineSearch.setText(text + " ");
        airlineSearch.setText(text);
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

    /**
     * Filters the list of airlines by country, leaving only airlines from the selected county.
     *
     * @throws IOException throws IOException error
     */
    public void filterAirlineCountry() throws IOException {
        updateAirlineSearch();
    }


    /**
     * Allows the user to load airline data from a file.
     *
     * @throws IOException throws IOException error
     */
    public void loadAirline() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        File in = fileChooser.showOpenDialog(App.primaryStage);
        if (in != null && in.exists()) {
            InputStream file = new FileInputStream(in);
            mainController.goToDataTab(mainController.getAirlineLabel());
            insertAirlineTable(file);
        }
    }


    private void loadDefaultAirline() throws IOException, URISyntaxException {
        if (Repository.airlineRepository != null) {
            loadSerializedAirline();
        } else {
            Repository.airlineRepository = new AirlineRepository();
            InputStream file = getClass().getResourceAsStream("/airlines.dat");
            if (file != null) {
                insertAirlineTable(file);
            }
        }
    }

    private void addNewAirline(Airline airline) {
        AirlineTable airlineEntryData = new AirlineTable(airline.getID(), airline.getName(),
                airline.getAlias(), airline.getIATA(),
                airline.getICAO(), airline.getCallsign(),
                airline.getCountry(), airline.getActive());
        airlineTData.add(airlineEntryData);
        airlineTableMap.put(airline.getID(), airlineEntryData);
        Repository.airlineRepository.getAirlines().put(airline.getID(), airline);
        if (airline.getCountry() != null) {
            airlineCountrySet.add(airline.getCountry());
        }
    }

    /* -------------------------------------------------------------------------------------------------------------------------------------------------------------- */
    /* Airline table loader methods below*/

    /**Insert the airlines in a given file into the airline table GUI checking for duplicates
     *
     * @param file InputStream
     * @throws IOException throws IOException error
     */
    private void insertAirlineTable(InputStream file) throws IOException {
        AirlineValidator validator = new AirlineValidator(file);
        ArrayList<Airline> airlines = validator.makeAirlines();
        ButtonResult buttonResult = null;
        for (Airline airline : airlines) {
            // if the airline ID already exists in the repository, warn the user
            if (!Repository.airlineRepository.getAirlines().containsKey(airline.getID())) {
                addNewAirline(airline);
            } else {
                // Override data option popup
                if (buttonResult == ButtonResult.OVERRIDEALL) {
                    overrideAirline(airline);
                } else if (buttonResult != ButtonResult.IGNOREALL) {
                    buttonResult = dataOverridePopup(airline.getID());
                    if (buttonResult == ButtonResult.OVERRIDE || buttonResult == ButtonResult.OVERRIDEALL) {
                        overrideAirline(airline);
                    } else if (buttonResult == ButtonResult.CANCEL) {
                        break;
                    }
                }

            }
        }
        updateAirlineCountryBox();
    }

    private void loadSerializedAirline() {
        Collection<Airline> airlines = Repository.airlineRepository.getAirlines().values();
        for (Airline airline : airlines) {
            addNewAirline(airline);
        }
        updateAirlineCountryBox();
    }

    /**
     * Clears the airline table and AirlineRepository then replaces them with the default airlines.
     *
     * @throws IOException when default airline file cannot be read
     */
    public void resetAirline() throws IOException {
        boolean result = mainController.resetConformation();
        if (result) {
            clearAirlineTable();
            InputStream file = getClass().getResourceAsStream("/airlines.dat");
            if (file != null) {
                insertAirlineTable(file);
            }
            Repository.serializeObject(Repository.airlineRepository, "airline");
        }
    }

    /**
     * Clears the airline table and AirlineRepository.
     */
    public void deleteAllAirlines() {
        boolean result = mainController.deleteAllConformation();
        if (result) {
            clearAirlineTable();
            Repository.serializeObject(Repository.airlineRepository, "airline");
        }
    }

    private void overrideAirline(Airline airline) {
        Repository.airlineRepository.getAirlines().put(airline.getID(), airline);
        AirlineTable oldAirline = airlineTableMap.get(airline.getID());
        oldAirline.setRname(airline.getName());
        oldAirline.setRalias(airline.getAlias());
        oldAirline.setRiata(airline.getIATA());
        oldAirline.setRicao(airline.getICAO());
        oldAirline.setRcallsign(airline.getCallsign());
        oldAirline.setRcountry(airline.getCountry());
        oldAirline.setRactive(airline.getActive());
        if (airline.getCountry() != null) {
            airlineCountrySet.add(airline.getCountry());
        }
    }

    private ButtonResult dataOverridePopup(int ID) {
        return OverrideDataController.getPopUpResult("Airline with ID " + ID + " already exists in the system");
    }

    private void clearAirlineTable() {
        airlineTData.clear();
        airlineTableMap = new HashMap<>();
        Repository.airlineRepository = new AirlineRepository();
    }

    /* Airline table methods above*/
    /* -------------------------------------------------------------------------------------------------------------------------------------------------------------- */

    private void updateAirlineCountryBox() {
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

    private void searchAirlines() {
        // searching for airline
        FilteredList<AirlineTable> airlineTableFiltered = new FilteredList<>(airlineTData, p -> true);

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
        SortedList<AirlineTable> airlineTableSorted = new SortedList<>(airlineTableFiltered);

        // Bind the SortedList comparator to the TableView comparator
        airlineTableSorted.comparatorProperty().bind(airlineTableID.comparatorProperty());

        // Add sorted (and filtered) data to the table
        airlineTableID.setItems(airlineTableSorted);
    }

    /**
     * Returns airlineTData to be used on other class.
     *
     * @return ObservableList<AirlineTable>
     */
    public ObservableList<AirlineTable> getAirlineTData() {
        return airlineTData;
    }

}