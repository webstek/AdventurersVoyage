package model.races;

import java.util.ArrayList;

/**
 * Abstract class for each race. Has species, description, strengths list of strings, and weakness list of strings.
 * Races also have base hp, mp, hp gain per level, and mp gain per level.
 */
public abstract class Race {
    protected String species = null;
    protected String description = null;
    protected ArrayList<String> strengths = new ArrayList<String>();
    protected ArrayList<String> weaknesses = new ArrayList<String>();
    protected int intelligence = 0;
    protected int strength = 0;
    protected int speed = 0;
    protected int dex = 0;
    protected int stealth = 0;
    protected double health = 0;
    protected int healthGain = 0;
    protected double mana = 0;
    protected int manaGain = 0;

    // EFFECTS:
    public String getSpecies() {
        return species;
    }

    // EFFECTS:
    public String getDescription() {
        return description;
    }

    // EFFECTS:
    public ArrayList<String> getStrengths() {
        return strengths;
    }

    // EFFECTS:
    public ArrayList<String> getWeaknesses() {
        return weaknesses;
    }

    // EFFECTS:
    public int intelligence() {
        return intelligence;
    }

    public int strength() {
        return strength;
    }

    public int speed() {
        return speed;
    }

    public int dex() {
        return dex;
    }

    public int stealth() {
        return stealth;
    }

    public double health() {
        return health;
    }

    public int healthGain() {
        return healthGain;
    }

    public double mana() {
        return mana;
    }

    public int manaGain() {
        return manaGain;
    }
}
