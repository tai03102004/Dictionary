package com.example.directory.Controllers.client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
public class AccountsController implements Initializable {
    @FXML
    public TextField userName_lbl;
    @FXML
    public TextField name_lbl;
    @FXML
    public TextField email_lbl;
    @FXML
    public TextField phone_lbl;
    public RadioButton Nam_lbl;
    public RadioButton Nu_lbl;
    public RadioButton Khac_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup genderToggleGroup = new ToggleGroup();

        Nam_lbl.setToggleGroup(genderToggleGroup);
        Nu_lbl.setToggleGroup(genderToggleGroup);
        Khac_lbl.setToggleGroup(genderToggleGroup);

        genderToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                showAlert();
                oldValue.setSelected(true);
            }
        });
    }
    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Cảnh báo");
        alert.setHeaderText(null);
        alert.setContentText("Vui lòng chọn một tùy chọn giới tính!");
        alert.showAndWait();
    }
}
