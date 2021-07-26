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
        while (info.isEnemyToFight()) {
            info.startTurn();
            Entity entityOnTurn = info.getMostCombatActionsEntity();
            while (info.isTurnOngoing()) {
                if (!entityOnTurn.hostility()) {
                    System.out.println("It's your time to act! Choose something to do!");
                    displayAbilities(entityOnTurn.getProfession(),false);
                    //TODO make option to end actionPhase with combatActions remaining, make a print for this option
                    System.out.println("So which ability will you choose?");
                    tryUsingAbility(entityOnTurn);
                    info.endActionPhase();
                } else {
                    System.out.println("Enemy Battle Turn");
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Queries the user for an ability to use and handles invalid inputs. Calls the info.uses() method to
    //          update the effectsToApply
    private void tryUsingAbility(Entity entity) {
        try {
            Ability chosenAbility = getAbilityString(entity);
            info.uses(entity,chosenAbility,getTargetsList(chosenAbility));
        } catch (InsufficientResourceException e) {
            tryUsingAbility(entity);
        }
    }

    private Ability getAbilityString(Entity entity) {
        Ability chosenAbility = null;
        try {
            chosenAbility = entity.getAbilityFromString(sc.next());
        } catch (UserInputException e) {
            getAbilityString(entity);
        }
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
            target = (getTargetFromString(getInput("Select target [" + targetNumber + "]")));
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
}