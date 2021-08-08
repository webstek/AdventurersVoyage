package model.items;

import model.Statistics;

import javax.swing.*;

/**
 * Birch Bow item, starting weapon of the ranger.
 */

public class BirchBow extends Item {
    // EFFECTS: creates a Birch Bow item
    public BirchBow() {
        this.name = "Birch Bow";
        this.description = "The basic starting bow of any aspiring ranger";
        this.itemIcon = new ImageIcon(System.getProperty("user.dir") + DIR_SEP + "data" + DIR_SEP + "items" + DIR_SEP
                + "birch bow.png");
        setItemStats();
    }

    // MODIFIES: this
    // EFFECTS: sets the Birch Bow's stats
    protected void setItemStats() {
        this.itemStats = new Statistics(new int[][]{{0,0,0,0,0,0,0},{0,0,0,0,0,15,0}});
    }
}