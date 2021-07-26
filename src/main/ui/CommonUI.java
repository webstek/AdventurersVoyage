package ui;

import java.util.ArrayList;
import java.util.Scanner;
import model.Statistics;
import model.ItemMatrix;
import model.entities.Entity;
import model.items.Item;
import model.professions.Profession;
import model.abilities.Ability;
import model.exceptions.UserInputException;


/**
 * Abstract class that holds many common conversions from the model classes to strings.
 */

public abstract class CommonUI {
    static final String[] STAT_NAMES = new String[]{"Intelligence","Strength","Speed","Dexterity","Stealth"};
    Scanner sc = new Scanner(System.in);

    // EFFECTS: returns the full line of input from the scanner that a user enters
    public String getInput(String prompt) {
        System.out.println(prompt);
        return sc.next();
    }

    // EFFECTS: returns a boolean after the user is prompted by the string parameter
    public boolean getBoolean(String prompt) throws UserInputException {
        System.out.println(prompt);
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
        StringBuilder formattedStats = new StringBuilder("|      Statistics Summary     |\n"
                + "|-----------------------------|\n");
        for (int i = 0; i < 5; i++) {
            formattedStats.append("| ").append(STAT_NAMES[i]).append(": ").append(stats.in(0, i))
                    .append(" + ").append(stats.in(1, i)).append("\n");
        }
        formattedStats.append("|-----------------------------|");
        if (withHealth) {
            formattedStats.append("\n| Health: ").append(stats.in(0, 5)).append("\n| Mana: ").append(stats.in(0, 6))
            .append("\n|-----------------------------|");
        }
        return formattedStats.toString();
    }

    // EFFECTS: displays the Health and mana of an Entity object
    public String displayHealthAndMana(Entity entity) {
        return ("\n| " + entity.name() + " | " + entity.stats().in(0,5) + "Hp | "
                + entity.stats().in(0,6) + "Mp |");
    }

    // EFFECTS: returns a nicely formatted version of the bonus stats fields in a single string.
    public String displayBonus(Statistics bnsStats) {
        // Width is 31 units across
        StringBuilder formattedStats = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            formattedStats.append("| ").append(STAT_NAMES[i]).append(": +").append(bnsStats.in(1, i)).append("\n");
        }
        formattedStats.append("|-----------------------------|");
        return formattedStats.toString();
    }

    // EFFECTS: returns a nicely formatted version of all the abilities of a profession in a single string.
    public String displayAbilities(Profession prof, boolean includeDescription) {
        StringBuilder abilitySummary = new StringBuilder("|           Abilities            \n|----------------"
                + "-------------|\n");
        for (Ability ability : prof.getAbilities()) {
            abilitySummary.append(displayAbility(ability, includeDescription)).append("\n");
        }
        abilitySummary.append("|-----------------------------|");
        return abilitySummary.toString();
    }

    // EFFECTS: returns a well formatted summary of a given ability.
    public String displayAbility(Ability a, boolean includeDescription) {
        StringBuilder abilitySummary = new StringBuilder("| " + a.name() + " | "
                + a.fstCost() + "spd | " + -a.getStatsEffect().in(0,6) + "mp");
        if (includeDescription) {
            abilitySummary.append("\n").append(a.getDescription());
        }
        return abilitySummary.toString();
    }

    // EFFECTS: returns a well formatted summary of an Inventory.
    public String displayInventory(ItemMatrix inventory, boolean includeDescription, boolean includeStats) {
        StringBuilder inventorySummary = new StringBuilder("| Inventory:\n|-----------------------------|\n");
        for (int pos = 1; pos <= inventory.length(); pos++) {
            Item item = inventory.inPos(pos);
            inventorySummary.append(item.name()).append(":\n");
            if (includeDescription) {
                inventorySummary.append(item.description()).append("\n");
            }
            if (includeStats) {
                inventorySummary.append(displayBonus(item.stats())).append("\n");
            }
            inventorySummary.append("|-----------------------------|\n");
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

}