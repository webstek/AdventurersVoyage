package model.exceptions;

public class InsufficientResourceException extends Exception {
    private final String errorMessage;

    public InsufficientResourceException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void notification() {
        System.out.println(errorMessage);
    }
}
