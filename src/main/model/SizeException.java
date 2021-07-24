package model;

/**
 * Exception class for SizedList. Is thrown if there is no spot in the list to add an element to.
 */
public class SizeException extends Exception {
    // EFFECTS: prints the errorMessage to the console
    public SizeException(String errorMessage) {
        System.out.println(errorMessage);
    }
}

