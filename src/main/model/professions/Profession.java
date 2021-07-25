package model.professions;

import model.IntegerMatrix;
import model.abilities.Ability;
import java.util.ArrayList;

/**
 * Abstract class that all profession subclasses are based upon. The profession class contains a title, description,
 * list of abilities, and bonusStats
 */

public abstract class Profession {
    protected String title = null;
    protected ArrayList<Ability> abilities = new ArrayList<Ability>();
    protected IntegerMatrix bonusStats;

    // EFFECT: Sets the appropriate bonusStats for each profession. Called in the constructors.
    protected abstract void setBonusStats();

    // EFFECT: returns the title of the profession
    public String getTitle() {
        return title;
    }

    // EFFECT: returns the list of abilities the profession can use
    public ArrayList<Ability> getAbilities() {
        return abilities;
    }
}
