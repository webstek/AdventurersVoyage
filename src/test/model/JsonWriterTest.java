package model;

import model.entities.Player;
import model.items.BirchBow;
import model.items.FluffyHat;
import model.professions.Ranger;
import model.races.Human;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends GeneralTestMethods {

    // EFFECTS: checks to determine if the Json Writer throws an exception for an illegal file name.
    @Test
    public void testWriterInvalidFile() {
        try {
            Player player = new Player("Marine Iguana");
            GameState gs = new GameState(1,player);
            JsonWriter writer = new JsonWriter("./data/\0illegalfileName.json");
            writer.open();
            fail("IOException was not thrown");
        } catch (IOException e) {
            // test passed.
        }
    }

    // EFFECTS: checks to determine if the Json writer is correctly writing a recoverable gameState from a known set
    //          of instances.
    @Test
    public void testWriterGeneralGameState() {
        try {
            Player player = new Player("Marine Iguana");
            player.setProfession(new Ranger());
            player.setRace(new Human());
            player.addToInventory(new FluffyHat());
            player.addToInventory(new BirchBow());
            player.receiveXp(50);
            player.deposit(100);
            GameState gs = new GameState(3, player);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGameState.json");
            writer.open();
            writer.write(gs);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGameState.json");
            gs = reader.read();
            assertEquals(3, gs.adventureNumber());
            checkPlayer("Marine Iguana", player.stats(), player.getInventory(), player.getRace(), player.getProfession(),
                    player.getGold(), player.xp(), gs.player());
            assertEquals(2, gs.player().getInventory().length());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
