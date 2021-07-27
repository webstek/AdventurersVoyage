package model.abilities;

import model.Statistics;
import model.entities.Entity;

/**
 * Wait ability. Sets the user's combatActions to zero for the turn.
 */

public class Wait extends Ability {
    // EFFECTS: sets the name, description, combatAction, and statsEffect quantities, including the correct damage
    //          based on the entity stats passed through the parameter
    public Wait(Entity entity) {
        this.name = "Wait";
        this.description = "Set your combat actions to zero for this turn.";
        this.combatAction = entity.getCombatActions();
        setStatsEffect();
        getEntityStats(entity);
    }

    // EFFECTS: takes a powershot object and produces a clone of it
    public Wait(Wait ability) {
        this.name = ability.name;
        this.description = ability.description;
        this.combatAction = ability.combatAction;
        this.statsEffect = ability.statsEffect.clone();
        this.entityStats = ability.entityStats;
    }

    // MODIFIES: this
    // EFFECT: sets the cost of the combatAction of wait to be the current combatAction of the using entity.
    public void refreshAbility(Entity entity) {
        this.combatAction = entity.getCombatActions();
    }

    // EFFECTS: returns a clone of the Ability the method is called on
    public Ability clone() {
        return new Wait(this);
    }

    // MODIFIES: this
    // EFFECTS: sets the Wait stats effects.
    protected void setStatsEffect() {
        statsEffect = new Statistics(new int[][]{{0,0,0,0,0,0,0},{0,0,0,0,0,0,0}});
    }

    // MODIFIES: this
    // EFFECTS: sets the entityStats field with current stats of the entity object passed into it
    protected void getEntityStats(Entity entity) {
        entityStats = entity.stats();
    }
}
