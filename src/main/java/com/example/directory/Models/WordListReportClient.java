package com.example.directory.Models;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.Initializable;

public class WordListReportClient implements Initializable {
    private final StringProperty Text;
    private final StringProperty UserNameClient;

    public WordListReportClient(String text, String userName) {
        this.Text = new SimpleStringProperty(this,"Text",text);
        this.UserNameClient = new SimpleStringProperty(this,"UserNameClient",userName) ;
    }

    public StringProperty getUserNameClient() {
        return UserNameClient;
    }

    public StringProperty textProperty() {
        return Text;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

