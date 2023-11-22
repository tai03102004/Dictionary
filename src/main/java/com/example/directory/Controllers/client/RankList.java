package com.example.directory.Controllers.client;

import com.example.directory.Controllers.LoginController;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import com.example.directory.Models.RankUser;
import javafx.scene.control.Label;
public class RankList extends LoginController implements Initializable {
    @FXML
    private Label headerLabel;

    @FXML
    private ListView<String> rankListView;

    public void initializeData() {
        String userName = getUser();

        ObservableList<String> rankData = FXCollections.observableArrayList(
                userName + ": 100 điểm",
                "Người dùng 2: 90 điểm",
                "Người dùng 3: 80 điểm"
        );

        rankListView.setItems(rankData);

        saveRankDataToDatabase(userName, 100);
        saveRankDataToDatabase("Người dùng 2", 90);
        saveRankDataToDatabase("Người dùng 3", 80);
    }

    private void saveRankDataToDatabase(String userName, int point) {
        RankUser rankUser = new RankUser();
        rankUser.setUserName(userName);
        rankUser.setPoint(point);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeData();
        setCustomStyles();
    }
    private void setCustomStyles() {
        rankListView.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Styles/RankUser.css")).toExternalForm());
        rankListView.setId("rankListView");

        headerLabel.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Styles/RankUser.css")).toExternalForm());
        headerLabel.setId("headerLabel");
    }
}
