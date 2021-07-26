package model.combat;

import model.abilities.Ability;
import model.entities.*;
import model.exceptions.InsufficientResourceException;
import model.items.Item;

import java.util.ArrayList;

// TODO Complete all methods and method specifiers

/**
 * Class representing a battle happening. This class has the methods to apply BattleEffects and determine turn order.
 * Also handles rewards that are dropped from enemies. .........
 */

public class Battle {
    private int turn = 1;
    private ArrayList<BattleEffect> effectsToApply = new ArrayList<>();
    private ArrayList<Entity> enemiesInCombat = new ArrayList<>();
    private ArrayList<Entity> entitiesInCombat = new ArrayList<>();
    private Player playerInCombat;

    // MODIFIES: player, enemies
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

    // EFFECTS: checks to determine if any entities have enough combat actions to use any abilities
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
    public BattleEffect uses(Entity user, Ability ability, ArrayList<Entity> targets, boolean actionPhase)
            throws InsufficientResourceException {
        user.areRequirementsMetForAbility(ability);
        return user.parse(ability,targets,actionPhase);
    }

    // MODIFIES: this, player, enemies
    // EFFECTS: appropriately applies all BattleEffects in effectsToApply and sets the combatActions of all entities
    //          in battle. Checks if any enemies are dead and removes them from enemies if so.
    public String startTurn() {
        StringBuilder startTurnSummary = new StringBuilder();
        startTurnSummary.append("Turn [").append(turn++).append("]");
        for (Entity entity : entitiesInCombat) {
            entity.resetCombatActions();
        }
        startTurnSummary.append(applyAllEffects());
        return startTurnSummary.toString();
    }

    // MODIFIES: this, player, enemies
    // EFFECTS: applies damage from effects with applyAtEndOfActionPhase set to true, then sets the value to false if
    //          there are persisting effects, or removes it from effectsToApply if not. Checks if any enemies are dead
    //          and if so, gives player their items and xp, then removes them from enemies.
    public String endActionPhase() {
        return applyAllEffects();
    }

    // MODIFIES: this
    // EFFECTS: checks enemies' Hp to determine if any have died (hp <= 0). If so, removes them from enemies and gives
    //          the player the dead enemy's items and xp.
    public void checkAllEnemiesAlive() {
        for (Entity enemy : enemiesInCombat) {
            collectDeadEnemyResources((Enemy) enemy);
        }
    }

    // REQUIRES: enemy hp to be <= 0
    // MODIFIES: player
    // EFFECTS: gives the player the dead enemy's items and xp.
    private void collectDeadEnemyResources(Enemy enemy) {
        if (enemy.isDead()) {
            for (int i = 1; i < enemy.getInventory().length(); i++) {
                Item itemToTransfer = enemy.getInventory().inPos(i);
                playerInCombat.addToInventory(itemToTransfer);
                enemy.dropFromInventory(itemToTransfer);
            }
            playerInCombat.receiveXp(enemy.xp());
        }
    }

    // MODIFIES: player, enemies, this
    // EFFECTS: applies all the BattleEffects in effectsToApply and returns a string that describes what happened when
    //          BattleEffects were applied.
    private String applyAllEffects() {
        StringBuilder applySummary = new StringBuilder();
        for (BattleEffect btlEff : effectsToApply) {
            applySummary.append(btlEff.apply()).append("\n");
        }
        return applySummary.toString();
    }

    // EFFECTS: adds the BattleEffect of the Enemy's first available ability and returns "" if this works, or returns a
    //          message indicating the enemy can't use any ability, setting it's combatActions to zero.
    public String getEnemyBattleEffects(Enemy enemy) {
        try {
            Ability chosenAbility = enemy.getFirstUsableAbility();
            ArrayList<Entity> players = new ArrayList<>();
            players.add(playerInCombat);
            if (enemy.areRequirementsMetForAbility(chosenAbility)) {
                addEffect(enemy.parse(chosenAbility,players,true));
            }

        } catch (InsufficientResourceException e) {
            return enemy.name() + "can't use any abilities!";
        }
        return "";
    }

    // MODIFIES: this, targets, player
    // EFFECTS: checks if any BattleEffects have expired and removes them from players and effectsToApply if so.
    public void endTurn() {
        for (BattleEffect btlEff : effectsToApply) {
            if (btlEff.getTurnsRemaining() == 0) {
                btlEff.remove();
                effectsToApply.remove(btlEff);
            } else {
                btlEff.decrementTurnsRemaining();
            }
        }
    }
}