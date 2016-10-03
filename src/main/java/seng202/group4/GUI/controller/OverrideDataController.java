package seng202.group4.GUI.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group4.GUI.ButtonResult;

import java.io.IOException;

/**
 * Shows a popup menu for overriding data when ID clashes.
 */
public class OverrideDataController {

    @FXML
    private CheckBox doAllButton;

    @FXML
    private Text overrideMessage;

    private ButtonResult result;
    private Stage stage;

    /**
     * Shows a popup menu for overriding data when ID clashes.
     *
     * @param message the message the user sees
     * @return ButtonResult
     */
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

    /**
     * Overrides the old data with current clashing data.
     */
    public void overrideData() {
        if (doAllButton.isSelected()) {
            this.result = ButtonResult.OVERRIDEALL;
        } else {
            this.result = ButtonResult.OVERRIDE;
        }
        stage.close();
    }

    /**
     * Ignores the current clashing data and retains the old data.
     */
    public void ignoreData() {
        if (doAllButton.isSelected()) {
            this.result = ButtonResult.IGNOREALL;
        } else {
            this.result = ButtonResult.IGNORE;
        }
        stage.close();
    }

    /**
     * Cancels the check and closes the popup.
     */
    public void cancelData() {
        this.result = ButtonResult.CANCEL;
        stage.close();
    }

}
