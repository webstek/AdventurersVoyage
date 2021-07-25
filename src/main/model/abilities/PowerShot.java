package model.abilities;

import model.IntegerMatrix;

public class PowerShot extends Ability {
    public PowerShot() {
        this.name = "Power Shot";
        this.description = "Shoot an arrow and deal damage equal to (weapon dmg + dex + level/2 + d-20 roll)";
        this.fstCost = 3;
        setStatsEffect();
    }

    // MODIFIES: this
    // EFFECTS: sets the Power Shot stats effects. Note the negative Mp indicating mana cost
    protected void setStatsEffect() {
        this.statsEffect = new IntegerMatrix(new int[][]{{0,0,0,0,0,0,-30},{0,0,0,0,0,10,0}});
        // I have substituted the d-20 roll with 10 base damage as I have not implemented randomness.
    }
}
