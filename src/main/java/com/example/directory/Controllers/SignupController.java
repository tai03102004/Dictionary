package com.example.directory.Controllers;

import com.example.directory.Models.DatabaseConnection;
import com.example.directory.Models.Model;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class SignupController implements Initializable {

    @FXML
    public Button login_btn;

    @FXML
    public Button cancel_btn;
    public Button SignUp_btn;
    public Label registrationMessageLabel;
    public PasswordField setPasswordField;
    public PasswordField confirmPasswordField;
    public Label ConfirmPasswordLabel;
    public TextField PhoneTextField;
    public TextField EmailTextField;
    public TextField UserNameTextField;
    public TextField FullNameTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Event handler for cancel_btn
        cancel_btn.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Close");
            alert.setHeaderText("Are you sure you want to close the application?");

            if (alert.showAndWait().get() == ButtonType.OK) {
                // Close the application
                Stage stage = (Stage) cancel_btn.getScene().getWindow();
                stage.close();
                Platform.exit();
            }
        });
    }

    public void loginButtonOnAction(ActionEvent event) {
        // Đóng cửa sổ đăng ký
        Stage stage = (Stage) SignUp_btn.getScene().getWindow();
        stage.close();
        Model.getInstance().getViewFactory().showLoginWindow();
    }
    public boolean isEmailValid() {
        String email = EmailTextField.getText();
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            registrationMessageLabel.setText("Error: Invalid email format");
            registrationMessageLabel.setStyle("-fx-text-fill: #DC143C;");
            return false;
        }
        return true;
    }

    public boolean isPhoneValid() {
        String phone = PhoneTextField.getText();
        String phoneRegex = "^(\\d{10}|\\d{11})$"; // Đây là ví dụ, bạn có thể thay đổi dựa trên quy định của mạng điện thoại
        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(phone);
        if (!matcher.matches()) {
            registrationMessageLabel.setText("Error: Invalid phone number format");
            registrationMessageLabel.setStyle("-fx-text-fill: #DC143C;");
            return false;
        }
        return true;
    }
    public void registerButtonOnAction(ActionEvent event) {
        if (setPasswordField.getText().equals(confirmPasswordField.getText())) {
            if (isEmailValid() && isPhoneValid()) {
                if (DatabaseConnection.registerUser(
                        FullNameTextField.getText(),
                        UserNameTextField.getText(),
                        setPasswordField.getText(),
                        EmailTextField.getText(),
                        PhoneTextField.getText())) {
                    // Registration successful
                    registrationMessageLabel.setText("You have successfully registered . Login to continue");
                } else {
                    registrationMessageLabel.setText("Error: Failed to register user.");
                    registrationMessageLabel.setStyle("-fx-text-fill: #DC143C;");
                }
            }
        } else {
            ConfirmPasswordLabel.setText("Password does not match");
        }
    }
}
