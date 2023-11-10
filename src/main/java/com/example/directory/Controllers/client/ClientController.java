package com.example.directory.Controllers.client;

import com.example.directory.Models.Model;

import javafx.fxml.Initializable;

import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    public BorderPane client_parent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
            switch (newVal) {
                case TRANSLATE -> client_parent.setCenter(Model.getInstance().getViewFactory().getTranslateView());
                case ACCOUNTS -> client_parent.setCenter(Model.getInstance().getViewFactory().getAccountView());
                case GAME -> client_parent.setCenter(Model.getInstance().getViewFactory().getGameView());
                case TEXTTRANSACTION -> client_parent.setCenter(Model.getInstance().getViewFactory().getTextTransactionView());
                case SETTING -> client_parent.setCenter(Model.getInstance().getViewFactory().getSettingView());
                case HISTORY -> client_parent.setCenter(Model.getInstance().getViewFactory().getHistoryView());
                case SAVED -> client_parent.setCenter(Model.getInstance().getViewFactory().getSavedView());
                default -> client_parent.setCenter(Model.getInstance().getViewFactory().getHomeView());
            }
        });
    }
}
