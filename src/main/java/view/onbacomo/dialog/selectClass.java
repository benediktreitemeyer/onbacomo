package view.onbacomo.dialog;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class selectClass {
    public void createDialog(String string) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(string);
        alert.setContentText("Please select:" + string);
        alert.showAndWait();
    }
}
