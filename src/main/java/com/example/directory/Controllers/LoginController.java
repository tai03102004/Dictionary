package com.example.directory.Controllers;

import com.example.directory.Models.DatabaseConnection;
import com.example.directory.Models.Model;
import com.example.directory.Views.AccountType;
import java.sql.*;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class LoginController implements Initializable {
    public ChoiceBox<AccountType> acc_selector;

    public Label account_dictionary_lbl;
    public TextField account_dictionary_fld;
    public TextField password_fld;
    public Button login_btn;
    public Button cancel_btn;
    public Label Error_lbl;
    public AnchorPane toggleButton;
    public Button signUp_btn;

    public static String user;
    public CheckBox login_selectShowPassword;
    public TextField login_showPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        acc_selector.setItems(FXCollections.observableArrayList(AccountType.CLIENT, AccountType.ADMIN));
        acc_selector.setValue(Model.getInstance().getViewFactory().getLoginAccountType());

        Error_lbl.setText("Đăng nhập để tiếp tục");
        password_fld.setVisible(true);
        login_showPassword.setVisible(false);
        login_selectShowPassword.setOnAction(event -> showPassword());

        login_btn.setOnAction(event -> {
            try {
                onLogin();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        // Initialize the database connection
        try {
            Connection connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (Model.getInstance().getViewFactory().getLoginAccountType() == AccountType.CLIENT) {
            signUp_btn.setOnAction(event -> {
                Stage loginStage = (Stage) signUp_btn.getScene().getWindow();
                loginStage.close();
                Model.getInstance().getViewFactory().showSignUpWindow();
            });
        }
        else {
            Error_lbl.setText("Bạn không có quyền đăng ký");
        }

        cancel_btn.setOnAction(event -> onCancel());
    }


    private void onLogin() throws SQLException {
        String username = account_dictionary_fld.getText();
        String password = password_fld.getText();

        Stage stage = (Stage) Error_lbl.getScene().getWindow();

        if (username.isBlank() || password.isBlank()) {
            // Display a warning dialog if both fields are empty
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Lỗi đăng nhập");
            alert.setHeaderText("Vui lòng nhập tài khoản và mật khẩu.");
            alert.showAndWait();
        } else {

            if (acc_selector.getValue() == AccountType.CLIENT) {
                System.out.println("CLIENT ACCOUNT");

                Model.getInstance().evaluateClientCred(account_dictionary_fld.getText(),password_fld.getText());
                    if (Model.getInstance().getClientLoginSuccessFlag()) {
                        Model.getInstance().getViewFactory().showClientWindow();

                        // Close the login stage
                        Model.getInstance().getViewFactory().closeStage(stage);
                    }
                    else {
                        account_dictionary_fld.setText("");
                        password_fld.setText("");
                        Error_lbl.setText("Không tìm thấy tài khoản");
                    }
            } else if (acc_selector.getValue() == AccountType.ADMIN) {
                // Evaluate Admin Login
                System.out.println("ADMIN ACCOUNT");

                Model.getInstance().evaluateAdminCred(account_dictionary_fld.getText(),password_fld.getText());
                    if (Model.getInstance().getAdminLoginSuccessFlag()) {
                        Model.getInstance().getViewFactory().showAdminWindow();
                        // Close the login
                        Model.getInstance().getViewFactory().closeStage(stage);
                    } else  {
                        account_dictionary_fld.setText("");
                        password_fld.setText("");
                        Error_lbl.setText("Không tìm thấy tài khoản");
                    }
                }
            }
        }
    public String getUser() {
        user = getAccount_dictionary_fld().getText();
        System.out.println(user);
        return user;
    }
    public TextField getAccount_dictionary_fld() {
        System.out.println(account_dictionary_fld);
        return account_dictionary_fld;
    }


    // Huỷ ko đăng nhập vào

    public void onCancel() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận thoát");
        alert.setHeaderText("Bạn muốn thoát ứng dụng?");


        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);


        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);

        if (result == buttonTypeYes) {
            // User click yes => out
            Stage stage = (Stage) Error_lbl.getScene().getWindow();
            stage.close();
        } else {
            // nothing
        }
    }

    // Show Password
    public void showPassword () {
        if (login_selectShowPassword.isSelected()) {
            login_showPassword.setText(password_fld.getText());
            login_showPassword.setVisible(true);
            password_fld.setVisible(false);
        } else {
            password_fld.setText(login_showPassword.getText());
            password_fld.setVisible(true);
            login_showPassword.setVisible(false);
        }
    }

}
