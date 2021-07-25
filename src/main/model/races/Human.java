package model.races;

import model.IntegerMatrix;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents the Human race in the game.
 */
public class Human extends Race {
    // EFFECTS: creates a Human object with all appropriately filled relevant fields.
    public Human() {
        setStatsValues();
        setLvlGainValues();
        this.species = "Human";
        this.description = "The vanilla/default/normal race for a character,"
                + "they are all-rounders, but possess good instincts.";
        this.strengths = new ArrayList<String>(Arrays.asList("Charisma", "Communication", "Dexterity"));
        this.weaknesses = new ArrayList<String>(Arrays.asList("Pointy things", "Fire", "Magic", "Spirits"));
    }

    // MODIFIES: this
    // EFFECTS: sets the baseStats field to the correct values for the race.
    protected void setStatsValues() {
        this.baseStats = new IntegerMatrix(new int[][]{{7,5,6,8,7,0,0},{0,0,0,0,0,0,0}});
    }

    // MODIFIES: this
    // EFFECTS: sets the lvlUpStats field to the correct values for the race.
    protected void setLvlGainValues() {
        this.lvlUpStats = new IntegerMatrix(new int[][]{{0,0,0,0,0,0,0},{0,0,2,4,2,0,0}});
    }
}
