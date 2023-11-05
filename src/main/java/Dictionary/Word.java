package Dictionary;

public class Word implements Comparable<Word>{
    private String word_target;
    private String word_explain;

    public Word(String target, String explain) {
        this.word_target = target;
        this.word_explain = explain;
    }



    @Override
    public int compareTo(Word o) {
        return this.word_explain.compareToIgnoreCase(o.word_explain);
    }

    public String getWordTarget() {
        return word_target;
    }

    public void setWordTarget(String target) {
        this.word_target = target;
    }

    public String getWordExplain() {
        return word_explain;
    }

    public void setWordExplain(String explain) {
        this.word_explain = explain;
    }
}
