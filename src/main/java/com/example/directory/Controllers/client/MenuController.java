package com.example.directory.Controllers.client;

import com.example.directory.Models.Model;
import com.example.directory.Views.ClientMenuOption;
import java.util.Optional;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MenuController implements Initializable {
    public Button home_btn;
    public Button translate_btn;
    public Button textTransaction_btn;
    public Button game_btn;
    public Button save_btn;
    public Button history_btn;
    public Button profile_btn;
    public Button logout_btn;
    public Button install_btn;
    public Button report_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        home_btn.setOnAction(event -> onSelectMenuItem(ClientMenuOption.DASHBOARD));
        translate_btn.setOnAction(event -> onSelectMenuItem(ClientMenuOption.TRANSLATE));
        profile_btn.setOnAction(event -> onSelectMenuItem(ClientMenuOption.ACCOUNTS));
        game_btn.setOnAction(event -> onSelectMenuItem(ClientMenuOption.GAME));
        textTransaction_btn.setOnAction(event -> onSelectMenuItem(ClientMenuOption.TEXTTRANSACTION));
        install_btn.setOnAction(event -> onSelectMenuItem(ClientMenuOption.SETTING));
        history_btn.setOnAction(event->onSelectMenuItem(ClientMenuOption.HISTORY));
        save_btn.setOnAction(actionEvent -> onSelectMenuItem(ClientMenuOption.SAVED));
        logout_btn.setOnAction(event -> onLogout());
    }

    private void onSelectMenuItem(ClientMenuOption option) {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(option);
    }

    public void onLogout() {
        // Get Stage
        Stage stage = (Stage) home_btn.getScene().getWindow();
        // Close the CLient Window
        Model.getInstance().getViewFactory().closeStage(stage);

        // Show Login Window
        Model.getInstance().getViewFactory().showLoginWindow();

        // Set Client Login Success Flag to false
        Model.getInstance().setClientLoginSuccessFlag(false);
    }


}
