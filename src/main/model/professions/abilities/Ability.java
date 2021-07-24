package model.professions.abilities;

import model.stats.Stats;

public abstract class Ability {
    protected int sc = 0;
    protected int mc = 0;
    protected String details = null;


    protected abstract Stats effect();

    protected abstract int damage();

    protected abstract Stats passive();
}
//TODO Add functionality to all the abilities