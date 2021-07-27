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
    private ArrayList<BattleEffect> effectsToRemove = new ArrayList<>();
    private ArrayList<Entity> enemiesInCombat;
    private ArrayList<Entity> entitiesInCombat = new ArrayList<>();
    private Player playerInCombat;
    private boolean isInCombat = true;

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
        effectsToRemove.add(btlEff);
    }

    // EFFECTS: checks to determine if there are any enemies in the enemies ArrayList
    public void isStillInCombat() {
        isInCombat = enemiesInCombat.size() > 0;
    }

    // EFFECTS: returns the Entity (player or enemy) with the largest value of the combatActions field.
    public Entity getHighestCombatActionsEntity() {
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
        return user.parse(ability.clone(),targets,actionPhase);
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
        String phaseDescription = "\n" + applyAllEffects() + "\n";
        removeDeadEnemies();
        return phaseDescription;
    }

    // MODIFIES: this, player, enemies
    // EFFECTS: removes dead enemies and gives the player their xp and items. Calls isStillInCombat after updating
    //          enemiesInCombat.
    private void removeDeadEnemies() {
        ArrayList<Entity> deadEnemies = getAllEnemiesDead();
        if (deadEnemies.size() > 0) {
            for (Entity enemy : deadEnemies) {
                enemiesInCombat.remove(enemy);
            }
        }
        isStillInCombat();
    }

    // MODIFIES: this
    // EFFECTS: checks enemies' Hp to determine if any have died (hp <= 0). If so, adds them to a list which is returned
    //          and gives the player the dead enemy's items and xp.
    public ArrayList<Entity> getAllEnemiesDead() {
        ArrayList<Entity> deadEnemies = new ArrayList<>();
        for (Entity enemy : enemiesInCombat) {
            if (enemy.isDead()) {
                collectDeadEnemyResources((Enemy) enemy);
                deadEnemies.add(enemy);
            }
        }
        return deadEnemies;
    }

    // REQUIRES: enemy hp to be <= 0
    // MODIFIES: player
    // EFFECTS: gives the player the dead enemy's items and xp.
    private void collectDeadEnemyResources(Enemy enemy) {
        for (int i = 1; i < enemy.getInventory().length(); i++) {
            Item itemToTransfer = enemy.getInventory().inPos(i);
            playerInCombat.addToInventory(itemToTransfer);
            enemy.dropFromInventory(itemToTransfer);
        }
        playerInCombat.receiveXp(enemy.xp());
    }

    // MODIFIES: player, enemies, this
    // EFFECTS: applies all the BattleEffects in effectsToApply and returns a string that describes what happened when
    //          BattleEffects were applied.
    private String applyAllEffects() {
        StringBuilder applySummary = new StringBuilder();
        for (BattleEffect btlEff : effectsToApply) {
            applySummary.append(btlEff.apply());
        }
        return applySummary.toString();
    }

    // EFFECTS: adds the BattleEffect of the Enemy's first available ability and returns "" if this works, or returns a
    //          message indicating the enemy can't use any ability, setting it's combatActions to zero.
    public void getEnemyBattleEffects(Enemy enemy) {
        try {
            Ability chosenAbility = enemy.getLastUsableAbility().clone();
            ArrayList<Entity> players = new ArrayList<>();
            players.add(playerInCombat);
            addEffect(enemy.parse(chosenAbility,players,true));
        } catch (InsufficientResourceException e) {
            System.out.println(e.getMessage());
        }
    }

    // MODIFIES: this, targets, player
    // EFFECTS: checks if any BattleEffects have expired and removes them from players and effectsToApply if so.
    //          Leveling up is handled by LevelUpHandler class in the ui package.
    public void endTurn() {
        for (BattleEffect btlEff : effectsToRemove) {
            if (btlEff.getTurnsRemaining() == 0) {
                btlEff.remove();
                effectsToApply.remove(btlEff);
            } else {
                btlEff.decrementTurnsRemaining();
            }
        }
    }

    // EFFECTS: checks to see if combat should have ended or not.
    public boolean isInCombat() {
        return isInCombat;
    }

    // EFFECTS: returns the effectsToApply array list
    public ArrayList<BattleEffect> getEffectsToApply() {
        return effectsToApply;
    }

    // EFFECTS: returns the effectsToRemove array list
    public ArrayList<BattleEffect> getEffectsToRemove() {
        return effectsToRemove;
    }
}