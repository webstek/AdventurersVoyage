package ui.gui;

import model.GameState;
import model.InstanceFactory;
import model.entities.Player;
import ui.CharacterCreator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Character CreatorGUI shown at the start of the game for the user to choose their character.
public class CharacterCreatorGUI extends JPanel {
    private static final InstanceFactory FACTORY = new InstanceFactory();
    private static final CharacterCreator CC_UI = new CharacterCreator();
    private static final Color LINE_COLOR = new Color(100,110,120);
    private static final Color TABLE_BG_COLOR = new Color(100,100,150);
    private static final int BONUS_PANEL_X_OFFSET = AdvVoyGUI.getFrameWidth() / 2 - 1;

    private Player player;
    private AdvVoyGUI mainGUI;
    private JScrollPane selectionPanel;
    private JLabel raceBonusPanel;
    private JLabel professionBonusPanel;
    private JPanel nameAndConfirmPanel;

    // EFFECTS: sets the player field and basic properties of the Character Creator GUI.
    public CharacterCreatorGUI(AdvVoyGUI mainGUI, GameState gs) {
        this.mainGUI = mainGUI;
        this.player = gs.player();

        setLayout(null);
        setBounds(0,0,AdvVoyGUI.getFrameWidth(),AdvVoyGUI.getFrameHeight());
        setBorder(BorderFactory.createLineBorder(LINE_COLOR));

        initSelectionPanel();
        initBonusesPanel();
        initNameAndConfirmPanel();

        add(selectionPanel);
        add(raceBonusPanel);
        add(professionBonusPanel);
        add(nameAndConfirmPanel);
    }
    
    // MODIFIES: this, selectionPanel, gs, player
    // EFFECTS: initializes the selection Panel to have the correct race and profession selection buttons
    private void initSelectionPanel() {
        String[] columnHeaders = {"Races","Professions"};
        String[][] data =
                {{"Human","Ranger"},
                 {"Elf","Fighter"}};
        DefaultTableModel selectionModel = new DefaultTableModel(data, columnHeaders);

        JTable selectionTable = new JTable(selectionModel);
        selectionTable.getTableHeader().setReorderingAllowed(false);
        selectionTable.setRowHeight(50);

        Action setValueOnClick = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int column = Integer.parseInt(e.getActionCommand().substring(0,1));
                String value = e.getActionCommand().substring(1);
                setValue(column, value);
            }
        };
        SelectionButtonColumn selectColumn1 = new SelectionButtonColumn(selectionTable,setValueOnClick,0);
        SelectionButtonColumn selectColumn2 = new SelectionButtonColumn(selectionTable,setValueOnClick,1);

        selectionPanel = new JScrollPane(selectionTable);
        selectionPanel.setBounds(0,0,AdvVoyGUI.getFrameWidth() / 2, AdvVoyGUI.getFrameHeight());
    }

    // MODIFIES: this, gs, player
    // EFFECTS: sets the player's race or profession. Column value 0 indicates setting race, while 1 is for profession.
    private void setValue(int column, String value) {
        if (column == 0) {
            player.setRace(FACTORY.raceInstance(value));
            refreshRacePanel();
        } else if (column == 1) {
            player.setProfession(FACTORY.professionInstance(value));
            refreshProfessionPanel();
        } else {
            System.out.println("Invalid column number, no value set.");
        }
    }

    // MODIFIES: this, raceBonusPanel, professionBonusPanel
    // EFFECTS: initializes the bonusPanels which displays the current Race and Profession bonuses selected.
    private void initBonusesPanel() {
        raceBonusPanel = new JLabel("| Race Attributes ----------- |");
        raceBonusPanel.setBounds(BONUS_PANEL_X_OFFSET, 0, AdvVoyGUI.getFrameWidth() / 4,
                2 * AdvVoyGUI.getFrameHeight() / 3);
        raceBonusPanel.setBackground(new Color(250,250,250));
        raceBonusPanel.setOpaque(true);
        raceBonusPanel.setHorizontalAlignment(SwingConstants.LEFT);
        raceBonusPanel.setVerticalAlignment(SwingConstants.TOP);
        raceBonusPanel.setBorder(BorderFactory.createLineBorder(LINE_COLOR));

        professionBonusPanel = new JLabel("| Profession Bonuses -------- |");
        professionBonusPanel.setBounds(3 * BONUS_PANEL_X_OFFSET / 2, 0, AdvVoyGUI.getFrameWidth() / 4 + 2,
                2 * AdvVoyGUI.getFrameHeight() / 3);
        professionBonusPanel.setBackground(new Color(250,250,250));
        professionBonusPanel.setOpaque(true);
        professionBonusPanel.setHorizontalAlignment(SwingConstants.LEFT);
        professionBonusPanel.setVerticalAlignment(SwingConstants.TOP);
        professionBonusPanel.setBorder(BorderFactory.createLineBorder(LINE_COLOR));
    }

    // MODIFIES: this, nameAndConfirmPanel
    // EFFECTS: initializes the name box, and Start adventure! button in the correct position.
    private void initNameAndConfirmPanel() {
        nameAndConfirmPanel = new JPanel();
        nameAndConfirmPanel.setBounds(BONUS_PANEL_X_OFFSET, 2 * AdvVoyGUI.getFrameHeight() / 3 - 3,
                AdvVoyGUI.getFrameWidth() / 2, AdvVoyGUI.getFrameHeight() / 3);
        JLabel nameLabel = new JLabel("Name: ");
        nameLabel.setFont(new Font("Ariel", Font.PLAIN,20));
        nameAndConfirmPanel.add(nameLabel, BorderLayout.CENTER);

        JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(AdvVoyGUI.getFrameWidth() / 2 - 85, 28));
        nameAndConfirmPanel.add(nameField, BorderLayout.CENTER);

        JButton confirmButton = new JButton("Start Adventuring!");
        confirmButton.addActionListener(e -> {
            String name = nameField.getText();
            player.setName(name);
            mainGUI.playingGUI();
        });
        confirmButton.setPreferredSize(new Dimension(300, 50));
        nameAndConfirmPanel.add(confirmButton);
    }

    // MODIFIES: this
    // EFFECTS: refreshes the race bonus string that is being displayed for the user.
    private void refreshRacePanel() {
        String raceBonusString = CC_UI.getRaceInfo(player.getRace());
        CC_UI.setTextOut(raceBonusPanel, raceBonusString);
    }

    // MODIFIES: this
    // EFFECTS; refreshes the profession bonus string that is being displayed for the user.
    private void refreshProfessionPanel() {
        String professionBonusString = CC_UI.getProfessionInfo(player.getProfession());
        CC_UI.setTextOut(professionBonusPanel,professionBonusString);
    }
}