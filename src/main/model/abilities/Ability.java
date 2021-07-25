package model.abilities;

import model.IntegerMatrix;

// TODO determine if Ability class must be modified to work with BattleEffects / how is damage computed.

/**
 * Abstract Ability class for all the abilities that can be used / passively active in combat. The Ability class has a
 * name, description, and statsEffect which gives mana cost and any other stat effects of the ability.
 */

public abstract class Ability {
    protected String name;
    protected String description;
    protected IntegerMatrix statsEffect;
    protected int fstCost;
    protected int turnDuration = 0;
    protected boolean applyEveryTurn = false;

    // EFFECT: sets the correct statsEffect for the
    protected abstract void setStatsEffect();

    // EFFECT: returns the name of the ability
    public String name() {
        return name;
    }

    // EFFECT: returns the description of the ability
    public String getDescription() {
        return description;
    }

    // EFFECT: returns the statsEffect of the Ability. Note that damage is negative Hp
    public IntegerMatrix getStatsEffect() {
        return statsEffect;
    }

    // TODO update fstCost with combatAction i think
    // EFFECT: returns the fstCost to use the ability in combat
    public int fstCost() {
        return fstCost;
    }

    // EFFECT: returns the number of turns the statsEffect should be applied for
    public int getTurnDuration() {
        return turnDuration;
    }

    // EFFECT: returns true if the statsEffect should be reapplied every turn, or just exist.
    public boolean isApplyEveryTurn() {
        return applyEveryTurn;
    }
}
