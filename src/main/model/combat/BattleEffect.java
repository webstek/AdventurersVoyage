package model.combat;

import model.Statistics;
import model.entities.*;
import model.abilities.*;
import java.util.ArrayList;

/**
 * Representation of all effects in combat. BattleEffects are created in the do() method of the entity class and are
 * passed to the Battle class which applies the effects to the appropriate entities in battle for the correct duration.
 */

public class BattleEffect {
    private Statistics statsEffect;
    private String name;
    private int turnsRemaining;
    private int initDamage;
    private int damagePerTurn;
    private int manaCost;
    private int combatActionCost;
    private ArrayList<Entity> targets;
    private Entity user;
    private boolean applyAtEndOfActionPhase = true;
    private boolean applied = false;

    // EFFECT: sets all the fields of the BattleEffects according to the ability, targets, and user parameters
    public BattleEffect(Ability ability, ArrayList<Entity> targets, Entity user, boolean actionPhase) {
        this.applyAtEndOfActionPhase = actionPhase;
        this.targets = targets;
        this.user = user;
        this.statsEffect = ability.getStatsEffect();
        this.name = ability.name();
        this.turnsRemaining = ability.getTurnDuration();
        this.damagePerTurn = ability.damagePerTurn();
        this.initDamage = ability.getStatsEffect().damage();
        this.manaCost = ability.getStatsEffect().in(0,6);
        this.combatActionCost = ability.caCost();
    }

    // MODIFIES: this
    // EFFECTS: sets the applyDuringActionPhase field.
    public void setApplyAtEndOfActionPhase(boolean bool) {
        this.applyAtEndOfActionPhase = bool;
    }

    // MODIFIES: user, targets;
    // EFFECT: applies the statsEffect values to the targets and user appropriately. Note the hp and mp fields are
    //         applied to the user, and the intl, str, ... , damage, manaBurn stats are applied to targets. Returns
    //         a string of what occurred during the applying.
    public String apply() {
        StringBuilder happened = new StringBuilder();
        applyAbilityCost();
        for (Entity target : targets) {
            if (statsEffect.damage() > 0) {
                happened.append(target.name()).append(" took ").append(initDamage)
                        .append(" from ").append(name).append("! ");
                target.stats().sub(0,5,initDamage);
                statsEffect.clear(1,5);
            }
            if (turnsRemaining > 0) {
                target.stats().sub(0,5,damagePerTurn);
                happened.append(target.name()).append(" took ").append(damagePerTurn)
                        .append(" from ").append(name).append("! ");
            }
            if (!applied) {
                target.stats().add(statsEffect);
                happened.append(target.name()).append(" had their stats reduced! ");
                applied = true;
            }
        }
        return happened.toString();
    }

    // MODIFIES: player
    // EFFECTS: applies the Ability costs to the user
    private void applyAbilityCost() {
        if (applyAtEndOfActionPhase) {
            user.stats().add(0, 6, manaCost);
            statsEffect.clear(0, 6);
            user.useCombatActions(combatActionCost);
            applyAtEndOfActionPhase = false;
        }
    }

    // REQUIRES: turnsRemaining == 0, applied == true
    // MODIFIES: targets
    // EFFECTS: reverts the status effects of the BattleEffect
    public void remove() {
        for (Entity target : targets) {
            target.stats().sub(statsEffect);
            applied = false;
        }
    }

    // MODIFIES: this
    // EFFECTS: decrements the turnsRemaining field
    public void decrementTurnsRemaining() {
        turnsRemaining--;
    }

    // EFFECTS: returns the turnsRemaining field
    public int getTurnsRemaining() {
        return turnsRemaining;
    }
}
