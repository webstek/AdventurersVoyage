package model.combat;

import model.GameState;
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

public class CombatHandler {
    private GameState gs;
    private final Player player;
    private final ArrayList<Entity> battleEnemies = new ArrayList<>();
    private final Battle battle;
    private int turnNumber = 0;

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
            if (battle.isNewTurn()) {
                newBattleInfo("a Cave Slug");
                gs.appendToDisplayText(battle.startTurn());
                battle.setNewTurn(false);
                handleCombatTurn();
            } else if (battle.isTurnOngoing()) {
                handleCombatTurn();
            } else {
                battle.setNewTurn(true);
                battle.endTurn();
                turnNumber++;
                handleCombatIO();
            }
        } else {
            battle.endBattle();
            turnNumber = 0;
            player.setInCombat(false);
            gs.completeAnAdventure();
            gs.handleState();
        }
    }

    // EFFECTS: appends the battle start text and the enemies that appeared to the display text
    private void newBattleInfo(String enemiesAppeared) {
        if (turnNumber == 0) {
            gs.appendToDisplayText(" -------------- Battle Start! -------------- \n\n"
                    + "  Oh no! " + enemiesAppeared + " appeared!\n");
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
                gs.appendToDisplayText(battle.endActionPhase());
                consumedUserInput();
                handleCombatTurn();
            } else {
                gs.appendToDisplayText("\n\nYou have the initiative! Choose an ability to use!\n");
                gs.setHasUnusedUserInput(true);
            }
        } else {
            battle.getEnemyBattleEffects((Enemy) entityWithInitiative);
        }
        gs.appendToDisplayText(battle.endActionPhase());
    }


    // MODIFIES: this, effectsToApply
    // EFFECTS: Queries the user for an ability to use and handles invalid inputs. Calls the info.uses() method to
    //          update the effectsToApply
    private void applyUserSelectedAbility() {
        try {
            Ability chosenAbility = chooseAbility(player);
            ArrayList<Entity> chosenTargets = new ArrayList<>();
            chosenTargets.add(battleEnemies.get(0));
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

    // EFFECTS: returns the enemies the player is in combat with
    public ArrayList<Entity> getEnemies() {
        return battleEnemies;
    }

    // MODIFIES: gs
    // EFFECTS: sets the gs hasUnusedUserInput to false
    public void consumedUserInput() {
        gs.setHasUnusedUserInput(false);
    }
}