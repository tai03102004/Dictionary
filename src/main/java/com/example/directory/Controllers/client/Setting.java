package com.example.directory.Controllers.client;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.example.directory.Dictionary.TextToSpeech;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import com.example.directory.Dictionary.VoiceRSS;

public class Setting implements Initializable {
    public Slider slider;
    public ChoiceBox<String> choiceBoxUK;
    public ChoiceBox<String> choiceBoxUS;

    List<String> voiceUS = Arrays.asList("Linda", "Amy", "Mary", "John", "Mike");
    List<String> voiceUK = Arrays.asList("Alice", "Nancy", "Lily", "Harry");

    public void HandleDrag(javafx.scene.input.MouseEvent mouseEvent) {

    }

    public enum Language {
        DEFAULT("default-language"),
        EN_US("en-us"),
        EN_GB("en-gb");

        private final String code;

        Language(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        slider.setValue(VoiceRSS.speed);
        choiceBoxUK.getItems().addAll(voiceUK);
        choiceBoxUS.getItems().addAll(voiceUS);
        choiceBoxUK.setValue(VoiceRSS.voiceNameUK);
        choiceBoxUS.setValue(VoiceRSS.voiceNameUS);
    }

    private void setVoiceParameters(String voiceName, Language language) throws Exception {
        VoiceRSS.Name = voiceName;
        VoiceRSS.language = language.getCode();
        VoiceRSS.speed = slider.getValue();
        VoiceRSS.speakWord("information");
    }

    private void setVoiceParametersFromChoiceBox(ChoiceBox<String> choiceBox, Language language) throws Exception {
        String selectedVoice = choiceBox.getValue();
        setVoiceParameters(selectedVoice, language);
    }

    public void voice(ActionEvent actionEvent) throws Exception {
        setVoiceParameters("DefaultVoice", Language.DEFAULT);
    }

    public void voiceus(ActionEvent actionEvent) throws Exception {
        setVoiceParametersFromChoiceBox(choiceBoxUS, Language.EN_US);
        TextToSpeech.language = TextToSpeech.us_accent;
        TextToSpeech.VoiceAudio("hello");
    }

    public void voiceuk(ActionEvent actionEvent) throws Exception {
        setVoiceParametersFromChoiceBox(choiceBoxUK, Language.EN_GB);
        TextToSpeech.language = TextToSpeech.uk_accent;
        TextToSpeech.VoiceAudio("hello");
    }

    public void saveVoice(ActionEvent actionEvent) {
        VoiceRSS.speed = slider.getValue();
        VoiceRSS.voiceNameUS = choiceBoxUS.getValue() != null ? choiceBoxUS.getValue() : "DefaultVoiceUS";
        VoiceRSS.voiceNameUK = choiceBoxUK.getValue() != null ? choiceBoxUK.getValue() : "DefaultVoiceUK";
    }
}
