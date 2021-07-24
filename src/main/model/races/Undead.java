package model.races;

import java.util.Arrays;

public class Undead extends Race {
    public Undead() {
        this.species = "Undead";
        this.description = "Undead characters receive a health restore of 10% of an enemy's max health when "
                + "are killed. They also have the passive ability to follow enemies, due to their sense of smell.";
        String[] strengthWords = {"Following Enemies", "Doesn't require Food/Water"};
        String[] weaknessWords = {"Talking", "Fire magic"};
        this.strengths.addAll(Arrays.asList(strengthWords));
        this.weaknesses.addAll(Arrays.asList(weaknessWords));

        this.intelligence = 5;
        this.strength = 8;
        this.speed = 6;
        this.dex = 5;
        this.stealth = 5;
        this.health = 250;
        this.healthGain = 30;
        this.mana = 50;
        this.manaGain = 5;
    }
}
