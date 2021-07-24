package model.stats;

/**
 * Stats subclass for any objects which give base stats only, such as race.
 */
public class BaseStatsOnly extends Stats {
    // EFFECTS: sets the bonus stats of the ItemStats object
    public BaseStatsOnly(int intl, int str, int spd, int dex, int stl) {
        this.intelligence = intl;
        this.strength = str;
        this.speed = spd;
        this.dex = dex;
        this.stealth = stl;
    }
}
