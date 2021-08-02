package model.items;

import model.InstanceFactory;
import model.Statistics;
import org.json.JSONObject;
import persistence.Writable;

/**
 * Representation of items. Items have a name, bonus stats, penalty stats, and a description.
 */

public abstract class Item implements Writable {
    protected Statistics itemStats;
    protected String name;
    protected String description;
    // TODO add ability field so consumables are possible, requires making abilities writable as well.

    // MODIFIES: this
    // EFFECTS: sets this Item's stats
    protected abstract void setItemStats();

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