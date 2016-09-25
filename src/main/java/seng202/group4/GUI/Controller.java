package seng202.group4.GUI;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng202.group4.data.repository.Repository;
import seng202.group4.data.repository.RouteRepository;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

//import com.aquafx_project.AquaFx;

/**
 * The main controller implementing GUI functions.
 */
public class Controller implements Initializable {

    public MenuBarController getMenuBarController() {
        return menuBarController;
    }

    @FXML
    private MenuBarController menuBarController;

    public DataTabController getDataTabController() {
        return dataTabController;
    }

    @FXML
    private DataTabController dataTabController;

    public FlightTabController getFlightTabController() {
        return flightTabController;
    }

    @FXML
    private FlightTabController flightTabController;

    public MapTabController getMapTabController() {
        return mapTabController;
    }

    @FXML
    private MapTabController mapTabController;


//    public SearchPanesController getSearchPanesController() {
//        return searchPanesController;
//    }
//
//    @FXML
//    private SearchPanesController searchPanesController;

//    // Map view
//    @FXML
//    private WebView mapView;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Tab mapTab;

//    @FXML
//    private Accordion accord1;
//
//    @FXML
//    private TitledPane instructions1;
//
//    @FXML
//    private TextField flightNameSearch1;





    public TabPane getTabPane() {
        return tabPane;
    }

    @FXML
    private TabPane tabPane;

    public Tab getDataTab() {
        return dataTab;
    }

    @FXML
    private Tab dataTab;

    @FXML
    private Tab flightTab;


//
//    @FXML
//    private ComboBox chooseAirport;
//
//    @FXML
//    private CheckBox allAirports;
//
//    @FXML
//    private CheckBox allRoutes;



    public String getAirlineLabel() {
        return airlineLabel;
    }

    public String getAirportLabel() {
        return airportLabel;
    }

    public String getRouteLabel() {
        return routeLabel;
    }

    private String airlineLabel = "Airlines";
    private String airportLabel = "Airports";
    private String routeLabel = "Routes";

    // Used in flightAnalysis
    ObservableMap<String, Integer> countAirport = FXCollections.observableHashMap();
    ObservableList<String> keys = FXCollections.observableArrayList();
    ObservableList<analysisTable> analysisTData = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    ObservableList<XYChart.Series<String, Integer>> barChartData = FXCollections.observableArrayList();

    // Used in equipmentAnalysis
    ObservableMap<String, Integer> countEquipment = FXCollections.observableHashMap();
    ObservableList<String> equipKeys = FXCollections.observableArrayList();
    ObservableList<equipAnalysisTable> equipAnalysisTData = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> equipPieChartData = FXCollections.observableArrayList();
    ObservableList<XYChart.Series<String, Integer>> equipBarChartData = FXCollections.observableArrayList();

    @FXML
    private ComboBox analysisDropdown;

    // airport and routes FXML
    @FXML
    private GridPane airportPane;

    @FXML
    private TableView<analysisTable> airportsAndRoutes;

    @FXML
    private TableColumn<analysisTable, String> airport;

    @FXML
    private TableColumn<analysisTable, Integer> airportCount;

    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart barChart;

    // equip and routes FXML
    @FXML
    private GridPane equipPane;

    @FXML
    private TableView<equipAnalysisTable> equipAndRoutes;

    @FXML
    private TableColumn<equipAnalysisTable, String> equipment;

    @FXML
    private TableColumn<equipAnalysisTable, Integer> equipCount;

    @FXML
    private PieChart equipPieChart;

    @FXML
    private BarChart equipBarChart;

    // equip and routes FXML
    @FXML
    private GridPane countryPane;


    /**
     * Where the program starts, initializes things like listeners and starts running the GUI.
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
//        mapView.getEngine().load(getClass().getClassLoader().getResource("map.html").toExternalForm());

        analysisDropdown.getItems().addAll(
                "Airports and Routes",
                "Equipment and Routes",
                "Country"
        );
    }

    public void airportAnalysis() {
        countAirport.addListener((MapChangeListener.Change<? extends String, ? extends Integer> change) -> {
            boolean added = change.wasAdded();
            if (added) {
                keys.add(change.getKey());
            }
        });

        for (routeTable airport : dataTabController.getRouteTData()) {
            if (countAirport.containsKey(airport.getRsource())) {
                countAirport.put(airport.getRsource(), countAirport.get(airport.getRsource()) + 1);
            } else {
                keys.add(airport.getRsource());
                countAirport.put(airport.getRsource(), 1);
            }
        }

        XYChart.Series<String, Integer> series1 = new XYChart.Series<String, Integer>();

        for (String key : countAirport.keySet()) {
            analysisTData.add(new analysisTable(key, countAirport.get(key)));
            pieChartData.add(new PieChart.Data(key, countAirport.get(key)));
            series1.getData().add(new XYChart.Data(key, countAirport.get(key)));
        }

        airport.setCellValueFactory(new PropertyValueFactory<>("airport"));
        airportCount.setCellValueFactory(new PropertyValueFactory<>("number"));
        airportsAndRoutes.setItems(analysisTData);

        pieChart.setData(pieChartData);
        pieChart.setTitle("Airports and Count Pie Chart");

        barChartData.addAll(series1);
        barChart.setData(barChartData);
        barChart.setTitle("Country Versus Count Bar Chart");
    }

    private void equipmentAnalysis() {
//        countEquipment.addListener((MapChangeListener.Change<? extends String, ? extends Integer> change) -> {
//            boolean added = change.wasAdded();
//            if (added) {
//                equipKeys.add(change.getKey());
//            }
//        });
//
//        for (routeTable equipment : dataTabController.getRouteTData()) {
//            if (countEquipment.containsKey(equipment.getRequipment())) {
//                countEquipment.put(equipment.getRequipment(), countEquipment.get(equipment.getRequipment()) + 1);
//            } else {
//                keys.add(equipment.getRequipment());
//                countEquipment.put(equipment.getRequipment(), 1);
//            }
//        }
//
//        XYChart.Series<String, Integer> series1 = new XYChart.Series<String, Integer>();
//
//        for (String key : countEquipment.keySet()) {
//            equipAnalysisTData.add(new analysisTable(key, countEquipment.get(key)));
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
        if (analysisDropdown.getSelectionModel().getSelectedIndex() == 0) {
            airportAnalysis();
        } else if (analysisDropdown.getSelectionModel().getSelectedIndex() == 1) {
            equipmentAnalysis();
        } else if (analysisDropdown.getSelectionModel().getSelectedIndex() == 2) {
            countryAnalysis();
        }
    }




//    public void showAllAirports() {
//        if (mapView.getEngine() != null) {
//            HashMap<Integer, Airport> airports = Repository.airportRepository.getAirports();
//            for (Map.Entry<Integer, Airport> entry : airports.entrySet()) {
//                double lat = entry.getValue().getLatitude();
//                double lon = entry.getValue().getLongitude();
//                String name = entry.getValue().getName();
//                mapView.getEngine().executeScript("addAirport(" + lat + ", " + lon + ");");
//            }
//            mapView.getEngine().executeScript("showAllAirports();");
//        }
//        if (allAirports.isSelected() == false) {
//            mapView.getEngine().executeScript("hideAllAirports();");
//        }
//    }
//
//
//    public void showAllRoutes() {
//        if (mapView.getEngine() != null) {
//            mapView.getEngine().executeScript("showAllRoutes();");
//        }
//        if (allRoutes.isSelected() == false) {
//            mapView.getEngine().executeScript("hideAllRoutes();");
//        }
//    }




    public Tab getFlightTab() {
        return flightTab;
    }




//    public void refreshMap() {
//        mapView.getEngine().load(getClass().getClassLoader().getResource("map.html").toExternalForm());
//    }



}