package model.entites;

import model.IntegerMatrix;
import model.ItemMatrix;
import model.professions.Profession;
import model.races.Race;

import java.util.ArrayList;

/**
 * Represents any entity that requires the data that a player would have. Entities have stats, race, profession,
 * inventory, a list of other entities within range, a combatActions integer, experience, and gold.
 */

public abstract class Entity {
    protected IntegerMatrix stats;
    protected Race race;
    protected Profession profession;
    protected ItemMatrix inventory;
    protected ArrayList<Entity> entitiesInRange;
    protected boolean isHostile = false;
    protected String name;
    protected int combatActions = 0;
    protected int xp = 0;
    protected int gold = 0;


}
