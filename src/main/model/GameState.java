package model;

import model.entities.Player;
import org.json.JSONObject;
import persistence.JsonWriter;

import java.io.FileNotFoundException;

public class GameState {
    private int adventureNumber;
    private Player player;
    private String actionText;
    private String displayText;

    // EFFECTS: constructs a gameState with the initial field values from the parameters
    public GameState(int adventureNumber, Player player) {
        this.adventureNumber = adventureNumber;
        this.player = player;
    }

    // EFFECTS: constructs a gameState with initial field values from the parameters
    public GameState(String displayText, int adventureNumber, Player player) {
        this(adventureNumber, player);
        this.displayText = displayText;
    }

    // EFFECTS: writes this object to a Json file, enabling game saves.
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("displayText",displayText);
        json.put("adventureNumber", adventureNumber);
        json.put("player", player.toJson());
        return json;
    }

    // EFFECTS: saves the gameState to file
    public void save() {
        try {
            JsonWriter jsonWriter = new JsonWriter("./data/gameState.json");
            jsonWriter.open();
            jsonWriter.write(this);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save game.");
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the actionText field
    public void setActionText(String str) {
        actionText = str;
    }

    // EFFECTS: returns the action text
    public String getActionText() {
        return actionText;
    }

    // EFFECTS: returns the display text
    public String getDisplayText() {
        return displayText;
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

    // EFFECTS: invokes the appropriate methods for the current game state
    public void handleState() {
        if (adventureNumber == 0) {
            if (!player.isInCombat()) {
                displayText = "Now, you must learn how to do combat! An exciting process, although somewhat tedious if "
                        + "I make your damage values too low. For now, the essentials. Battles are made up of turns, "
                        + "which end when all entities in battle have zero combat actions (ca). When an entity has the "
                        + "highest ca, it has the initiative, so they get to use an ability. After each turn, ca are "
                        + "reset for each entity, to their current total speed stat. The battle ends when all enemies "
                        + "are dead!\n\nThat's all on the essentials of battle folks."
                        + "\n\nWell then, perhaps we need to motivate the necessity of a tutorial. We should consider "
                        + "that every adventure story needs a training arc, and so this one will be yours. Just to "
                        + "make sure you are capable enough to be an Adventurer, and not some person trying to "
                        + "break my game, there shall be a test. Defeat the opponent in front of you!"
                        + "\n\n\n\n Press enter when you are ready...";
                player.setInCombat(true);
            } else {
                displayText = " ---------------- Battle start! --------------- ";
            }
        }
    }
}
