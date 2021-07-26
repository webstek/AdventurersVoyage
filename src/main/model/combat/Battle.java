package model.combat;

import model.abilities.Ability;
import model.entities.*;
import model.exceptions.InsufficientResourceException;

import java.util.ArrayList;

/**
 * Class representing a battle happening. This class has the methods to apply BattleEffects and determine turn order.
 * Also handles rewards that are dropped from enemies. .........
 */

public class Battle {
    private int turn = 0;
    private ArrayList<BattleEffect> effectsToApply = new ArrayList<>();
    private ArrayList<Entity> enemies = new ArrayList<>();
    private Player playerInCombat;

    public Battle(Player player, ArrayList<Entity> enemies) {
        this.playerInCombat = player;
        this.enemies = enemies;
    }

    public void addEffect(BattleEffect btlEff) {
        effectsToApply.add(btlEff);
    }

    public boolean isAnAgroedEnemy() {
        return false;
    }

    public Entity getBigSpeedEntity() {
        return null;
    }

    public boolean isTurnOver() {
        return true;
    }

    public void add(BattleEffect parse) {
    }

    public void uses(Entity entity, Ability ability, ArrayList<Entity> targets) throws InsufficientResourceException {

    }

    public void startTurn() {
    }

    public void endActionPhase() {
    }
}
