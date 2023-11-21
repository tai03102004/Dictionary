package com.example.directory.Controllers.Admin;

import com.example.directory.Models.Client;
import com.example.directory.Models.Model;
import com.example.directory.Views.ClientCellFactory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ClientsController implements Initializable {
    public ListView<Client> clients_listview;
    public TextField searchTF;
    private final FilteredList<Client> filteredClients;
    public ClientsController() {
        filteredClients = new FilteredList<>(Model.getInstance().getClients(), p -> true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initData();
//        clients_listview.setItems(Model.getInstance().getClients());
        clients_listview.setCellFactory(e -> new ClientCellFactory());
        clients_listview.setItems(filteredClients);
        searchTF.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredClients.setPredicate(client -> {
                // Nếu ô tìm kiếm trống, hiển thị tất cả các người dùng
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // So sánh theo tên người dùng (username) hoặc tên đầy đủ
                String lowerCaseFilter = newValue.toLowerCase();
                return client.userNameProperty().getValue().toLowerCase().contains(lowerCaseFilter) ||
                        client.userNameProperty().getValue().toLowerCase().contains(lowerCaseFilter);
            });
        });
    }

    public void initData() {
        if (Model.getInstance().getClients().isEmpty()) {
            Model.getInstance().setClients();
        }
    }
}
