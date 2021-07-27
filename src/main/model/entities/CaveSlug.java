package model.entities;

import model.ItemMatrix;
import model.Statistics;
import model.abilities.Ability;
import model.abilities.Slap;
import model.items.BirchBow;


/**
 * Cave slug enemy class
 */

public class CaveSlug extends Enemy {
    // EFFECTS: creates the Cave Slug with the correct field values
    public CaveSlug() {
        this.stats = new Statistics(new int[][]{{2,4,1,2,4,200,50},{0,0,0,0,0,15,0}});
        this.inventory = new ItemMatrix(1,1);
        this.addToInventory(new BirchBow());
        setHostility(true);
        this.name = "Cave Slug";
        this.combatActions = 1;
        this.xp = 10;
        this.level = 1;
        this.gold = 1;
        setAbilities();
    }


    // MODIFIES: this
    // EFFECTS: adds the unique Abilities of the enemy to the abilities list;
    protected void setAbilities() {
        this.abilities.add(new SlugBomb());
        this.abilities.add(new Slap(this));
        refreshAbilities();
    }

    // EFFECTS: returns the string that gives a detailed explanation of the enemy
    public String getDescription() {
        return "Cave slugs leave behind goo trails that reduce base Speed to 0 for 3 turns if the character steps in it"
                + ". Cave slugs like to appear in caves, and enjoy playing Euchre biweekly.";
    }

    private class SlugBomb extends Ability {
        private SlugBomb() {
            this.name = "Slug Bomb";
            this.description = "The slug bombs its opponent with slime, reducing their base speed to zero.";
            this.turnDuration = 3;
            this.combatAction = 1;
            setStatsEffect();
        }

        public SlugBomb(SlugBomb ability) {
            this.name = ability.name;
            this.description = ability.description;
            this.combatAction = ability.combatAction;
            this.statsEffect = ability.statsEffect.clone();
        }

        public void refreshAbility(Entity entity) {
            getEntityStats(entity);
            int damage = 0;
            statsEffect.add(1,5,damage);
        }

        public Ability clone() {
            return new SlugBomb(this);
        }

        protected void setStatsEffect() {
            this.statsEffect = new Statistics(new int[][]{{0,0,-99,0,0,0,-30},{0,0,0,0,0,0,0}});
        }


        protected void getEntityStats(Entity entity) {
            this.entityStats = stats;
        }
    }
}
