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
    private int turnsRemaining;
    private ArrayList<Entity> targets;
    private Entity user;
    private boolean applyAtEndOfActionPhase = true;

    // EFFECT: sets all the fields of the BattleEffects according to the ability, targets, and user parameters
    public BattleEffect(Ability ability, ArrayList<Entity> targets, Entity user) {
        this.statsEffect = ability.getStatsEffect();
        this.turnsRemaining = ability.getTurnDuration();
        this.targets = targets;
        this.user = user;
    }

    // MODIFIES: this
    // EFFECTS: sets the applyDuringActionPhase field.
    public void setApplyAtEndOfActionPhase(boolean bool) {
        this.applyAtEndOfActionPhase = bool;
    }
}
