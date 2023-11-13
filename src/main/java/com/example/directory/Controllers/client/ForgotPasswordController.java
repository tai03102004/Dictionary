package com.example.directory.Controllers.client;

import com.example.directory.Controllers.SignupController;
import com.example.directory.Models.Model;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ForgotPasswordController extends SignupController implements Initializable {
    public TextField forgot_answer;
    public Button forgot_proceedBtn;
    public Button forgot_backBtn;
    public ComboBox<String> forgot_selectQuestion;
    public TextField forgot_username;
    public Label success_lbl;

    public static String user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> questionsList = getQuestionsList();
        ObservableList<String> listData = FXCollections.observableArrayList(questionsList);
        forgot_selectQuestion.setItems(listData);
    }

    public String getUser() {
        return user;
    }

    public void forgotPassword(ActionEvent actionEvent) {
        System.out.println(getUser());
        String userName = forgot_username.getText();
        String question = forgot_selectQuestion.getValue();
        String answer = forgot_answer.getText();
        boolean success = Model.getInstance().getDatabaseConnection().forgotPassword(userName,question,answer);
        if (forgot_username.getText().isEmpty()
                || forgot_selectQuestion.getSelectionModel().getSelectedItem() == null
                || forgot_answer.getText().isEmpty()) {
            success_lbl.setText("Vui lòng điền đầy đủ tất cả ");
            success_lbl.setStyle("-fx-text-fill: #ff4d3d;-fx-font-size: 1.0em;-fx-font-weight: bold");
        } else {
            if (success) {
                success_lbl.setStyle("-fx-text-fill: #51a7fd ; -fx-font-size: 1.0em;-fx-font-weight: bold");
                success_lbl.setText("Thay đổi mật khẩu thành công");
                user = userName;
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(event -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/ChangePassword.fxml"));
                        Parent root = loader.load();

                        // Create a new stage for the "Forgot Password" window
                        Stage forgotPasswordStage = new Stage();
                        forgotPasswordStage.setTitle("Change Password");
                        forgotPasswordStage.setScene(new Scene(root));

                        // Show the "Forgot Password" window
                        forgotPasswordStage.show();

                        // Hide the login window
                        Stage loginStage = (Stage) forgot_backBtn.getScene().getWindow();
                        loginStage.hide();
                    } catch (IOException e) {
                        e.printStackTrace();
                        // Handle the exception as needed
                    }
                });
                pause.play();
            } else {
                success_lbl.setStyle("-fx-text-fill: #ff2e2e ; -fx-font-size: 1.0em;-fx-font-weight: bold");
                success_lbl.setText("Thay đổi mật khẩu thất bại");
            }
        }
    }

    public void switchForm(ActionEvent actionEvent) {
        Stage stage = (Stage) forgot_backBtn.getScene().getWindow();
        stage.close();
        Model.getInstance().getViewFactory().showLoginWindow();
    }
}
