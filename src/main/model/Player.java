package model;

import model.items.*;
import model.professions.*;
import model.races.*;
import model.stats.*;

/**
 * Represents a player with health points (hp), mana points (mp), experience points (xp), an inventory of items, and
 * a name, race, and profession.
 */
public class Player {
    int hp;
    int mp;
    int xp;
    private Stats stats;
    private final SizedList<Item> inventory;
    private final String name;
    private Race race;
    private Profession prfsn;

    // EFFECTS: name field is set to name parameter, race and profession are set, which set the Stats field
    public Player(String name, String raceString,String prfsnString) {
        this.name = name;
        try {
            this.setRace(raceString);
        } catch (NotImplementedException e) {
            e.printStackTrace();
        }
        try {
            this.setPrfsn(prfsnString);
        } catch (NotImplementedException e) {
            e.printStackTrace();
        }
        this.setStats();
        this.inventory = new SizedList<Item>(18);
    }

    // MODIFIES: this
    // EFFECTS: sets the race field of the player by a string argument. Throws NotImplemented if the input string
    //          doesn't match any of the cases in the switch statement.
    public void setRace(String raceString) throws NotImplementedException {
        switch (raceString.toUpperCase()) {
            case "HUMAN":
                this.race = new Human();
                break;
            case "UNDEAD":
                this.race = new Undead();
                break;
            default: throw new NotImplementedException("Race not implemented.");
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the profession field of the player by a string argument. Throws NotImplemented if the input string
    //          doesn't match any of the cases in the switch statement.
    private void setPrfsn(String prfsnString) throws NotImplementedException {
        switch (prfsnString.toUpperCase()) {
            case "RANGER":
                this.prfsn = new Ranger();
                break;
            case "NECROMANCER":
                this.prfsn = new Necromancer();
                break;
            default: throw new NotImplementedException("Profession not implemented.");
        }
    }

    // REQUIRES: race and prfsn fields must not be null.
    // MODIFIES: this
    // EFFECTS: sets the base stats of
    private void setStats() {
        this.stats = new AllStats(race.intelligence(), race.strength(), race.speed(), race.dex(), race.stealth(),
                prfsn.bonusInt(), prfsn.bonusStrength(), prfsn.bonusSpeed(), prfsn.bonusDex(), prfsn.bonusStealth());
    }

    //TODO change this to return the stats field, or a list of the stat fields
    public void displayStats() {
        this.stats.display();
    }

    // MODIFIES: this
    // EFFECTS: adds the parameter item to the inventory list and updates the player stats
    public void addToInventory(Item item) {
        this.inventory.add(item);
        this.stats.update(item.getBonus(), true);
        this.stats.update(item.getPenalty(), false);
    }

    // REQUIRES: parameter item in the inventory list
    // MODIFIES: this
    // EFFECTS: removes the parameter item from the inventory list and updates the player stats
    public void dropFromInventory(Item item) {
        this.inventory.remove(item);
        this.stats.update(item.getBonus(), false);
        this.stats.update(item.getPenalty(), true);
    }

    //TODO move to the advVoyApp class or another for displaying things
    public void displayInventory() {
        if (this.inventory.size() == 0) {
            System.out.println("Your inventory is empty!");
        } else {
            for (Item t : this.inventory) {
                System.out.println("Item: " + t.itemName() + " Description: " + t.description());
            }
        }
    }

    // EFFECTS: returns the race object that is in the race field of the player
    public Race getRace() {
        return this.race;
    }

    // EFFECTS: returns the profession object that is in the profession field of the player
    public Profession getPrfsn() {
        return this.prfsn;
    }

    // EFFECTS: returns the name of the player as a string
    public String getName() {
        return this.name;
    }
}
