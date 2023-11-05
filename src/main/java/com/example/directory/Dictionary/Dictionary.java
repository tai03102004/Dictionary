import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Dictionary {
    private String path;
    private String history;
    private String bookmark;

    private HashMap<String,Word> vocabulary = new HashMap<>();
    private HashMap<String,Word> historyVocabulary = new HashMap<>();
    private HashMap<String,Word> bookmarkVocabulary = new HashMap<>();

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

    public HashMap<String,Word> getBookmarkVocabulary() {
        return bookmarkVocabulary;
    }

    public HashMap<String,Word> getHistoryVocabulary() {
        return historyVocabulary;
    }

    public HashMap<String,Word> getVocabulary() {
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

    public void loadDataFromHTMLFile(String path, HashMap<String,Word> temp) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] Words = line.split("<html>");
                String word = Words[0];
                String definition = "<html>" + Words[1];
                Word wordObj = new Word(word, definition);
                temp.put(word,wordObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportWordToHTMLFile(String path, String spelling) {
        try {
            File file = new File(path);
            FileWriter fileWriter = new FileWriter(file, true);
            Word word = vocabulary.get(spelling);
            fileWriter.write(word.getWordTarget() + word.getWordExplain() + "\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UpdateDictionary(String path, HashMap<String,Word> arr) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            for (Map.Entry<String, Word> entry : arr.entrySet()) {
                String key = entry.getKey();
                Word value = entry.getValue();
                writer.write(key + value.getWordExplain() + "\n");
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
        Word word = new Word(target,meaning);
        vocabulary.replace(target,word);
        UpdateDictionary(path, vocabulary);
    }

    public void deleteWord(String target, String path, HashMap<String,Word> arr) {
        target = target.toLowerCase();
        arr.remove(target);
        UpdateDictionary(path, arr);
    }

    public void addWord(String target, String meaning) {
        target = target.toLowerCase();
        vocabulary.put(target,new Word(target, meaning));
        UpdateDictionary(path,vocabulary);
    }

    public void ReadHistoryFile(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(history));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] Words = line.split("<html>");
                String target = Words[0];
                String meaning = "<html>" + Words[1];
                Word word = new Word(target, meaning);
                historyVocabulary.put(target,word);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}
