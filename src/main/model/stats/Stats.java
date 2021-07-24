package model.stats;

import java.lang.reflect.*;

/**
 * Abstract class for the Statistics of every entity or item.
 */
public abstract class Stats {
    int[][] statValues = new int[2][5];

    // EFFECTS: returns an array of Field objects from reflection corresponding to the Stats fields.
    //          Used for iterating over the fields to reduce written code.
    private Field[] getFields() {
        return this.getClass().getFields();
    }

    // TODO FIX THE UPDATE FUNCTION
    // REQUIRES: all Stats values in newStats parameter to be non negative
    // MODIFIES: this
    // EFFECTS: adds or subtracts all the field values in newStats to/from this Stats object depending on the value of
    //          the add parameter (true for addition, or false for subtraction).
    public void update(Stats modifyerStats, Boolean add) {
        for (Field f : getFields()) {
            f.setAccessible(true);
            try {
                int addFieldVal = f.getInt(this) + f.getInt(modifyerStats);
                int subFieldVal = f.getInt(this) - f.getInt(modifyerStats);
                if (add) {
                    f.setInt(this, addFieldVal);
                    System.out.println("Updating stats");
                } else {
                    f.setInt(this, subFieldVal);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    // CORRECT EFFECTS: returns a 2d array of the integer field values in the stat class. 0 indexed row for base stats,
    //                  1 indexed row for bonus stats.
    //TODO make the appropriate method here and in advVoyApp class
    public void display() {
        System.out.println("Statistics Page: \n Intelligence: " + intelligence + "+" + bonusIntelligence
                + " \n Strength: " + strength + "+" + bonusStrength
                + " \n Speed: " + speed + "+" + bonusSpeed + " \n Dexterity: " + dex + "+" + bonusDex
                + " \n Stealth: " + stealth + "+" + bonusStealth);
    }

    // EFFECTS: returns the intelligence field value
    public int intelligence() {
        return statValues[0][1];
    }

    // EFFECTS: returns the strength field value
    public int strength() {
        return strength;
    }

    // EFFECTS: returns the speed field value
    public int speed() {
        return speed;
    }

    // EFFECTS: returns the dex field value
    public int dex() {
        return dex;
    }

    // EFFECTS: returns the stealth field value
    public int stealth() {
        return stealth;
    }

    // EFFECTS: returns the bonusIntelligence field value
    public int bonusIntelligence() {
        return bonusIntelligence;
    }

    // EFFECTS: returns the bonusStrength field value
    public int bonusStrength() {
        return bonusStrength;
    }

    // EFFECTS: returns the bonusSpeed field value
    public int bonusSpeed() {
        return bonusSpeed;
    }

    // EFFECTS: returns the bonusDex field value
    public int bonusDex() {
        return bonusDex;
    }

    // EFFECTS: returns the bonusStealth field value
    public int bonusStealth() {
        return bonusStealth;
    }
}
