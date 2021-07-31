package ui;

import model.entities.*;

/**
 * AdventurerVoyageApp handles the story and calling the appropriate ui handler for the story event happening.
 */

public class AdventurerVoyageApp {
    private static Player PLAYER;
    private int adventureNumber = 0;

    // MODIFIES: this
    // EFFECTS: calls the main method that tell the adventure story
    public AdventurerVoyageApp() {
        goAdventuring1();
    }

    // EFFECTS
    private void goAdventuring1() {
        System.out.println("Welcome to Adventurer's Voyage!\n-------------------------------");
        loadFromSaveOption();
        System.out.println("You, yes YOU, are about to embark on a peculiar voyage of untold perils, ingenious\n"
                + " discoveries, and fights against unknown monsters. Clearly, at this point, the perils are\n"
                + " mostly as I haven't written all of them, and the monsters are unknown since I you haven't faced any"
                + " yet.\n HAH! But this is all besides the point. For now, you must decide who you really are....\n");
        CharacterCreator cc = new CharacterCreator();
        PLAYER = cc.createdPlayer();
        System.out.println("To be continued.......                            Right now!");
        String dscrpt1 = "\nEvery adventure story needs a training arc, and this one is yours. Just to make sure "
                + "you are capable enough to \nbe an Adventurer, there is a test you must pass. And that test"
                + " is to defeat the opponent in front of you!";
        new CombatHandler(PLAYER, new Enemy[]{new CaveSlug()}, dscrpt1);
        System.out.println("Congratulations, you have just slapped a cave slug to death! Not the first thing you "
                + "think of doing when you wake up, but it was fun nonetheless.");

        adventureNumber++;
        System.out.println("\n\n Thanks for playing! Much, much, MUCH, more content is in the works!  :O");
    }

    // TODO implement data persistence (i.e. save player data and adventureNumber to file).
    // MODIFIES: this
    // EFFECTS: queries the user to choose if they want to load save data from the hard drive and does so if they wish.
    private void loadFromSaveOption() {
        // Stub
    }
}

