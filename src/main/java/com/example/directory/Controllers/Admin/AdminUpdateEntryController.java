package com.example.directory.Controllers.Admin;

import com.example.directory.Models.Model;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

public class AdminUpdateEntryController implements Initializable {

    public ToggleGroup data;
    public TextField editTextEV; // target
    public WebView web;
    public HTMLEditor edit; // definition
    public CheckBox checkboxTrueFalse; // checkboxTrueFalse

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        checkboxTrueFalse.setSelected(false);

        editTextEV.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean trueCheckbox = Model.getInstance().getDatabaseConnection().selectStatusDeleted(newValue);
            checkboxTrueFalse.setSelected(trueCheckbox);
        });

        editTextEV.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                edit.setHtmlText(newValue);

                if (!newValue.isEmpty()) {
                    String currentDefinition = Model.getInstance().getDatabaseConnection().getDefinitionForTarget(newValue);
                    if (currentDefinition != null) {
                        edit.setHtmlText(currentDefinition);
                    }
                }
            }
        });
    }

    public void save(ActionEvent actionEvent) {
        String target = editTextEV.getText();
        String definition = edit.getHtmlText();
        System.out.println(checkboxTrueFalse.isSelected());
        boolean targetExists = Model.getInstance().getDatabaseConnection().checkIfTargetExists(target);
        if (targetExists) {
            if (checkboxTrueFalse.isSelected()) {
                Model.getInstance().getDatabaseConnection().updateDeletedStatus(target, true);
            } else {
                Model.getInstance().getDatabaseConnection().updateDeletedStatus(target, false);
            }

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Từ với target đã tồn tại. Đã cập nhật trạng thái deleted.");
            alert.showAndWait();
        }

        if (target.isEmpty() || definition.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng không để trống thông tin.");
            alert.showAndWait();
        } else {

            boolean successInsert = Model.getInstance().getDatabaseConnection().updateWord(target, definition);

            if (successInsert) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thành công");
                alert.setHeaderText(null);
                alert.setContentText("Sửa từ thành công!");
                alert.showAndWait();

                editTextEV.setText("");
                edit.setHtmlText("");
            }
        }
    }

    public void remove(ActionEvent actionEvent) {
        String targetToRemove = editTextEV.getText().trim();

        if (targetToRemove.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng nhập từ cần xóa.");
            alert.showAndWait();
        } else {
            boolean success = Model.getInstance().getDatabaseConnection().markAsDeleted(targetToRemove);

            if (success) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thành công");
                alert.setHeaderText(null);
                alert.setContentText("Từ đã được đánh dấu xóa thành công!");
                alert.showAndWait();

                editTextEV.setText("");
                edit.setHtmlText("");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Đánh dấu xóa từ không thành công. Vui lòng thử lại!");
                alert.showAndWait();
            }
        }
    }
}
