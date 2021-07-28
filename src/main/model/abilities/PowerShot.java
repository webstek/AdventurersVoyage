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

    // EFFECTS: takes a powershot object and produces a clone of it
    public PowerShot(PowerShot ability) {
        this.name = ability.name;
        this.description = ability.description;
        this.combatAction = ability.combatAction;
        this.statsEffect = ability.statsEffect.clone();
    }

    // MODIFIES: this
    // EFFECT: sets the damage of the ability based on the entity that is using it
    public void refreshAbility(Entity entity) {
        getEntityStats(entity);
        int damage = (entityStats.damage() + entityStats.getStat(3) + (entity.level() / 2) + 10);
        // Note the d-20 roll is replaced by 10 damage as randomness is not implemented yet.
        statsEffect.set(1,5,damage);
    }

    // EFFECTS: returns a clone of the Ability the method is called on
    public Ability clone() {
        return new PowerShot(this);
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
