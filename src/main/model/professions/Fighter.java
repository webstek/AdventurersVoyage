package model.professions;

import model.Statistics;
import model.abilities.Slam;

import java.util.ArrayList;

public class Fighter extends Profession {
    // EFFECTS: Creates a ranger object with the appropriate fields
    public Fighter() {
        this.title = "Fighter";
        this.abilities = new ArrayList<>();
        abilities.add(new Slam());
        this.bonusStats = setBonusStats();
    }

    // MODIFIES: this
    // EFFECTS: sets the correct values in the bonusStats field
    protected Statistics setBonusStats() {
        return new Statistics(new int[][]{{0,0,0,0,0,0,0},{0,4,0,4,0,0,0}});
    }
}
