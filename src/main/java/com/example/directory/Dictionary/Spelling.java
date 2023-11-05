import java.io.*;
import java.util.*;

public class Spelling {

    private final HashMap<String, Integer> nWords = new HashMap<>();

    public Spelling(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.toLowerCase().trim(); // Chuyển thành chữ thường và loại bỏ khoảng trắng thừa
            String[] words = line.split("\\s+");
            for (String word : words) {
                if (!word.isEmpty()) {
                    nWords.put(word, (int) (nWords.getOrDefault(word, (int) 0) + 1));
                }
            }
        }
        reader.close();
    }

    private final ArrayList<String> edits(String word) {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            result.add(word.substring(0, i) + word.substring(i + 1));
        }
        for (int i = 0; i < word.length() - 1; i++) {
            result.add(word.substring(0, i) + word.substring(i + 1, i + 2) + word.substring(i, i + 1) + word.substring(i + 2));
        }
        for (int i = 0; i < word.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                result.add(word.substring(0, i) + c + word.substring(i + 1));
            }
        }
        for (int i = 0; i <= word.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                result.add(word.substring(0, i) + c + word.substring(i));
            }
        }
        return result;
    }

    public final String correct(String word) {
        if (nWords.containsKey(word)) {
            return word;
        }
        ArrayList<String> list = edits(word);
        IdentityHashMap<Integer, String> candidates = new IdentityHashMap<>();
        for (String s : list) {
            if (nWords.containsKey(s)) {
                candidates.put(nWords.get(s), s);
            }
        }
        if (!candidates.isEmpty()) {
            return candidates.get(Collections.max(candidates.keySet()));
        }
        for (String s : list) {
            for (String w : edits(s)) {
                if (nWords.containsKey(w)) {
                    candidates.put(nWords.get(w), w);
                }
            }
        }
        return !candidates.isEmpty() ? candidates.get(Collections.max(candidates.keySet())) : word;
    }

    public static void main(String[] args) throws IOException {
        String word = "heol";
        Spelling corrector = new Spelling("src/Dictionary Datas/vocab/spelling.txt");
        System.out.println(corrector.correct(word.toLowerCase()));
    }
}
