package model;

import model.entities.Player;
import model.items.Item;
import model.professions.Profession;
import model.races.Race;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeneralTestMethods {
    public boolean statsEqualValues(Statistics stats1, Statistics stats2) {
        for (int p = 1; p <= 14; p++) {
            int i = (p-1) / 7;
            int j = p - 1 - (i * 7);
            if (stats1.in(i,j) != stats2.in(i,j)) {
                return false;
            }
        }
        return true;
    }

    public boolean inventoryEqualValues(ItemMatrix inventory1, ItemMatrix inventory2) {
        for (Item item : inventory1) {
            if (!inventory2.contains(item)) {
                return false;
            }
        }
        return true;
    }

    protected void checkPlayer(String name, Statistics stats, ItemMatrix inventory, Race race, Profession profession
                               ,int gold, int xp, Player player) {
        assertEquals(name, player.name());
        assertTrue(statsEqualValues(stats, player.stats()));
        assertTrue(inventoryEqualValues(inventory, player.getInventory()));
        assertEquals(race.getSpecies(),player.getRace().getSpecies());
        assertEquals(profession.getTitle(),player.getProfession().getTitle());
        assertEquals(gold,player.getGold());
        assertEquals(xp % 100, player.xp());
    }
}
