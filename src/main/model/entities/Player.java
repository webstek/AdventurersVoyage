package model.entities;

import model.ItemMatrix;
import model.abilities.Slap;
import model.abilities.Wait;
import org.json.JSONObject;
import persistence.Writable;

/**
 * Represents the user's player/adventurer. Players have an extra field that is their level. Increasing level applies
 * lvlUpStats and for some players will cause unique effects.
 */

public class Player extends Entity implements Writable {
    // EFFECTS: constructs a base player with a name and base starting gold of 50.
    public Player(String name) {
        this.name = name;
        this.inventory = new ItemMatrix(3, 6);
    }

    // MODIFIES: this
    // EFFECTS: sets the abilities list of the player. That is, profession abilities and basic abilities;
    protected void setAbilities() {
        abilities.add(new Wait(this));
        abilities.add(new Slap(this));
        abilities.addAll(this.profession.getAbilities());
        refreshAbilities();
    }

    // MODIFIES: this
    // EFFECTS: checks if the player has enough xp to level up. The xp requirement is always 100. Returns true if the
    //          player can level up, returns false otherwise.
    public boolean tryLvlUp() {
        if (this.xp >= 100) {
            this.level += 1;
            this.xp -= 100;
            return true;
        }
        return false;
    }

    // EFFECTS: converts player to a Json object, enabling the saving of the player data.
    public JSONObject toJson() {
        JSONObject playerJson = new JSONObject();

        playerJson.put("stats",this.stats.toJson());
        playerJson.put("race",this.race.getSpecies());
        playerJson.put("profession",this.profession.getTitle());
        playerJson.put("inventory",this.inventory.toJson());
        playerJson.put("name",this.name);
        playerJson.put("xp",this.xp);
        playerJson.put("gold",this.gold);

        return playerJson;
    }
}
