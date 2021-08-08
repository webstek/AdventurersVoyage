package ui.gui;

import model.GameState;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * GUI class for Adventurer's Voyage.
 */

public class AdvVoyGUI extends JFrame {
    private static final Dimension FRAME_SIZE = new Dimension(1152, 648);

    private PlayerGUI playerSummary;
    private InventoryGUI playerInventory;
    private TextInOutGUI textInOut;
    private JPanel detailsWindow;

    private GameState gs;

    // EFFECTS: initializes all GUI components
    public AdvVoyGUI() {
        super("Adventurer's Voyage");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(FRAME_SIZE);
        getContentPane().setBackground(new Color(240, 235, 225));
        setUndecorated(true);
        setLayout(null);

        // TODO currently set to a test file for ui testing purposes. Replace with placeholder file when UI complete.
        JsonReader initReader = new JsonReader("./data/testGameState.json");
        try {
            gs = initReader.read();
            gs.player().resetCombatActions();
        } catch (IOException e) {
            System.out.println("Unable to initialize game-state.");
        }
        playerSummary = new PlayerGUI(gs);
        playerInventory = new InventoryGUI(gs.player().getInventory());
        textInOut = new TextInOutGUI();

        setPlayingUI();

        centerOnScreen();
        setVisible(true);
    }

    private void setPlayingUI() {
        add(textInOut);
        add(playerSummary);
        add(playerInventory);
        textInOut.setVisible(true);
        playerSummary.setVisible(true);
        playerInventory.setVisible(true);
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
