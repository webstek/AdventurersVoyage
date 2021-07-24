package model.professions;

import model.professions.abilities.Ability;
import java.util.ArrayList;

public abstract class Profession {
    protected String title = null;
    protected ArrayList<Ability> abilities = new ArrayList<Ability>();
    protected int bonusIntelligence = 0;
    protected int bonusStrength = 0;
    protected int bonusSpeed = 0;
    protected int bonusDex = 0;
    protected int bonusStealth = 0;

    public String getTitle() {
        return this.title;
    }

    public ArrayList<Ability> getAbilities() {
        return this.abilities;
    }

    public int bonusInt() {
        return bonusIntelligence;
    }

    public int bonusStrength() {
        return bonusStrength;
    }

    public int bonusSpeed() {
        return bonusSpeed;
    }

    public int bonusDex() {
        return bonusDex;
    }

    public int bonusStealth() {
        return bonusStealth;
    }
}
