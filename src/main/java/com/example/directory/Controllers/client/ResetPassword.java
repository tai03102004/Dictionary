package com.example.directory.Controllers.client;

import com.example.directory.Models.Client;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ResetPassword extends Client implements Initializable {
    public TextField userName_tfd;
    public TextField oldPassword_tfd;
    public TextField newPassword_tfd;

    public ResetPassword() {
        super("", "", "", ""); // You may need to provide default values for the superclass
    }

    public ResetPassword(String uName, String fName, String eName, String pName) {
        super(uName, fName, eName, pName);
    }

    public TextField acceptPassword_tfd;
    public Button okePassword_btn;

    public void SuccessAccept(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String userNameValue = userNameProperty().get(); //  Giá trị của userName trong Client
        userName_tfd.setText(userNameValue);
    }
}
