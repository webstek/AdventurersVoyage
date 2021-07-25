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
        this.title = "Ranger";
        this.abilities = new ArrayList<>();
        abilities.add(new PowerShot());
        this.bonusStats = setBonusStats();
    }

    // MODIFIES: this
    // EFFECTS: sets the correct values in the bonusStats field
    protected IntegerMatrix setBonusStats() {
        return new IntegerMatrix(new int[][]{{0,0,0,0,0,0,0},{0,0,2,4,2,0,0}});
    }

}
