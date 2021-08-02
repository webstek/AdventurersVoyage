package model;

import model.entities.Player;
import org.json.JSONObject;

import java.io.FileNotFoundException;

public class GameState {
    private int adventureNumber;
    private Player player;

    // EFFECTS: constructs a gameState with the initial field values from the parameters
    public GameState(int adventureNumber, Player player) {
        this.adventureNumber = adventureNumber;
        this.player = player;
    }

    // EFFECTS: writes this object to a Json file, enabling game saves.
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("adventureNumber", adventureNumber);
        json.put("player", player.toJson());
        return json;
    }

    // MODIFIES: this
    // EFFECTS: increases the adventure number by 1
    public void completeAnAdventure() {
        adventureNumber++;
    }

    // EFFECTS: returns the adventureNumber field value
    public int adventureNumber() {
        return adventureNumber;
    }

    // MODIFIES: this
    // EFFECTS: sets the player field from a player object
    public void setPlayer(Player player) {
        this.player = player;
    }

    // EFFECTS: gets the player field
    public Player player() {
        return player;
    }
}
