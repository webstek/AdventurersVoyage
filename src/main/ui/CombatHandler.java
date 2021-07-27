package ui;

import model.abilities.Ability;
import model.combat.*;
import model.entities.*;
import model.exceptions.InsufficientResourceException;
import model.exceptions.UserInputException;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Called when a battle is necessary in the Story. Displays what's
 */

public class CombatHandler extends CommonUI {
    private final Player player;
    private final ArrayList<Entity> battleEnemies = new ArrayList<>();
    private final Battle info;

    // EFFECTS: set fields and calls the main method for doing battle
    public CombatHandler(Player player, Enemy[] enemies, String description) {
        this.player = player;
        Collections.addAll(battleEnemies, enemies);
        System.out.println(description);
        this.info = new Battle(player, battleEnemies);
        enterCombat();
    }

    // MODIFIES: this
    // EFFECTS: main method for displaying what is happening in a battle
    private void enterCombat() {
        System.out.println(" ---------------- Battle start! --------------- ");
        while (info.isInCombat()) {
            System.out.println(info.startTurn());
            Entity entityOnTurn = info.getMostCombatActionsEntity();
            while (info.isTurnOngoing() && info.isInCombat()) {
                if (!entityOnTurn.hostility()) {
                    combatSummary();
                    System.out.println("| It's your time to act! Choose something to do!");
                    System.out.println(displayAbilities(entityOnTurn.abilities(),false,true));
                    //TODO make option to end actionPhase with combatActions remaining, make a print for this option
                    System.out.println("So which ability will you choose?");
                    tryUsingAbility(entityOnTurn);
                    System.out.println(info.endActionPhase());
                } else {
                    combatSummary();
                    System.out.println(info.getEnemyBattleEffects((Enemy) entityOnTurn));
                    System.out.println(info.endActionPhase());
                }
            }
            info.endTurn();
        }
        System.out.println(" -------------- Battle complete! -------------- ");
    }

    // MODIFIES: this, effectsToApply
    // EFFECTS: Queries the user for an ability to use and handles invalid inputs. Calls the info.uses() method to
    //          update the effectsToApply
    private void tryUsingAbility(Entity entity) {
        try {
            Ability chosenAbility = getAbilityString(entity);
            ArrayList<Entity> chosenTargets = new ArrayList<>();
            if (battleEnemies.size() == 1) {
                chosenTargets.add(battleEnemies.get(0));
            } else {
                getTargetsList(chosenAbility);
            }
            info.addEffect(info.uses(entity,chosenAbility,chosenTargets,true));
        } catch (InsufficientResourceException e) {
            tryUsingAbility(entity);
        }
    }

    // MODIFIES: this
    // EFFECTS: gets an input from the user and selects the associated ability if the string corresponds.
    private Ability getAbilityString(Entity entity) {
        Ability chosenAbility = null;
        try {
            chosenAbility = entity.getAbilityFromString(getFullInput(null));
            return chosenAbility;
        } catch (UserInputException e) {
            System.out.println(e.getRequest());
            return getAbilityString(entity);
        }
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
    public void combatSummary() {
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
//        combatSummary.append("\n| --------------------------- |");
        System.out.println(combatSummary);
    }
}