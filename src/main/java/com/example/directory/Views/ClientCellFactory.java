package com.example.directory.Views;

import com.example.directory.Controllers.Admin.ClientCellController;
import com.example.directory.Controllers.client.AccountsController;
import com.example.directory.Models.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class ClientCellFactory extends ListCell<Client> {
    @Override

    protected void updateItem(Client client , boolean empty) {
        super.updateItem(client,empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            System.out.println("List View Client");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/ClientsCell.fxml"));
            ClientCellController controller = new ClientCellController(client);
            loader.setController(controller);
            setText(null);
            try {
                setGraphic(loader.load());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
