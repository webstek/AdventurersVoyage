package model.items;

import model.stats.BonusStatsOnly;

public class BirchBow extends Item {
    public BirchBow() {
        this.itemName = "Birch Bow";
        this.bonus = new BonusStatsOnly(0,0,2,0,0);
        this.penalty = new BonusStatsOnly(0,1,0,0,0);
        this.description = "The basic starting bow of any aspiring ranger";
    }
}
