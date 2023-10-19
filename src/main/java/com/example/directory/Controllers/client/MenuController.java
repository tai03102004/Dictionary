package com.example.directory.Controllers.client;

import com.example.directory.Models.Model;
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
    }

    private void onHome() {
        System.out.println("Home");
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set("Home");
    }
    private void onTranslate() {
        System.out.println("Translate");
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set("Translate");
    }
}
