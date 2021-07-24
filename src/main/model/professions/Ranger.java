package model.professions;


import model.professions.abilities.Camoflauge;
import model.professions.abilities.Powershot;
import model.professions.abilities.StrategicBonus;
import model.professions.abilities.Track;
import model.professions.abilities.Basics;


public class Ranger extends Profession {
    public Ranger() {
        this.title = "Ranger";
        this.abilities.add(new Powershot());
        this.abilities.add(new Camoflauge());
        this.abilities.add(new StrategicBonus());
        this.abilities.add(new Track());
        this.abilities.addAll(Basics.list());

        this.bonusDex = 4;
        this.bonusStealth = 2;
        this.bonusSpeed = 2;
    }
}
