package model.stats;

/**
 * Stats subclass for objects that have any combination of base and bonus stats. Creatures (such as players or enemies)
 * have non-zero base stats and some (if not all) non-zero bonus stats.
 */
public class AllStats extends Stats {
    // EFFECTS: sets the base and bonus stats fields of the CreatureStats object
    public AllStats(int intl, int str, int spd, int dex, int stl,
                    int bnIntl, int bnStr, int bnSpd, int bnDex, int bnStl) {
        this.intelligence = intl;
        this.strength = str;
        this.speed = spd;
        this.dex = dex;
        this.stealth = stl;
        this.bonusIntelligence = bnIntl;
        this.bonusStrength = bnStr;
        this.bonusSpeed = bnSpd;
        this.bonusDex = bnDex;
        this.bonusStealth = bnStl;
    }

    // TODO figure out the extra constructor here
    // EFFECTS: creates an AllStats object from a pair of BaseStatsOnly and BonusStatsOnly objects
//    public AllStats(BaseStatsOnly baseStats, BonusStatsOnly bonusStats) {
//        this.update(baseStats, true);
//        this.update(bonusStats, true);
//    }
}