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
    }

    public void airportAnalysis() {
        countAirport.addListener((MapChangeListener.Change<? extends String, ? extends Integer> change) -> {
            boolean added = change.wasAdded();
            if (added) {
                keys.add(change.getKey());
            }
        });

        for (routeTable airport : dataTabController.getRouteAnchorController().getRouteTData()) {
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