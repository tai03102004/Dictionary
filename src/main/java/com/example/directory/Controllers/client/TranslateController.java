package com.example.directory.Controllers.client;

import com.atlascopco.hunspell.Hunspell;

import com.example.directory.Controllers.LoginController;
import com.example.directory.Models.DatabaseConnection;
import com.example.directory.Models.Model;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.example.directory.Dictionary.TextToSpeech;
public class TranslateController extends LoginController implements Initializable {


    public Button transLanguageEV;
    public Button transLanguageVE;
    public TextField searchField;
    public ListView<String> wordListView; //các từ có thể có
    public Label headText; //Tiêu đề
    public Label speaker1Language;
    public Button speaker1;
    public Label speaker2Language;
    public Button speaker2;
    public Button bookmarkTrue;
    public WebView definitionView;
    public ListView<String> wordListViewFalse;
    public Button editButton;

    private boolean isWordSaved = false;


    // Thư viện giúp sửa lỗi chính tả khi nhập
    private Hunspell hunspell;
    private final ObservableList<String> suggestedWordsList = FXCollections.observableArrayList();
    private static final PseudoClass ACTIVE = PseudoClass.getPseudoClass("active");

    private Task<Void> currentSearchTask;
    private static final long DEBOUNCE_DELAY = 50;

    public String getUserName () {
        return getUser();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Thêm sự kiện lắng nghe cho searchField khi người dùng gõ phím
        hunspell = new Hunspell("src/main/resources/vocab/en_US.dic", "src/main/resources/vocab/en_US.aff");
        searchField.setOnKeyReleased(this::handleSearchFieldKeyReleased);
        wordListView.setOnMouseClicked(this::handleWordListViewClick);
        wordListViewFalse.setOnMouseClicked(this::showHistoryWordDefinitionFalse);
        bookmarkTrue.setOnAction(this::handleClickBookmarkButton);
        editButton.setOnAction(this::suggestionAdmin);
    }


    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private void handleSearchFieldKeyReleased(KeyEvent event) {
        showHistoryWordDefinition();

        showHistoryWordDefinition();
        // Nếu người dùng nhấn Enter hoặc nhấn click từ sẽ vaò
        if (event.getCode() == KeyCode.ENTER) {
            String searchTerm = searchField.getText().toLowerCase().trim();
            addToHistoryAndShow(searchTerm);
        }

        if (event.getCode().isLetterKey() || event.getCode() == KeyCode.ENTER) {
            if (currentSearchTask != null && !currentSearchTask.isDone()) {
                currentSearchTask.cancel(); // Hủy tác vụ hiện tại
            }

            String searchTerm = searchField.getText().toLowerCase().trim();

            currentSearchTask = createSearchTask(searchTerm);

            executorService.submit(currentSearchTask);
        } else if (event.getCode() == KeyCode.BACK_SPACE && searchField.getText().isEmpty()) {
            clearWordListViews();
        }
    }

    private Task<Void> createSearchTask(String searchTerm) {

        long lastSearchTime = 0;

        if (System.currentTimeMillis() - lastSearchTime < DEBOUNCE_DELAY) {
            return null;
        }

        lastSearchTime = System.currentTimeMillis();

        boolean isSpelledCorrectly = hunspell.spell(searchTerm);

        Platform.runLater(() -> {
            updateUIBasedOnSpelling(isSpelledCorrectly, searchTerm);
        });

        return null;

    }

    private void updateUIBasedOnSpelling(boolean isSpelledCorrectly, String searchTerm) {
        if (!isSpelledCorrectly) {
            wordListViewFalse.setVisible(true);
            wordListView.setVisible(false);
            suggestCorrectWords(searchTerm);
        } else {
            wordListViewFalse.setVisible(false);
            wordListView.setVisible(true);
            performSearch();
        }
    }

    @FXML
    private void handleClickBookmarkButton(ActionEvent event) {
        String searchTerm = searchField.getText().toLowerCase().trim();
        if (!searchTerm.isEmpty()) {
            boolean isWordSaved = Model.getInstance().getDatabaseConnection().isWordSaved(searchTerm);

            if (isWordSaved) {
                Model.getInstance().getDatabaseConnection().deleteWordItem(searchTerm);
                showDeleteConfirmation();
                updateWordListAfterDeletion(searchTerm);
            } else {
                showSaveConfirmation();
            }

            event.consume();
            updateBookmarkButtonState(event);
        }
    }

    private void clearWordListViews() {
        wordListView.getItems().clear();
        wordListViewFalse.getItems().clear();
    }
    private void performSearch() {
        String searchTerm = searchField.getText().toLowerCase().trim();
        updateSearchResults(searchTerm);

        if (!searchTerm.isEmpty()) {
            // Kiểm tra chính tả sử dụng Hunspell
            boolean isSpelledCorrectly = hunspell.spell(searchTerm);

            if (!isSpelledCorrectly) {
                // Nếu chính tả sai, gợi ý các từ đúng và hiển thị chúng trong wordListView
                suggestCorrectWords(searchTerm);
            }
            // Nếu chính tả đúng hoặc không có từ gợi ý, tiến hành tìm kiếm từ điển và cập nhật giao diện người dùng
            updateSearchResults(searchTerm);
        }
    }

    // Khi từ tiếng anh ko có trong database
    private void suggestCorrectWords(String searchTerm) {
        String[] suggestions = hunspell.suggest(searchTerm).toArray(new String[0]);
        showWord(suggestions);
        showSuggestedWordsList();
    }
    private void showWord(String[] suggestions) {
        ObservableList<String> observableSuggestions = FXCollections.observableArrayList(suggestions);
        wordListViewFalse.setItems(observableSuggestions);
        // Thêm các từ cần sửa vào danh sách
        suggestedWordsList.setAll(suggestions);
    }

    private void showSuggestedWordsList() {
        wordListView.setItems(suggestedWordsList);
    }

    public void showHistoryWordDefinitionFalse(MouseEvent mouseEvent) {
        String selectedWord = wordListViewFalse.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            searchField.setText(selectedWord);
            if (wordListViewFalse.isVisible()) {
                performSearch();
            }
        }
    }

    private void updateSearchResults(String searchTerm) {
        // Thực hiện tìm kiếm từ điển và cập nhật nội dung trang
        DatabaseConnection.SearchResult result = Model.getInstance().getDatabaseConnection().getWordClient(searchTerm);

        if (result.isFound()) {
            definitionView.getEngine().loadContent(result.getDefinition());
        }
    }

    // End search word

    // History Word
    private void addToHistoryAndShow(String word) {
        String userName = getUser();

        if (!Model.getInstance().getDatabaseConnection().isWordInHistory(word, userName)) {
            DatabaseConnection.SearchResult result = Model.getInstance().getDatabaseConnection().getWordClient(word);
            String definition = result.isFound() ? result.getDefinition() : "";

            Model.getInstance().getDatabaseConnection().saveWordToHistory(word, definition, userName);
        }
    }
    public void close() {
        if (hunspell != null) {
            hunspell.close();
        }
        executorService.shutdown();
        definitionView.getEngine().load(null);
    }

    // End History Word

    //  Lịch sử các từ tìm kiếm

    public void showHistoryWordDefinition() {
        String partialKeyword = searchField.getText().toLowerCase().trim();

        List<String> suggestedWords = Model.getInstance().getDatabaseConnection().getSuggestedWords(partialKeyword);

        List<String> filteredSuggestions = suggestedWords.stream()
                .filter(word -> word.toLowerCase().contains(partialKeyword))
                .collect(Collectors.toList());

        showWordSuggestionsSuggest(filteredSuggestions);
    }

    private void showWordSuggestionsSuggest(List<String> suggestions) {
        ObservableList<String> observableSuggestions = FXCollections.observableArrayList(suggestions);
        wordListView.setItems(observableSuggestions);
        performSearch();
    }

    private void handleWordListViewClick(MouseEvent event) {
        String selectedWord = wordListView.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            searchField.setText(selectedWord);
            performSearch();
            insertWordToHistory(selectedWord);
        }
    }
    private void insertWordToHistory(String word) {
        String userName = getUser();

        DatabaseConnection.SearchResult result = Model.getInstance().getDatabaseConnection().getWordClient(word);
        String definition = result.isFound() ? result.getDefinition() : "";

        // Lưu từ vào HistoryWord
        Model.getInstance().getDatabaseConnection().saveWordToHistory(word, definition, userName);
    }

    // End Lịch sử các từ tìm kiếm
    public void handleClickTransButton(ActionEvent actionEvent) {
        clearPane();
    }

    // UK(Speak)
    public void handleClickSpeaker1(ActionEvent actionEvent) {
        TextToSpeech.language = TextToSpeech.uk_accent;
        TextToSpeech.VoiceAudio(searchField.getText());
    }
    // US(Speak)
    public void handleClickSpeaker2(ActionEvent actionEvent) {
        TextToSpeech.language = TextToSpeech.us_accent;
        TextToSpeech.VoiceAudio(searchField.getText());
    }
    private void updateBookmarkButtonState(ActionEvent event) {
        bookmarkTrue.pseudoClassStateChanged(ACTIVE, !bookmarkTrue.getPseudoClassStates().contains(ACTIVE));
        event.consume();
    }

    // Save Word

    private void showSaveConfirmation() {
        Alert saveConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        saveConfirmation.setTitle("Xác nhận lưu từ");
        saveConfirmation.setHeaderText(null);
        saveConfirmation.setContentText("Bạn có chắc chắn muốn lưu từ này?");

        saveConfirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Người dùng chấp nhận lưu từ
                showSaveSuccess();
            }
        });
    }
    private void showSaveSuccess() {
        String searchTerm = searchField.getText().toLowerCase().trim();
        WebEngine webEngine = definitionView.getEngine();
        String userName = getUser();
        String definitionContent = (String) webEngine.executeScript("document.documentElement.outerHTML");
        boolean success = Model.getInstance().getDatabaseConnection().saveWord(searchTerm, definitionContent,userName);

        if (success) {
            // Xóa từ khỏi danh sách gợi ý để tránh tình trạng lặp lại
            suggestedWordsList.remove(searchTerm);

            Alert saveSuccess = new Alert(Alert.AlertType.INFORMATION);
            saveSuccess.setTitle("Lưu từ thành công");
            saveSuccess.setHeaderText(null);
            saveSuccess.setContentText("Từ đã được lưu thành công!");
            saveSuccess.show();
        }
    }

    // End Save Word

    // Delete Save Word
    private void updateWordListAfterDeletion(String deletedWord) {
        ObservableList<String> wordListItems = wordListView.getItems();
        wordListItems.remove(deletedWord);
    }
    private void showDeleteConfirmation() {
        Alert deleteConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        deleteConfirmation.setTitle("Xác nhận xoá từ");
        deleteConfirmation.setHeaderText(null);
        deleteConfirmation.setContentText("Bạn có chắc chắn muốn xoá từ này?");

        deleteConfirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                showDeleteSuccess();
            }
        });
    }

    private void showDeleteSuccess() {
        Alert deleteSuccess = new Alert(Alert.AlertType.INFORMATION);
        deleteSuccess.setTitle("Xoá từ thành công");
        deleteSuccess.setHeaderText(null);
        deleteSuccess.setContentText("Từ đã được xoá thành công!");
        deleteSuccess.show();
    }

    // End Delete Save Word

    // Feedback Client
    public void suggestionAdmin(ActionEvent actionEvent) {
        Stage suggestionStage = new Stage();

        suggestionStage.initModality(Modality.APPLICATION_MODAL);
        suggestionStage.setTitle("Thêm ghi chú");

        TextArea noteTextArea = new TextArea();
        noteTextArea.setWrapText(true);
        noteTextArea.getStyleClass().add("text-area"); // Thêm css

        Button saveButton = getButton(noteTextArea, suggestionStage);
        saveButton.getStyleClass().add("button");

        VBox suggestionLayout = new VBox(10);

        // Đặt button ở giữa
        suggestionLayout.getChildren().addAll(noteTextArea, saveButton);

        Scene suggestionScene = new Scene(suggestionLayout, 500, 220);

        // Thêm css
        suggestionScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Styles/styles.css")).toExternalForm());

        suggestionStage.setScene(suggestionScene);
        suggestionStage.showAndWait();
    }

    private Button getButton(TextArea noteTextArea, Stage suggestionStage) {
        Button saveButton = new Button("Lưu");
        String userName = getUser();
        saveButton.setOnAction(event -> {
            String noteText = noteTextArea.getText();

            if (noteText.trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Phản hồi không được để trống.");
                alert.showAndWait();
            } else {
                boolean success = Model.getInstance().getDatabaseConnection().getReportAdmin(noteText, userName);
                if (success) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText(null);
                    alert.setContentText("Cám ơn bạn đã phản hồi.");
                    alert.showAndWait();
                    suggestionStage.close();
                }
            }
        });
        return saveButton;
    }


    // End Feedback Client
    public void handleHistorySearchBar(KeyEvent keyEvent) {
    }

    public void handleClickListView(MouseEvent event) {
    }
    public void clearPane() {
        searchField.clear();
        definitionView.getEngine().loadContent("");
        headText.setText("Nghĩa của từ");
        wordListView.getItems().clear();
    }
}
