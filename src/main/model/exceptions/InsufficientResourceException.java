package model.exceptions;

public class InsufficientResourceException extends Error {
    public InsufficientResourceException(String errorMessage) {
        super(errorMessage);
    }
}
