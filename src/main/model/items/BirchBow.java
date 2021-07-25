package model.items;

import model.IntegerMatrix;

/**
 * Birch Bow item, starting weapon of the ranger.
 */

public class BirchBow extends Item {
    // EFFECTS: creates a Birch Bow item
    public BirchBow() {
        this.name = "Birch Bow";
        this.description = "The basic starting bow of any aspiring ranger";
        setItemStats();
    }

    // MODIFIES: this
    // EFFECTS: sets the Birch Bow's stats
    protected void setItemStats() {
        this.itemStats = new IntegerMatrix(new int[][]{{0,0,0,0,0,0,0},{0,0,0,0,0,15,0}});
    }
}