package model.races;

import model.Statistics;

/**
 * Represents the Human race in the game.
 */
public class Human extends Race {
    // EFFECTS: creates a Human object with all appropriately filled relevant fields.
    public Human() {
        this.hpGain = 25;
        this.mpGain = 10;
        this.species = "Human";
        this.description = "The vanilla/default/normal race for a character,"
                + "they are all-rounders, but possess good instincts.";
        this.strengths = "Charisma, Communication, Dexterity";
        this.weaknesses = "Pointy things, Fire, Magic, Spirits";
        setStatsValues();
        setLvlGainValues();
    }

    // MODIFIES: this
    // EFFECTS: sets the baseStats field to the correct values for the race.
    protected void setStatsValues() {
        this.baseStats = new Statistics(new int[][]{{7,5,6,8,7,200,100},{0,0,0,0,0,0,0}});
    }

    // MODIFIES: this
    // EFFECTS: sets the lvlUpStats field to the correct values for the race.
    protected void setLvlGainValues() {
        this.lvlUpStats = new Statistics(new int[][]{{0,0,0,0,0,hpGain,mpGain},{0,0,0,0,0,0,0}});
    }
}
