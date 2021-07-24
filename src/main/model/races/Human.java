package model.races;

import java.util.Arrays;

public class Human extends Race {
    public Human() {
        this.species = "Human";
        this.description = "The vanilla/default/normal race for a character,"
               + "they are all-rounders, but possess good instincts.";
        String[] strengthWords = { "Charisma","Communication","Dexterity" };
        String[] weaknessWords = { "Pointy things", "Fire", "Magic", "Spirits" };
        this.strengths.addAll(Arrays.asList(strengthWords));
        this.weaknesses.addAll(Arrays.asList(weaknessWords));

        this.intelligence = 7;
        this.strength = 5;
        this.speed = 6;
        this.dex = 8;
        this.stealth = 7;
        this.health = 250;
        this.healthGain = 25;
        this.mana = 100;
        this.manaGain = 10;
    }
}
