package com.example.directory.Controllers.client;

public class NewestComparator implements WordItemComparator {

    @Override
    public int compare(WordItem o1, WordItem o2) {
        // Assuming timestamp is a comparable type, like Date or Instant
        Comparable timestamp1 = (o1 != null && o1.timestamp != null) ? o1.timestamp : null;
        Comparable timestamp2 = (o2 != null && o2.timestamp != null) ? o2.timestamp : null;


        // Handle null cases
        if (timestamp1 == null && timestamp2 == null) {
            return 0;  // Both timestamps are null, consider them equal
        } else if (timestamp1 == null) {
            return 1;  // Null is considered greater than a non-null value
        } else if (timestamp2 == null) {
            return -1; // Non-null is considered greater than null
        }

        // Use reverse order to sort from newest to oldest
        return timestamp2.compareTo(timestamp1);
    }
}
