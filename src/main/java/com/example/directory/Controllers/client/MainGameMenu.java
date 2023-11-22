package com.example.directory.Controllers.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class MainGameMenu extends TopicController{

    @FXML
    private Button Game1;

    @FXML
    private Button Game2;

    @FXML
    private AnchorPane GameMenu;

    @FXML
    private Button ChooseTopic;

    @FXML
    void HandleClickButtonToGame1(ActionEvent event) throws IOException {
        System.out.println("SpeedKeyTest");
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxml/Client/Game.fxml")));
        GameMenu.getChildren().removeAll();
        GameMenu.getChildren().setAll(pane);
    }
    @FXML
    void HandleClickButtonToGame2(ActionEvent event) throws IOException{
        System.out.println("EnglishLearning");
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxml/Client/ELearning.fxml")));
        GameMenu.getChildren().removeAll();
        GameMenu.getChildren().setAll(pane);
    }
    @FXML
    void ChooseTopicButton(ActionEvent event) throws IOException{
        System.out.println("ChooseTopic");
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxml/Client/Topic.fxml")));
        GameMenu.getChildren().removeAll();
        GameMenu.getChildren().setAll(pane);
    }

}

