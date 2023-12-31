package com.example.directory.Controllers.client;

import com.example.directory.Controllers.LoginController;
import com.example.directory.Models.Model;
import java.net.URL;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class History extends LoginController implements Initializable , Model.DatabaseChangeListener {
    public TextField searchWord;
    public ListView<WordItem> listViewWord;

    public Button SortViewHistory;
    public Label UserName;
    private WordItemComparator currentComparator = new AlphabeticalComparator();

    private final ObservableList<WordItem> wordList = FXCollections.observableArrayList();

    public Button deleteButton;


    private final ObservableList<WordItem> checkedItems = FXCollections.observableArrayList();

    private void sortWordList() {
        wordList.sort(currentComparator);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String userName = getUser();
        UserName.setText(userName);

        List<WordItem> wordItems = Model.getInstance().getDatabaseConnection().accountHistoryWordList(userName);

        // load từ từ database
        loadWordsFromDatabase();
        wordList.clear();

        wordList.addAll(wordItems);
        listViewWord.setItems(wordList);

        // Click 2 lần sẽ hiện thông tin từ
        listViewWord.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Kiểm tra nếu là double-click
                WordItem selectedWord = listViewWord.getSelectionModel().getSelectedItem();
                if (selectedWord != null) {
                    // Hiển thị từ khi chọn từ trong listViewWord
                    showWordDetails(selectedWord);
                }
            }
        });

        // Đăng ký lắng nghe sự kiện từ Model
        Model.getInstance().addDatabaseChangeListener(this);

        // Sắp xếp các từ
        SortViewHistory.setOnAction(event -> {
            SortingDialog.display();

            // Update the sorting comparator based on the user's choice
            String selectedSort = SortingDialog.getSelectedSort();
            if ("Sắp xếp mới nhất".equals(selectedSort)) {
                currentComparator = new NewestComparator();
            } else if("Sắp xếp theo bảng chữ cái(giảm dần)".equals(selectedSort)) {
                currentComparator = new ReverseAlphabeticalComparator();
            } else {
                currentComparator = new AlphabeticalComparator();
            }

            sortWordList();
        });

        listViewWord.setCellFactory(param -> new ListCell<WordItem>() {
            @Override
            protected void updateItem(WordItem item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    VBox vbox = new VBox();

                    Text textItem = new Text(item.getLimitItem());
                    CheckBox checkBox = new CheckBox();

                    checkBox.setSelected(item.isChecked());
                    checkBox.setOnAction(event -> handleCheckBoxSelection(item, checkBox.isSelected()));

                    checkBox.setFont(Font.font(10));

                    vbox.getChildren().addAll(textItem, checkBox);

                    vbox.setSpacing(10);
                    setGraphic(vbox);
                }
            }
        });

        // delete word
        setUpDeleteConfirmationDialog();
        deleteButton.setOnAction(event -> handleDeleteAction());

        configureSearchField();
    }

    private final Set<WordItem> wordSet = new LinkedHashSet<>();

    private void loadWordsFromDatabase() {
        List<WordItem> words = retrieveWordsFromDatabase();

        wordList.clear();
        wordSet.clear();

        wordSet.addAll(words);
        wordList.addAll(wordSet);
    }
    private void showWordDetails(WordItem word) {
        ShowWordSavingClient.showDetails(word.getTarget(), word.getDefinition());
    }
    @Override
    public void onDataChange() {
        loadWordsFromDatabase();
    }


    private void configureSearchField() {
        FilteredList<WordItem> filteredWordList = new FilteredList<>(wordList);

        listViewWord.setItems(filteredWordList);

        searchWord.setOnAction(event -> {
            WordItem selectedWord = listViewWord.getSelectionModel().getSelectedItem();
            if (selectedWord != null) {
                showWordDetails(selectedWord);
            }
        });

        searchWord.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredWordList.setPredicate(wordItem ->
                    wordItem.getTarget().toLowerCase().contains(newValue.toLowerCase())
            );
            if (filteredWordList.isEmpty()) {
                listViewWord.getItems().clear();
            }
        });

        searchWord.setUserData(filteredWordList);

    }
    // delete
    // alert delete
    private void setUpDeleteConfirmationDialog() {
        Alert deleteConfirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        deleteConfirmationDialog.setTitle("Confirmation");
        deleteConfirmationDialog.setHeaderText("Delete Word");
        deleteConfirmationDialog.setContentText("Are you sure you want to delete this word?");
    }

    private void handleCheckBoxSelection(WordItem wordItem, boolean selected) {
        wordItem.setChecked(selected);

        if (selected) {
            checkedItems.add(wordItem);
            deleteButton.setDisable(false);
        } else {
            checkedItems.remove(wordItem);
            deleteButton.setDisable(checkedItems.isEmpty());
        }
    }

    private void handleDeleteAction() {
        if (!checkedItems.isEmpty()) {
            Alert confirmDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDeleteAlert.setTitle("Confirmation");
            confirmDeleteAlert.setHeaderText("Delete Words");
            confirmDeleteAlert.setContentText("Are you sure you want to delete the selected word(s)?");

            Optional<ButtonType> result = confirmDeleteAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                deleteSelectedWords();
            } else {
                for (WordItem checkedItem : checkedItems) {
                    checkedItem.setChecked(false);
                }
                checkedItems.clear();
                deleteButton.setDisable(true);
            }
        }
    }


    private void deleteSelectedWords() {
        for (WordItem checkedItem : checkedItems) {
            Model.getInstance().getDatabaseConnection().deleteWordItemHistory(checkedItem.getTarget());
            wordList.remove(checkedItem);
        }

        checkedItems.clear();
        deleteButton.setDisable(true);
    }

    private List<WordItem> retrieveWordsFromDatabase() {
        return Model.getInstance().getDatabaseConnection().getWordItemsHistoryFromDatabase(getUser());
    }
}
