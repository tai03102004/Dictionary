package com.example.directory.Controllers.client;

import com.example.directory.Models.Model;
import com.example.directory.Views.ClientMenuOption;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuController extends AccountsController implements Initializable {
    public Button home_btn;
    public Button translate_btn;
    public Button textTransaction_btn;
    public Button game_btn;
    public Button save_btn;
    public Button history_btn;
    public Button profile_btn;
    public Button logout_btn;
    public Button install_btn;
    public Button report_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        home_btn.setOnAction(event -> onSelectMenuItem(ClientMenuOption.DASHBOARD));
        translate_btn.setOnAction(event -> onSelectMenuItem(ClientMenuOption.TRANSLATE));
        profile_btn.setOnAction(event -> onSelectMenuItem(ClientMenuOption.ACCOUNTS));
        game_btn.setOnAction(event -> onSelectMenuItem(ClientMenuOption.GAME));
        textTransaction_btn.setOnAction(event -> onSelectMenuItem(ClientMenuOption.TEXTTRANSACTION));
        install_btn.setOnAction(event -> onSelectMenuItem(ClientMenuOption.SETTING));
        history_btn.setOnAction(event->onSelectMenuItem(ClientMenuOption.HISTORY));
        save_btn.setOnAction(actionEvent -> onSelectMenuItem(ClientMenuOption.SAVED));
        report_btn.setOnAction(event->onReport());
        logout_btn.setOnAction(event -> onLogout());
    }

    private void onSelectMenuItem(ClientMenuOption option) {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(option);
    }

    private Stage reportStage;

    public void onReport() {
        try {
            if (reportStage == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Report.fxml"));
                Parent root = loader.load();

                // Create a new stage for the "Report" window
                reportStage = new Stage();
                reportStage.setTitle("Report Window");

                // Tạo một Scene với root là nội dung của FXML và đặt nó vào Stage
                Scene scene = new Scene(root);
                reportStage.setScene(scene);

                // Set a listener to handle the close event of the report window
                reportStage.setOnCloseRequest(event -> {
                    // Reset the stage reference when the window is closed
                    reportStage = null;
                });

                // Hiển thị cửa sổ
                reportStage.show();
            }

        } catch (IOException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ nếu cần thiết
        }
    }

    public void onLogout() {
        // Get Stage
        System.out.println("Logout");
        Stage stage = (Stage) home_btn.getScene().getWindow();

        // Close the CLient Window
        Model.getInstance().getViewFactory().closeStage(stage);

        super.onLogout();


        // Show Login Window
        Model.getInstance().getViewFactory().showLoginWindow();

        // Set Client Login Success Flag to false
        Model.getInstance().setClientLoginSuccessFlag(false);
    }


}
