package seng202.group4.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import seng202.group4.data.dataType.Airport;
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
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();

    private TreeSet<String> airportCountrySet = new TreeSet();

    // FXML for analysis tab
    @FXML
    private ComboBox analysisDropdown;

    @FXML
    private ComboBox countryDropdown;

    @FXML
    private TableView<AnalysisTable> airportsAndRoutes;

    @FXML
    private TableColumn<AnalysisTable, String> column1;

    @FXML
    private TableColumn<AnalysisTable, Integer> airportCount;

    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart<String,Number> barChart = new BarChart<String,Number>(xAxis,yAxis);

    @FXML
    private Text selectCountryText;

    @FXML
    private Text warningPieChart;

    @FXML
    private Text warningBarChart;

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
                "Airports with routes",
                "Equipment used on routes",
                "Airports per country",
                "Airlines per country"
        );

        countryDropdown.setDisable(true);

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

        // if the combo box doesn't have --ALL AIRPORT-- then add one
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
//        xAxis.setLabel("Airports");
//        yAxis.setLabel("Routes Count");

        for (RouteTable airport : dataTabController.getRouteAnchorController().getRouteTData()) {
            if (countAirport.containsKey(airport.getRsource())) {
                countAirport.put(airport.getRsource(), countAirport.get(airport.getRsource()) + 1);
            } else {
                countAirport.put(airport.getRsource(), 1);
            }
        }

        XYChart.Series airportMostRoutes = new XYChart.Series();

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
                    airportMostRoutes.getData().add(new XYChart.Data(keyFiltered, countAirport.get(keyFiltered)));
                }
            }
        } else if (country.equals("--ALL COUNTRIES--")) {
            for (String key : countAirport.keySet()) {
                analysisTData.add(new AnalysisTable(key, countAirport.get(key)));
            }
        } else {
            analysisTData.add(new AnalysisTable("no data", 0)); // In case something else happens
        }

        column1.setCellValueFactory(new PropertyValueFactory<>("column1"));
        column1.setText("Airport");
        airportCount.setCellValueFactory(new PropertyValueFactory<>("number"));
        airportCount.setText("Number of Routes");
        airportsAndRoutes.setItems(analysisTData);
        airportCount.setSortType(TableColumn.SortType.DESCENDING);
        airportsAndRoutes.getSortOrder().setAll(airportCount);

        if (!country.equals("--ALL COUNTRIES--")) {
            pieChart.setVisible(true);
            warningPieChart.setVisible(false);

            barChart.setVisible(true);
            warningBarChart.setVisible(false);

            pieChart.setData(pieChartData);
            pieChart.setTitle("Routes by Airports in " + country);

            barChart.getData().setAll(airportMostRoutes);
            barChart.setTitle("Routes by Airports in " + country);
        } else {
            pieChart.setVisible(false);
            warningPieChart.setVisible(true);
            warningPieChart.setText("Please select country to enable pie chart.");

            barChart.setVisible(false);
            warningBarChart.setVisible(true);
            warningBarChart.setText("Please select country to enable bar chart.");
        }

        rowSize.setVisible(true);
        if (!country.equals("--ALL COUNTRIES--")) {
            rowSize.setText(Integer.toString(analysisTData.size()) + " airport(s) found in " + country + " with routes.");
        } else {
            rowSize.setText(Integer.toString(analysisTData.size()) + " airports are shown in the table.");
        }
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

        for (String key : countEquip.keySet()) {
            analysisTData.add(new AnalysisTable(key, countEquip.get(key)));
            pieChartData.add(new PieChart.Data(key, countEquip.get(key)));
        }

        column1.setCellValueFactory(new PropertyValueFactory<>("column1"));
        column1.setText("Equipment");
        airportCount.setCellValueFactory(new PropertyValueFactory<>("number"));
        airportCount.setText("Number of Routes");
        airportsAndRoutes.setItems(analysisTData);
        airportCount.setSortType(TableColumn.SortType.DESCENDING);
        airportsAndRoutes.getSortOrder().setAll(airportCount);

        pieChart.setVisible(true);
        warningPieChart.setVisible(false);
        pieChart.setData(pieChartData);
        pieChart.setTitle("Routes by equipment");

        barChart.setVisible(false);
        warningBarChart.setVisible(true);
        warningBarChart.setText("Bar chart not available for this analysis.");

        rowSize.setVisible(true);
        rowSize.setText(Integer.toString(analysisTData.size()) + " equipments are shown in the table.");
    }

    public void airportCountryAnalysis() {
        ObservableList<AnalysisTable> analysisTData = FXCollections.observableArrayList();
        ObservableMap<String, Integer> countAirport = FXCollections.observableHashMap();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (AirportTable airport : dataTabController.getAirportAnchorController().getAirportTData()) {
            if (countAirport.containsKey(airport.getAtcountry())) {
                countAirport.put(airport.getAtcountry(), countAirport.get(airport.getAtcountry()) + 1);
            } else {
                countAirport.put(airport.getAtcountry(), 1);
            }
        }

        for (String key : countAirport.keySet()) {
                analysisTData.add(new AnalysisTable(key, countAirport.get(key)));
                pieChartData.add(new PieChart.Data(key, countAirport.get(key)));
        }

        column1.setCellValueFactory(new PropertyValueFactory<>("column1"));
        column1.setText("Country");
        airportCount.setCellValueFactory(new PropertyValueFactory<>("number"));
        airportCount.setText("Number of Airports");
        airportsAndRoutes.setItems(analysisTData);
        airportCount.setSortType(TableColumn.SortType.DESCENDING);
        airportsAndRoutes.getSortOrder().setAll(airportCount);

        pieChart.setVisible(true);
        warningPieChart.setVisible(false);
        pieChart.setData(pieChartData);
        pieChart.setTitle("Airports by Country");


        barChart.setVisible(false);
        warningBarChart.setVisible(true);
        warningBarChart.setText("Bar chart not available for this analysis.");

        rowSize.setVisible(true);
        rowSize.setText(Integer.toString(analysisTData.size()) + " countries are shown in the table.");
    }

    private void airlineCountryAnalysis() {
        ObservableList<AnalysisTable> analysisTData = FXCollections.observableArrayList();
        ObservableMap<String, Integer> countAirline = FXCollections.observableHashMap();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
//        xAxis.setLabel("Country");
//        yAxis.setLabel("Value");


        for (AirlineTable airline : dataTabController.getAirlineAnchorController().getAirlineTData()) {
            if (countAirline.containsKey(airline.getRcountry())) {
                countAirline.put(airline.getRcountry(), countAirline.get(airline.getRcountry()) + 1);
            } else {
                countAirline.put(airline.getRcountry(), 1);
            }
        }

        for (String key : countAirline.keySet()) {
            if (key != null) {
                analysisTData.add(new AnalysisTable(key, countAirline.get(key)));
                pieChartData.add(new PieChart.Data(key, countAirline.get(key)));
            }
        }

        column1.setCellValueFactory(new PropertyValueFactory<>("column1"));
        column1.setText("Country");
        airportCount.setCellValueFactory(new PropertyValueFactory<>("number"));
        airportCount.setText("Number of Airlines");
        airportsAndRoutes.setItems(analysisTData);
        airportCount.setSortType(TableColumn.SortType.DESCENDING);
        airportsAndRoutes.getSortOrder().setAll(airportCount);

        pieChart.setVisible(true);
        warningPieChart.setVisible(false);
        pieChart.setData(pieChartData);
        pieChart.setTitle("Airlines by Country");

        barChart.setVisible(false);
        warningBarChart.setVisible(true);
        warningBarChart.setText("Bar chart not available for this analysis.");

        rowSize.setVisible(true);
        rowSize.setText(Integer.toString(analysisTData.size()) + " countries are shown in the table.");
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
            selectCountryText.setDisable(false);
            countryDropdown.setDisable(false);
        } else if (analysisDropdown.getSelectionModel().getSelectedIndex() == 1) {
            selectCountryText.setDisable(true);
            countryDropdown.setDisable(true);
        } else if (analysisDropdown.getSelectionModel().getSelectedIndex() == 2) {
            selectCountryText.setDisable(true);
            countryDropdown.setDisable(true);
        } else if (analysisDropdown.getSelectionModel().getSelectedIndex() == 3) {
            selectCountryText.setDisable(true);
            countryDropdown.setDisable(true);
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