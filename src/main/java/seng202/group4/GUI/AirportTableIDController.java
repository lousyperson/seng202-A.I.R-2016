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
public class AirportTableIDController implements Initializable{

    Controller mainController;

    @FXML
    private TableColumn<airportTable, String> apid;

    @FXML
    private TableColumn<airportTable, String> apname;

    @FXML
    private TableColumn<airportTable, String> apcity;

    @FXML
    private TableColumn<airportTable, String> apcountry;

    @FXML
    private TableColumn<airportTable, String> apiata;

    @FXML
    private TableColumn<airportTable, String> apicao;

    @FXML
    private TableColumn<airportTable, String> aplat;

    @FXML
    private TableColumn<airportTable, String> aplong;

    @FXML
    private TableColumn<airportTable, String> apalt;

    @FXML
    private TableColumn<airportTable, String> aptimezone;

    @FXML
    private TableColumn<airportTable, String> apdst;

    @FXML
    private TableColumn<airportTable, String> aptz;



    /**
     * Where the program starts, initializes things like listeners and starts running the GUI.
     *
     * @param location  URL
     * @param resources ResourceBundle
     */
    public void initialize(URL location, ResourceBundle resources) {
        // initialise airport table resources
        apid.setCellValueFactory(new PropertyValueFactory<>("atid"));
        apname.setCellValueFactory(new PropertyValueFactory<>("atname"));
        apcity.setCellValueFactory(new PropertyValueFactory<>("atcity"));
        apcountry.setCellValueFactory(new PropertyValueFactory<>("atcountry"));
        apiata.setCellValueFactory(new PropertyValueFactory<>("atiata"));
        apicao.setCellValueFactory(new PropertyValueFactory<>("aticao"));
        aplat.setCellValueFactory(new PropertyValueFactory<>("atlatitude"));
        aplong.setCellValueFactory(new PropertyValueFactory<>("atlongitude"));
        apalt.setCellValueFactory(new PropertyValueFactory<>("ataltitude"));
        aptimezone.setCellValueFactory(new PropertyValueFactory<>("attimezone"));
        apdst.setCellValueFactory(new PropertyValueFactory<>("atdst"));
        aptz.setCellValueFactory(new PropertyValueFactory<>("attzdatabase"));

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
