package model.abilities;

import model.Statistics;
import model.entities.*;

public class PowerShot extends Ability {
    // EFFECTS: sets the name, description, combatAction, and player independent statEffects, excluding damage.
    //          Used in character creation to display the information about the ability.
    public PowerShot() {
        this.name = "Power Shot";
        this.description = "Shoot an arrow and deal damage equal to (weapon dmg + dex + level/2 + d-20 roll)";
        this.combatAction = 3;
        setStatsEffect();
    }

    // EFFECTS: sets the name, description, combatAction, and statsEffect quantities, including the correct damage
    //          based on the entity stats passed through the parameter
    public PowerShot(Entity entity) {
        this.name = "Power Shot";
        this.description = "Shoot an arrow and deal damage equal to (weapon dmg + dex + level/2 + d-20 roll)";
        this.combatAction = 3;
        setStatsEffect();
        getEntityStats(entity);
        int damage = entityStats.damage() + entityStats.getStat(3) + (entity.level() / 2) + 10;
        // Note the d-20 roll is replaced by 10 damage as randomness is not implemented yet.
        statsEffect.add(0,6,damage);
    }


    // MODIFIES: this
    // EFFECTS: sets the Power Shot stats effects. Note the negative Mp indicating mana cost
    protected void setStatsEffect() {
        this.statsEffect = new Statistics(new int[][]{{0,0,0,0,0,0,-50},{0,0,0,0,0,0,0}});
        // Note the dmg field is 0 by default
    }

    // MODIFIES: this
    // EFFECTS: sets the entityStats field with current stats of the entity object passed into it
    protected void getEntityStats(Entity entity) {
        this.entityStats = entity.stats();
    }
}
