package model.entities;

import model.Statistics;
import model.ItemMatrix;
import model.exceptions.UserInputException;
import model.items.*;
import model.professions.*;
import model.races.*;
import model.abilities.*;
import model.combat.BattleEffect;

import java.util.ArrayList;

/**
 * Represents any entity that requires the data that a player would have. Entities have stats, race, profession,
 * inventory, a list of other entities within range, a combatActions integer, experience, and gold.
 */

public abstract class Entity {
    protected Statistics stats = new Statistics();
    protected Race race;
    protected Profession profession;
    protected ItemMatrix inventory = new ItemMatrix(3, 6);
    protected ArrayList<Entity> entitiesInRange;
    protected boolean isHostile = false;
    protected String name;
    protected int combatActions;
    protected int xp = 0;
    protected int level = 0;
    protected int gold = 0;
    protected ArrayList<Ability> abilities = new ArrayList<>();

    // MODIFIES: this
    // EFFECTS: adds all abilities to the abilities list;
    protected abstract void setAbilities();

    // MODIFIES: this
    // EFFECTS: sets the race field of the player by a string argument.
    public void setRace(Race race) {
        for (int i = 0; i < stats.getMaxColumns(); i++) {
            this.stats.clear(0,i);
        }
        this.race = race;
        this.stats.add(race.stats());
    }

    // MODIFIES: this
    // EFFECTS: sets the profession field of the player by a string argument.
    public void setProfession(Profession prof) {
        for (int i = 0; i < stats.getMaxColumns(); i++) {
            this.stats.clear(1,i);
        }
        this.profession = prof;
        this.stats.add(prof.stats());
    }

    // MODIFIES: this
    // EFFECTS: sets the combatActions to the total Speed statistic
    public void resetCombatActions() {
        this.combatActions = this.stats.getStat(2);
    }

    // EFFECTS: compares the combatActions and mana fields to all ability combat Actions costs returning true if there
    //          are abilities that can be used. Otherwise, returns false if no abilities can be used.
    public boolean canAct() {
        boolean[] abilitiesRequirementsState = new boolean[abilities.size()];
        for (int i = 0; i < abilities.size(); i++) {
            abilitiesRequirementsState[i] = areRequirementsMetForAbility(abilities.get(i));
        }
        return !allFalse(abilitiesRequirementsState);
    }

    // EFFECTS: returns true if there is enough mana and combatActions to use the ability
    public boolean areRequirementsMetForAbility(Ability ability) {
        return ability.getStatsEffect().in(0,6) + stats.in(0,6) > 0 && ability.caCost() < combatActions;
    }

    // MODIFIES: this
    // EFFECTS: adds the parameter item to the inventory list and updates the player stats
    public void addToInventory(Item item) {
        this.inventory.add(item);
        this.stats.add(item.stats());
    }

    // REQUIRES: parameter item in the inventory list
    // MODIFIES: this
    // EFFECTS: removes the parameter item from the inventory list and updates the player stats
    public void dropFromInventory(Item item) {
        this.inventory.sub(item);
        this.stats.sub(item.stats());
    }

    // EFFECTS: returns the current stats matrix of the entity
    public Statistics stats() {
        return stats;
    }

    // EFFECTS: returns the ItemMatrix field
    public ItemMatrix getInventory() {
        return inventory;
    }

    // EFFECTS: returns the list of abilities an entity can use
    public ArrayList<Ability> abilities() {
        return abilities;
    }

    // EFFECTS: returns the combatActions field value
    public int getCombatActions() {
        return combatActions;
    }

    // EFFECTS: returns the name string
    public String name() {
        return name;
    }

    // EFFECTS: returns the level of the Entity
    public int level() {
        return level;
    }

    // EFFECTS: returns a BattleEffect constructed from the parameters
    public BattleEffect parse(Ability ability, ArrayList<Entity> targets) {
        return new BattleEffect(ability, targets, this);
    }

    // EFFECTS: returns the Ability class corresponding to the parameter string, throws UserInputException if the
    //          ability is not available to the player.
    public Ability getAbilityFromString(String abilityName) throws UserInputException {
        for (Ability ability : this.profession.getAbilities()) {
            if (abilityName.equalsIgnoreCase(ability.name())) {
                return ability;
            }
        }
        throw new UserInputException("Please enter an ability you have.\n");
    }

    // EFFECTS: returns the isHostile value
    public boolean hostility() {
        return isHostile;
    }

    // EFFECTS: returns the profession field object
    public Profession getProfession() {
        return profession;
    }

    // EFFECTS: returns true if all values in a boolean[] are false, returns true otherwise;
    public boolean allFalse(boolean[] booleans) {
        for (boolean b : booleans) {
            if (b) {
                return false;
            }
        }
        return true;
    }
}
