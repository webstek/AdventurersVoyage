package model.professions;

import model.Statistics;
import model.abilities.Ability;
import java.util.ArrayList;

/**
 * Abstract class that all profession subclasses are based upon. The profession class contains a title, description,
 * list of abilities, and bonusStats
 */

public abstract class Profession {
    protected String title = null;
    protected ArrayList<Ability> abilities = new ArrayList<>();
    protected Statistics bonusStats;

    // EFFECT: Sets the appropriate bonusStats for each profession. Called in the constructors.
    protected abstract Statistics setBonusStats();

    // EFFECT: returns the title of the profession
    public String getTitle() {
        return title;
    }

    // EFFECT: returns the list of abilities the profession can use
    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    // EFFECT: returns the IntegerMatrix of bonus stats from the profession
    public Statistics stats() {
        return bonusStats;
    }
}