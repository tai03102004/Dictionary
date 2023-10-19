package com.example.directory.Views;

import com.example.directory.Controllers.client.ClientController;
import java.sql.SQLOutput;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {
    // Client View

    private final StringProperty clientSelectedMenuItem;
    private AnchorPane homeView;
    private AnchorPane translateView;
    public ViewFactory(){
        this.clientSelectedMenuItem = new SimpleStringProperty("");
    }

    public StringProperty getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    public AnchorPane getHomeView() {
        System.out.println("getHomeView");
        if (homeView == null ){
            try {
                homeView = new FXMLLoader(getClass().getResource("/Fxml/Client/Dashboard.fxml")).load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return homeView;
    }

    public AnchorPane getTranslateView() {
        System.out.println("getTranslateView");
        if (translateView == null ){
            try {
                translateView = new FXMLLoader(getClass().getResource("/Fxml/Client/Translate.fxml")).load();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return  translateView;
    }

    public void showLoginWindow() {
        System.out.println("showLoginWindow");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/login.fxml"));
        createStage(loader);
    }

    public void showClientWindow() {
        System.out.println("showClientWindow");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController(clientController);
        createStage(loader);
    }

    public void createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Distionary");
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }

}
