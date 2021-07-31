package model.abilities;

import model.Statistics;
import model.entities.CaveSlug;
import model.entities.Entity;

public class SlugBomb extends Ability {
    // EFFECTS: sets the name, description, combatAction, and statsEffect quantities, including the correct damage
    //          based on the entity stats passed through the parameter
    public SlugBomb(CaveSlug caveSlug) {
        this.name = "Slug Bomb";
        this.description = "The slug bombs its opponent with slime, reducing their base speed by 3 for three turns.";
        this.turnDuration = 3;
        this.combatAction = 1;
        setStatsEffect();
        getEntityStats(caveSlug);
    }

    // EFFECTS: takes a SlugBomb object and produces a clone of it
    public SlugBomb(SlugBomb ability) {
        this.name = ability.name;
        this.description = ability.description;
        this.combatAction = ability.combatAction;
        this.turnDuration = ability.turnDuration;
        this.statsEffect = ability.statsEffect.clone();
    }


    public void refreshAbility(Entity entity) {
        getEntityStats(entity);
        int damage = 0;
        statsEffect.set(1, 5, damage);
    }

    // EFFECTS: returns a clone of the Ability the method is called on
    public Ability clone() {
        return new SlugBomb(this);
    }

    // MODIFIES: this
    // EFFECTS: sets the SlugBomb stats effects.
    protected void setStatsEffect() {
        this.statsEffect = new Statistics(new int[][]{{0, 0, -3, 0, 0, 0, -30}, {0, 0, 0, 0, 0, 0, 0}});
    }

    // MODIFIES: this
    // EFFECTS: sets the entityStats field with current stats of the entity object passed into it
    protected void getEntityStats(Entity entity) {
        this.entityStats = entity.stats();
    }
}
