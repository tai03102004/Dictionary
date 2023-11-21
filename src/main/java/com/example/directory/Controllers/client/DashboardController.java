package com.example.directory.Controllers.client;

import com.example.directory.Controllers.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javax.swing.text.html.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController extends LoginController implements Initializable {

    @FXML
    public Label userName_lbl;
    @FXML
    public TextField txt_search;
    @FXML
    public ProgressBar progress4;
    @FXML
    public Pane pane_14;
    @FXML
    public Pane pane_13;
    @FXML
    public ProgressBar progress2;
    @FXML
    public Pane pane_12;
    @FXML
    public ProgressBar progress1;
    public Pane pane_1;
    public ProgressBar progress;
    public Pane pane_11;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String user = getUser();
        userName_lbl.setText(user);
    }

    public void clickInfo(MouseEvent event) {
    }
}
