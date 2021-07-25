package ui;

import model.entities.Player;
import model.items.BirchBow;
import model.races.*;
import model.professions.*;

import java.sql.SQLOutput;

public class CharacterCreator extends CommonUI {

    // EFFECTS: starts a CharacterCreator object, which loops until the user has confirmed their race and profession.
    //          Creates a player object which has the correct starting stats and items before the constructor finishes.
    public CharacterCreator() {
        Player player = new Player(getInput("What might your name be, oh adventurous one?"));
        boolean raceConfirmed = false;
        boolean professionConfirmed = false;
        while (!raceConfirmed || !professionConfirmed) {
            System.out.println(arrangeRacesAndProfessions());
            String selection = getInput("Type the name of the race or profession you want to know more about");
            if (!raceConfirmed) {
                raceConfirmed = raceSelection(player, selection);
            }
            if (!professionConfirmed) {
                professionConfirmed = professionSelection(player, selection);
            }
        }
        System.out.println("And now, " + player.name() + ", you set off on you're journey with... ");
        System.out.println(displayStats(player.stats(), true));
        System.out.println(displayInventory(player.getInventory(), true, true));

    }

    // MODIFIES: player
    // EFFECTS: will set the race of player to selection if confirmed and return true. If unconfirmed, returns false.
    //          Must be updated as new Races are added.
    private boolean raceSelection(Player player, String selection) {
        if ("HUMAN".equalsIgnoreCase(selection)) {
            Human human = new Human();
            getRaceInfo(human);
            if (getBoolean("Do you want to choose 'Human' as your race? [True/False]")) {
                player.setRace(human);
                return true;
            }
        }
        return false;
    }

    // MODIFIES: player
    // EFFECTS: will set the profession of player to selection if confirmed, and return true. If unconfirmed, returns
    //          false. Must be updated as new Professions are added.
    private boolean professionSelection(Player player, String selection) {
        if ("RANGER".equalsIgnoreCase(selection)) {
            Ranger ranger = new Ranger();
            getProfessionInfo(ranger);
            if (getBoolean("Do you want to choose 'Ranger' as your profession? [True/False]")) {
                player.setProfession(ranger);
                player.addToInventory(new BirchBow());
                return true;
            }
        }
        return false;
    }

    // EFFECTS: sets a single string of all the available races and professions in side by side lists. Note this must be
    //          updated as new races and professions are added.
    private String arrangeRacesAndProfessions() {
        // Races column is 11 units across, Professions column is 17 units across. Total width is 31 units
        return    "|-----------|-----------------|\n"
                + "|   Races   |   Professions   |\n"
                + "|===========|=================|\n"
                + "|   Human   |     Ranger      |\n"
                + "|-----------|-----------------|";
    }

    // EFFECTS: prints the Species, description, strengths and weaknesses, Stats, and lvlUpGains of the requested race
    public void getRaceInfo(Race race) {
        System.out.println(race.getDescription() + "\n Strengths: " + race.getStrengths() + "\n Weaknesses: "
                + race.getWeaknesses());
        System.out.println(displayStats(race.stats(), true)
                + "\n| With per level gains of:\n| " + race.getHpGain() + "Hp and " + race.getMpGain() + "Mp."
                + "\n|-----------------------------|");
    }

    // EFFECTS: prints the Title: title, description, list of abilities, and bonusStats of the requested profession
    public void getProfessionInfo(Profession prof) {
        System.out.println("|     Profession Bonuses      |\n" + displayBonus(prof.stats()));
        System.out.println(displayAbilities(prof));
    }
}
