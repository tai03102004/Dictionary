package com.example.directory.Controllers.Admin;

import com.example.directory.Models.Client;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ClientCellController implements Initializable {
    public Label fullName_lbl;
    public Label username_lbl;
    public Label email_lbl;
    public Label phone_lbl;
    public Label sex_lbl;
    public Button deleted_btn;

    private Client client;

    public ClientCellController(Client client) {
        this.client = client;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fullName_lbl.textProperty().bind(client.fullNameProperty());
        username_lbl.textProperty().bind(client.userNameProperty());
        email_lbl.textProperty().bind(client.emailNameProperty());
        phone_lbl.textProperty().bind(client.phoneNameProperty());
        sex_lbl.textProperty().bind(client.sexNameProperty());
    }
}
