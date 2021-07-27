package model.exceptions;

/**
 *  Not a boolean exception class. Thrown when user input is not valid for the question
 */
public class UserInputException extends Exception {
    public UserInputException(String errorMessage) {
        super(errorMessage);
    }

}