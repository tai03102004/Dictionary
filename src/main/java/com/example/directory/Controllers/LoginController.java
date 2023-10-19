package com.example.directory.Controllers;

import com.example.directory.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.Stage;
public class LoginController implements Initializable {
    public ChoiceBox acc_selector;
    public Label account_dictionary_lbl;
    public TextField account_dictionary_fld;
    public TextField password_fld;
    public Button login_btn;
    public Label Error_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_btn.setOnAction(event-> onLogin());
    }

    private void onLogin() {
        Stage stage = (Stage) Error_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showClientWindow();
    }

}
