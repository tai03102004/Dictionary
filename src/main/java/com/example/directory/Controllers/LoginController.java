package com.example.directory.Controllers;

//import com.example.directory.Models.AccountDAL;
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

    private String user;
    public Label account_dictionary_lbl;
    public TextField account_dictionary_fld;
    public TextField password_fld;
    public Button login_btn;
    public Button cancel_btn;
    public Label Error_lbl;
    public Label showPassword;
    public AnchorPane toggleButton;
    public ToggleButton toggleBtn;
    public Button signUp_btn;

    private Connection connection;
    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showPassword.setVisible(false);
        acc_selector.setItems(FXCollections.observableArrayList(AccountType.CLIENT, AccountType.ADMIN));
        acc_selector.setValue(Model.getInstance().getViewFactory().getLoginAccountType());
        acc_selector.valueProperty().addListener(observable -> setAcc_selector());
        Error_lbl.setText("Đăng nhập để tiếp tục");

        login_btn.setOnAction(event -> {
            try {
                onLogin();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        // Initialize the database connection
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        signUp_btn.setOnAction(event -> {
            Stage loginStage = (Stage) signUp_btn.getScene().getWindow();
            loginStage.close();
            Model.getInstance().getViewFactory().showSignUpWindow();
        });

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

                if (Model.getInstance().getViewFactory().getLoginAccountType() == AccountType.CLIENT) {
                    Model.getInstance().evaluateClientCred(account_dictionary_fld.getText(),password_fld.getText());
                    if (Model.getInstance().getClientLoginSuccessFlag()) {
                        Model.getInstance().getViewFactory().showClientWindow();
                        this.user =  account_dictionary_fld.getText();
                        // Close the login stage
                        Model.getInstance().getViewFactory().closeStage(stage);
                    }
                    else {
                        account_dictionary_fld.setText("");
                        password_fld.setText("");
                        Error_lbl.setText("No Such Login");
                    }
                 } else {
                    // Evaluate Admin Login
                    Model.getInstance().evaluateAdminCred(account_dictionary_fld.getText(),password_fld.getText());
                    if (Model.getInstance().getAdminLoginSuccessFlag()) {
                        Model.getInstance().getViewFactory().showAdminWindow();
                        // Close the login
                        Model.getInstance().getViewFactory().closeStage(stage);
                    } else  {
                        account_dictionary_fld.setText("");
                        password_fld.setText("");
                        Error_lbl.setText("No Such Login");
                    }


                }
            }
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

    @FXML

    public void toggleBtn(ActionEvent actionEvent) {
        if (toggleBtn.isSelected()) {
            showPassword.setVisible(true);
            showPassword.textProperty().bind(Bindings.concat(password_fld.getText()));
            toggleBtn.setText("Hide");
        } else {
            showPassword.setVisible(false);
            toggleBtn.setText("Show");
        }
    }

    @FXML

    public void passwordFieldKeyTyped(KeyEvent keyEvent) {
        showPassword.textProperty().bind(Bindings.concat(password_fld.getText()));
    }

    private void setAcc_selector() {
        Model.getInstance().getViewFactory().setLoginAccountType(acc_selector.getValue());
        if (acc_selector.getValue() == AccountType.ADMIN) {
            account_dictionary_lbl.setText("UsernameAdmin:");
        } else {

        }
    }

}
