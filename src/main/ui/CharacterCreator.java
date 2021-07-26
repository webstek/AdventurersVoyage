package ui;

import model.entities.Player;
import model.items.FluffyHat;
import model.races.*;
import model.professions.*;
import model.exceptions.UserInputException;

public class CharacterCreator extends CommonUI {
    private Player playerField;
    boolean raceConfirmed = false;
    boolean professionConfirmed = false;

    // EFFECTS: starts a CharacterCreator object, which loops until the user has confirmed their race and profession.
    //          Creates a player object which has the correct starting stats and items before the constructor finishes.
    public CharacterCreator() {
        characterCreation();
    }

    // MODIFIES: this
    // EFFECTS: loops until the user has confirmed their race and profession. modifies playerField, setting its starting
    //          name, stats, race, profession, inventory, (all fields).
    private void characterCreation() {
        playerField = new Player(getInput("What might your name be, oh adventurous one?"));
        System.out.println("The currently playable races and professions are: ");
        while (!raceConfirmed || !professionConfirmed) {
            System.out.println(arrangeRacesAndProfessions());
            String selection = getInput("What race or profession do you want to know more about:");
            raceSelection(selection);
            professionSelection(selection);
        }
        System.out.println("And now, " + playerField.name() + ", you set off on your journey with... ");
        System.out.println(displayStats(playerField.stats(), true));
        System.out.println(displayInventory(playerField.getInventory(), true, true));
    }

    // MODIFIES: this
    // EFFECTS: will set the race of playerField to selection if confirmed by the user.
    //          Must be updated as new Professions are added.
    private void raceSelection(String selection) {
        if ("HUMAN".equalsIgnoreCase(selection)) {
            Human human = new Human();
            getRaceInfo(human);
            confirmRace(human);
            if (raceConfirmed) {
                System.out.println("Hmf, that isn't very interesting, although it is a good choice.\n");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: will set the profession of playerField to selection if confirmed by the user.
    //          Must be updated as new Professions are added.
    private void professionSelection(String selection) {
        if ("RANGER".equalsIgnoreCase(selection)) {
            Ranger ranger = new Ranger();
            getProfessionInfo(ranger);
            confirmProfession(ranger);
            if (professionConfirmed) {
                System.out.println("Sneaky sneaky you go through the forest, go get some yummy dinner for yourself.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: queries the user if they want to confirm the parameter race
    private void confirmRace(Race race) {
        try {
            raceConfirmed = getBoolean("Do you want to choose " + race.getSpecies()
                    + " as your race? [Yes]/[No]");
            if (raceConfirmed) {
                playerField.setRace(race);
            }
        } catch (UserInputException e) {
            confirmRace(race);
        }
    }

    // MODIFIES: this
    // EFFECTS: queries the user if they want to confirm the parameter profession
    private void confirmProfession(Profession profession) {
        try {
            professionConfirmed = getBoolean("Do you want to choose " + profession.getTitle()
                    + " as your profession? [Yes]/[No]");
            if (professionConfirmed) {
                playerField.setProfession(profession);
                playerField.addToInventory(new FluffyHat());
            }
        } catch (UserInputException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: sets a single string of all the available races and professions in side by side lists. Note this must be
    //          updated as new races and professions are added.
    private String arrangeRacesAndProfessions() {
        // Races column is 11 units across, Professions column is 17 units across. Total width is 31 units
        return  "\n|-----------|-----------------|\n"
                + "|   Races   |   Professions   |\n"
                + "|===========|=================|\n"
                + "|   Human   |     Ranger      |\n"
                + "|-----------|-----------------|\n";
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
        System.out.println(displayAbilities(prof.getAbilities(), true));
    }

    // EFFECTS: returns the player field;
    public Player createdPlayer() {
        return playerField;
    }
}