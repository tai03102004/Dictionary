package com.example.directory.Controllers.client;

public class AlphabeticalComparator implements WordItemComparator {
    @Override
    public int compare(WordItem word1, WordItem word2) {
        // Sắp xếp theo bảng chữ cái
        return word1.getTarget().compareToIgnoreCase(word2.getTarget());
    }
}

