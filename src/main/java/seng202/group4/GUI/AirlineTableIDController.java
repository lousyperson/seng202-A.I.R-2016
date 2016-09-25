package seng202.group4.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by psu43 on 25/09/16.
 */
public class AirlineTableIDController implements Initializable {

    @FXML
    private TableColumn<airlineTable, String> aid;

    @FXML
    private TableColumn<airlineTable, String> aname;

    @FXML
    private TableColumn<airlineTable, String> aalias;

    @FXML
    private TableColumn<airlineTable, String> aiata;

    @FXML
    private TableColumn<airlineTable, String> aicao;

    @FXML
    private TableColumn<airlineTable, String> acallsign;

    @FXML
    private TableColumn<airlineTable, String> acountry;

    @FXML
    private TableColumn<airlineTable, String> aactive;

    public Controller getMainController() {
        return mainController;
    }

    Controller mainController;

    /**
     * Where the program starts, initializes things like listeners and starts running the GUI.
     *
     * @param location  URL
     * @param resources ResourceBundle
     */
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("init for airlinetableid");
        // initialise airline table resources
        aid.setCellValueFactory(new PropertyValueFactory<>("rid"));
        aname.setCellValueFactory(new PropertyValueFactory<>("rname"));
        aalias.setCellValueFactory(new PropertyValueFactory<>("ralias"));
        aiata.setCellValueFactory(new PropertyValueFactory<>("riata"));
        aicao.setCellValueFactory(new PropertyValueFactory<>("ricao"));
        acallsign.setCellValueFactory(new PropertyValueFactory<>("rcallsign"));
        acountry.setCellValueFactory(new PropertyValueFactory<>("rcountry"));
        aactive.setCellValueFactory(new PropertyValueFactory<>("ractive"));

    }
    /**
     * Sets the main controller and retrieves private variables from the main controller
     *
     * @param controller Controller
     */
    public void setMainController(Controller controller) {
        this.mainController = controller;

    }

}