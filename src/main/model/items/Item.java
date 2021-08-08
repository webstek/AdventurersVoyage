package model.items;

import model.InstanceFactory;
import model.Statistics;
import org.json.JSONObject;
import persistence.Writable;

import javax.swing.*;

/**
 * Representation of items. Items have a name, bonus stats, penalty stats, and a description.
 *
 * Note that all item subclasses must have their icons in the data/items folder.
 */

public abstract class Item implements Writable {
    protected Statistics itemStats;
    protected String name;
    protected String description;
    protected ImageIcon itemIcon;
    // TODO add ability field so consumables are possible, requires making abilities writable as well.

    protected static final String DIR_SEP = System.getProperty("file.separator");

    // MODIFIES: this
    // EFFECTS: sets this Item's stats
    protected abstract void setItemStats();

    // EFFECTS: returns the ImageIcon object corresponding to the correct image.
    public ImageIcon getItemIcon() {
        return itemIcon;
    }

    // EFFECTS: returns the itemStats
    public Statistics stats() {
        return itemStats;
    }

    // EFFECTS: returns the item name
    public String name() {
        return name;
    }

    // EFFECTS: returns the item description
    public String description() {
        return description;
    }

    // REQUIRES: Item names with more than one word must have the first letter of each word capitalized.
    // EFFECTS: converts the item into a Json object representation so it can be saved
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",this.name.replace(" ",""));
        return jsonObject;
    }
}