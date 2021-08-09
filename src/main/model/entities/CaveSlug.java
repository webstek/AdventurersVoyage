package model.entities;

import model.ItemMatrix;
import model.Statistics;
import model.abilities.Slap;
import model.abilities.SlugBomb;
import model.items.BirchBow;
import model.items.Item;

import java.util.ArrayList;


/**
 * Cave slug enemy class
 */

public class CaveSlug extends Enemy {
    // EFFECTS: creates the Cave Slug with the correct field values
    public CaveSlug(Item item) {
        this.stats = new Statistics(new int[][]{{1,4,2,2,4,200,50},{0,0,0,0,0,15,0}});
        this.inventory = new ItemMatrix(1,1);
        if (item != null) {
            this.addToInventory(item);
        }
        setHostility(true);
        this.name = "Cave Slug";
        this.combatActions = 2;
        this.xp = 10;
        this.level = 1;
        this.gold = 1;
        setAbilities();
    }


    // MODIFIES: this
    // EFFECTS: adds the unique Abilities of the enemy to the abilities list;
    protected void setAbilities() {
        this.abilities = new ArrayList<>();
        this.abilities.add(new Slap(this));
        this.abilities.add(new SlugBomb(this));
        refreshAbilities();
    }

    // EFFECTS: returns the string that gives a detailed explanation of the enemy
    public String getDescription() {
        return "Cave slugs leave behind goo trails that reduce base Speed to 0 for 3 turns if the character steps in it"
                + ". Cave slugs like to appear in caves, and enjoy playing Euchre biweekly.";
    }
}
