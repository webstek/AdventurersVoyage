package persistence;

import model.GameState;
import model.InstanceFactory;
import model.entities.Player;
import model.professions.Profession;
import model.races.Race;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Structure based on the Json Serialization Demo Project (Paul Carter, UBC CPSC 210)
 * Reads JSON files to create the appropriate game state in AdventurerVoyageApp. That is, the correct player data, and
 * correct adventureNumber (which keeps track of story progress).
 */

public class JsonReader {
    private String gameStateToRead;

    // EFFECTS: constructs reader to read from the source file.
    public JsonReader(String gameStateToRead) {
        this.gameStateToRead = gameStateToRead;
    }

    // EFFECTS: reads gameState from save file and returns it. Throws IOException if the save file cannot be read.
    public GameState read() throws IOException {
        String jsonData = readFile(gameStateToRead);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGameState(jsonObject);
    }

    // readFile Method from Json Serialization Demo Project:
    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: creates a gameState instance from JSON object and returns it
    private GameState parseGameState(JSONObject jsonObject) {
        int adventureNumber = jsonObject.getInt("adventureNumber");
        Player player = parsePlayer(jsonObject);
        return new GameState(adventureNumber,player);
    }

    // EFFECTS: creates a player instance from JSON object and returns it
    private Player parsePlayer(JSONObject jsonObject) {
        JSONObject jsonPlayer = jsonObject.getJSONObject("player");
        JSONArray jsonInventory = jsonPlayer.getJSONArray("inventory");
        InstanceFactory factory = new InstanceFactory();

        Player player = new Player(jsonPlayer.getString("name"));
        Race race = factory.raceInstanceFromString(jsonPlayer.getString("race"));
        player.setRace(race);
        Profession profession = factory.professionInstanceFromString(jsonPlayer.getString("profession"));
        player.setProfession(profession);

        for (Object json : jsonInventory) {
            JSONObject jsonItem = (JSONObject) json;
            player.addToInventory(factory.itemInstanceFromString(jsonItem.getString("name")));
        }
        player.receiveXp(jsonPlayer.getInt("xp"));
        player.tryLvlUp();
        player.deposit(jsonPlayer.getInt("gold"));

        return player;
    }
}
