package seng202.group4.GUI;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group4.App;
import seng202.group4.data.dataType.Airline;
import seng202.group4.data.repository.AirlineRepository;
import seng202.group4.data.repository.AirportRepository;
import seng202.group4.data.repository.Repository;
import seng202.group4.data.repository.RouteRepository;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Optional;

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
     *
     * @param controller Controller
     */
    public void setMainController(Controller controller) {
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
     *
     * @return mainController
     */
    public Controller getMainController() {
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
        if (in != null && in.exists()) {
            InputStream file = new FileInputStream(in);
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
        if (in != null && in.exists()) {
            InputStream file = new FileInputStream(in);
            //System.out.println("file opened oh ye bb~");
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
        if (in != null && in.exists()) {
            InputStream file = new FileInputStream(in);
            //System.out.println("file opneeeedddd");
            goToDataTab(routeLabel);
            mainController.insertRouteTable(file);
        }
    }

    /**
     * Allows the user to load a flight from a file
     *
     * @throws IOException throws IOException error
     */
    public void loadFlight() throws IOException {

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        File in = fileChooser.showOpenDialog(stage);
        if (in != null && in.exists()) {
            InputStream file = new FileInputStream(in);
            //System.out.println("oooo yah flights");
            // change tab to flight tab if its not on it already
            if (!tabPane.getSelectionModel().equals(flightTab)) {
                tabPane.getSelectionModel().select(flightTab);
            }
            mainController.insertFlightTable(file);
        }
    }

    public void loadFlight2(String someCSV) throws IOException {
        InputStream file = new ByteArrayInputStream(someCSV.getBytes(StandardCharsets.UTF_8));
        mainController.insertFlightTable(file);
    }

    /**
     * Shows Aviation Information Reader's help page
     */
    public void getHelp() {
        //System.out.println("help");
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // change tab to data tab if its not on it already and switch the selection tab to the given name
    private void goToDataTab(String name) {
        if (!tabPane.getSelectionModel().equals(dataTab)) {
            tabPane.getSelectionModel().select(dataTab);
        }
        datalist.getSelectionModel().select(name);
    }

    /**
     * Clears the airline table and AirlineRepository then replaces them with the default airlines
     * @throws IOException when default airline file cannot be read
     */
    public void resetAirline() throws IOException {
        boolean result = resetConformation();
        if (result) {
            mainController.clearAirlineTable();
            Repository.airlineRepository = new AirlineRepository();
            InputStream file = getClass().getResourceAsStream("/airlines.dat");
            //File file = new File(getClass().getClassLoader().getResource("airlines.dat").toURI());
            if (file != null) {
                mainController.insertAirlineTable(file);
            }
            Repository.serializeObject(Repository.airlineRepository, "airline");
        }
    }

    /**
     * Clears the airport table and AirportRepository then replaces them with the default airports
     * @throws IOException when default airport file cannot be read
     */
    public void resetAirport() throws IOException {
        boolean result = resetConformation();
        if (result) {
            mainController.clearAirportTable();
            Repository.airportRepository = new AirportRepository();
            InputStream file = getClass().getResourceAsStream("/airports.dat");
            //File file = new File(getClass().getClassLoader().getResource("airlines.dat").toURI());
            if (file != null) {
                mainController.insertAirportTable(file);
            }
            Repository.serializeObject(Repository.airportRepository, "airport");
        }
    }

    /**
     * Clears the route table and routeRepository then replaces them with the default routes
     * @throws IOException when default route file cannot be read
     */
    public void resetRoute() throws IOException {
        boolean result = resetConformation();
//        boolean done = false;

        if (result) {
            ProgressBar pb = new ProgressBar();
            ProgressIndicator pin = new ProgressIndicator();
            Button toClose = new Button();
            toClose.setText("Click me!");
            toClose.setVisible(false);

            Task<Void> task = new Task<Void>() {
                @Override
                public Void call() {
                    // process long-running computation, data retrieval, etc...
                    mainController.clearRouteTable();
                    Repository.routeRepository = new RouteRepository();
                    InputStream file = getClass().getResourceAsStream("/routes.dat");
                    //File file = new File(getClass().getClassLoader().getResource("airlines.dat").toURI());
                    if (file != null) {
                        try {
                            mainController.insertRouteTable(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Repository.serializeObject(Repository.routeRepository, "route");
                    return null;
                }
            };

            task.setOnSucceeded(e -> {
                pb.setProgress(1.0f);
                pin.setProgress(1.0f);
                toClose.setVisible(true);
            });

            new Thread(task).start();

            Stage stage = new Stage();
            Group root = new Group();
            Scene scene = new Scene(root, 300, 150);
            stage.setScene(scene);
            stage.setTitle("Progress Controls");

            Label label = new Label();
            label.setText("loading: ");

            pb.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);

            pin.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
            HBox hb = new HBox();
            hb.setSpacing(5);
            hb.setAlignment(Pos.CENTER);
            hb.getChildren().addAll(label, pb, pin);

            VBox vb = new VBox();

            vb.setSpacing(5);
            vb.getChildren().addAll(hb, toClose);
            scene.setRoot(vb);
            stage.show();
            toClose.setOnAction( event ->
                    stage.close()
            );
        }
    }

    private boolean resetConformation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure you want to reset your selected data?");
        alert.setContentText("This will replace the data with the default data.\nThis may take a few moments" +
                "\n\nWARNING: The action cannot be undone and may take a while.\n");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    private Alert showLoading() {
        Alert loading = new Alert(Alert.AlertType.INFORMATION);
        loading.setTitle("Loading...");
        loading.setHeaderText("Loading default data");
        loading.setContentText("This will close when data is loaded");
        return loading;
    }
}