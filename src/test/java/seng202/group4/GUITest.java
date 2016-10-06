package seng202.group4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng202.group4.GUI.controller.Controller;

import java.io.IOException;

/**
 * JUnit tests for the GUI as much as possible.
 * Created by jjg64 on 28/08/16.
 */
public class GUITest extends Application{

    /**
     * Initializes everything for the GUITest class.
     *
     * @param primaryStage a stage parameter
     * @throws Exception throws an exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
//        FXMLLoader loader = new FXMLLoader();
//        Parent root = loader.load(getClass().getClassLoader().getResource("GUI.fxml").openStream());
//        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        //Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("../GUI.fxml"));
//        primaryStage.setTitle("Aviation Information Reader");
//        primaryStage.setScene(new Scene(root, 600, 775));
//        primaryStage.show();
        test();
    }

    private void test() throws IOException {
        testAirline();
        testAirport();
        testRoute();
    }

    private void testAirline() throws IOException {
        ValidatorGUITest.testInvalidAirlineFile("testfiles/Airlines/oneDodgyAirline.txt");
    }

    private void testRoute() throws IOException {
        ValidatorGUITest.testInvalidRouteFile("testfiles/Routes/oneDodgyRoute.txt");
    }

    private void testAirport() throws IOException {
        ValidatorGUITest.testInvalidAirportFile("testfiles/Airports/invalidAirport.txt");
    }

    /**
     * The main class.
     *
     * @param args a String argument parameter
     */
    public static void main(String[] args) {
        launch(args);
    }
}
