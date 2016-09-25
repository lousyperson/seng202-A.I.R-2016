package seng202.group4.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.net.URL;
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

    public AirlinePaneController getAirlinePaneController() {
        return airlinePaneController;
    }

    @FXML
    private AirlinePaneController airlinePaneController;

    @FXML
    private AirlineTableIDController airlineTableIDController;

    @FXML
    private AirportTableIDController airportTableIDController;

    @FXML
    private RouteTableIDController routeTableIDController;

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



    /**
     * Where the program starts, initializes things like listeners and starts running the GUI.
     *
     * @param location URL
     * @param resources ResourceBundle
     */
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("control init");
        // Passing MainController to the other controllers
        menuBarController.setMainController(this);
        mapTabController.setMainController(this);
        dataTabController.setMainController(this);
        System.out.println("done data tab set");
        flightTabController.setMainController(this);
        System.out.println("done flight tab set");


//        System.out.println("done airlinepane set");
//        // loads default airline list
//        try {
//            airlinePaneController.loadDefaultAirline();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }

//        airlineTableIDController.setMainController(this);
//        System.out.println("done airlinetable id set");
//        routeTableIDController.setMainController(this);
//        System.out.println("done route table id set");
        //airlinePaneController.setMainController(this);
       // airlinePaneController.setMainController(this);
//        System.out.println("done airlinepane set");

//        airportTableIDController.setMainController(this);


//        mapView.getEngine().load(getClass().getClassLoader().getResource("map.html").toExternalForm());

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