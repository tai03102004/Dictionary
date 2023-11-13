package com.example.directory.Controllers.client;

import com.example.directory.Controllers.LoginController;
import com.example.directory.Models.Model;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AccountsController extends LoginController implements Initializable {
    @FXML
    public TextField userName_lbl;
    @FXML
    public TextField name_lbl;
    @FXML
    public TextField email_lbl;
    @FXML
    public TextField phone_lbl;
    public Label update_lbl;
    public Button Save_btn;
    public Button profile_btn;
    public Button changePassword_btn;

    public TextField getUserName_lbl() {
        return userName_lbl;
    }

    public void onLogout() {
        clearUserInfo();
    }

    private void clearUserInfo() {
        userName_lbl.clear();
        name_lbl.clear();
        email_lbl.clear();
        phone_lbl.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String user = getUser();
        System.out.println("User from LoginController: " + user);

        ResultSet resultSet = Model.getInstance().getDatabaseConnection().infoClient(user);


        try {
            if (resultSet.next()) {
                String fullName = resultSet.getString("FullName");
                String email = resultSet.getString("Email");
                String phone = resultSet.getString("Phone");

                // Điền thông tin vào các trường trên giao diện
                userName_lbl.setText(user);
                userName_lbl.setDisable(true);
                name_lbl.setText(fullName);
                email_lbl.setText(email);
                phone_lbl.setText(phone);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Save_btn.setOnAction(event -> updateClient());
    }

    public void updateClient() {

        String userName = userName_lbl.getText();
        String fullName = name_lbl.getText();
        String email = email_lbl.getText();
        String phone = phone_lbl.getText();

        // Gọi phương thức cập nhật trong DatabaseConnection
        boolean success = Model.getInstance().getDatabaseConnection().updateClient(userName, fullName, email, phone);

        if (success) {
            // Cập nhật thành công, thực hiện cập nhật giao diện
            update_lbl.setStyle("-fx-text-fill: blue ; -fx-font-size: 1.3em;-fx-font-weight: bold");
            update_lbl.setText("Cập nhật thông tin thành công");

            // Cập nhật các trường trên giao diện với dữ liệu mới
            name_lbl.setText(fullName);
            email_lbl.setText(email);
            phone_lbl.setText(phone);
        } else {
            update_lbl.setStyle("-fx-text-fill: red ; -fx-font-size: 1.3em;-fx-font-weight: bold");
            update_lbl.setText("Cập nhật thông tin thất bại");
        }
    }
    public Stage createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/icon.png"))));
        stage.setResizable(false);
        stage.setTitle("Dictionary");
        return stage;
    }


    public void changePassword(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/ResetPassword.fxml"));
        Stage resetPasswordStage = createStage(loader);
        resetPasswordStage.show();
    }


    public void closeStage(Stage stage) {
        stage.close();
    }

}
