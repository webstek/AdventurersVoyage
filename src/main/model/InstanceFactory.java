package model;

import model.items.Item;
import model.professions.Profession;
import model.races.Race;

public class InstanceFactory {
    // EFFECTS: returns an instance of the desired item from a string name of an item class already defined.
    public Object fromString(String dir, String className) {
        Object instance = null;
        try {
            Class<?> classPlaceHolder = Class.forName(dir + className);
            instance = classPlaceHolder.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }

    // EFFECTS: returns an instance of the desired item from a string name of an item class already defined.
    public Item itemInstance(String itemName) {
        InstanceFactory itemFactory = new InstanceFactory();

        Object maybeItemInstance = itemFactory.fromString("model.items.",itemName);
        if (maybeItemInstance instanceof Item) {
            return (Item) maybeItemInstance;
        }
        return null;
    }

    // EFFECTS: returns an instance of the desired race from a string name of a race class already defined.
    //          returns null if the input string does not match with a defined race.
    public Race raceInstance(String raceName) {
        InstanceFactory raceFactory = new InstanceFactory();

        Object maybeRaceInstance = raceFactory.fromString("model.races.",raceName);
        if (maybeRaceInstance instanceof Race) {
            return (Race) maybeRaceInstance;
        }
        return null;
    }

    // EFFECTS: returns an instance of the desired profession from a string name of a profession class already defined.
    //          returns null if the input string does not match with a defined profession.
    public Profession professionInstance(String professionName) {
        InstanceFactory professionFactory = new InstanceFactory();

        Object maybeProfessionInstance = professionFactory.fromString("model.professions.",professionName);
        if (maybeProfessionInstance instanceof Profession) {
            return (Profession) maybeProfessionInstance;
        }
        return null;
    }
}
