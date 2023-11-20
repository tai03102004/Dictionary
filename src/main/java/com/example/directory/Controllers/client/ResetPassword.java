package com.example.directory.Controllers.client;

import com.example.directory.Controllers.LoginController;
import com.example.directory.Models.Client;
import com.example.directory.Models.Model;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ResetPassword extends LoginController implements Initializable {
    public TextField userName_tfd;
    public TextField oldPassword_tfd;
    public TextField newPassword_tfd;


    public TextField acceptPassword_tfd;
    public Button okePassword_btn;
    public Label infoReset;

    public void SuccessAccept() {
        String userName = getUser();
        String passwordUser = getPassword();
        String password = oldPassword_tfd.getText();
        String newPassword = newPassword_tfd.getText();
        String acceptPassword = acceptPassword_tfd.getText();

        boolean success = Model.getInstance().getDatabaseConnection().updatePassword(userName,newPassword);

        if (password.isBlank() || newPassword.isBlank() || acceptPassword.isBlank()) {
            infoReset.setText("Không được để trống các dữ liệu");
            infoReset.setStyle("-fx-text-fill:#f15454");
        } else {
            if (!password.equals(passwordUser)) {
                infoReset.setText("Mật khẩu bạn nhập chưa chính xác");
                infoReset.setStyle("-fx-text-fill:#f15454");
            } else {
                if (!newPassword.equals(acceptPassword)) {
                    infoReset.setText("Mật khẩu bạn nhập không trùng nhau");
                    infoReset.setStyle("-fx-text-fill:#f15454");
                } else {
                    if (success) {
                        infoReset.setStyle("-fx-text-fill: #4343f5 ; -fx-font-size: 1.3em;-fx-font-weight: bold");
                        infoReset.setText("Cập nhật mật khẩu thành công");

                    } else {
                        infoReset.setStyle("-fx-text-fill:#f15454");
                        infoReset.setText("Cập nhật mật khẩu thất bại");
                    }
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String userName = getUser();
        userName_tfd.setText(userName);
        userName_tfd.setVisible(true);
        new ResetPassword();
    }
}
