package model.entities;

/**
 * Represents the user's player/adventurer. Players have an extra field that is their level. Increasing level applies
 * lvlUpStats and for some players will cause unique effects.
 */

public class Player extends Entity {
    // EFFECTS: constructs a base player with a name and base starting gold of 50.
    public Player(String name) {
        this.name = name;
        this.gold = 50;
    }

    // MODIFIES: this
    // EFFECTS: checks if the player has enough xp to level up. The xp requirement is always 100. Returns true if the
    //          player can level up, returns false otherwise.
    private boolean checkLvlUp() {
        if (this.xp >= 100) {
            this.level += 1;
            this.xp -= 100;
            return true;
        }
        return false;
    }
}
