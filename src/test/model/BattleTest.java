package model;

import model.combat.Battle;
import model.entities.*;
import model.entities.CaveSlug;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

public class BattleTest {
    private Battle battleTest;
    private Player player;
    private ArrayList<Entity> enemies = new ArrayList<>();

    @BeforeEach
    public void setup() {
        enemies.add(new CaveSlug());
        battleTest = new Battle(player, enemies);
    }
}
