package model;

import model.abilities.*;
import model.entities.*;
import model.exceptions.InsufficientResourceException;
import model.exceptions.UserInputException;
import model.items.*;
import model.professions.*;
import model.races.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntityTest {
    private static final GeneralTestMethods GENERAL_TESTS = new GeneralTestMethods();
    private Entity entityTest;
    private Entity humanRangerPlayer;

    @BeforeEach
    public void setup() {
        // Since entity is abstract a player actual type must be instantiated
        entityTest = new Player("MarineIguana");
        humanRangerPlayer = new Player ("geftedGicko");
        humanRangerPlayer.setRace(new Human());
        humanRangerPlayer.setProfession(new Ranger());
    }

    @Test
    public void setRaceHumanTest() {
        Race human = new Human();
        Statistics prevStats = entityTest.stats().clone();
        entityTest.setRace(human);
        assertEquals(entityTest.getRace(),human);
        Statistics changedStats = entityTest.stats();
        assertFalse(GENERAL_TESTS.statsEqualValues(prevStats, changedStats));
    }

    @Test
    public void setProfessionRangerTest() {
        Profession ranger = new Ranger();
        Statistics prevStats = entityTest.stats().clone();
        entityTest.setProfession(ranger);
        assertEquals(entityTest.getProfession(), ranger);
        Statistics changedStats = entityTest.stats();
        assertFalse(GENERAL_TESTS.statsEqualValues(prevStats,changedStats));
        try {
            assertTrue(entityTest.abilities().contains(entityTest.getAbilityFromString("Slap")));
            assertTrue(entityTest.abilities().contains(entityTest.getAbilityFromString("Power Shot")));
            assertTrue(entityTest.abilities().contains(entityTest.getAbilityFromString("Wait")));
        } catch (UserInputException e) {
            fail("Not all correct abilities were found");
        }
    }

    @Test
    public void canActTest() {
        humanRangerPlayer.resetCombatActions();
        assertEquals(8, humanRangerPlayer.getCombatActions());
        assertTrue(humanRangerPlayer.canAct());
        humanRangerPlayer.useCombatActions(7);
        assertEquals(1,humanRangerPlayer.getCombatActions());
        assertTrue(humanRangerPlayer.canAct());
        humanRangerPlayer.useCombatActions(1);
        assertEquals(0,humanRangerPlayer.getCombatActions());
        assertFalse(humanRangerPlayer.canAct());
    }

    @Test
    public void areRequirementsMetForAbilityNotEnoughManaTest() {
        humanRangerPlayer.resetCombatActions();
        humanRangerPlayer.stats().clear(0,6); // Clears mana statistics
        try {
            humanRangerPlayer.areRequirementsMetForAbility(new PowerShot());
            fail("Resource exception was not thrown");
        } catch (InsufficientResourceException e) {
            assertTrue(true);
        }
    }

    @Test
    public void getAbilityFromStringExceptionTest() {
        try {
            humanRangerPlayer.getAbilityFromString("Punch");
            fail("Failed to catch ability that player does not have");
        } catch (UserInputException e) {
            assertTrue(true);
        }
    }

    @Test
    public void addAndSubToInventoryTest() {
        Item fluffyHat = new FluffyHat();
        humanRangerPlayer.addToInventory(fluffyHat);
        assertEquals(fluffyHat,humanRangerPlayer.getInventory().inPos(1));
        humanRangerPlayer.dropFromInventory(fluffyHat);
        assertNull(humanRangerPlayer.getInventory().inPos(1));
    }

    @Test
    public void addMultipleToInventoryTest() {
        Item fluffyHat = new FluffyHat();
        Item groveBow = new BirchBow();
        humanRangerPlayer.addToInventory(fluffyHat);
        humanRangerPlayer.addToInventory(groveBow);
        assertEquals(fluffyHat,humanRangerPlayer.getInventory().inPos(1));
        assertEquals(groveBow,humanRangerPlayer.getInventory().inPos(2));
    }

    @Test
    public void enemyGettingLastAbilityTest() {
        Enemy caveSlug = new CaveSlug();
        assertEquals("Slug Bomb", caveSlug.getLastUsableAbility().name());
        caveSlug.useCombatActions(1);
        caveSlug.stats().sub(0,6,30);
        assertEquals("Slap",caveSlug.getLastUsableAbility().name());
    }
}
