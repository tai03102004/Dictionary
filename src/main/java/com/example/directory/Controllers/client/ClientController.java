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
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().addListener((observableValue,oldVal,newVal) ->{
            switch (newVal){
                case "Translate" -> client_parent.setCenter(Model.getInstance().getViewFactory().getTranslateView());
                default -> client_parent.setCenter(Model.getInstance().getViewFactory().getHomeView());
            }
        });
    }

}
