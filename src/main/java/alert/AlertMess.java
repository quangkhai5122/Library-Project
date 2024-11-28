package alert;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertMess {
    private Alert alert;

    public void errorMessage(String message) {
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void successMessage(String message) {
        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
