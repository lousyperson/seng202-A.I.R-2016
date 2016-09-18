package seng202.group4;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seng202.group4.GUI.Controller;
import seng202.group4.data.repository.*;

/**
 * Main, runs the program.
 *
 */
public class App extends Application {

    // create data list
    ObservableList<String> items = FXCollections.observableArrayList("Default Airlines", "Default Airports", "Default Routes");


    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader fxml = new FXMLLoader();
        // set location for fxml file
        fxml.setLocation(getClass().getClassLoader().getResource("GUI.fxml"));
        Parent root = fxml.load();
//        Controller controller = fxml.getController();
//
//        System.out.println(controller);
//        controller.getMenuBarController().setMainController(controller);

        primaryStage.setTitle("Aviation Information Reader");
        primaryStage.setScene(new Scene(root, 1280, 775));

        primaryStage.show();
    }


    public static void main(String[] args) {
        Repository.initRepository();
        launch(args);
        Repository.serialize();
    }
}
