package Controllers.Admin;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

public class AdminAddEntryController implements Initializable {
    public RadioButton addEV;
    public ToggleGroup data1;
    public RadioButton addVE;
    public TextField addText;

    public WebView webView;
    public HTMLEditor addEditor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }

    public void handleClickArrow(ActionEvent actionEvent) {
    }

    public void add(ActionEvent actionEvent) {
    }

    public void addReset(ActionEvent actionEvent) {
    }
}
