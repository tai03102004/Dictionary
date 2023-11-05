package Controllers.client;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.animation.KeyValue;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameController implements Initializable {


    public javafx.scene.shape.Circle Circle;
    public ImageView movingImageView;
    public ImageView anh2_img;
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
            reader = new BufferedReader(new
                    FileReader("wordsList"));
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
        playAgain_btn.setVisible(true);
        playAgain_btn.setDisable(false);

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
        if (executor != null) {
            executor.shutdown();
        }
        timer = 60;

        // Bắt đầu đếm thời gian lại
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(r, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        playAgain_btn.setVisible(false);
        playAgain_btn.setDisable(true);


        addToList();
        Collections.shuffle(words);
        programWord_text.setText(words.get(wordCounter));
        secondProgramWord_text.setText(words.get(wordCounter+1));
        wordCounter++;


        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        saveData = new File("src/data/"+formatter.format(date).strip()+".txt");

        try {
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
        if (isAnimationRunning) {
            return;
        }
        isAnimationRunning = true;
        Timeline timeline = new Timeline();

        KeyFrame keyFrame1 = new KeyFrame(Duration.ZERO, new KeyValue(correct_img.opacityProperty(), 0.0));
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(200), new KeyValue(correct_img.opacityProperty(), 1.0));
        KeyFrame keyFrame3 = new KeyFrame(Duration.millis(400), event -> {
            correct_img.setOpacity(0.0);
            isAnimationRunning = false;
        }, new KeyValue(correct_img.opacityProperty(), 0.0));

        timeline.getKeyFrames().addAll(keyFrame1, keyFrame2, keyFrame3);
        timeline.play();
    }

    private void fadeWrongAnimation() {
        if (isAnimationRunning) {
            return;
        }
        isAnimationRunning = true;
        Timeline timeline = new Timeline();

        KeyFrame keyFrame1 = new KeyFrame(Duration.ZERO, new KeyValue(wrong_img.opacityProperty(), 0.0));
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(200), new KeyValue(wrong_img.opacityProperty(), 0.5));
        KeyFrame keyFrame3 = new KeyFrame(Duration.millis(400), new KeyValue(wrong_img.opacityProperty(), 1.0));
        KeyFrame keyFrame4 = new KeyFrame(Duration.millis(600), event -> {
            wrong_img.setOpacity(0.0);
            isAnimationRunning = false;
        }, new KeyValue(wrong_img.opacityProperty(), 0.0));

        timeline.getKeyFrames().addAll(keyFrame1, keyFrame2, keyFrame3, keyFrame4);
        timeline.play();
    }

    public void startGame(KeyEvent ke) {
        anh2_img.setVisible(false);
        if (first == 1) {
            first = 0;
            executor.scheduleAtFixedRate(r, 0, 1, TimeUnit.SECONDS);
        }

        if (ke.getCode().equals(KeyCode.ENTER) ||ke.getCode().equals(KeyCode.SPACE) ) {
            if (!isStarted) {
                executor.scheduleAtFixedRate(r, 0, 1, TimeUnit.SECONDS);
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
}