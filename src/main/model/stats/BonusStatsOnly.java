package model.stats;

/**
 * Stats subclass for any objects which give bonus stats only, such as professions or items (normally).
 */
public class BonusStatsOnly extends Stats {
    // EFFECTS: sets the bonus stats of the BonusStatsOnly object
    public BonusStatsOnly(int bnIntl, int bnStr, int bnSpd, int bnDex, int bnStl) {
        this.bonusIntelligence = bnIntl;
        this.bonusStrength = bnStr;
        this.bonusSpeed = bnSpd;
        this.bonusDex = bnDex;
        this.bonusStealth = bnStl;
    }
}
