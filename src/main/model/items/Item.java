package model.items;

import model.stats.BonusStatsOnly;

public abstract class Item {
    protected BonusStatsOnly bonus;
    protected BonusStatsOnly penalty;
    protected String itemName;
    protected String description;

    public BonusStatsOnly getBonus() {
        return bonus;
    }

    public BonusStatsOnly getPenalty() {
        return penalty;
    }

    public String itemName() {
        return itemName;
    }

    public String description() {
        return description;
    }
}
