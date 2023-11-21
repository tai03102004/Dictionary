package com.example.directory.Controllers.client;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SortingDialog {

    private static StringProperty selectedSortProperty = new SimpleStringProperty("Sắp xếp mới nhất");

    public static void display() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Sắp xếp");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label label = new Label("Chọn cách sắp xếp:");

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Sắp xếp mới nhất", "Sắp xếp theo bảng chữ cái(tăng dần)","Sắp xếp theo bảng chữ cái(giảm dần)");
        choiceBox.setValue(selectedSortProperty.get());

        Button sortButton = new Button("Sắp xếp");
        sortButton.setOnAction(event -> {
            selectedSortProperty.set(choiceBox.getValue());
            System.out.println("Đã chọn: " + selectedSortProperty.get());
            stage.close();
        });

        layout.getChildren().addAll(label, choiceBox, sortButton);

        Scene scene = new Scene(layout, 250, 150);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public static StringProperty selectedSortProperty() {
        return selectedSortProperty;
    }

    public static String getSelectedSort() {
        return selectedSortProperty.get();
    }
}
