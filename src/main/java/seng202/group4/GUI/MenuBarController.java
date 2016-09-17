package seng202.group4.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Pang on 18/09/16.
 */
public class MenuBarController{

    private Tab flightTab;
    private String airlineLabel;
    private String airportLabel;
    private String routeLabel;
    private TabPane tabPane;
    private Tab dataTab;
    private ListView<String> datalist;




    private Controller mainController;

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


    public Controller getMainController(){
        return mainController;
    }


    /**
     * Allows the user to load airline data from a file
     *
     * @throws IOException
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
     * @throws IOException
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
     * @throws IOException
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
     * @throws IOException
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
}
