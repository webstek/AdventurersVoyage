package model;

import model.combat.CombatHandler;
import model.entities.CaveSlug;
import model.entities.Enemy;
import model.entities.Entity;
import model.entities.Player;
import model.items.BirchBow;
import model.items.FluffyHat;
import org.json.JSONObject;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GameState {
    private int adventureNumber;
    private Player player;
    private CombatHandler combat;
    private String displayText;
    private String actionText;
    private boolean hasUnusedUserInput = false;

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

    // MODIFIES: this
    // EFFECTS: sets the hasUnUsedUserInput field to the boolean argument value
    public void setHasUnusedUserInput(boolean bool) {
        hasUnusedUserInput = bool;
    }

    // EFFECTS: returns the value of the hasUnusedUserInput field
    public boolean hasUnusedUserInput() {
        return hasUnusedUserInput;
    }

    // MODIFIES: this
    // EFFECTS: sets the displayText field
    public void setDisplayText(String str) {
        displayText = str;
    }

    // MODIFIES: this
    // EFFECTS: appends the str in the argument to the displayText field. Note there are no default spaces or new lines.
    public void appendToDisplayText(String str) {
        displayText = displayText + str;
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

    // EFFECTS: returns the list of enemies the user is currently in combat with.
    public ArrayList<Entity> getEnemies() {
        return combat.getEnemies();
    }

    // EFFECTS: invokes the appropriate methods for the current game state
    public void handleState() {
        if (adventureNumber == 0) {
            battle0();
        } else if (adventureNumber == 1) {
            displayText = "\n\nAlright " + this.player().name() + ", it's time for you to begin your voyage of epic "
                    + "proportions, well, let's hope for your sake that it's long. Until we meet again adventurer!"
                    + "\n\nYou have just finished eating a yummy breakfast. Including french toast, although "
                    + "without the syrup. The previous evening, you received a request from the local bowyer to "
                    + "bring them a Birch Bow. Knowing that the Cave Slugs to the south frequently have stashes of "
                    + "Birch bows, you decide to go hunt one down."
                    + "\n\nIn the caves to the south, you find a Cave Slug, and it happens to have a Birch Bow!"
                    + "\n\n\n\n Thanks for playing! Much, much, MUCH, more content is in the works!  :O";
        } else {
            displayText = "\n\n\n\n\n\n\n\n\nThanks for playing! Much, much, MUCH, more content is in the works!  :O";
        }
    }

    private void battle0() {
        if (!player.isInCombat()) {
            displayText = "Now, you must learn how to do combat! An exciting process, although somewhat tedious if "
                    + "I make your damage values too low. For now, the essentials. Battles are made up of turns, "
                    + "which end when all entities in battle have zero combat actions (ca). When an entity has the "
                    + "highest ca, it has the initiative, so they get to use an ability. After each turn, ca are "
                    + "reset for each entity, to their current total speed stat. The battle ends when all enemies "
                    + "are dead!\n\nThat's all on the essentials of battle folks. Well, if it isn't your initiative, "
                    + "the battle must be advanced by pressing enter."
                    + "\n\nWell then, perhaps we need to motivate the necessity of a tutorial. We should consider "
                    + "that every adventure story needs a training arc, and so this one will be yours. Just to "
                    + "make sure you are capable enough to be an Adventurer, and not some person trying to "
                    + "break my game, there shall be a test. Defeat the opponent in front of you!"
                    + "\n\n\n\n Press enter when you are ready...";
            player.setInCombat(true);
            combat = new CombatHandler(this, new Enemy[]{new CaveSlug(new BirchBow())});
        } else {
            if (combat.getTurnNumber() == 0) {
                displayText = " -------------- Battle Start! -------------- \n  Oh no! A cave slug appeared!\n";
            } else if (combat.getTurnNumber() != 0) {
                displayText = "";
            }
            combat.handleCombatIO();
        }
    }
}