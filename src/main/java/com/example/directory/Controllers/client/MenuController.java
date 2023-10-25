package com.example.directory.Controllers.client;

import com.example.directory.Models.Model;
import com.example.directory.Views.ClientMenuOption;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
public class MenuController implements Initializable {
    public Button home_btn;
    public Button translate_btn;
    public Button textTransaction_btn;
    public Button accounts_btn;
    public Button game_btn;
    public Button save_btn;

    public Button history_btn;

    public Button profile_btn;
    public Button layout_btn;
    public Button install_btn;
    public Button report_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }
    private void addListeners() {
        home_btn.setOnAction(event -> onHome() );
        translate_btn.setOnAction(event -> onTranslate());
        profile_btn.setOnAction(event -> onAccount());
        game_btn.setOnAction(event -> onGame());
        textTransaction_btn.setOnAction(event->onTextTransaction());
        install_btn.setOnAction(event->onSetting());
    }

    private void onSetting() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOption.SETTING);
    }

    private void onAccount() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOption.ACCOUNTS);
    }

    private void onHome() {
        System.out.println("Home");
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOption.DASHBOARD);
    }
    private void onTranslate() {
        System.out.println("Translate");
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOption.TRANSLATE);
    }
    private void onTextTransaction() {
        System.out.println("TextTransaction");
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOption.TEXTTRANSACTION);
    }
    private void onGame() {
        System.out.println("Game");
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOption.GAME);
    }

}
