package model.entities;

import model.IntegerMatrix;
import model.ItemMatrix;
import model.items.*;
import model.professions.*;
import model.races.*;

import java.util.ArrayList;

/**
 * Represents any entity that requires the data that a player would have. Entities have stats, race, profession,
 * inventory, a list of other entities within range, a combatActions integer, experience, and gold.
 */

public abstract class Entity {
    protected IntegerMatrix stats = new IntegerMatrix();
    protected Race race;
    protected Profession profession;
    protected ItemMatrix inventory = new ItemMatrix(3, 6);
    protected ArrayList<Entity> entitiesInRange;
    protected boolean isHostile = false;
    protected String name;
    protected int combatActions = 0;
    protected int xp = 0;
    protected int gold = 0;

    // MODIFIES: this
    // EFFECTS: sets the race field of the player by a string argument.
    public void setRace(Race race) {
        this.race = race;
        this.stats.add(race.stats());
    }

    // MODIFIES: this
    // EFFECTS: sets the profession field of the player by a string argument.
    public void setProfession(Profession prof) {
        this.profession = prof;
        this.stats.add(prof.stats());
    }

    // MODIFIES: this
    // EFFECTS: sets the 7 main stats and 5 bonus stats of the player stats field. In the field, indices [1][5] (dmg)
    //          and [1][6] (mBrn) are reserved for use in damage calculations.
    protected void setStatsFromRaceAndProfession() {
        stats.add(race.stats());
        stats.add(profession.stats());
    }

    // EFFECTS: returns the current stats matrix of the player
    public IntegerMatrix stats() {
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


    // TODO implement do method
}
