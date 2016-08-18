package seng202.group4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getClassLoader().getResource("GUI.fxml").openStream());
//        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        //Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("../GUI.fxml"));
        primaryStage.setTitle("A.I.R.");
        primaryStage.setScene(new Scene(root, 600, 775));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
