package model.abilities;

import model.Statistics;
import model.entities.Entity;

public class Slap extends Ability {
    // EFFECTS: sets the name, description, combatAction, and statsEffect quantities, including the correct damage
    //          based on the entity stats passed through the parameter
    public Slap(Entity entity) {
        this.name = "Slap";
        this.description = "Give the opponent a big ol' slap dealing damage equal to 3x strength.";
        this.combatAction = 1;
        setStatsEffect();
        getEntityStats(entity);
        int damage = 3 * entityStats.getStat(1);
        statsEffect.add(1,5,damage);
    }

    // EFFECTS: takes a powershot object and produces a clone of it
    public Slap(Slap ability) {
        this.name = ability.name;
        this.description = ability.description;
        this.combatAction = ability.combatAction;
        this.statsEffect = ability.statsEffect.clone();
    }

    // MODIFIES: this
    // EFFECT: sets the damage of the ability based on the entity that is using it
    public void setDamage(Entity entity) {
        getEntityStats(entity);
        int damage = 3 * entityStats.getStat(1);
        statsEffect.add(1,5,damage);
    }

    // EFFECTS: returns a clone of the Ability the method is called on
    public Ability clone() {
        return new Slap(this);
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
