package com.example.directory.Controllers;

import com.example.directory.Models.DatabaseConnection;
import com.example.directory.Models.Model;
import java.net.URL;
import java.util.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    public ComboBox signup_selectQuestion;
    public TextField signup_answer;
    public void registerClearFields() {
        FullNameTextField.setText("");
        EmailTextField.setText("");
        UserNameTextField.setText("");
        setPasswordField.setText("");
        confirmPasswordField.setText("");
        signup_selectQuestion.getSelectionModel().clearSelection();
        signup_answer.setText("");
        PhoneTextField.setText("");
    }
    private String[] questions = {
            "What is the name of your first pet?",
            "In what city were you born?",
            "What is the name of your favorite book?",
            "What is your favorite movie?",
    };


    public void questions() {
        List<String> listQ = new ArrayList<>();
        for (String data : questions) {
            listQ.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listQ);
        signup_selectQuestion.setItems(listData);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Event handler for cancel_btn
        questions();
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

    public List<String> getQuestionsList() {
        return Arrays.asList(questions);
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
                String fullName = FullNameTextField.getText();
                String userName = UserNameTextField.getText();
                String email = EmailTextField.getText();
                String phone = PhoneTextField.getText();
                String password = setPasswordField.getText();
                String question = signup_selectQuestion.getValue().toString();
                String answer = signup_answer.getText();

                Date date = new Date();
                java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());

                if (DatabaseConnection.registerUser(fullName, userName, email, phone, password, question, answer, timestamp)) {
                    // Registration successful
                    registrationMessageLabel.setText("Bạn đã đăng ký thành công, Đăng nhập để tiếp tục");
                    registrationMessageLabel.setStyle("-fx-text-fill: #3dcee7;");
                    registerClearFields();
                } else {
                    registrationMessageLabel.setText("Lỗi: Mời bạn đăng ký lại.");
                    registrationMessageLabel.setStyle("-fx-text-fill: #DC143C;");
                }
            }
        } else {
            ConfirmPasswordLabel.setText("Mật khẩu không trùng nhau");
        }
    }

}
