package model.stats;

import java.lang.reflect.*;

public class StatUpdater {
    Stats modifyeeStats; // Stats that get returned at
    Stats modifyerStats;

    // EFFECTS: Sets the modifyeeStats and modifyerStats by the parameters and adds or subtracts
    public StatUpdater(Stats modifyeeStats,Stats modifyerStats) {
        this.modifyeeStats = modifyeeStats;
        this.modifyerStats = modifyerStats;
    }

    // EFFECTS: returns an array of Field objects from reflection corresponding to the Stats fields.
    //          Used for iterating over the fields to reduce written code.
    private Field[] getFields() {
        return modifyeeStats.getClass().getDeclaredFields();
    }

    // REQUIRES: all Stats values in newStats parameter to be non negative
    // MODIFIES: this
    // EFFECTS: adds or subtracts all the field values in newStats to/from this Stats object depending on the value of
    //          the add parameter (true for addition, or false for subtraction).
    public void updateStats(Boolean add) {
        for (Field f : getFields()) {
            f.setAccessible(true);
            try {
                int addFieldVal = f.getInt(modifyeeStats) + f.getInt(modifyerStats);
                int subFieldVal = f.getInt(modifyeeStats) - f.getInt(modifyerStats);
                if (add) {
                    f.setInt(modifyeeStats, addFieldVal);
                } else {
                    f.setInt(modifyeeStats, subFieldVal);
                }
            } catch (IllegalAccessException e) {
                System.out.println("I'm surprised this happened. Wow.");
            }
        }
    }
}
