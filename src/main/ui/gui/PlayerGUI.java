package ui.gui;

import model.GameState;
import model.Statistics;
import model.entities.Player;

import javax.swing.*;
import java.awt.*;

public class PlayerGUI extends JPanel {
    static final String[] STAT_NAMES = new String[]{"Intelligence","Strength","Speed","Dexterity","Stealth"};
    private static final int FONT_SIZE = 15;
    private static final int STAT_OFFSET = 10;
    private static final Color LINE_COLOR = new Color(100,110,120);
    private static final Color RESOURCE_BAR_BG_COLOR = new Color(80,80,80);
    private static final Color FONT_COLOR = new Color(0,0,0);
    int maxHealth = 0;
    int maxMana = 0;
    int maxCAPx = 0;

    private Player player;

    // EFFECTS: sets the size of the playerSummary window, updates the player to have its summary displayed.
    public PlayerGUI(Player player) {
        this.player = player;
        setBackground(new Color(200,200,200));
        setBorder(BorderFactory.createLineBorder(LINE_COLOR));
        setBounds(0, 3 * AdvVoyGUI.getFrameHeight() / 4, 2 * AdvVoyGUI.getFrameWidth() / 3,
                AdvVoyGUI.getFrameHeight() / 4);
    }

    // MODIFIES: g
    // EFFECTS: called to draw the player summary.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawPlayerSummary(g);
    }

    // MODIFIES: g
    // EFFECTS: draws the player summary onto g.
    private void drawPlayerSummary(Graphics g) {
        g.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        drawStats(g);
        drawHpBar(g);
        drawMpBar(g);
        drawCaBar(g);
        drawXpBar(g);
    }

    // MODIFIES: g
    // EFFECTS: draws the Statistics portion of the player summary onto g.
    private void drawStats(Graphics g) {
        Statistics stats = player.stats();
        for (int i = 0; i < 5; i++) {
            String str = STAT_NAMES[i] + ": " + stats.in(0,i) + "+" + stats.in(1,i);
            g.drawString(str, STAT_OFFSET, i * (AdvVoyGUI.getFrameHeight() / 20) + 22);
        }
        g.setColor(LINE_COLOR);
        g.fillRect(170, 0,1,AdvVoyGUI.getFrameHeight() / 4);
    }

    // MODIFIES: g
    // EFFECTS: draws the Hp bar and health text onto g.
    private void drawHpBar(Graphics g) {
        int health = player.stats().in(0,5);
        if (health > maxHealth) {
            maxHealth = health;
        }
        g.setColor(FONT_COLOR);
        g.drawString("Health", 178, 40);
        g.setColor(RESOURCE_BAR_BG_COLOR);
        g.fillRect(233,18,maxHealth + 4, 34);
        g.setColor(new Color(225,30,10));
        g.fillRect(235,20,health,30);
    }

    // MODIFIES: g
    // EFFECTS: draws the Mp bar and mana text onto g.
    private void drawMpBar(Graphics g) {
        int mana = player.stats().in(0,6);
        if (mana > maxMana) {
            maxMana = mana;
        }
        g.setColor(FONT_COLOR);
        g.drawString("Mana", 178, 85);
        g.setColor(RESOURCE_BAR_BG_COLOR);
        g.fillRect(233,65, maxMana + 4, 34);
        g.setColor(new Color(0,170,250));
        g.fillRect(235,67, mana,30);
    }

    // MODIFIES: g
    // EFFECTS: draws the Combat Actions bar and ca text onto g.
    private void drawCaBar(Graphics g) {
        int caPx = 40 * player.getCombatActions();
        if (caPx > maxCAPx) {
            maxCAPx = caPx;
        }
        g.setColor(FONT_COLOR);
        g.drawString("Combat", 178, 125);
        g.drawString("Actions", 178, 138);
        g.setColor(RESOURCE_BAR_BG_COLOR);
        g.fillRect(233,110, maxCAPx + 4, 34);
        g.setColor(new Color(240,180,20));
        g.fillRect(235,112, caPx,30);
    }

    // MODIFIES: g
    // EFFECTS: draws the Xp bar and Level text onto g.
    private void drawXpBar(Graphics g) {
        int xpBarXPos = 2 * AdvVoyGUI.getFrameWidth() / 3 - 70;
        g.setColor(FONT_COLOR);
        g.drawString("Level: " + player.getLevel(), xpBarXPos, 145);
        g.setColor(RESOURCE_BAR_BG_COLOR);
        g.fillRect(xpBarXPos,125, 52, -104);
        g.setColor(new Color(100,180,0));
        g.fillRect(xpBarXPos + 2,123, 48,-1 * player.xp());
    }

    public void refresh() {
        this.paintAll(getGraphics());
    }
}
