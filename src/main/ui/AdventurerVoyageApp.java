package ui;

import java.util.concurrent.TimeUnit;

/**
 * AdventurerVoyageApp handles the story and calling the appropriate ui handler for the story event happening.
 */

public class AdventurerVoyageApp {
    public AdventurerVoyageApp() {
        System.out.println("Welcome to Adventurer's Voyage!\n-------------------------------");
        System.out.println("You, yes YOU, are about to embark on a peculiar voyage of untold perils, ingenious\n"
                + " discoveries, and fights against unknown monsters. Clearly at this point, the perils are\n"
                + " untold as I haven't written them, and the monsters are unknown since I haven't created them.\n"
                + " But this is all besides the point. For now, you should decide on who you really are....\n");
        new CharacterCreator();
        System.out.println("Wow it really worked first try!!");
    }
}

