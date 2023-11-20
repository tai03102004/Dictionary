package com.example.directory.Controllers.client;

import com.example.directory.Dictionary.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class eLearningController extends TopicController implements Initializable {

    @FXML
    private AnchorPane GameMenu;

    @FXML
    private Text definition_text;

    @FXML
    private Button escapeGame_btn;

    @FXML
    private Button nextWord;

    @FXML
    private Button previousWord;

    @FXML
    private Text programWord_text;

    private List<Word> words = new ArrayList<>();
    private int wordCount = 0;
    @FXML
    void HandleNextWord(ActionEvent event) {
        wordCount++;
        if (wordCount == words.size()) wordCount = 0;
        programWord_text.setText(words.get(wordCount).getWordTarget());
        definition_text.setText(words.get(wordCount).getWordExplain());
    }

    @FXML
    void HandlePreviousWord(ActionEvent event) {
        wordCount--;
        if (wordCount < 0) wordCount = words.size() - 1;
        programWord_text.setText(words.get(wordCount).getWordTarget());
        definition_text.setText(words.get(wordCount).getWordExplain());
    }

    @FXML
    void toMainScreen(ActionEvent event) throws IOException {
        System.out.println("GetGame");
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxml/Client/MainGameMenu.fxml")));
        GameMenu.getChildren().removeAll();
        GameMenu.getChildren().setAll(pane);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            String line;
            BufferedReader in = new BufferedReader(new FileReader(getFilePath()));
            while ((line = in.readLine()) != null) {
                String[] wordsInLine = line.split("\t");
                Word word = new Word(wordsInLine[0], wordsInLine[1]);
                words.add(word);
            }
            programWord_text.setText(words.get(0).getWordTarget());
            definition_text.setText(words.get(0).getWordExplain());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
