package com.example.directory.Controllers.client;

import java.time.LocalDateTime;

public class WordItem {
    private final String target;
    private final String definition;
    LocalDateTime timestamp;
    private boolean checked; // CheckBox

    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }


    public WordItem(String target, String definition) {
        this.target = target;
        this.definition = definition;
    }

    public String getTarget() {
        return target;
    }

    public String getDefinition() {
        return definition;
    }

    public String getLimitItem() {
        int maxLength = 100;
        if (definition.length() > maxLength) {
            return definition.substring(0, maxLength - 3) + "...";
        } else {
            return definition;
        }
    }


    @Override
    public String toString() {
        return definition == null ? target : target + " - " + definition;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
}
