package com.example.directory.Controllers.client;

import com.example.directory.Dictionary.Dictionary;
import com.example.directory.Dictionary.Spelling;
import com.example.directory.Dictionary.TextToSpeech;
import com.example.directory.Dictionary.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

public class TranslateController implements Initializable {

    private static final String evPath = "src/main/resources/vocab/eng_vie.txt";
    private static final String vePath = "src/main/resources/vocab/vie_eng.txt";
    private static final String enHistoryPath = "src/main/resources/vocab/eng_history.txt";
    private static final String viHistoryPath = "src/main/resources/vocab/vie_history.txt";
    private static final String enBookmarkPath = "src/main/resources/vocab/eng_bookmark.txt";
    private static final String viBookmarkPath = "src/main/resources/vocab/vie_bookmark.txt";

    public WebView Translate_listview;
    public Button transLanguageEV;
    public Button transLanguageVE;
    public TextField searchField;
    public ListView<String> wordListView; //các từ có thể có
    public Label headText; //Tiêu đề
    public Label speaker1Language;
    public Button speaker1;
    public Label speaker2Language;
    public Button speaker2;
    public Button saveChangeButton;
    public Button bookmarkTrue;
    public Button bookmarkFalse;
    public Button editButton;
    public Button removeButton;
    public WebView definitionView;

    protected boolean isEV = true;
    protected static Dictionary evDic = new Dictionary(evPath, enHistoryPath, enBookmarkPath);
    protected static Dictionary veDic = new Dictionary(vePath, viHistoryPath, viBookmarkPath);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void WarningAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error!");
        alert.setHeaderText(null);
        alert.setContentText("Không có từ nào được chọn!");
        alert.showAndWait();
    }


    public void handleClickTransButton(ActionEvent actionEvent) { // Choose EV dictionary or VE dictionary
        isEV = !isEV;
        if(isEV) {
            transLanguageEV.setVisible(true);
            transLanguageVE.setVisible(false);
            speaker1Language.setText("UK");
            speaker2.setVisible(true);
            speaker2Language.setVisible(true);
        } else {
            transLanguageEV.setVisible(false);
            transLanguageVE.setVisible(true);
            speaker1Language.setText("VIE");
            speaker2.setVisible(false);
            speaker2Language.setVisible(false);
        }

    }

    public Dictionary getCurrentDic() {
        if (isEV) return evDic;
        else return veDic;
    }

    public void handleHistorySearchBar(KeyEvent keyEvent) throws IOException {

    }

    public void handleClickListView(MouseEvent mouseEvent) throws IOException { // tim tu
        String word = searchField.getText();
        if (getCurrentDic().getVocabulary().get(word) == null) {
            Spelling corrector = new Spelling("src/main/resources/vocab/spelling.txt");
            word = corrector.correct(word);
        }
        searchField.setText(word);
    }

    public void showHistoryWordDefinition(MouseEvent mouseEvent) throws IOException { //in ra definition
        String spelling = wordListView.getSelectionModel().getSelectedItem();
        if (spelling == null) {
            return;
        }
        String meaning = getCurrentDic().getVocabulary().get(spelling).getWordExplain();
        definitionView.getEngine().loadContent(meaning, "text/html");
        if (getCurrentDic().getHistoryVocabulary().get(spelling) == null) {
            getCurrentDic().addWordToFile(spelling, meaning, getCurrentDic().getHistory());
        }
    }

    public void handleClickSpeaker1(ActionEvent actionEvent) { //UK's accent
        if(isEV) {
            TextToSpeech.language = TextToSpeech.uk_accent;
            TextToSpeech.VoiceAudio(searchField.getText());
        } else {
            TextToSpeech.language = TextToSpeech.vietnamese;
            TextToSpeech.VoiceAudio(searchField.getText());
        }
    }

    public void handleClickSpeaker2(ActionEvent actionEvent) { //US's accent
        TextToSpeech.language = TextToSpeech.us_accent;
        TextToSpeech.VoiceAudio(searchField.getText());
    }

    public void handleClickSaveButton(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText("Sửa từ thành công!");
        alert.showAndWait();
    }

    public void handleClickBookmarkButton(ActionEvent actionEvent) throws IOException{
        String spelling = searchField.getText();
        if (spelling.equals("")) {
            WarningAlert();
            return ;
        } else {

        }

    }

    public void handleClickEditButton(ActionEvent actionEvent) throws IOException{
        String spelling = searchField.getText();
        if (spelling.equals("")) {
            WarningAlert();
            return;
        }
    }

    public void handleClickRemoveButton(ActionEvent actionEvent) throws IOException{
        String spelling = searchField.getText();
        if (spelling.equals("")) {
            WarningAlert();
            return;
        }
    }

}
