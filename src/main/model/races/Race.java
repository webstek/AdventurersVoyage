package model.races;

import model.InstanceFactory;
import model.Statistics;

/**
 * Abstract class that all race subclasses are based upon. The race class contains a species name, description, list of
 * strengths, list of weaknesses, baseStats
 */

public abstract class Race {
    protected String species = null;
    protected String description = null;
    protected String strengths;
    protected String weaknesses;
    protected Statistics baseStats;
    protected Statistics lvlUpStats;
    protected int hpGain;
    protected int mpGain;

    // MODIFIES: this
    // EFFECTS: sets the appropriate base stats for each race. Called in the constructors.
    protected abstract void setStatsValues();

    // MODIFIES: this
    // EFFECTS: sets the perLvlHpMpGain for each race. Called in the constructors.
    protected abstract void setLvlGainValues();

    // EFFECTS: returns the species name in a String
    public String getSpecies() {
        return species;
    }

    // EFFECTS: returns the description string of the race
    public String getDescription() {
        return description;
    }

    // EFFECTS: returns the strength ArrayList
    public String getStrengths() {
        return strengths;
    }

    // EFFECTS: returns the weaknesses ArrayList
    public String getWeaknesses() {
        return weaknesses;
    }

    // EFFECTS: returns the baseStats IntegerMatrix
    public Statistics stats() {
        return baseStats;
    }

    // EFFECTS: returns the lvlUpStats IntegerMatrix
    public Statistics lvlUpStats() {
        return lvlUpStats;
    }

    // EFFECTS: returns the hpGain per level integer
    public int getHpGain() {
        return hpGain;
    }

    // EFFECTS: returns the mpGain per level integer
    public int getMpGain() {
        return mpGain;
    }
}
