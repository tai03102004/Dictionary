package com.example.directory.Controllers.client;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.directory.Dictionary.TextToSpeech;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class textTransactionController extends com.example.directory.Dictionary.TranslateAPI implements Initializable {
    private String languageFrom = "";
    private String languageTo = "";

    public Button langFromFirst;
    public Button langFromSecond;
    public Button langFromThird;
    public Button langFromFourth;

    public TextArea area1;
    public TextField area2;
    public TextField text1;
    public TextField text2;

    public Button langToFirst;
    public Button langToSecond;
    public Button langToThird;
    public Button langToFourth;
    public Button langToFifth;

    public void showWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error!");
        alert.setHeaderText(null);
        alert.setContentText("Ngôn ngữ dịch và ngôn ngữ cần dịch trùng nhau!");
        alert.showAndWait();
    }

    public void resetActiveStyleLangFrom() {
        langFromFirst.getStyleClass().removeAll("active");
        langFromSecond.getStyleClass().removeAll("active");
        langFromThird.getStyleClass().removeAll("active");
        langFromFourth.getStyleClass().removeAll("active");
    }

    public void resetActiveStyleLangTo() {
        langToFirst.getStyleClass().removeAll("active");
        langToSecond.getStyleClass().removeAll("active");
        langToThird.getStyleClass().removeAll("active");
        langToFourth.getStyleClass().removeAll("active");
        langToFifth.getStyleClass().removeAll("active");
    }

    @FXML
    public void detect() {
        resetActiveStyleLangFrom();
        langFromFirst.getStyleClass().add("active");
        languageFrom = "auto";
        text1.setText("Phát hiện ngôn ngữ");
    }

    @FXML
    void eng() {
        resetActiveStyleLangFrom();
        langFromSecond.getStyleClass().add("active");
        text1.setText("Tiếng Anh");
        languageFrom = "en";
        if (languageFrom.equals(languageTo)) {
            showWarning();
        }
    }

    @FXML
    void vie1() {
        resetActiveStyleLangFrom();
        langFromThird.getStyleClass().add("active");
        text1.setText("Tiếng Việt");
        languageFrom = "vi";
        if (languageFrom.equals(languageTo)) {
            showWarning();
        }
    }

    @FXML
    void korea() {
        resetActiveStyleLangFrom();
        langFromFourth.getStyleClass().add("active");
        text1.setText("Tiếng Hàn");
        languageFrom = "ko";
        if (languageFrom.equals(languageTo)) {
            showWarning();
        }
    }

    @FXML
    void vie2() throws IOException {
        resetActiveStyleLangTo();
        langToFirst.getStyleClass().add("active");
        text2.setText("Tiếng Việt");
        languageTo = "vi";
        if (languageFrom.equals(languageTo)) {
            showWarning();
            return;
        }
        if (!Objects.equals(area1.getText(), "")) {
            area2.setText(Translate(languageFrom, languageTo, area1.getText()));
        }
    }

    @FXML
    void eng2() throws IOException {
        resetActiveStyleLangTo();
        langToSecond.getStyleClass().add("active");
        text2.setText("Tiếng Anh");
        languageTo = "en";
        if (languageFrom.equals(languageTo)) {
            showWarning();
            return;
        }
        if (!Objects.equals(area1.getText(), "")) {
            area2.setText(Translate(languageFrom, languageTo, area1.getText()));
        }
    }

    @FXML
    void korea2() throws IOException {
        resetActiveStyleLangTo();
        langToThird.getStyleClass().add("active");
        text2.setText("Tiếng Hàn");
        languageTo = "ko";
        if (languageFrom.equals(languageTo)) {
            showWarning();
            return;
        }
        if (!Objects.equals(area1.getText(), "")) {
            area2.setText(Translate(languageFrom, languageTo, area1.getText()));
        }
    }

    @FXML
    void rus() throws IOException {
        resetActiveStyleLangTo();
        langToFourth.getStyleClass().add("active");
        text2.setText("Tiếng Nga");
        languageTo = "ru";
        if (languageFrom.equals(languageTo)) {
            showWarning();
            return;
        }
        if (!Objects.equals(area1.getText(), "")) {
            area2.setText(Translate(languageFrom, languageTo, area1.getText()));
        }
    }

    @FXML
    void chinese() throws IOException {
        resetActiveStyleLangTo();
        langToFifth.getStyleClass().add("active");
        text2.setText("Tiếng Trung");
        languageTo = "zh";
        if (languageFrom.equals(languageTo)) {
            showWarning();
            return;
        }
        if (!Objects.equals(area1.getText(), "")) {
            area2.setText(Translate(languageFrom, languageTo, area1.getText()));
        }
    }

    @FXML
    void translate() throws IOException {
        if (languageFrom.equals(languageTo)) {
            showWarning();
            return;
        }
        if (!Objects.equals(area1.getText(), "")) {
            area2.setText(Translate(languageFrom, languageTo, area1.getText()));
        }
    }

    @FXML
    void speak1() throws Exception {
        if (!Objects.equals(area1.getText(), "")) {
            TextToSpeech.VoiceAudio(area1.getText());
        }
    }

    @FXML
    void speak2() throws Exception {
        TextToSpeech.language = languageTo;
        if (!Objects.equals(area2.getText(), "")) {
            TextToSpeech.VoiceAudio(area2.getText());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        langFromFirst.getStyleClass().add("active");
        langToFirst.getStyleClass().add("active");

        area1.setText("");
        text1.setText("Phát hiện n.ngữ");
        languageFrom = "en";

        text2.setText("Tiếng Việt");
        languageTo = "vi";
    }
}
