package model.entities;

/**
 * Represents the user's player/adventurer. Players have an extra field that is their level. Increasing level applies
 * lvlUpStats and for some players will cause unique effects.
 */

public class Player extends Entity {
    private int level = 0;

    // EFFECTS: constructs a base player with a name and base starting gold of 50.
    public Player(String name) {
        this.name = name;
        this.gold = 50;
    }
}
