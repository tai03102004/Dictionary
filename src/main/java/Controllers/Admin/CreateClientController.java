package Controllers.Admin;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class CreateClientController implements Initializable {
    public VBox createClient_btn;
    public TextField name_fid;
    public TextField email_fid;
    public TextField phone_fib;
    public Label Error_lbl;
    public TextField password_fib;
    public Button CreateClient_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }
}
