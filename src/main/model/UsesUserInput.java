package model;

/**
 * Interface for all classes that consume a user input from the GameState actionText. When the actionText value is used,
 * the implementation of consumedUserInput() must be called to notify the stateHandler that if another user input value
 * is required, the handling method must be recalled with new actionText.
 */
public interface UsesUserInput {
    // MODIFIES: gs
    // EFFECTS: sets the gs.hasUnusedUserInput field to false for the associated GameState with the implementing class.
    void consumedUserInput();
}
