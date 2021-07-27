package model;

import model.abilities.Slap;
import model.combat.Battle;
import model.combat.BattleEffect;
import model.entities.*;
import model.races.*;
import model.professions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class BattleTest {
    private Battle battleTest;
    private Player player;
    private ArrayList<Entity> enemies = new ArrayList<>();

    @BeforeEach
    public void setup() {
        player = new Player("MarineIguana");
        player.setRace(new Human());
        player.setProfession(new Ranger());
        enemies.add(new CaveSlug());
        battleTest = new Battle(player, enemies);
    }

    @Test
    public void addEffectTest() {
        battleTest.addEffect(new BattleEffect(new Slap(player),enemies, player,true));
        assertEquals(1,battleTest.getEffectsToApply().size());
        assertEquals(1,battleTest.getEffectsToRemove().size());
    }

    @Test
    public void turnMethodsTest() {
        battleTest.addEffect(new BattleEffect(new Slap(player),enemies, player,true));

    }
}
