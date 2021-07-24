package model.professions;

import model.professions.abilities.*;

public class Necromancer extends Profession {
    public Necromancer() {
        this.title = "Necromancer";
        this.abilities.add(new SummonSkeleton());
        this.abilities.add(new PoisonPit());
        this.abilities.add(new SkeletalObedience());
        this.abilities.addAll(Basics.list());

        this.bonusIntelligence = 5;
        this.bonusDex = 3;
    }

}