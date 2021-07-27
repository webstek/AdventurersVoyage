package model.abilities;

import model.Statistics;
import model.entities.*;


/**
 * Abstract Ability class for all the abilities that can be used / passively active in combat. The Ability class has a
 * name, description, and statsEffect which gives mana cost and any other stat effects of the ability.
 */

public abstract class Ability implements Cloneable {
    protected String name;
    protected String description;
    protected Statistics statsEffect;
    protected Statistics entityStats;
    protected int combatAction;
    protected int numTargets = 1;
    protected int turnDuration = 0;
    protected boolean applyEveryTurn = false;
    protected int perTurnDamage = 0;

    // EFFECT: returns a copy of the ability the method is called on by calling the clone constructor.
    public abstract Ability clone();

    // MODIFIES: this
    // EFFECT: sets the damage of the ability based on the entity that is using it
    public abstract void setDamage(Entity entity);

    // MODIFIES: this
    // EFFECT: sets the correct statsEffect for the ability, except for the damage stat, must be set separately
    protected abstract void setStatsEffect();

    // MODIFIES: this
    // EFFECT: gets the current stats of the entity class handed to it
    protected abstract void getEntityStats(Entity entity);

    // EFFECT: returns the name of the ability
    public String name() {
        return name;
    }

    // EFFECT: returns the description of the ability
    public String getDescription() {
        return description;
    }

    // EFFECT: returns the statsEffect of the Ability. Note that damage is negative Hp
    public Statistics getStatsEffect() {
        return statsEffect;
    }

    // EFFECT: returns the fstCost to use the ability in combat
    public int caCost() {
        return combatAction;
    }

    // EFFECT: returns the number of turns the statsEffect should be applied for
    public int getTurnDuration() {
        return turnDuration;
    }

    // EFFECT: returns true if the statsEffect should be reapplied every turn, or just exist.
    public boolean isApplyEveryTurn() {
        return applyEveryTurn;
    }

    // EFFECT: returns the numTargets field
    public int numTargets() {
        return numTargets;
    }

    // EFFECT: returns the perTurnDamage field
    public int damagePerTurn() {
        return perTurnDamage;
    }
}
