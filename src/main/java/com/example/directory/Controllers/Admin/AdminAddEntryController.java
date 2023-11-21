package com.example.directory.Controllers.Admin;

import com.example.directory.Models.Model;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.beans.value.ObservableValue;

public class AdminAddEntryController implements Initializable {
    public TextField addText; // target
    public HTMLEditor addEditor; // definition
    public WebView web;
    public Button success;
    public CheckBox checkboxTrueFalse;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void add(ActionEvent actionEvent) {
        String target = addText.getText().trim();
        String definition = addEditor.getHtmlText().trim();

        if (target.isEmpty() || definition.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng không để trống thông tin.");
            alert.showAndWait();
        } else {
            boolean targetExists = Model.getInstance().getDatabaseConnection().checkIfTargetExists(target);
            if (targetExists) {
                // Hiển thị cảnh báo nếu target đã tồn tại
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cảnh báo");
                alert.setHeaderText(null);
                alert.setContentText("Từ với target đã tồn tại. Vui lòng chọn target khác.");
                alert.showAndWait();
            } else {
                boolean deletedStatus = checkboxTrueFalse.isSelected();
                boolean successInsert = Model.getInstance().getDatabaseConnection().insertWord(target, definition,deletedStatus);

                if (successInsert) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thành công");
                    alert.setHeaderText(null);
                    alert.setContentText("Từ đã được thêm thành công!");
                    alert.showAndWait();

                    addText.setText("");
                    addEditor.setHtmlText("");
                }
            }
        }
    }

    public void addReset(ActionEvent actionEvent) {
        addText.setText("");
        addEditor.setHtmlText("");
    }
}
