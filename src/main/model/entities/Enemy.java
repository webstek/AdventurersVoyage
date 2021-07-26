package model.entities;

import model.abilities.Ability;
import model.exceptions.UserInputException;

import java.util.ArrayList;

/**
 * Abstract class to give all enemies an ability list and to ensure all enemies have the required
 * methods to interact with in combat.
 */
public abstract class Enemy extends Entity {
    protected ArrayList<Ability> abilities = new ArrayList<>();

    // MODIFIES: this
    // EFFECTS: adds the unique Abilities of the enemy to the abilities list;
    protected abstract void setAbilities();

    // EFFECTS: returns the string that gives a detailed explanation of the enemy
    public abstract String getDescription();

    // MODIFIES: this
    // EFFECTS: changes the hostility field in entity to the parameter value
    public void setHostility(Boolean bool) {
        this.isHostile = bool;
    }

    // EFFECTS: returns the Ability class corresponding to the parameter string, throws UserInputException if the
    //          ability is not available to the player.
    public Ability getAbilityFromString(String abilityName) throws UserInputException {
        ArrayList<Ability> allAbilities = new ArrayList<>(this.abilities);
        if (this.profession != null) {
            allAbilities.addAll(this.profession.getAbilities());
        }
        for (Ability ability : allAbilities) {
            if (abilityName.equalsIgnoreCase(ability.name())) {
                return ability;
            }
        }
        throw new UserInputException("Please enter an ability you have.");
    }
}
