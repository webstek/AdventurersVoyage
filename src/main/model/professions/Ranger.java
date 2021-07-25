package model.professions;

import model.IntegerMatrix;
import model.abilities.*;
import java.util.ArrayList;

/**
 * represents the Ranger profession in the game.
 */

public class Ranger extends Profession {
    // EFFECTS: Creates a ranger object with the appropriate fields
    public Ranger() {
        setBonusStats();
        this.title = "Ranger";
        this.abilities = new ArrayList<Ability>();
        abilities.add(new PowerShot());
    }

    // MODIFIES: this
    // EFFECTS: sets the correct values in the bonusStats field
    protected void setBonusStats() {
        this.bonusStats = new IntegerMatrix(new int[][]{{0,0,0,0,0,0,0},{0,0,2,4,2,0,0}});
    }

}
