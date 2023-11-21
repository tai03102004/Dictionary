package com.example.directory.Controllers.Admin;

import com.example.directory.Models.WordListReportClient;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;


public class ReportClientCellController implements Initializable {

    public Label userName;

    public TextArea text;
    private WordListReportClient reportClient;

    public ReportClientCellController (WordListReportClient reportClient) {
        this.reportClient = reportClient;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userName.textProperty().bind(reportClient.getUserNameClient());
        text.textProperty().bind(reportClient.textProperty());
    }
}
