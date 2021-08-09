package ui.gui;

import model.GameState;
import persistence.JsonReader;
import ui.CommonUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * GUI class for Adventurer's Voyage.
 */

public class AdvVoyGUI extends JFrame {
    private static final Dimension FRAME_SIZE = new Dimension(1152, 648);

    private CharacterCreatorGUI characterCreator;
    private PlayerGUI playerSummary;
    private InventoryGUI playerInventory;
    private TextInOutGUI textInOut;
    private JPanel detailsWindow;

    private GameState gs;
    private boolean loadingFromSave = false;

    // EFFECTS: initializes all GUI components
    public AdvVoyGUI() {
        super("Adventurer's Voyage");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(FRAME_SIZE);
        setUndecorated(true);
        setLayout(null);

        JsonReader initReader = new JsonReader("./data/testGameState.json");
        try {
            gs = initReader.read();
            gs.player().resetCombatActions();
        } catch (IOException e) {
            System.out.println("Unable to initialize game-state.");
        }
        characterCreator = new CharacterCreatorGUI(this, gs);
        playerSummary = new PlayerGUI(gs.player());
        playerInventory = new InventoryGUI(gs.player().getInventory());
        textInOut = new TextInOutGUI(this, gs);

        init();

        centerOnScreen();
        setVisible(true);
    }

    private void init() {
        queryLoadFromSave();
        if (loadingFromSave) {
            loadFromSave();
        } else {
            characterCreationGUI();
        }
    }

    // MODIFIES: this
    // EFFECTS: opens a JOptionPane window and asks the user if they want to load a saved game.
    private void queryLoadFromSave() {
        JOptionPane saveQueryFrame = new JOptionPane();
        int o = JOptionPane.showConfirmDialog(saveQueryFrame,"Do you want to load a saved game?");
        if (o == JOptionPane.YES_OPTION) {
            loadingFromSave = true;
        }
        saveQueryFrame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates a popup that queries the user to load their data from a save, or not to.
    private void loadFromSave() {
        JsonReader saveReader = new JsonReader("./data/gameState.json");
        try {
            gs = saveReader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        playerSummary = new PlayerGUI(gs.player());
        playerInventory = new InventoryGUI(gs.player().getInventory());
        textInOut = new TextInOutGUI(this, gs);
        gs.player().resetCombatActions();
        playingGUI();
    }

    // MODIFIES: this
    // EFFECTS: refreshes the values in the playingGUI components.
    public void refreshPlayerGUI() {
        playerSummary.refresh();
        playerInventory.refresh();
    }

    // MODIFIES: this
    // EFFECTS: adds the UI objects for the user to create their character.
    private void characterCreationGUI() {
        add(characterCreator);
    }

    // MODIFIES: this
    // EFFECTS: adds the UI objects to play the game and removes the character creation UI objects.
    public void playingGUI() {
        add(textInOut);
        add(playerSummary);
        add(playerInventory);
        characterCreator.setVisible(false);
        gs.handleState();
        textInOut.updateTextOut();
    }

    // MODIFIES: this
    // EFFECTS: location of frame is set so frame is centered on desktop screen.
    // Based on centreOnScreen method in SpaceInvaders
    private void centerOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
    }

    // EFFECTS: returns the frame height
    public static int getFrameHeight() {
        return FRAME_SIZE.height;
    }

    // EFFECTS: returns the frame width
    public static int getFrameWidth() {
        return FRAME_SIZE.width;
    }
}
