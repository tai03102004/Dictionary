package com.example.directory.Controllers.Admin;

import com.example.directory.Models.Model;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class AdminController implements Initializable {
   public BorderPane admin_parent ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener((observableValue,oldVal,newVal)->{
            switch (newVal) {
                case CLIENTS -> admin_parent.setCenter(Model.getInstance().getViewFactory().getClientsView());
                case ADD_ENTRY -> admin_parent.setCenter(Model.getInstance().getViewFactory().getAddEntryView());
                default -> admin_parent.setCenter(Model.getInstance().getViewFactory().getCreateClientView());
            }

        });
    }
}
