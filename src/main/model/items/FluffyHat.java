package model.items;

import model.Statistics;

import javax.swing.*;

/**
 * Fluffy Hat item, given as a token of goodwill to every player for now.
 */

public class FluffyHat extends Item {
    // EFFECTS: creates a Birch Bow item
    public FluffyHat() {
        this.name = "Fluffy Hat";
        this.description = "A necessity for any determined adventurer.";
        this.itemIcon = new ImageIcon(System.getProperty("user.dir") + DIR_SEP + "data" + DIR_SEP + "items" + DIR_SEP
                + "fluffy hat.png");
        setItemStats();
    }

    // MODIFIES: this
    // EFFECTS: sets the Fluffy Hat's stats
    protected void setItemStats() {
        this.itemStats = new Statistics(new int[][]{{0,0,0,0,0,125,0},{0,0,0,0,0,0,0}});
    }
}