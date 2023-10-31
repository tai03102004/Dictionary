import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;

public class Dictionary {
    private static final String splitPattern = "<html>";
    private String path;
    private String history;
    private String bookmark;

    private ArrayList<Word> vocabulary = new ArrayList<>();
    private ArrayList<Word> historyVocabulary = new ArrayList<>();
    private ArrayList<Word> bookmarkVocabulary = new ArrayList<>();

    public Dictionary(String path, String history, String bookmark) {
        this.path = path;
        this.history = history;
        this.bookmark = bookmark;
    }

    //getter of class Dictionary.
    //Setter of this function will be read in file
    public String getBookmark() {
        return bookmark;
    }

    public String getHistory() {
        return history;
    }

    public String getPath() {
        return path;
    }

    public ArrayList<Word> getBookmarkVocabulary() {
        return bookmarkVocabulary;
    }

    public ArrayList<Word> getHistoryVocabulary() {
        return historyVocabulary;
    }

    public ArrayList<Word> getVocabulary() {
        return vocabulary;
    }
    //-----------------------------------------------------------------------

    public void addWordToFile(String word, String meaning, String path) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            writer.write(word + meaning);
            writer.newLine();
            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UpdateDictionary(String path, ArrayList<Word> arr) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            for (Word word : arr) {
                writer.write(word.getWordTarget() + word.getWordExplain() + "\n");
            }
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void fixWord(String target, String meaning) {
        target = target.toLowerCase();
        meaning = meaning.toLowerCase();
        int index = -1;
        index = Collections.binarySearch(vocabulary, new Word(target, meaning));
        if (index > -1) vocabulary.get(index).setWordExplain(meaning);
        else return;

        UpdateDictionary(path, vocabulary);
    }

    public void deleteWord(String target, String path, ArrayList<Word> arr) {
        target = target.toLowerCase();
        int index = Collections.binarySearch(arr, new Word(target, null));
        if (index >= 0) arr.remove(arr.get(index));
        else return;
        UpdateDictionary(path, arr);
    }

    public void addWord(String target, String path, ArrayList<Word> arr) {

    }

}
