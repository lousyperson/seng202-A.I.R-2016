package seng202.group4.GUI;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seng202.group4.data.parser.AirlineParser;

import java.io.*;


public class Controller {

    public void loadAirline() throws FileNotFoundException {

            Stage stage = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open file");
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                AirlineParser parser = new AirlineParser(br);
            }


    }
}
