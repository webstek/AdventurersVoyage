package ui.gui;

import model.GameState;
import model.entities.Entity;
import persistence.JsonReader;
import ui.CommonUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

/**
 * GUI class for Adventurer's Voyage.
 */

public class AdvVoyGUI extends JFrame {
    private static final Dimension FRAME_SIZE = new Dimension(1152, 648);
    private static final CommonUI CMN_UI = new CommonUI();

    private CharacterCreatorGUI characterCreator;
    private PlayerGUI playerSummary;
    private InventoryGUI playerInventory;
    private TextInOutGUI textInOut;
    private JPanel detailsPanel;
    private JPanel menuPanel;
    private JLabel enemySummaries;

    private GameState gs;
    private boolean loadingFromSave = false;
    private ArrayList<Entity> enemies;

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

        detailsPanel = new JPanel();
        menuPanel = new JPanel();
        enemySummaries = new JLabel();

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

        initMenuPanel();
        detailsPanel.setVisible(false);
        add(detailsPanel);
        detailsPanel.add(enemySummaries);
        add(menuPanel);
        characterCreator.setVisible(false);
        gs.handleState();
        textInOut.updateTextOut();
    }

    // MODIFIES: this, details panel
    // EFFECTS: checks if enemies is not null and draws the details panel if so. Otherwise hides the panel.
    public void tryDetailsPanel() {
        detailsPanel.setBounds(2 * getFrameWidth() / 3, 100, getFrameWidth() / 3, getFrameHeight() / 2 - 25);
        detailsPanel.setBackground(new Color(190,190,200));
        detailsPanel.setLayout(null);

        enemySummaries.setHorizontalAlignment(SwingConstants.LEFT);
        enemySummaries.setVerticalAlignment(SwingConstants.TOP);
        enemySummaries.setFont(new Font("Arial",Font.PLAIN,15));
        enemySummaries.setBounds(120,30,180,90);
        if (enemies != null && gs.player().isInCombat()) {
            enemySummaries.setText("");
            detailsPanel.setVisible(true);
            for (Entity enemy : enemies) {
                drawEnemyStats(enemySummaries, enemy);
            }
        } else {
            detailsPanel.setVisible(false);
        }
    }

    // MODIFIES: detailsPanel
    // EFFECTS: displays the Enemy's name, hp, mp, and ca in detailsPanel
    private void drawEnemyStats(JLabel enemySummaries, Entity enemy) {
        String summaryString = enemySummaries.getText()
                + "\n ----- " + enemy.name() + " ----- \n  Hp: " + enemy.stats().in(0,5) + "\n  Mp: "
                + enemy.stats().in(0,6) + "\n  CA: " + enemy.getCombatActions();
        System.out.println("setting new text");
        CMN_UI.setTextOut(enemySummaries, summaryString);
    }

    // MODIFIES: this
    // EFFECTS: initializes the menuPanel and
    private void initMenuPanel() {
        menuPanel.setBounds(2 * getFrameWidth() / 3, 0, getFrameWidth() / 3, getFrameHeight() / 2 - 25);
        menuPanel.setBackground(new Color(190,190,200));

        JButton playerSummary = new JButton("Player Summary");
        playerSummary.addActionListener(e -> {
            JOptionPane summary = new JOptionPane();
            JOptionPane.showMessageDialog(summary, CMN_UI.playerSummary(gs.player()));
        });
        JButton exit = new JButton("Save and Exit");
        exit.addActionListener(e -> {
            gs.save();
            textInOut.notifySaved();
            dispatchEvent(new WindowEvent(textInOut.getMainGUI(), WindowEvent.WINDOW_CLOSING));
        });
        JButton inventorySummary = new JButton("Inventory Summary");
        inventorySummary.addActionListener(e -> {
            JOptionPane summary = new JOptionPane();
            JOptionPane.showMessageDialog(summary, CMN_UI.displayInventory(gs.player().getInventory(),true));
        });

        menuPanel.add(playerSummary);
        menuPanel.add(exit);
        menuPanel.add(inventorySummary);
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

    // MODIFIES: this
    // EFFECTS: sets the enemies field.
    public void setEnemies(ArrayList<Entity> enemies) {
        this.enemies = enemies;
    }
}
