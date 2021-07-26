package model.abilities;

import model.Statistics;
import model.entities.Entity;

public class Slap extends Ability {
    // EFFECTS: sets the name, description, combatAction, and statsEffect quantities, including the correct damage
    //          based on the entity stats passed through the parameter
    public Slap(Entity entity) {
        this.name = "Slap";
        this.description = "Give the opponent a big ol' slap dealing damage equal to 2x strength.";
        this.combatAction = 1;
        setStatsEffect();
        getEntityStats(entity);
        int damage = entityStats.getStat(1);
        statsEffect.add(0,6,damage);
    }


    // MODIFIES: this
    // EFFECTS: sets the Slap stats effects. Note the damage field is empty as damage is added in the constructor
    protected void setStatsEffect() {
        this.statsEffect = new Statistics(new int[][]{{0,0,0,0,0,0,0},{0,0,0,0,0,0,0}});
    }

    // MODIFIES: this
    // EFFECTS: sets the entityStats field with current stats of the entity object passed into it
    protected void getEntityStats(Entity entity) {
        this.entityStats = entity.stats();
    }
}
