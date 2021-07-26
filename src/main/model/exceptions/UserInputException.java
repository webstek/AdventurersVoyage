package model.exceptions;

/**
 *  Not a boolean exception class. Thrown when user input
 */
public class UserInputException extends Exception {
    private final String errorMessage;

    public UserInputException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getRequest() {
        return errorMessage;
    }
}