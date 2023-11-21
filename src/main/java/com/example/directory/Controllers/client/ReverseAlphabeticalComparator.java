package com.example.directory.Controllers.client;

import java.util.Comparator;

public class ReverseAlphabeticalComparator implements WordItemComparator {
    @Override
    public int compare(WordItem word1, WordItem word2) {
        String target1 = word1.getTarget();
        String target2 = word2.getTarget();

        return target2.compareToIgnoreCase(target1);
    }
}
