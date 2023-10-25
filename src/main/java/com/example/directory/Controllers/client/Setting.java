package com.example.directory.Controllers.client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;

public class Setting implements Initializable {
    public Slider slider;
    public ChoiceBox<String> choiceBoxUK;
    public ChoiceBox<String> choiceBoxUS;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBoxUK.getItems().addAll("Item1", "Item2", "Item3");
        choiceBoxUS.getItems().addAll("ItemA", "ItemB", "ItemC");
    }

    public void voice(ActionEvent actionEvent) {
        // Add logic for the "voice" button (e.g., handle the action when this button is clicked)
        String selectedUKItem = choiceBoxUK.getValue();
        System.out.println("UK Choice: " + selectedUKItem);
    }

    public void voiceus(ActionEvent actionEvent) {
        // Add logic for the "voiceus" button (e.g., handle the action when this button is clicked)
        String selectedUSItem = choiceBoxUS.getValue();
        System.out.println("US Choice: " + selectedUSItem);
    }

    public void voiceuk(ActionEvent actionEvent) {
        // Add logic for the "voiceuk" button (e.g., handle the action when this button is clicked)
    }

    public void saveVoice(ActionEvent actionEvent) {
        // Add logic for the "saveVoice" button (e.g., save the settings)
        double sliderValue = slider.getValue();
        System.out.println("Slider Value: " + sliderValue);
    }
}