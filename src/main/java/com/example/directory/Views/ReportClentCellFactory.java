package com.example.directory.Views;

import com.example.directory.Controllers.Admin.ReportClientCellController;
import com.example.directory.Models.WordListReportClient;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class ReportClentCellFactory extends ListCell<WordListReportClient> {
    @Override

    protected void updateItem(WordListReportClient reportClient , boolean empty) {
        super.updateItem(reportClient,empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            System.out.println("List View Report Client");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/ReportClientCell.fxml"));
            ReportClientCellController controller = new ReportClientCellController(reportClient);
            loader.setController(controller);
            setText(null);
            try {
                setGraphic(loader.load());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
