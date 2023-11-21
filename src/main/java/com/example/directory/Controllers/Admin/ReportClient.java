package com.example.directory.Controllers.Admin;

import com.example.directory.Models.Model;
import com.example.directory.Models.WordListReportClient;
import com.example.directory.Views.ReportClentCellFactory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ReportClient implements Initializable {
    public ListView<WordListReportClient> ReportClients;
    public Button sortReportCLient;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initData();
        ReportClients.setItems(Model.getInstance().getWordListReportClient());
        ReportClients.setCellFactory(e -> new ReportClentCellFactory());
    }
    public void initData() {
        if (Model.getInstance().getWordListReportClient().isEmpty()) {
            Model.getInstance().setWordListReportClient();
        }
    }
}
