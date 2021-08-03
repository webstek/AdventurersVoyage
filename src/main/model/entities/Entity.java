package model.entities;

import model.Statistics;
import model.ItemMatrix;
import model.exceptions.InsufficientResourceException;
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
    protected ItemMatrix inventory;
    protected boolean isHostile = false;
    protected String name;
    protected int combatActions;
    protected int xp = 0;
    protected int level = 0;
    protected int gold = 0;
    protected ArrayList<Ability> abilities = new ArrayList<>();
    // GOAL: implement entitiesInRange for target selection depending on ability selected;

    // MODIFIES: this
    // EFFECTS: adds all abilities to the abilities list;
    protected abstract void setAbilities();

    // REQUIRES: setAbilities() must be called at least once beforehand
    // MODIFIES: this
    // EFFECTS: refreshes calculable values for all abilities of an entity.
    public void refreshAbilities() {
        for (Ability ability : abilities) {
            ability.refreshAbility(this);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the race field of the player by a string argument.
    public void setRace(Race race) {
        if (this.race != null) {
            for (int i = 0; i < stats.getMaxColumns(); i++) {
                this.stats.clear(0, i);
            }
        }
        this.race = race;
        this.stats.add(race.stats());
    }

    // MODIFIES: this
    // EFFECTS: sets the profession field of the player by a string argument.
    public void setProfession(Profession prof) {
        if (this.profession != null) {
            for (int i = 0; i < stats.getMaxColumns(); i++) {
                this.stats.clear(1, i);
            }
        }
        this.profession = prof;
        this.stats.add(prof.stats());
        setAbilities();
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
            try {
                abilitiesRequirementsState[i] = areRequirementsMetForAbility(abilities.get(i));
            } catch (InsufficientResourceException e) {
                abilitiesRequirementsState[i] = false;
            }
        }
        return !allFalse(abilitiesRequirementsState);
    }

    // EFFECTS: returns true if there is enough mana and combatActions to use the ability
    public boolean areRequirementsMetForAbility(Ability ability) throws InsufficientResourceException {
        boolean goodMana = ability.getStatsEffect().in(0,6) + stats.in(0,6) >= 0;
        boolean goodCombatActions = ability.caCost() <= combatActions && combatActions > 0;
        if (!goodMana) {
            throw new InsufficientResourceException("Not enough mana for " + ability.name() + "!");
        }
        if (!goodCombatActions) {
            throw new InsufficientResourceException("Not enough combat actions for " + ability.name() + "!");
        }
        return true;
    }

    // MODIFIES: this
    // EFFECTS: adds the parameter item to the inventory list and updates the player stats
    public void addToInventory(Item item) {
        this.inventory.add(item);
        this.stats.add(item.stats());
        // TODO add item ability if item is consumable
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
    public BattleEffect parse(Ability ability, ArrayList<Entity> targets, Boolean actionPhase) {
        refreshAbilities();
        return new BattleEffect(ability, targets, this, actionPhase);
    }

    // EFFECTS: returns the Ability class corresponding to the parameter string, throws UserInputException if the
    //          ability is not available to the player.
    public Ability getAbilityFromString(String abilityName) throws UserInputException {
        Ability abilityWithName = null;
        for (Ability ability : this.abilities) {
            if (ability.name().equalsIgnoreCase(abilityName)) {
                abilityWithName = ability;
            }
        }
        if (abilityWithName == null) {
            throw new UserInputException("Please enter an ability you have.");
        }
        return abilityWithName;
    }

    // EFFECTS: returns the isHostile value
    public boolean hostility() {
        return isHostile;
    }

    // EFFECTS: returns the race field object
    public Race getRace() {
        return race;
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

    // MODIFIES: this
    // EFFECTS: reduces the combatActions field by combatActionCost
    public void useCombatActions(int combatActionCost) {
        combatActions -= combatActionCost;
    }

    // EFFECTS: Returns true if the Health stat is less than or equal to zero, otherwise returns false.
    public boolean isDead() {
        return this.stats.in(0,5) <= 0;
    }

    // EFFECTS: gives xp of the parameter value to the entity;
    public void receiveXp(int xpReceived) {
        xp += xpReceived;
    }

    // EFFECTS: returns the amount of xp the entity has
    public int xp() {
        return xp;
    }

    // EFFECTS: returns the level number of the player
    public int getLevel() {
        return level;
    }

    // MODIFIES: this
    // EFFECTS: spends the amount of gold specified in the argument if the player has enough; throws ResourceException
    //          if there isn't enough money.
    public void spend(int amount) throws InsufficientResourceException {
        if (gold >= amount) {
            gold -= amount;
        } else {
            throw new InsufficientResourceException("Not enough gold.");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds the amount of gold specified in the argument to the gold field.
    public void deposit(int amount) {
        gold += amount;
    }

    // EFFECTS: returns the amount of gold the player has
    public int getGold() {
        return gold;
    }

    // REQUIRES: race and profession to be set
    // MODIFIES: this
    // EFFECTS: resets the character's health and mana to it's maximum
    public void resetHealthAndMana() {
        for (int i = 5; i <= 6; i++) {
            stats.clear(0, i);
            stats.add(0, i, this.race.stats().in(0, i));
            stats.add(0, i, this.profession.stats().in(0, i));
            for (Item item : inventory) {
                stats.add(0, i, item.stats().in(0, i));
            }
        }
    }
}
