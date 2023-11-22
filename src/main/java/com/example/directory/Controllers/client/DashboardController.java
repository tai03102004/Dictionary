package com.example.directory.Controllers.client;

import com.example.directory.Controllers.LoginController;
import java.awt.event.ActionEvent;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;
import javax.swing.text.Style;

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
    public Button btnMode;
    public ImageView imgMode;
    public AnchorPane parent;
    public Label text1;
    public Label text2;
    public Label text3;

    private boolean isLightMode = true;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String user = getUser();
        userName_lbl.setText(user);
    }

    public void changeMode (javafx.event.ActionEvent event) {
        isLightMode = !isLightMode;
        if (isLightMode) {
            setLightMode();
        } else {
            setDarkMode();
        }
    }

    private void setLightMode() {
        try {
            parent.getStylesheets().remove(Objects.requireNonNull(getClass().getResource("/Styles/DarkMode.css")).toExternalForm());
            parent.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Styles/LightMode.css")).toExternalForm());
            Image image = new Image(Objects.requireNonNull(getClass().getResource("/Images/dark.png")).toExternalForm());
            imgMode.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDarkMode() {
        try {
            parent.getStylesheets().remove(Objects.requireNonNull(getClass().getResource("/Styles/LightMode.css")).toExternalForm());
            parent.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Styles/DarkMode.css")).toExternalForm());
            Image image = new Image(Objects.requireNonNull(getClass().getResource("/Images/light.jpeg")).toExternalForm());
            imgMode.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickInfo(MouseEvent event) {
    }
}
