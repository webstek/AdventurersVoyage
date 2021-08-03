package ui;

import model.GameState;
import model.entities.*;
import model.exceptions.UserInputException;
import model.items.BirchBow;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;
import persistence.Writable;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * AdventurerVoyageApp handles the story and calling the appropriate ui handler for the story event happening.
 */

public class AdventurerVoyageApp implements Writable {
    private static final String JSON_FILE_PATH = "./data/gameState.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static GameState gs;

    // MODIFIES: this
    // EFFECTS: calls the main method that tell the adventure story
    public AdventurerVoyageApp() {
        jsonWriter = new JsonWriter(JSON_FILE_PATH);
        jsonReader = new JsonReader(JSON_FILE_PATH);
        gs = new GameState(0,null);
        introduction();

        System.out.println("\n\nThanks for playing! Much, much, MUCH, more content is in the works!  :O");
    }

    // EFFECTS: introduces the game and prompts the user if they want to load their game from a save file.
    private void introduction() {
        System.out.println("Welcome to Adventurer's Voyage!\n-------------------------------");
        optionalLoadFromSave();
        gotoAdventure(gs.adventureNumber());
    }

    // EFFECTS: selects the appropriate goAdventuring method for the current place of the user in the story
    private void gotoAdventure(int adventureNumber) {
        if (adventureNumber == 0) {
            goAdventuring0();
        } else if (adventureNumber == 1) {
            goAdventuring1();
        }
    }

    // EFFECTS: the character creation and combat tutorial stage of the game.
    private void goAdventuring0() {
        System.out.println("\nYou, yes YOU, are about to embark on a peculiar voyage of untold perils, ingenious"
                + "\ndiscoveries, and fights against unknown monsters. Clearly, at this point, the perils are"
                + "\nmostly as I haven't written all of them, and the monsters are unknown since I you haven't faced"
                + "\nany yet. HAH! But this is all besides the point. For now, you must decide who you really are....");
        CharacterCreator cc = new CharacterCreator();
        gs.setPlayer(cc.createdPlayer());
        System.out.println("\nNow, you must learn how to do combat! An exciting process, although somewhat tedious if"
                + "\nI make your damage values too low. For now, the essentials. Battles are made up of turns, which"
                + "\nend when all entities in battle have zero combat actions (ca). When an entity has the highest ca,"
                + "\nit has the initiative, so they get to use an ability. After each turn, ca are reset for each"
                + "\nentity, to their current total speed stat. The battle ends when all enemies are dead!"
                + "\nThat's all on the essentials of battle folks.");
        String dscrpt1 = "\nWell then, perhaps we need to motivate the necessity of a tutorial. We should consider that"
                + "\nevery adventure story needs a training arc, and so this one will be yours. Just to make sure "
                + "\nyou are capable enough to be an Adventurer, and not some person trying to break my game, there"
                + "\nshall be a test. Defeat the opponent in front of you!";
        new CombatHandler(gs.player(), new Enemy[]{new CaveSlug(null)}, dscrpt1);
        System.out.println("\nCongratulations, you have just slapped a cave slug to death! Not the first \nthing you "
                + "think of doing when you wake up, but it was fun nonetheless.");
        gs.completeAnAdventure();
        saveGameState();
        gotoAdventure(gs.adventureNumber());
    }

    // EFFECTS: the first real quest of the game.
    private void goAdventuring1() {
        System.out.println("\n\nAlright " + gs.player().name() + ", it's time for you to begin your voyage of epic"
                + "\nproportions, well, let's hope for your sake that it's long. Until we meet again adventurer!");
        System.out.println("\n\nYou have just finished eating a yummy breakfast. Including french toast, although"
                + "\nwithout the syrup. The previous evening, you received a request from the local bowyer to"
                + "\nbring them a Birch Bow. Knowing that the Cave Slugs to the south frequently have stashes of"
                + "\nBirch bows, you decide to go hunt one down.");
        String dscrpt1 = "\nIn the caves to the south, you find a Cave Slug, and it happens to have a Birch Bow!";
        new CombatHandler(gs.player(), new Enemy[]{new CaveSlug(new BirchBow())}, dscrpt1);
    }

    // EFFECTS: saves the gameState to file
    private void saveGameState() {
        try {
            jsonWriter.open();
            jsonWriter.write(gs);
            jsonWriter.close();
            System.out.println("\nYour game has been saved.");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_FILE_PATH);
        }
    }

    // EFFECTS: saves the game state to the save file
    public void loadGameState() {
        try {
            gs = jsonReader.read();
            System.out.println("Loaded game from save file.");
        } catch (IOException e) {
            System.out.println("Unable to read game-state from file.");
        }
    }

    // TODO implement data persistence (i.e. save player data and adventureNumber to file).
    // MODIFIES: this
    // EFFECTS: queries the user to choose if they want to load save data from the hard drive and does so if they wish.
    private void optionalLoadFromSave() {
        CommonUI commonUI = new CommonUI();
        boolean isLoadingRequired = false;
        try {
            isLoadingRequired = commonUI.getBoolean("Do you want to load the currently saved game? [Yes]/[No]");
        } catch (UserInputException e) {
            System.out.println(e.getMessage());
            optionalLoadFromSave();
        }
        if (isLoadingRequired) {
            loadGameState();
            System.out.println(commonUI.playerSummary(gs.player()));
        }
    }

    // EFFECTS: writes this object to a Json file, enabling game saves.
    public JSONObject toJson() {
        return gs.toJson();
    }
}

