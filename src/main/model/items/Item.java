package model.items;

import model.Statistics;

/**
 * Representation of items. Items have a name, bonus stats, penalty stats, and a description.
 */

public abstract class Item {
    protected Statistics itemStats;
    protected String name;
    protected String description;

    // MODIFIES: this
    // EFFECTS: sets this Item's stats
    protected abstract void setItemStats();

    // EFFECTS: returns the itemStats
    public Statistics stats() {
        return itemStats;
    }

    // EFFECTS: returns the item name
    public String name() {
        return name;
    }

    // EFFECTS: returns the item description
    public String description() {
        return description;
    }
}