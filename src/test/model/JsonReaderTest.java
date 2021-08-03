package model;

import model.items.BirchBow;
import model.items.FluffyHat;
import model.professions.Ranger;
import model.races.Human;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends GeneralTestMethods {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            GameState gamestate = reader.read();
            fail("IOException was not thrown");
        } catch (IOException e) {
            // test passed.
        }
    }

    @Test
    public void testReaderGeneralGameState() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGameState.json");
        try {
            GameState gs = reader.read();
            ItemMatrix expectedInventory = new ItemMatrix(3,6);
            expectedInventory.add(new FluffyHat());
            expectedInventory.add(new BirchBow());
            Statistics expectedStatistics = new Statistics(new int[][]{{7,5,6,8,7,325,100},{0,0,2,4,2,15,0}});

            assertEquals(1,gs.adventureNumber());
            checkPlayer("Marine Iguana",expectedStatistics,expectedInventory,new Human(),new Ranger(),
                    0 , 10, gs.player());
            assertEquals(2,gs.player().getInventory().length());
        } catch (IOException e) {
            fail("Couldn't read data from file");
        }
    }
}
