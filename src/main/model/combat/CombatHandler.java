package model.combat;

import model.GameState;
import model.UsesUserInput;
import model.abilities.Ability;
import model.entities.*;
import model.exceptions.InsufficientResourceException;
import model.exceptions.UserInputException;
import ui.CommonUI;


import java.util.ArrayList;
import java.util.Collections;

/**
 * Called when a battle is necessary in the Story. Sends the appropriate response string for the input string
 */

public class CombatHandler extends CommonUI implements UsesUserInput {
    private GameState gs;
    private final Player player;
    private final ArrayList<Entity> battleEnemies = new ArrayList<>();
    private final Battle battle;

    // EFFECTS: set fields and calls the main method for doing battle
    public CombatHandler(GameState gs, Enemy[] enemies) {
        this.gs = gs;
        this.player = gs.player();
        Collections.addAll(battleEnemies, enemies);
        this.battle = new Battle(player, battleEnemies);
    }

    // MODIFIES: this, gs
    // EFFECTS: handles the actionText inputted by the user by invoking the correct methods and setting the displayText
    public void handleCombatIO() {
        if (battle.isInCombat()) {
            if (!battle.isNewTurn()) {
                gs.appendToDisplayText(battle.startTurn());
                battle.setNewTurn(false);
            } else if (battle.isTurnOngoing()) {
                handleCombatTurn();
            } else {
                battle.setNewTurn(true);
                battle.endTurn();
            }
        } else {
            battle.endBattle();
            player.setInCombat(false);
            gs.completeAnAdventure();
            gs.handleState();
        }
    }

    // MODIFIES: gs, player, enemies
    // EFFECTS: handles the actionText inputted by the user for each turn of battle
    private void handleCombatTurn() {
        Entity entityWithInitiative = battle.getHighestCombatActionsEntity();
        entityWithInitiative.refreshAbilities();
        if (entityWithInitiative instanceof Player) {
            if (gs.hasUnusedUserInput()) {
                applyUserSelectedAbility();
                consumedUserInput();
                handleCombatTurn();
            } else {
                gs.setHasUnusedUserInput(true);
            }
        } else {
            battle.getEnemyBattleEffects((Enemy) entityWithInitiative);
        }
        gs.appendToDisplayText(battle.endActionPhase());
    }

    // MODIFIES: this
    // EFFECTS: main method for displaying what is happening in a battle
    private void doCombat() {
        while (battle.isInCombat()) {
            System.out.println(battle.startTurn());
            while (battle.isTurnOngoing() && battle.isInCombat()) {
                Entity entityWithInitiative = battle.getHighestCombatActionsEntity();
                inCombatSummary();
                entityWithInitiative.refreshAbilities();
                if (!entityWithInitiative.hostility()) {
                    System.out.println("\nYou have the initiative! Choose something to do!");
                    System.out.println(displayAbilities(entityWithInitiative.abilities(),false,true));
                    //TODO make option to end actionPhase with combatActions remaining, make a print for this option
                    System.out.println("Select an ability to use: ");
                    applyUserSelectedAbility();
                } else {
                    System.out.println("\n" + entityWithInitiative.name() + " has the initiative!\n");
                    battle.getEnemyBattleEffects((Enemy) entityWithInitiative);
                }
                System.out.println(battle.endActionPhase());
            }
            battle.endTurn();
        }
        battle.endBattle();
    }

    // EFFECTS: prints the battle complete message and displays the players stats and inventory.
    private void postCombatSummary() {
        System.out.println(" -------------- Battle complete! -------------- ");
        System.out.println("\n Your health and mana have been replenished.\n" + displayStats(player.stats(),true));
        System.out.println(displayInventory(player.getInventory(), true));
    }

    // MODIFIES: this, effectsToApply
    // EFFECTS: Queries the user for an ability to use and handles invalid inputs. Calls the info.uses() method to
    //          update the effectsToApply
    private void applyUserSelectedAbility() {
        try {
            Ability chosenAbility = chooseAbility(player);
            ArrayList<Entity> chosenTargets = new ArrayList<>();
            if (battleEnemies.size() == 1) {
                chosenTargets.add(battleEnemies.get(0));
            } else {
                chosenTargets = getTargetsList(chosenAbility);
            }
            battle.addEffect(battle.uses(player,chosenAbility,chosenTargets,true));
        } catch (InsufficientResourceException | UserInputException e) {
            gs.setDisplayText(e.getMessage());
        }
    }

    // MODIFIES: this
    // EFFECTS: gets an input from the user and selects the associated ability if the string corresponds.
    private Ability chooseAbility(Entity entity) throws UserInputException {
        Ability chosenAbility;
        chosenAbility = entity.getAbilityFromString(gs.getActionText());
        return chosenAbility;
    }

    private ArrayList<Entity> getTargetsList(Ability ability) {
        System.out.println(displayEntityArrayList(battleEnemies));
        ArrayList<Entity> targets = new ArrayList<>();
        int targetCounter = ability.numTargets(); // -1 indicates self-use
        if (targetCounter == -1) {
            targets.add(player);
            return targets;
        }

        for (int i = 1; i <= targetCounter; i++) {
            targets.add(getTarget(i));
        }
        return targets;
    }

    private Entity getTarget(int targetNumber) {
        Entity target = null;
        try {
            target = (getTargetFromString(getFullInput("| Select target number [" + targetNumber + "]")));
        } catch (UserInputException e) {
            getTarget(targetNumber);
        }
        return target;
    }

    private Entity getTargetFromString(String input) throws UserInputException {
        for (Entity entity : battleEnemies) {
            if (input.equalsIgnoreCase(entity.name())) {
                return entity;
            }
        }
        throw new UserInputException("Please enter an actual target.");
    }

    // EFFECTS: returns a formatted string of the current Hp and Mana of all entities in combat
    public void inCombatSummary() {
        StringBuilder combatSummary = new StringBuilder("| --------------------------- |");
        combatSummary.append("\n| ").append(player.name())
                .append("\n|      Health: ").append(player.stats().in(0,5))
                .append("\n|        Mana: ").append(player.stats().in(0,6))
                .append("\n|   CmbtActns: ").append(player.getCombatActions())
                .append("\n| --------------------------- |");
        for (Entity enemy : battleEnemies) {
            combatSummary.append("\n| ").append(enemy.name())
                    .append("\n|      Health: ").append(enemy.stats().in(0,5))
                    .append("\n|        Mana: ").append(enemy.stats().in(0,6))
                    .append("\n|   CmbtActns: ").append(enemy.getCombatActions())
                    .append("\n| --------------------------- |");
        }
        System.out.println(combatSummary);
    }

    @Override
    public void consumedUserInput() {
        gs.setHasUnusedUserInput(false);
    }
}