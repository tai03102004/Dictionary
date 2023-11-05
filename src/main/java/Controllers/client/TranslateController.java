package Controllers.client;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class TranslateController implements Initializable {

    public ListView Translate_listview;
    public Button transLanguageEV;
    public Button transLanguageVE;
    public TextField searchField;
    public ListView wordListView;
    public Label headText;

    public Label speaker1Language;
    public Button speaker1;

    public Label speaker2Language;
    public Button speaker2;

    public Button saveChangeButton;
    public Button bookmarkTrue;
    public Button bookmarkFalse;
    public Button editButton;
    public Button removeButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void handleClickTransButton(ActionEvent actionEvent) {
    }

    public void handleHistorySearchBar(KeyEvent keyEvent) {
    }

    public void handleClickListView(MouseEvent mouseEvent) {
    }

    public void showHistoryWordDefinition(MouseEvent mouseEvent) {
    }

    public void handleClickSpeaker1(ActionEvent actionEvent) {
    }

    public void handleClickSpeaker2(ActionEvent actionEvent) {
    }

    public void handleClickSaveButton(ActionEvent actionEvent) {
    }

    public void handleClickBookmarkButton(ActionEvent actionEvent) {
    }

    public void handleClickEditButton(ActionEvent actionEvent) {
    }

    public void handleClickRemoveButton(ActionEvent actionEvent) {
    }
}
