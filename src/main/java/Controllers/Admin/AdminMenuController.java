package Controllers.Admin;
import Models.Model;
import Views.AdminMenuOption;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class AdminMenuController implements Initializable {
    public Button create_client_btn;
    public Button clients_btn;
    public Button addEntry_btn;
    public Button updateTerm_btn;
    public Button Logout_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }
    private void addListeners(){
        create_client_btn.setOnAction(event -> onCreateClient() );
        addEntry_btn.setOnAction(event -> onAddEntry());
        clients_btn.setOnAction(event -> onClients());
    }

    private void onCreateClient(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOption.CREATE_CLIENT);
    }
    private void onAddEntry() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOption.ADD_ENTRY);
    }


    private void onClients() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOption.CLIENTS);
    }
}
