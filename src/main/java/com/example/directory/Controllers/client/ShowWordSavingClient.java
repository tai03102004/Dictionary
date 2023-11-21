package com.example.directory.Controllers.client;

import com.example.directory.Models.DatabaseConnection;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.web.WebView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ShowWordSavingClient {

    public static void showDetails(String target, String definition) {
        List<WordItem> wordHtml = DatabaseConnection.getWordHtml();
        for (WordItem wordItem : wordHtml) {
            if (wordItem.getTarget().equals(target)) {
                definition = wordItem.getDefinition();
                break;
            }
        }
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(target);

        VBox layout = new VBox(10);
        layout.setPadding(new javafx.geometry.Insets(10));

        // Convert the text content back to HTML using Jsoup
        Document doc = Jsoup.parseBodyFragment(definition);
        Element body = doc.body();

        WebView webView = new WebView();
        webView.getEngine().loadContent(body.html());

        layout.getChildren().add(webView);

        Scene scene = new Scene(layout, 600, 500); // Adjust size as needed
        stage.setScene(scene);
        stage.showAndWait();
    }
}
