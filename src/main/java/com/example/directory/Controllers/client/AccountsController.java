package com.example.directory.Controllers.client;

import com.example.directory.Controllers.LoginController;
import com.example.directory.Models.Client;
import com.example.directory.Models.Model;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AccountsController implements Initializable {
    @FXML
    public TextField userName_lbl;
    @FXML
    public TextField name_lbl;
    @FXML
    public TextField email_lbl;
    @FXML
    public TextField phone_lbl;
    public RadioButton Nam_lbl;
    public RadioButton Nu_lbl;
    public RadioButton Khac_lbl;
    public Label update_lbl;
    public Button Save_btn;

    LoginController loginController = new LoginController();
    String user = loginController.getUser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        LoginController loginController = new LoginController();

        // Lấy thông tin người dùng từ LoginController
        String user = loginController.getUser();
        System.out.println(user);

        ResultSet resultSet = Model.getInstance().getDatabaseConnection().infoClient(user);

        System.out.println("123123");

        try {
            if (resultSet.next()) {
                String fullName = resultSet.getString("FullName");
                String email = resultSet.getString("Email");
                String phone = resultSet.getString("Phone");

                // Điền thông tin vào các trường trên giao diện
                userName_lbl.setText(user);
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
        // Update Client
        System.out.println(name_lbl);
        String fullName = name_lbl.getText();
        String userName = userName_lbl.getText();
        String email = email_lbl.getText();
        String phone = phone_lbl.getText();
        String sex = "";

        // Kiểm tra giới tính được chọn
        if (Nam_lbl.isSelected()) {
            sex = "Nam";
        } else if (Nu_lbl.isSelected()) {
            sex = "Nữ";
        } else if (Khac_lbl.isSelected()) {
            sex = "Khác";
        }

        Model.getInstance().getDatabaseConnection().updateClient(fullName,userName,email,phone,sex);
        Model.getInstance().getDatabaseConnection().infoClient(userName);
        update_lbl.setStyle("-fx-text-fill: blue ; -fx-font-size: 1.3em;-fx-font-weight: bold");
        update_lbl.setText("Cập nhật thông tin thành công");
    }

}
