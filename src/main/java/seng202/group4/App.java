package seng202.group4;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application {


    // create data list
    ObservableList<String> items = FXCollections.observableArrayList("Default Airlines", "Default Airports", "Default Routes");


    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getClassLoader().getResource("GUI.fxml").openStream());
//        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        //Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("../GUI.fxml"));
        primaryStage.setTitle("Aviation Information Reader");
        primaryStage.setScene(new Scene(root, 1050, 775));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
