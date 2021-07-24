package ui;

import model.Player;
import model.items.BirchBow;

import java.util.Scanner;

public class AdventurerVoyageApp {
    public void characterCreation() {
        System.out.println("Enter the race of your character:");
        String species = getString();
        System.out.println("So you are a " + species + " then.");
        System.out.println("Enter the profession of your character:");
        String title = getString();
        System.out.println("Hmmm, " + title + " a good choice indeed.");
        System.out.println("What is your name, adventurer?");
        String name = getString();

        Player player = new Player(name, species, title);
        System.out.println("This is your voyage, " + player.getName() + ", the "
                + player.getRace().getSpecies() + " " + player.getPrfsn().getTitle() + ". Enjoy it while you can.\n");

        System.out.println("You have: " + player.getRace().health() + " hp and " + player.getRace().mana() + " mp.");

        player.addToInventory(new BirchBow());
        player.displayInventory();
        player.displayStats();
    }

    private String getString() {
        Scanner sc = new Scanner(System.in);
        return sc.next() + sc.nextLine();
    }
}
