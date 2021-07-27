package model.entities;

import model.abilities.Ability;
import model.exceptions.InsufficientResourceException;

import java.util.ArrayList;

/**
 * Abstract class to give all enemies an ability list and to ensure all enemies have the required
 * methods to interact with in combat.
 */
public abstract class Enemy extends Entity {
    // EFFECTS: returns the string that gives a detailed explanation of the enemy
    public abstract String getDescription();

    // MODIFIES: this
    // EFFECTS: changes the hostility field in entity to the parameter value
    public void setHostility(Boolean bool) {
        this.isHostile = bool;
    }

    // EFFECTS: returns the Ability class corresponding to the position in the abilities list.
    public Ability getLastUsableAbility() throws InsufficientResourceException {
        Ability lastUsableAbility = null;
        for (Ability ability : abilities) {
            try {
                if (areRequirementsMetForAbility(ability)) {
                    lastUsableAbility = ability;
                }
            } catch (InsufficientResourceException e) {
                System.out.println(e.getMessage());
            }
        }
        if (lastUsableAbility == null) {
            this.combatActions = 0;
            throw new InsufficientResourceException(name + " can't use any abilities!");
        }
        return lastUsableAbility;
    }
}
