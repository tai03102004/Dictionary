package com.example.directory.Models;

public class WordsDataBase {
    public String target;
    public String definition;

    public WordsDataBase(String target, String definition) {
        this.target = target;
        this.definition = definition;
    }
    public String getTarget() {
        return target;
    }

    public String getDefinition() {
        return definition;
    }
}
