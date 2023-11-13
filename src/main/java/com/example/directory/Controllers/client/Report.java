package com.example.directory.Controllers.client;

import com.example.directory.Controllers.LoginController;
import com.example.directory.Models.Model;
import com.example.directory.Models.Review;
import com.example.directory.Models.Reviewable;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Rating;

import java.net.URL;
import java.util.ResourceBundle;

public class Report extends LoginController implements Initializable {
    public Rating start_rating;
    public TextArea comment_area;
    public TextField title_tfd;
    public Button cancel_btn;
    public Button direct_btn;

    private String userName;
    private double start;
    private String comment;
    private String title;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        direct_btn.setOnAction(event -> Direct());
        cancel_btn.setOnAction(event -> Cancel());
        Reviewable lastReview = Model.getInstance().getDatabaseConnection().getLastReview();
        if (lastReview != null) {
            start = lastReview.getStart();
            comment = lastReview.getComment();
            title = lastReview.getTitle();

            start_rating.setRating(start);
            comment_area.setText(comment);
            title_tfd.setText(title);
        }
    }

    public void Cancel() {
        Stage stage = (Stage) direct_btn.getScene().getWindow();
        stage.close();
    }

    public void Direct() {
        // Assign values to fields
        userName = getUser();
        start = start_rating.getRating();
        comment = comment_area.getText();
        title = title_tfd.getText();

        boolean success = Model.getInstance().getDatabaseConnection().reportClient(start, title, comment, userName);

        if (success) {
            showThankYouAlert("Cảm ơn bạn đã đánh giá!");
        } else {
            showThankYouAlert("Đánh giá thất bại");
        }
    }

    private void showThankYouAlert(String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cảm ơn");
        alert.setHeaderText(null);
        alert.setContentText(contentText);

        alert.show();

        // Tự động đóng alert sau 2 giây
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> alert.hide()));
        timeline.play();
    }

    // You can add getter methods for the fields if needed
    public String getUserName() {
        return userName;
    }

    public double getStart() {
        return start;
    }

    public String getComment() {
        return comment;
    }

    public String getTitle() {
        return title;
    }
}
