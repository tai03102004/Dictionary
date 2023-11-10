package com.example.directory.Controllers.Admin;

import com.example.directory.Models.Model;
import com.example.directory.Views.AdminMenuOption;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminMenuController implements Initializable {
    public Button clients_btn;
    public Button addEntry_btn;
    public Button updateTerm_btn;
    public Button Logout_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }
    private void addListeners(){
        addEntry_btn.setOnAction(event -> onAddEntry());
        updateTerm_btn.setOnAction(event -> onUpdateEntry());
        clients_btn.setOnAction(event -> onClients());
        Logout_btn.setOnAction(actionEvent -> onLogout());
    }

    private void onAddEntry() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOption.ADD_ENTRY);
    }
    private void onUpdateEntry() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOption.UPDATE_TERM);
    }

    private void onClients() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOption.CLIENTS);
    }

    public void onLogout() {
        // Get Stage
        Stage stage = (Stage) clients_btn.getScene().getWindow();
        // Close the Admin Window
        Model.getInstance().getViewFactory().closeStage(stage);

        // Show Login Window
        Model.getInstance().getViewFactory().showLoginWindow();

        // Set Client Login Success Flag to false
        Model.getInstance().setAdminLoginSuccessFlag(false);
    }
}
