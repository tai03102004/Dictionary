package com.example.directory.Controllers.client;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.animation.KeyValue;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameController extends TopicController implements Initializable {

    public Button buttonBtnRank;
    @FXML
    private AnchorPane GameMenu;
    public javafx.scene.shape.Circle Circle;
    public ImageView movingImageView;
    public ImageView anh2_img;
    public ImageView anh2_img2;
    public ImageView anh2_img3;
    private int wordCounter = 0;
    private int first = 1;
    public ImageView wrong_img;
    public Text programWord_text;
    public TextField userWord_textField;
    public Text seconds_text;
    public Text wordsPerMin_text;
    public Text accuracy_text;
    public Button playAgain_btn;
    public Text secondProgramWord_text;
    public ImageView correct_img;

    private File saveData;

    private int countAll = 0;
    private int counter = 0;

    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    ArrayList<String> words = new ArrayList<>();

    // add words to array list
    public void addToList() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(getWordsTopic()));
            String line = reader.readLine();
            while (line != null) {
                words.add(line);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean isStarted = false;

    public void toMainMenu(ActionEvent ae) throws IOException {
        resetGame();
    }
    private void resetGame() {
        // Đặt lại tất cả biến và trạng thái ban đầu của trò chơi ở đây
        playAgain_btn.setVisible(false);
        playAgain_btn.setDisable(true);
        anh2_img.setVisible(false);
        anh2_img2.setVisible(false);
        anh2_img3.setVisible(false);

        wordCounter = 0;
        first = 1;
        countAll = 0;
        counter = 0;
        isAnimationRunning = false;
        userWord_textField.setDisable(false);
        userWord_textField.clear();
        wordsPerMin_text.setText("0");
        accuracy_text.setText("0");
        seconds_text.setText("60");

        // Đặt lại văn bản hiển thị từ danh sách từ
        Collections.shuffle(words);
        programWord_text.setText(words.get(wordCounter));
        secondProgramWord_text.setText(words.get(wordCounter + 1));
        wordCounter = 1; // Đảm bảo rằng vị trí từ là 0
        isStarted = false;


        // Bắt đầu đếm thời gian lại
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
        }
        timer = 60;
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(r, 0, 1, TimeUnit.SECONDS);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anh2_img.setVisible(false);
        anh2_img2.setVisible(false);
        anh2_img3.setVisible(false);

        playAgain_btn.setVisible(false);
        playAgain_btn.setDisable(true);

        addToList();
        Collections.shuffle(words);
        programWord_text.setText(words.get(wordCounter));
        secondProgramWord_text.setText(words.get(wordCounter+1));
        wordCounter++;


        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        saveData = new File("src/data/" + formatter.format(date).strip() + ".txt");

        try {
            // Kiểm tra xem thư mục chứa tệp đã tồn tại chưa
            if (!saveData.getParentFile().exists()) {
                saveData.getParentFile().mkdirs();
            }

            if (saveData.createNewFile()) {
                System.out.println("File created: " + saveData.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private int timer = 60;

    Runnable r = new Runnable() {
        @Override
        public void run() {

            if (timer > -1) {
                seconds_text.setText(String.valueOf(timer));
                timer -= 1;
            }

            else {
                if (timer == -1) {
                    userWord_textField.setDisable(true);
                    userWord_textField.setText("Game over");
                    anh2_img.setVisible(true);
                    anh2_img2.setVisible(true);
                    anh2_img3.setVisible(true);

                    Model.getInstance().getDatabaseConnection().saveScore(LoginController.user,counter);

                    try {
                        FileWriter myWriter = new FileWriter(saveData);
                        myWriter.write(countAll +";");
                        myWriter.write(counter +";");
                        myWriter.write(String.valueOf(countAll-counter));
                        myWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (timer == -4) {
                    playAgain_btn.setVisible(true);
                    playAgain_btn.setDisable(false);
                    executor.shutdown();
                }

                timer -= 1;
            }
        }
    };

    private boolean isAnimationRunning = false;

    private void fadeCorrectAnimation() {

        if (!isAnimationRunning) {

            isAnimationRunning = true;

            AnimationTimer timer = new AnimationTimer() {

                @Override
                public void handle(long now) {
                    if (correct_img.getOpacity() < 1) {
                        correct_img.setOpacity(correct_img.getOpacity() + 0.05);
                    } else {
                        correct_img.setOpacity(0);
                        stop();
                        isAnimationRunning = false;
                    }
                }

            };

            timer.start();

        }

    }

    private void fadeWrongAnimation() {

        if (!isAnimationRunning) {

            isAnimationRunning = true;

            AnimationTimer timer = new AnimationTimer() {

                @Override
                public void handle(long now) {
                    if (wrong_img.getOpacity() < 1) {
                        wrong_img.setOpacity(wrong_img.getOpacity() + 0.05);
                    } else {
                        wrong_img.setOpacity(0);
                        stop();
                        isAnimationRunning = false;
                    }
                }

            };

            timer.start();

        }

    }

    public void startGame(KeyEvent ke) {

        playAgain_btn.setVisible(false);
        playAgain_btn.setDisable(true);

        if (first == 1) {
            first = 0;
            executor.scheduleAtFixedRate(r, 0, 2, TimeUnit.SECONDS);
        }

        if (ke.getCode().equals(KeyCode.ENTER) ||ke.getCode().equals(KeyCode.SPACE) ) {
            if (!isStarted) {
                executor.scheduleAtFixedRate(r, 0, 2, TimeUnit.SECONDS);

                isStarted = true;
            }
            String s = userWord_textField.getText();
            s = s.trim();
            String real = programWord_text.getText();
            countAll++;

            if (s.equals(real)) {
                counter++;
                wordsPerMin_text.setText(String.valueOf(counter));
                fadeCorrectAnimation();
            } else {
                fadeWrongAnimation();
            }

            userWord_textField.setText("");
            accuracy_text.setText(String.valueOf(Math.round((counter * 1.0 / countAll) * 100)));
            programWord_text.setText(words.get(wordCounter));
            secondProgramWord_text.setText(words.get(wordCounter + 1));
            wordCounter++;
        }
    }

    @FXML
    void HandleReturnButton(ActionEvent event) throws IOException{
        System.out.println("GetGame");
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxml/Client/MainGameMenu.fxml")));
        GameMenu.getChildren().removeAll();
        GameMenu.getChildren().setAll(pane);
    }

    public void rankUser(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/RankList.fxml"));
        Parent root = loader.load();

        Stage rankListStage = new Stage();
        rankListStage.setScene(new Scene(root));
        rankListStage.setTitle("Rank");
        rankListStage.show();
    }

}
