package model.professions.abilities;

import model.stats.Stats;

import java.util.ArrayList;

public class Basics {
    public static ArrayList<Ability> list() {
        ArrayList<Ability> list = new ArrayList<Ability>();
        list.add(new EscapeBattle());
        return list;
    }

    private static class EscapeBattle extends Ability {
        //TODO write method to change battle-state to false
        @Override
        protected Stats effect() {
            return null;
        }

        @Override
        protected int damage() {
            return 0;
        }

        @Override
        protected Stats passive() {
            return null;
        }
    }
}
