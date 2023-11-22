package com.example.directory.Models;

import java.util.HashMap;
import java.util.Map;

public class WordRadixTree {
    private final Node root;

    public WordRadixTree() {
        this.root = new Node();
    }

    public void addWord(String word, String definition) {
        addWord(root, word, definition);
    }

    private void addWord(Node node, String remaining, String definition) {
        if (remaining.isEmpty()) {
            node.setDefinition(definition);
            return;
        }

        char firstChar = remaining.charAt(0);
        Node child = node.getChild(firstChar);

        if (child == null) {
            child = new Node();
            node.addChild(firstChar, child);
        }

        addWord(child, remaining.substring(1), definition);
    }

    public String searchWord(String partialKeyword) {
        return searchWord(root, partialKeyword);
    }

    private String searchWord(Node node, String remaining) {
        if (node == null) {
            return null;
        }

        if (remaining.isEmpty()) {
            return node.getDefinition();
        }

        char firstChar = remaining.charAt(0);
        Node child = node.getChild(firstChar);

        if (child == null) {
            return null;
        }

        return searchWord(child, remaining.substring(1));
    }

    private static class Node {
        private final Map<Character, Node> children = new HashMap<>();
        private String definition;

        public Node getChild(char c) {
            return children.get(c);
        }

        public void addChild(char c, Node child) {
            children.put(c, child);
        }

        public String getDefinition() {
            return definition;
        }

        public void setDefinition(String definition) {
            this.definition = definition;
        }
    }
}
