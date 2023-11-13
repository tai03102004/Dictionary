package com.example.directory.Controllers.client;

import com.example.directory.Models.Model;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import java.util.Date;
import javafx.stage.Stage;

public class ChangePasswordController extends ForgotPasswordController implements Initializable {
    public Button changePass_proceedBtn;
    public Button changePass_backBtn;
    public PasswordField changePass_password;
    public PasswordField changePass_cPassword;
    public Label success_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(getUser());
    }

    public void changePassword(ActionEvent actionEvent) {
        String userName = getUser();
        String password = changePass_password.getText();
        System.out.println(userName);
        Date date = new Date();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());


        boolean success = Model.getInstance().getDatabaseConnection().changePassword(userName,password, timestamp);

        if(changePass_password.getText().isEmpty() || changePass_cPassword.getText().isEmpty()){
            success_lbl.setText("Vui lòng điền đầy đủ tất cả ");
            success_lbl.setStyle("-fx-text-fill: #ef412b;");
        } else if(!changePass_password.getText().equals(changePass_cPassword.getText())){
            // CHECK IF THE PASSWORD AND CONFIRMATION ARE NOT MATCH
            success_lbl.setText("Mật khẩu bạn nhập không trùng nhau");
            success_lbl.setStyle("-fx-font-weight: bold ; -fx-font-size: 1.0em;-fx-text-fill: #ef412b;");
        } else {
            if (success) {
                success_lbl.setStyle("-fx-text-fill: #51a7fd ; -fx-font-size: 1.0em;-fx-font-weight: bold");
                success_lbl.setText("Thay đổi mật khẩu thành công");
            } else {
                success_lbl.setStyle("-fx-text-fill: #ff2e2e ; -fx-font-size: 1.0em;-fx-font-weight: bold");
                success_lbl.setText("Thay đổi mật khẩu thất bại");
            }
        }
    }

    public void switchForm(ActionEvent actionEvent) {
        Stage stage = (Stage) changePass_proceedBtn.getScene().getWindow();
        stage.close();
        Model.getInstance().getViewFactory().showLoginWindow();
    }
}
