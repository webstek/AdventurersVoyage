package ui;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

import model.Statistics;
import model.ItemMatrix;
import model.entities.Entity;
import model.entities.Player;
import model.items.Item;
import model.abilities.Ability;
import model.exceptions.UserInputException;
import model.races.Race;

import javax.swing.*;


/**
 * Abstract class that holds many common conversions from the model classes to strings.
 */

public class CommonUI {
    static final String[] STAT_NAMES = new String[]{"Intelligence","Strength","Speed","Dexterity","Stealth"};

    // EFFECTS: returns the first word of input from the scanner that a user enters. Leave null prompt to skip it.
    public String getInput(String prompt) {
        Scanner sc = new Scanner(System.in);
        if (prompt != null) {
            System.out.println(prompt);
        }
        return sc.next();
    }

    // EFFECTS: returns the full line from the scanner that a user enters. Leave null prompt to skip it.
    public String getFullInput(String prompt) {
        Scanner sc = new Scanner(System.in);
        if (prompt != null) {
            System.out.println(prompt);
        }
        return sc.nextLine();
    }

    // EFFECTS: returns a boolean after the user is prompted by the string parameter
    public boolean getBoolean(String prompt) throws UserInputException {
        Scanner sc = new Scanner(System.in);
        if (prompt != null) {
            System.out.println(prompt);
        }
        String input = sc.next();
        if (input.equalsIgnoreCase("TRUE") || input.equalsIgnoreCase("Y")
                || input.equalsIgnoreCase("YES")) {
            return true;
        } else if (input.equalsIgnoreCase("FALSE") || input.equalsIgnoreCase("N")
                || input.equalsIgnoreCase("NO")) {
            return false;
        } else {
            throw new UserInputException("Please enter [Yes] or [No].");
        }
    }

    // EFFECTS: returns a nicely formatted version of all the Stats fields in a single string.
    public String displayStats(Statistics stats, boolean withHealth) {
        // Width is 31 units across
        StringBuilder formattedStats = new StringBuilder("\n| Statistics: --------------- |\n");
        for (int i = 0; i < 5; i++) {
            formattedStats.append("| ").append(STAT_NAMES[i]).append(": ").append(stats.in(0, i))
                    .append(" + ").append(stats.in(1, i)).append("\n");
        }
        if (withHealth) {
            formattedStats.append("| Health: ").append(stats.in(0, 5)).append("\n| Mana: ").append(stats.in(0, 6));
        }
        return formattedStats.toString();
    }

    // EFFECTS: returns a nicely formatted version of the baseTypeStats fields in a single string.
    public String displayTypeStats(Statistics typeStats) {
        // Width is 31 units across
        StringBuilder formattedStats = new StringBuilder("|-----------------------------|");
        for (int i = 0; i < 5; i++) {
            formattedStats.append("\n| ").append(STAT_NAMES[i]).append(": +").append(typeStats.in(0, i));
        }
        return formattedStats.toString();
    }

    // EFFECTS: returns a nicely formatted version of the bonus stats fields in a single string.
    public String displayBonus(Statistics bnsStats) {
        StringBuilder formattedStats = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            formattedStats.append("\n").append("|  ").append(STAT_NAMES[i]).append(": +").append(bnsStats.in(1, i));
        }
        return formattedStats.toString();
    }

    // EFFECTS: displays the Health and mana of an Entity object
    public String displayRaceHealthAndMana(Race race) {
        return "|-----------------------------|\n" + "| Health: " + race.stats().in(0, 5) + "\n| Mana: "
                + race.stats().in(0, 6);
    }

    // EFFECTS: returns a nicely formatted version of all the abilities of a profession in a single string.
    public String displayAbilities(ArrayList<Ability> abilities, boolean includeDescription, boolean includeDamage) {
        StringBuilder abilitySummary = new StringBuilder("\n| Abilities: ---------------- |");
        for (Ability ability : abilities) {
            abilitySummary.append(displayAbility(ability, includeDescription, includeDamage));
        }
        return abilitySummary.toString();
    }

    // EFFECTS: returns a well formatted summary of a given ability.
    public String displayAbility(Ability a, boolean includeDescription, boolean includeDamage) {
        StringBuilder abilitySummary = new StringBuilder("\n| --- " + a.name() + " ---\n| "
                + a.caCost() + "ca, " + -a.getStatsEffect().in(0,6) + "mp");
        if (includeDamage) {
            abilitySummary.append(", ").append(a.getStatsEffect().damage()).append("dmg");
        }
        if (includeDescription) {
            abilitySummary.append("\n| ").append(a.getDescription());
        }
        return abilitySummary.toString();
    }

    // TODO: change to includeStats only if there are non zero bonuses for the item.
    // EFFECTS: returns a well formatted summary of an Inventory.
    public String displayInventory(ItemMatrix inventory, boolean includeDescription) {
        StringBuilder inventorySummary = new StringBuilder("\n| Inventory: ---------------- |");
        for (Item item : inventory) {
            inventorySummary.append("\n| --- ").append(item.name()).append(" ---\n");
            if (item.stats().areNonZeroTypeStats()) {
                inventorySummary.append(displayBonus(item.stats()));
            }
            if (includeDescription) {
                inventorySummary.append("| ").append(item.description());
            }
        }
        return inventorySummary.toString();
    }

    // EFFECTS: returns a nice looking string of the Entities in an ArrayList<Entity> object.
    public String displayEntityArrayList(ArrayList<Entity> list) {
        StringBuilder listSummary = new StringBuilder("| ");
        for (Entity entity : list) {
            listSummary.append(entity.name()).append(" | ");
        }
        return listSummary.toString();
    }

    public String playerSummary(Player player) {
        return "\n| Player Summary ------------ |\n| Name: " + player.name()
                + "\n" + displayStats(player.stats(), true)
                + "\n" + displayAbilities(player.abilities(), true, true)
                + "\n" + displayInventory(player.getInventory(), true);
    }

    // MODIFIES: textOut
    // EFFECTS: displays the String in the argument with \n creating a new line in the textOut box.
    public void setTextOut(JLabel textOut, String str) {
        textOut.setFont(new Font("Arial", Font.PLAIN, 15));
        textOut.setText("");
        String formattedStr = str.replace("\n","<br>");
        formattedStr = "<html>" + formattedStr + "</html>";
        textOut.setText(formattedStr);
    }
}