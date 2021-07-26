package model.combat;

import model.abilities.Ability;
import model.entities.*;
import model.exceptions.InsufficientResourceException;

import java.util.ArrayList;

// TODO Complete all methods and method specifiers

/**
 * Class representing a battle happening. This class has the methods to apply BattleEffects and determine turn order.
 * Also handles rewards that are dropped from enemies. .........
 */

public class Battle {
    private int turn = 0;
    private ArrayList<BattleEffect> effectsToApply = new ArrayList<>();
    private ArrayList<Entity> enemiesInCombat = new ArrayList<>();
    private ArrayList<Entity> entitiesInCombat = new ArrayList<>();
    private Player playerInCombat;

    // EFFECTS: sets the playerInCombat and Enemies fields
    public Battle(Player player, ArrayList<Entity> enemies) {
        this.playerInCombat = player;
        this.enemiesInCombat = enemies;
        this.entitiesInCombat.add(playerInCombat);
        this.entitiesInCombat.addAll(enemiesInCombat);
    }

    // MODIFIES: this
    // EFFECTS: appends the parameter btlEff to the effectsToApply list.
    public void addEffect(BattleEffect btlEff) {
        effectsToApply.add(btlEff);
    }

    // REQUIRES: btlEff must already be on effectsToApply
    // MODIFIES: this
    // EFFECTS: removes btlEff from the effectsToApply list
    public void subEffect(BattleEffect btlEff) {
        effectsToApply.remove(btlEff);
    }

    // EFFECTS: checks to determine if there are any enemies in the enemies ArrayList
    public boolean isEnemyToFight() {
        return enemiesInCombat.size() > 0;
    }

    // EFFECTS: returns the Entity (player or enemy) with the largest value of the combatActions field.
    public Entity getMostCombatActionsEntity() {
        Entity mostCombatActionsEntity = playerInCombat; // Entity accumulator
        for (Entity entity : enemiesInCombat) {
            if (entity.getCombatActions() > mostCombatActionsEntity.getCombatActions()) {
                mostCombatActionsEntity = entity;
            }
        }
        return mostCombatActionsEntity;
    }

    // EFFECTS: checks to determine if any entities have combatActions left.
    public boolean isTurnOngoing() {
        int numEntitiesWithActions = 0;
        for (Entity entity : entitiesInCombat) {
            if (entity.canAct()) {
                numEntitiesWithActions++;
            }
        }
        return numEntitiesWithActions > 0;
    }

    // MODIFIES: this, player, enemies
    // EFFECTS: checks if the user has enough resources to use ability on targets. adds a BattleEffect to effectsToApply
    //          that correctly represents the ability's effects.
    public void uses(Entity user, Ability ability, ArrayList<Entity> targets) throws InsufficientResourceException {

    }

    // MODIFIES: this, player, enemies
    // EFFECTS: appropriately applies all BattleEffects in effectsToApply and sets the combatActions of all entities
    //          in battle. Checks if any enemies are dead and removes them from enemies if so.
    public void startTurn() {
    }

    // MODIFIES: this, player, enemies
    // EFFECTS: applies damage from effects with applyAtEndOfActionPhase set to true, then sets the value to false if
    //          there are persisting effects, or removes it from effectsToApply if not. Checks if any enemies are dead
    //          and removes them from enemies if so.
    public void endActionPhase() {
    }

    // MODIFIES: this
    // EFFECTS: checks enemies' Hp to determine if any have died (hp <= 0). If so, removes them from enemies.
    public void checkAllEnemiesAlive(ArrayList<Entity> enemies) {
    }

    // MODIFIES:


}
