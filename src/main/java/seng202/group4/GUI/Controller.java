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


    // Used in flightAnalysis
//    ObservableList<String> keys = FXCollections.observableArrayList();
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();

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
    private BarChart<String,Number> barChart = new BarChart<String,Number>(xAxis,yAxis);

    @FXML
    private Text rowSize;

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
                "Airports with most routes",
                "Equipment with most routes",
                "Countries with most airports",
                "Countries with most airlines"
        );

        updateCountryDropdown();
    }

    private void updateCountryDropdown() {
        // clear the current combo box
        countryDropdown.getItems().clear();

        // populate airportCountrySet for combobox use later
        HashMap<Integer, Airport> airports = Repository.airportRepository.getAirports();
        if (airports != null) {
            for (Airport airport : airports.values()) {
                if (airport != null && airport.getCountry() != null) {
                    airportCountrySet.add(airport.getCountry());
                }
            }
        }

        // if the combo box doesn't have --CHOOSE AIRPORT-- then add one
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

    private void airportAnalysis(String country) {
        ObservableList<AnalysisTable> analysisTData = FXCollections.observableArrayList();
        ObservableMap<String, Integer> countAirport = FXCollections.observableHashMap();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        xAxis.setLabel("Airports");
        yAxis.setLabel("Routes Count");
        ObservableList<XYChart.Series<String, Integer>> barChartData = FXCollections.observableArrayList();
//        countAirport.addListener((MapChangeListener.Change<? extends String, ? extends Integer> change) -> {
//            boolean added = change.wasAdded();
//            if (added) {
//                keys.add(change.getKey());
//            }
//        });

        for (RouteTable airport : dataTabController.getRouteAnchorController().getRouteTData()) {
            if (countAirport.containsKey(airport.getRsource())) {
                countAirport.put(airport.getRsource(), countAirport.get(airport.getRsource()) + 1);
            } else {
//                keys.add(airport.getRsource());
                countAirport.put(airport.getRsource(), 1);
            }
        }

        XYChart.Series series1 = new XYChart.Series();

        if (!country.equals("--ALL COUNTRIES--")) {
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
//                pieChartData.add(new PieChart.Data(key, countAirport.get(key)));
//                series1.getData().add(new XYChart.Data(key, countAirport.get(key)));
            }
        } else {
            analysisTData.add(new AnalysisTable("no data", 0)); // To be managed
        }

        airport.setCellValueFactory(new PropertyValueFactory<>("airport"));
        airport.setText("Airport");
        airportCount.setCellValueFactory(new PropertyValueFactory<>("number"));
        airportCount.setText("Number of Routes");
        airportsAndRoutes.setItems(analysisTData);
        airportCount.setSortType(TableColumn.SortType.DESCENDING);
        airportsAndRoutes.getSortOrder().setAll(airportCount);

        pieChart.setData(pieChartData);
        pieChart.setTitle("Airports and Count Pie Chart");

        barChart.getData().setAll(series1);
        barChart.setTitle("Country Versus Count Bar Chart");

        rowSize.setVisible(true);
        rowSize.setText(Integer.toString(analysisTData.size()) + " airports found in " + country + ".");
    }

    private void equipmentAnalysis() {
        ObservableList<AnalysisTable> analysisTData = FXCollections.observableArrayList();
        ObservableMap<String, Integer> countEquip = FXCollections.observableHashMap();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (RouteTable airport : dataTabController.getRouteAnchorController().getRouteTData()) {
            List<String> items = Arrays.asList(airport.getRequipment().split("\\s*,\\s*"));
            for (String equip : items) {
                if (countEquip.containsKey(equip)) {
                    countEquip.put(equip, countEquip.get(equip) + 1);
                } else {
                    countEquip.put(equip, 1);
                }
            }
        }

//        XYChart.Series series1 = new XYChart.Series();

        for (String key : countEquip.keySet()) {
            analysisTData.add(new AnalysisTable(key, countEquip.get(key)));
            pieChartData.add(new PieChart.Data(key, countEquip.get(key)));
//            series1.getData().add(new XYChart.Data(key, countEquip.get(key)));
        }

        airport.setCellValueFactory(new PropertyValueFactory<>("airport"));
        airport.setText("Equipment");
        airportCount.setCellValueFactory(new PropertyValueFactory<>("number"));
        airportCount.setText("Number of Routes");
        airportsAndRoutes.setItems(analysisTData);
        airportCount.setSortType(TableColumn.SortType.DESCENDING);
        airportsAndRoutes.getSortOrder().setAll(airportCount);

        pieChart.setData(pieChartData);
        pieChart.setTitle("Equipment and Count Pie Chart");

//        barChart.getData().setAll(series1);
//        barChart.setTitle("Equipment Versus Count Bar Chart");

        rowSize.setVisible(true);
        rowSize.setText(Integer.toString(analysisTData.size()) + " equipments found in route table.");
    }

    public void airportCountryAnalysis() {
        ObservableList<AnalysisTable> analysisTData = FXCollections.observableArrayList();
        ObservableMap<String, Integer> countAirport = FXCollections.observableHashMap();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        ObservableList<XYChart.Series<String, Integer>> barChartData = FXCollections.observableArrayList();
        xAxis.setLabel("Country");
        yAxis.setLabel("Count");

        for (AirportTable airport : dataTabController.getAirportAnchorController().getAirportTData()) {
            if (countAirport.containsKey(airport.getAtcountry())) {
                countAirport.put(airport.getAtcountry(), countAirport.get(airport.getAtcountry()) + 1);
            } else {
                countAirport.put(airport.getAtcountry(), 1);
            }
        }

        XYChart.Series series1 = new XYChart.Series();

        for (String key : countAirport.keySet()) {
                analysisTData.add(new AnalysisTable(key, countAirport.get(key)));
                pieChartData.add(new PieChart.Data(key, countAirport.get(key)));
                series1.getData().add(new XYChart.Data(key, countAirport.get(key)));
        }

        airport.setCellValueFactory(new PropertyValueFactory<>("airport"));
        airport.setText("Country");
        airportCount.setCellValueFactory(new PropertyValueFactory<>("number"));
        airportCount.setText("Number of airports");
        airportsAndRoutes.setItems(analysisTData);
        airportCount.setSortType(TableColumn.SortType.DESCENDING);
        airportsAndRoutes.getSortOrder().setAll(airportCount);

        pieChart.setData(pieChartData);
        pieChart.setTitle("Country and Airport Count Pie Chart");

//        barChartData.addAll(series1);
        barChart.getData().setAll(series1);
        barChart.setTitle("Airport Count Versus Country Bar Chart");

        rowSize.setVisible(true);
        rowSize.setText("There are " + Integer.toString(analysisTData.size()) + " countries in the airport table.");
    }

    private void airlineCountryAnalysis() {
        ObservableList<AnalysisTable> analysisTData = FXCollections.observableArrayList();
        ObservableMap<String, Integer> countAirline = FXCollections.observableHashMap();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        ObservableList<XYChart.Series<String, Integer>> barChartData = FXCollections.observableArrayList();
        xAxis.setLabel("Country");
        yAxis.setLabel("Value");


        for (AirlineTable airline : dataTabController.getAirlineAnchorController().getAirlineTData()) {
            if (countAirline.containsKey(airline.getRcountry())) {
                countAirline.put(airline.getRcountry(), countAirline.get(airline.getRcountry()) + 1);
            } else {
                countAirline.put(airline.getRcountry(), 1);
            }
        }

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("airline vs country");

        for (String key : countAirline.keySet()) {
            if (key != null) {
                analysisTData.add(new AnalysisTable(key, countAirline.get(key)));
                pieChartData.add(new PieChart.Data(key, countAirline.get(key)));
//                System.out.println(key + " " + Integer.toString(countAirline.get(key)));
            series1.getData().add(new XYChart.Data(key, countAirline.get(key)));
            }
        }

        airport.setCellValueFactory(new PropertyValueFactory<>("airport"));
        airport.setText("Country");
        airportCount.setCellValueFactory(new PropertyValueFactory<>("number"));
        airportCount.setText("Number of airlines");
        airportsAndRoutes.setItems(analysisTData);
        airportCount.setSortType(TableColumn.SortType.DESCENDING);
        airportsAndRoutes.getSortOrder().setAll(airportCount);

        pieChart.setData(pieChartData);
        pieChart.setTitle("Country and Airline Count Pie Chart");

//        barChartData.addAll(series1);
        barChart.getData().setAll(series1);
        barChart.setTitle("Airline Count Versus Country Bar Chart");

        rowSize.setVisible(true);
        rowSize.setText("There are " + Integer.toString(analysisTData.size()) + " airlines in the airline table.");
    }

    public void getAnalysis() {
        String country = countryDropdown.getSelectionModel().getSelectedItem().toString();
        if (analysisDropdown.getSelectionModel().getSelectedIndex() == 0) {
            airportAnalysis(country);
        } else if (analysisDropdown.getSelectionModel().getSelectedIndex() == 1) {
            equipmentAnalysis();
        } else if (analysisDropdown.getSelectionModel().getSelectedIndex() == 2) {
            airportCountryAnalysis();
        } else if (analysisDropdown.getSelectionModel().getSelectedIndex() == 3) {
            airlineCountryAnalysis();
        }
    }

    public void disableCountryDropdown() {
        if (analysisDropdown.getSelectionModel().getSelectedIndex() == 0) {
            countryDropdown.setVisible(true);
        } else if (analysisDropdown.getSelectionModel().getSelectedIndex() == 1) {
            countryDropdown.setVisible(true);
        } else if (analysisDropdown.getSelectionModel().getSelectedIndex() == 2) {
            countryDropdown.setVisible(false);
        } else if (analysisDropdown.getSelectionModel().getSelectedIndex() == 3) {
            countryDropdown.setVisible(false);
        }
    }


    /**
     * Getter for flight tab
     * @return Tab
     */
    public Tab getFlightTab() {
        return flightTab;
    }

    /**
     * Getting for tab pane
     * @return TabPane
     */
    public TabPane getTabPane() {
        return tabPane;
    }

    /**
     * Getter for data tab
     * @return Tab
     */
    public Tab getDataTab() {
        return dataTab;
    }

    /**
     * Getter for dataTabController
     * @return DataTabController
     */
    public DataTabController getDataTabController() {
        return dataTabController;
    }

    /**
     * Getter for routeAnchorController
     * @return RouteAnchorController
     */
    public RouteAnchorController getRouteAnchorController() {
        return routeAnchorController;
    }

    /**
     * Getter for flightTabController
     * @return FlightTabController
     */
    public FlightTabController getFlightTabController() {
        return flightTabController;
    }

    /**
     * Getter for airportAnchorController
     * @return AirportAnchorController
     */
    public AirportAnchorController getAirportAnchorController() {
        return airportAnchorController;
    }

    /**
     * Getter for airlineAnchorController
     * @return AirlineAnchorController
     */
    public AirlineAnchorController getAirlineAnchorController() {
        return airlineAnchorController;
    }

    /**
     * Getter for menuBarController
     * @return MenuBarController
     */
    public MenuBarController getMenuBarController() {
        return menuBarController;
    }

    /**
     * Getter for mapTabController
     * @return MapTabController
     */
    public MapTabController getMapTabController() {
        return mapTabController;
    }



}