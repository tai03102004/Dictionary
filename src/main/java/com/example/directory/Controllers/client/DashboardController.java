package com.example.directory.Controllers.client;

import com.example.directory.Controllers.LoginController;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;

public class DashboardController extends LoginController implements Initializable {

    public Label userName_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String user = getUser();
        userName_lbl.setText(user);
    }
}
