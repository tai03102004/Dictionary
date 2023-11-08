package com.example.directory.Controllers.client;

import com.example.directory.Models.Model;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.Stage;

public class ClientController implements Initializable {

    public BorderPane client_parent;
    public int count = 0 ;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
            switch (newVal) {
                case TRANSLATE -> client_parent.setCenter(Model.getInstance().getViewFactory().getTranslateView());
                case ACCOUNTS -> client_parent.setCenter(Model.getInstance().getViewFactory().getAccountView());
                case GAME -> client_parent.setCenter(Model.getInstance().getViewFactory().getGameView());
                case TEXTTRANSACTION -> client_parent.setCenter(Model.getInstance().getViewFactory().getTextTransactionView());
                case SETTING -> client_parent.setCenter(Model.getInstance().getViewFactory().getSettingView());
                default -> client_parent.setCenter(Model.getInstance().getViewFactory().getHomeView());
            }
        });
    }
}
