package seng202.group4.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
 * Created by psu43 on 25/09/16.
 */
public class AirlinePaneController implements Initializable{
    public Controller getMainController() {
        return mainController;
    }

    private Controller mainController;
    private DataTabController dataTabController;

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

    // create table data
    private ObservableList<airlineTable> airlineTData = FXCollections.observableArrayList();

    //private TableView<airlineTable> airlineTableID;
    // airlineCountrySet holds all the countries uploaded to airline
    private TreeSet airlineCountrySet = new TreeSet();

    public TreeSet getAirlineCountrySet() {
        return airlineCountrySet;
    }

    private String allCountriesTag;

    /**
     * Where the program starts, initializes things like listeners and starts running the GUI.
     *
     * @param location URL
     * @param resources ResourceBundle
     */
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("airlinepane init");

//        if(dataTabController == null){
//            System.out.println("yeah its null");
//        }
//
//
        // loads default airline list
//        try {
//            loadDefaultAirline();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        searchAirlines();
    }


    /**
     * Sets the main controller and retrieves private variables from the main controller
     *
     * @param controller Controller
     */
    public void setMainController(Controller controller) {
        System.out.println("whatttt");
        this.mainController = controller;
        this.dataTabController = mainController.getDataTabController();
        this.allCountriesTag = dataTabController.getAllCountriesTag();
        this.airlineTData= dataTabController.getAirlineTData();
        //this.airlineTableID = dataTabController.getAirlineTableID();

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
            dataTabController.goToDataTab(mainController.getAirlineLabel());
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
                    dataTabController.duplicateIDAlert("Please fix the conflict and reupload the file.", airline.getID());
                    break;
                }
                updateAirlineCountryBox();
            }
        }
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
    public void loadDefaultAirline() throws IOException, URISyntaxException {
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

    /**
     * Clears the airline table and AirlineRepository then replaces them with the default airlines
     * @throws IOException when default airline file cannot be read
     */
    public void resetAirline() throws IOException {
        boolean result = dataTabController.resetConformation();
        if (result) {
            dataTabController.clearAirlineTable();
            Repository.airlineRepository = new AirlineRepository();
            InputStream file = getClass().getResourceAsStream("/airlines.dat");
            //File file = new File(getClass().getClassLoader().getResource("airlines.dat").toURI());
            if (file != null) {
                insertAirlineTable(file);
            }
            Repository.serializeObject(Repository.airlineRepository, "airline");
        }
    }

    public void searchAirlines() {
        System.out.println("action on search airlines");
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
        airlineTableSorted.comparatorProperty().bind(dataTabController.airlineTableID.comparatorProperty());

        // Add sorted (and filtered) data to the table
        dataTabController.airlineTableID.setItems(airlineTableSorted);

    }

}
