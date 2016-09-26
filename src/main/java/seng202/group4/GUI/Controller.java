package seng202.group4.GUI;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import seng202.group4.data.dataType.Airport;
import seng202.group4.data.repository.AirportRepository;
import seng202.group4.data.repository.FlightRepository;
import seng202.group4.data.repository.Repository;

import java.net.URL;
import java.util.*;

//import com.aquafx_project.AquaFx;

/**
 * The main controller implementing GUI functions.
 */
public class Controller implements Initializable {

    @FXML
    private MenuBarController menuBarController;

    @FXML
    private RouteAnchorController routeAnchorController;

    @FXML
    private DataTabController dataTabController;

    @FXML
    private FlightTabController flightTabController;

    @FXML
    private MapTabController mapTabController;

    @FXML
    private AirportAnchorController airportAnchorController;

    @FXML
    private AirlineAnchorController airlineAnchorController;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Tab mapTab;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab dataTab;

    @FXML
    private Tab flightTab;

    // labels for the user's data selection
    private String airlineLabel = "Airlines";
    private String airportLabel = "Airports";
    private String routeLabel = "Routes";

    // Used in flightAnalysis
    ObservableList<String> keys = FXCollections.observableArrayList();

    // Used in equipmentAnalysis
    ObservableMap<String, Integer> countEquipment = FXCollections.observableHashMap();
    ObservableList<String> equipKeys = FXCollections.observableArrayList();
    ObservableList<EquipAnalysisTable> equipAnalysisTData = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> equipPieChartData = FXCollections.observableArrayList();
    ObservableList<XYChart.Series<String, Integer>> equipBarChartData = FXCollections.observableArrayList();

    // airportCountrySet holds all the airport countries imported
    private TreeSet<String> airportCountrySet = new TreeSet();

    @FXML
    private ComboBox analysisDropdown;

    @FXML
    private ComboBox countryDropdown;

    // airport and routes FXML
    @FXML
    private GridPane airportPane;

    @FXML
    private TableView<AnalysisTable> airportsAndRoutes;

    @FXML
    private TableColumn<AnalysisTable, String> airport;

    @FXML
    private TableColumn<AnalysisTable, Integer> airportCount;

    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart barChart;

    @FXML
    private Text rowSize;

    // equip and routes FXML
    @FXML
    private GridPane equipPane;

    @FXML
    private TableView<EquipAnalysisTable> equipAndRoutes;

    @FXML
    private TableColumn<EquipAnalysisTable, String> equipment;

    @FXML
    private TableColumn<EquipAnalysisTable, Integer> equipCount;

    @FXML
    private PieChart equipPieChart;

    @FXML
    private BarChart equipBarChart;

    // equip and routes FXML
    @FXML
    private GridPane countryPane;

    /**
     * Initializes the controller
     *
     * @param location URL
     * @param resources ResourceBundle
     */
    public void initialize(URL location, ResourceBundle resources) {
        // Passing MainController to the other controllers
        menuBarController.setMainController(this);
        mapTabController.setMainController(this);
        dataTabController.setMainController(this);
        flightTabController.setMainController(this);

        analysisDropdown.getItems().addAll(
                "Airports and Routes",
                "Equipment and Routes",
                "Country"
        );

        HashMap<Integer, Airport> airports = Repository.airportRepository.getAirports();
        if (airports != null) {
            for (Airport airport : airports.values()) {
                if (airport != null && airport.getCountry() != null) {
                    airportCountrySet.add(airport.getCountry());
                }
            }
        }

        updateCountryDropdown();
    }

    private void updateCountryDropdown() {
        // clear the current combo box
        countryDropdown.getItems().clear();

        // if the combo box doesn't have --CHOOSE AIRPORT-- then add one
        if (!countryDropdown.getItems().contains("--CHOOSE COUNTRY--")) {
            countryDropdown.getItems().add("--CHOOSE COUNTRY--");
        }
        if (!countryDropdown.getItems().contains("--ALL COUNTRIES--")) {
            countryDropdown.getItems().add("--ALL COUNTRIES--");
        }
        // add countries from TreeSet as combobox options
        Iterator<String> itr = airportCountrySet.iterator();
        while (itr.hasNext()) {
            countryDropdown.getItems().add(itr.next());
        }
        countryDropdown.getSelectionModel().select(0);
    }

    public void airportAnalysis(String country) {
        ObservableList<AnalysisTable> analysisTData = FXCollections.observableArrayList();
        ObservableMap<String, Integer> countAirport = FXCollections.observableHashMap();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        ObservableList<XYChart.Series<String, Integer>> barChartData = FXCollections.observableArrayList();
        countAirport.addListener((MapChangeListener.Change<? extends String, ? extends Integer> change) -> {
            boolean added = change.wasAdded();
            if (added) {
                keys.add(change.getKey());
            }
        });

        for (RouteTable airport : dataTabController.getRouteAnchorController().getRouteTData()) {
            if (countAirport.containsKey(airport.getRsource())) {
                countAirport.put(airport.getRsource(), countAirport.get(airport.getRsource()) + 1);
            } else {
                keys.add(airport.getRsource());
                countAirport.put(airport.getRsource(), 1);
            }
        }

        XYChart.Series<String, Integer> series1 = new XYChart.Series<String, Integer>();

        if (!country.equals("--CHOOSE COUNTRY--") && !country.equals("--ALL COUNTRIES--")) {
            Set<Integer> countryAirportID = Repository.airportRepository.airportIDsOfCountry(country.toLowerCase());
            Set<String> countryAirport = new HashSet<>();
            for (Integer id : countryAirportID) {
                countryAirport.add(Repository.airportRepository.findAirportIATA(id));
            }

            for (String keyFiltered : countAirport.keySet()) {
                if (countryAirport.contains(keyFiltered)) {
                    analysisTData.add(new AnalysisTable(keyFiltered, countAirport.get(keyFiltered)));
                    pieChartData.add(new PieChart.Data(keyFiltered, countAirport.get(keyFiltered)));
                    series1.getData().add(new XYChart.Data(keyFiltered, countAirport.get(keyFiltered)));
                }
            }
        } else if (country.equals("--ALL COUNTRIES--")) {
            for (String key : countAirport.keySet()) {
                analysisTData.add(new AnalysisTable(key, countAirport.get(key)));
                pieChartData.add(new PieChart.Data(key, countAirport.get(key)));
                series1.getData().add(new XYChart.Data(key, countAirport.get(key)));
            }
        } else {
            analysisTData.add(new AnalysisTable("no data", 0)); // To be managed
        }

        airport.setCellValueFactory(new PropertyValueFactory<>("airport"));
        airportCount.setCellValueFactory(new PropertyValueFactory<>("number"));
        airportsAndRoutes.setItems(analysisTData);
        airportCount.setSortType(TableColumn.SortType.DESCENDING);
        airportsAndRoutes.getSortOrder().setAll(airportCount);

        pieChart.setData(pieChartData);
        pieChart.setTitle("Airports and Count Pie Chart");

        barChartData.addAll(series1);
        barChart.setData(barChartData);
        barChart.setTitle("Country Versus Count Bar Chart");

        rowSize.setVisible(true);
        rowSize.setText(Integer.toString(analysisTData.size()) + " airports found in " + country + ".");
    }

    private void equipmentAnalysis() {
        countEquipment.addListener((MapChangeListener.Change<? extends String, ? extends Integer> change) -> {
            boolean added = change.wasAdded();
            if (added) {
                equipKeys.add(change.getKey());
            }
        });

        for (RouteTable equipment : dataTabController.getRouteAnchorController().getRouteTData()) {
            String[] something = equipment.getRequipment().split(", ");
            if (something.length == 1) {

            }
        }
//
//        XYChart.Series<String, Integer> series1 = new XYChart.Series<String, Integer>();
//
//        for (String key : countEquipment.keySet()) {
//            equipAnalysisTData.add(new AnalysisTable(key, countEquipment.get(key)));
//            equipPieChartData.add(new PieChart.Data(key, countEquipment.get(key)));
//            series1.getData().add(new XYChart.Data(key, countEquipment.get(key)));
//        }
//
//        airport.setCellValueFactory(new PropertyValueFactory<>("airport"));
//        number.setCellValueFactory(new PropertyValueFactory<>("number"));
//        airportsAndRoutes.setItems(equipAnalysisTData);
//
//        pieChart.setData(equipPieChartData);
//        pieChart.setTitle("Airports and Count Pie Chart");
//
//        barChartData.addAll(series1);
//        barChart.setData(barChartData);
//        barChart.setTitle("Country Versus Count Bar Chart");
    }

    private void countryAnalysis() {

    }

    public void getAnalysis() {
        String country = countryDropdown.getSelectionModel().getSelectedItem().toString();
        if (analysisDropdown.getSelectionModel().getSelectedIndex() == 0) {
            airportPane.setVisible(true);
            equipPane.setVisible(false);
            countryPane.setVisible(false);
            airportAnalysis(country);
        } else if (analysisDropdown.getSelectionModel().getSelectedIndex() == 1) {
            airportPane.setVisible(false);
            equipPane.setVisible(true);
            countryPane.setVisible(false);
            equipmentAnalysis();
        } else if (analysisDropdown.getSelectionModel().getSelectedIndex() == 2) {
            airportPane.setVisible(false);
            equipPane.setVisible(false);
            countryPane.setVisible(true);
            countryAnalysis();
        }
    }

    public String getAirlineLabel() {
        return airlineLabel;
    }

    public String getAirportLabel() {
        return airportLabel;
    }

    public String getRouteLabel() {
        return routeLabel;
    }


    public Tab getFlightTab() {
        return flightTab;
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    public Tab getDataTab() {
        return dataTab;
    }

    public DataTabController getDataTabController() {
        return dataTabController;
    }

    public RouteAnchorController getRouteAnchorController() {
        return routeAnchorController;
    }

    public FlightTabController getFlightTabController() {
        return flightTabController;
    }

    public AirportAnchorController getAirportAnchorController() {
        return airportAnchorController;
    }

    public AirlineAnchorController getAirlineAnchorController() {
        return airlineAnchorController;
    }

    public MenuBarController getMenuBarController() {
        return menuBarController;
    }

    public MapTabController getMapTabController() {
        return mapTabController;
    }



}