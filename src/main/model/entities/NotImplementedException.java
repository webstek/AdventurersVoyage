package model.entities;

/**
 * Exception class for unimplemented races and professions. Thrown by setRace and setPrfsn methods in player.
 */
public class NotImplementedException extends Exception {
    // EFFECTS: returns Exception class with the appropriate detail error message
    public NotImplementedException(String errorMessage) {
        super(errorMessage);
    }
}