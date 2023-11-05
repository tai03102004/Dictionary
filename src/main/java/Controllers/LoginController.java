package Controllers;

import Models.DatabaseConnection;
import Models.Model;
import Views.AccountType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class LoginController implements Initializable {
    public ChoiceBox<AccountType> acc_selector;
    public Label account_dictionary_lbl;
    public TextField account_dictionary_fld;
    public TextField password_fld;
    public Button login_btn;
    public Button cancel_btn;
    public Label Error_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        acc_selector.setItems(FXCollections.observableArrayList(AccountType.CLIENT, AccountType.ADMIN));
        acc_selector.setValue(Model.getInstance().getViewFactory().getLoginAccountType());
        acc_selector.valueProperty().addListener(observable -> Model.getInstance().getViewFactory().setLoginAccountType(acc_selector.getValue()));
        Error_lbl.setText("Đăng nhập để tiếp tục");

        login_btn.setOnAction(event -> onLogin());

        cancel_btn.setOnAction(event -> onCancel());
    }


    private void onLogin() {
        String username = account_dictionary_fld.getText();
        String password = password_fld.getText();

        if (username.isBlank() || password.isBlank()) {
            // Hiển thị hộp thoại cảnh báo nếu cả hai trường đều trống
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Lỗi đăng nhập");
            alert.setHeaderText("Vui lòng nhập tài khoản và mật khẩu.");
            alert.showAndWait();
        } else {
            // Nếu cả hai trường đều có giá trị, thực hiện đăng nhập
            Stage stage = (Stage) Error_lbl.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(stage);

            if (Model.getInstance().getViewFactory().getLoginAccountType() == AccountType.CLIENT) {
                Model.getInstance().getViewFactory().showClientWindow();
            } else {
                Model.getInstance().getViewFactory().showAdminWindow();
            }
        }
    }

    public void onCancel() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận thoát");
        alert.setHeaderText("Bạn muốn thoát ứng dụng?");

        // Tạo nút Yes và No trong hộp thoát ứng dụng
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        // Hiển thị hộp thoát ứng dụng và chờ người dùng lựa chọn
        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);

        if (result == buttonTypeYes) {
            // Người dùng chọn "Yes", thoát ứng dụng
            Stage stage = (Stage) Error_lbl.getScene().getWindow();
            stage.close();
        } else {
            // Người dùng chọn "No", không làm gì cả
        }
    }

    public void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) from Dictionary.new_table where UserName = '" + account_dictionary_fld.getText().isBlank()
                + "' and password = '" + password_fld.getText().isBlank() + "'";

        try{
            Statement statement = connectDb.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    Error_lbl.setText("Welcome!");
                } else {
                    Error_lbl.setText("Invalid Login. Please try again. ");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
