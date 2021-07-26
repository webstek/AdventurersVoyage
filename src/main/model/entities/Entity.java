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
    protected int combatActions = 0;
    protected int xp = 0;
    protected int level = 0;
    protected int gold = 0;

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

    // EFFECTS: returns the current stats matrix of the player
    public Statistics stats() {
        return stats;
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

    // EFFECTS: returns the ItemMatrix field
    public ItemMatrix getInventory() {
        return inventory;
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
}
