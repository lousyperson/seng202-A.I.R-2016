package seng202.group4.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 *
 */
public class OverrideDataController {

    @FXML
    private CheckBox doAllButton;

    @FXML
    private Text overrideMessage;

    private ButtonResult result;
    private Stage stage;

    public static ButtonResult getPopUpResult(String message) {
        try {
            FXMLLoader fxml = new FXMLLoader();
            Parent root = fxml.load(OverrideDataController.class.getClassLoader().getResourceAsStream("overrideData.fxml"));
            OverrideDataController popUp = fxml.getController();
            popUp.overrideMessage.setText(message);
            popUp.stage = new Stage();
            popUp.stage.initModality(Modality.APPLICATION_MODAL);
            popUp.stage.setResizable(false);
            popUp.stage.setTitle("Clashing ID");
            popUp.stage.setScene(new Scene(root, 450, 200));
            popUp.stage.showAndWait();
            return popUp.result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void overrideData() {
        if (doAllButton.isSelected()) {
            this.result = ButtonResult.OVERRIDEALL;
        } else {
            this.result = ButtonResult.OVERRIDE;
        }
        stage.close();
    }

    public void ignoreData() {
        if (doAllButton.isSelected()) {
            this.result = ButtonResult.IGNOREALL;
        } else {
            this.result = ButtonResult.IGNORE;
        }
        stage.close();
    }

    public void cancelData() {
        this.result = ButtonResult.CANCEL;
        stage.close();
    }

}
