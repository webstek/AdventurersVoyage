package model.races;

import model.Statistics;

public class Elf extends Race {
    public Elf() {
        this.hpGain = 25;
        this.mpGain = 20;
        this.species = "Elf";
        this.description = "The typical wise, long-living, magical Elves seen in Tolkien's world. They have pointed "
                + "ears and an additional 50 + 25 every 5 levels magic potency.";
        this.strengths = "Healing, Mobility, Communication, Exploration";
        this.weaknesses = "Pointy things, Duels, Curses";
        setStatsValues();
        setLvlGainValues();
    }

    // MODIFIES: this
    // EFFECTS: sets the baseStats field to the correct values for the race.
    protected void setStatsValues() {
        this.baseStats = new Statistics(new int[][]{{8,6,6,8,7,250,200},{0,0,0,0,0,0,0}});
    }

    // MODIFIES: this
    // EFFECTS: sets the lvlUpStats field to the correct values for the race.
    protected void setLvlGainValues() {
        this.lvlUpStats = new Statistics(new int[][]{{0,0,0,0,0,hpGain,mpGain},{0,0,0,0,0,0,0}});
    }

}
