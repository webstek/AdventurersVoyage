package model.abilities;

import model.Statistics;
import model.entities.Entity;

public class Slam extends Ability {
    // EFFECTS: sets the name, description, combatAction, and player independent statEffects, excluding damage.
    //          Used in character creation to display the information about the ability.
    public Slam() {
        this.name = "Slam";
        this.description = "Drops the enemy and slams it to the ground, dealing damage equal to 40 + strength + lvl";
        this.combatAction = 4;
        setStatsEffect();
    }

    // EFFECTS: takes a Slam object and produces a clone of it
    public Slam(Slam ability) {
        this.name = ability.name;
        this.description = ability.description;
        this.combatAction = ability.combatAction;
        this.statsEffect = ability.statsEffect.clone();
    }

    // MODIFIES: this
    // EFFECT: sets the damage of the ability based on the entity that is using it
    public void refreshAbility(Entity entity) {
        getEntityStats(entity);
        int damage = entityStats.getStat(1) + entity.level() + 40;
        statsEffect.set(1,5,damage);
    }

    // EFFECTS: returns a clone of the Ability the method is called on
    public Ability clone() {
        return new Slam(this);
    }

    // MODIFIES: this
    // EFFECTS: sets the Power Shot stats effects. Note the negative Mp indicating mana cost
    protected void setStatsEffect() {
        this.statsEffect = new Statistics(new int[][]{{0,0,0,0,0,0,0},{0,0,0,0,0,0,0}});
        // Note the dmg field is 0 by default
    }

    // MODIFIES: this
    // EFFECTS: sets the entityStats field with current stats of the entity object passed into it
    protected void getEntityStats(Entity entity) {
        this.entityStats = entity.stats();
    }
}
