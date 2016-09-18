package seng202.group4.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group4.data.dataType.Airline;
import seng202.group4.data.repository.AirlineRepository;
import seng202.group4.data.repository.AirportRepository;
import seng202.group4.data.repository.Repository;
import seng202.group4.data.repository.RouteRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * The MenuBarController with functions for importing data
 */
public class MenuBarController {

    private Tab flightTab;
    private String airlineLabel;
    private String airportLabel;
    private String routeLabel;
    private TabPane tabPane;
    private Tab dataTab;
    private ListView<String> datalist;

    private Controller mainController;

    /**
     * Sets the main controller and retrieves private variables from the main controller
     * @param controller Controller
     */
    public void setMainController(Controller controller){
        this.mainController = controller;
        this.flightTab = mainController.getFlightTab();
        this.airlineLabel = mainController.getAirlineLabel();
        this.airportLabel = mainController.getAirportLabel();
        this.routeLabel = mainController.getRouteLabel();
        this.tabPane = mainController.getTabPane();
        this.dataTab = mainController.getDataTab();
        this.datalist = mainController.getDatalist();
    }

    /**
     * Getter for the main controller
     * @return mainController
     */
    public Controller getMainController(){
        return mainController;
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
        if (in.exists()) {
            InputStream file = new FileInputStream(in);
            System.out.println("file opneedd");
            goToDataTab(airlineLabel);
            mainController.insertAirlineTable(file);
        }
    }

    /**
     * Allows the user to load airport data from a file
     *
     * @throws IOException throws IOException error
     */
    public void loadAirport() throws IOException {

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        File in = fileChooser.showOpenDialog(stage);
        if (in.exists()) {
            InputStream file = new FileInputStream(in);
            System.out.println("file opened oh ye bb~");
            goToDataTab(airportLabel);
            mainController.insertAirportTable(file);
        }
    }

    /**
     * Allows the user to load route data from a file
     *
     * @throws IOException throws IOException error
     */
    public void loadRoute() throws IOException {

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        File in = fileChooser.showOpenDialog(stage);
        if (in.exists()) {
            InputStream file = new FileInputStream(in);
            System.out.println("file opneeeedddd");
            goToDataTab(routeLabel);
            mainController.insertRouteTable(file);
        }
    }

    /**
     * Allows the user to load a flight from a file
     * @throws IOException throws IOException error
     */
    public void loadFlight() throws IOException {

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        File in = fileChooser.showOpenDialog(stage);
        if (in.exists()) {
            InputStream file = new FileInputStream(in);
            System.out.println("oooo yah flights");
            // change tab to flight tab if its not on it already
            if(!tabPane.getSelectionModel().equals(flightTab)){
                tabPane.getSelectionModel().select(flightTab);
            }
            mainController.insertFlightTable(file);
        }
    }

    /**
     * Shows Aviation Information Reader's help page
     */
    public void getHelp(){
        System.out.println("help");
        try{
            FXMLLoader fxml = new FXMLLoader();
            fxml.setLocation(getClass().getClassLoader().getResource("help.fxml"));
            Parent root = fxml.load();
            //Parent root = FXMLLoader.load(getClass().getResource("help.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setTitle("Aviation Information Reader Help");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    // change tab to data tab if its not on it already and switch the selection tab to the given name
    private void goToDataTab(String name){
        if(!tabPane.getSelectionModel().equals(dataTab)){
            tabPane.getSelectionModel().select(dataTab);
        }
        datalist.getSelectionModel().select(name);
    }

    public void resetAirline() throws IOException {
//        Repository.airlineRepository = new AirlineRepository();
//        InputStream file = getClass().getResourceAsStream("/airlines.dat");
//        //File file = new File(getClass().getClassLoader().getResource("airlines.dat").toURI());
//        if (file != null) {
//            mainController.insertAirlineTable(file);
//        }

    }

    public void resetAirport() throws IOException {
//        Repository.airportRepository = new AirportRepository();
//        InputStream file = getClass().getResourceAsStream("/airlines.dat");
//        //File file = new File(getClass().getClassLoader().getResource("airlines.dat").toURI());
//        if (file != null) {
//            mainController.insertAirportTable(file);
//        }
    }

    public void resetRoute() throws IOException {
//        Repository.routeRepository = new RouteRepository();
//        InputStream file = getClass().getResourceAsStream("/routes.dat");
//        //File file = new File(getClass().getClassLoader().getResource("airlines.dat").toURI());
//        if (file != null) {
//            mainController.insertRouteTable(file);
//        }
    }
}
