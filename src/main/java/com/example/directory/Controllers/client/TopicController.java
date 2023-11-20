package com.example.directory.Controllers.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class TopicController {

    @FXML
    private Button Economic;

    @FXML
    private AnchorPane GameMenu;

    @FXML
    private Button Natural;

    @FXML
    private Button Sport;

    @FXML
    private Button Technology;

    protected static String topic = "ELearning.txt";
    protected final String tech = "Technology.txt";
    private final String eco = "Economic.txt";
    private final String nt = "Natural.txt";
    private final String sp = "Sport.txt";
    protected static String filePath ;
    protected static String wordsTopic ;

    @FXML
    public void EconomicButton(ActionEvent event) throws IOException {
        setTopic(eco);
        setFilePath();
        setTopicPath();
        System.out.println("Topic: Economic");
        System.out.println("MainMenuView");
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxml/Client/MainGameMenu.fxml")));
        GameMenu.getChildren().removeAll();
        GameMenu.getChildren().setAll(pane);
    }

    @FXML
    public void NaturalButton(ActionEvent event) throws Exception {
        setTopic(nt);
        setFilePath();
        setTopicPath();
        System.out.println("Topic: Natural");
        System.out.println("MainMenuView");
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxml/Client/MainGameMenu.fxml")));
        GameMenu.getChildren().removeAll();
        GameMenu.getChildren().setAll(pane);
    }

    @FXML
    public void SportButton(ActionEvent event) throws Exception {
        setTopic(sp);
        setFilePath();
        setTopicPath();
        System.out.println("Topic: Sport");
        System.out.println("MainMenuView");
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxml/Client/MainGameMenu.fxml")));
        GameMenu.getChildren().removeAll();
        GameMenu.getChildren().setAll(pane);
    }

    @FXML
    public void TechnologyButton(ActionEvent event) throws Exception {
        setTopic(tech);
        setFilePath();
        setTopicPath();
        System.out.println("Topic: Technology");
        System.out.println("MainMenuView");
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxml/Client/MainGameMenu.fxml")));
        GameMenu.getChildren().removeAll();
        GameMenu.getChildren().setAll(pane);
    }

    public void setTopic(String tp) {
        topic = tp;
    }

    public String getTopic() {
        return topic;
    }

    public String getFilePath() {
        return filePath;
    }
    public void setFilePath() {
        filePath = System.getProperty("user.dir") + "/src/main/resources/vocab/Elearning/" + getTopic();
    }

    public static String getWordsTopic() {
        return wordsTopic;
    }
    public void setTopicPath() {
        wordsTopic = System.getProperty("user.dir") + "/src/main/resources/vocab/Game/" + getTopic();
    }
}

