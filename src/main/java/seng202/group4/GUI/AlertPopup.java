package seng202.group4.GUI;

import javafx.scene.control.Alert;

/**
 * Shows error messages.
 */
public class AlertPopup {

    public static void makeError(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
