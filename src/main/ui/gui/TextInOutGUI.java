package ui.gui;

import model.GameState;
import ui.CommonUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TextInOutGUI extends JPanel {
    private static final Color LINE_COLOR = new Color(100,110,120);
    private static final int TEXT_IO_HEIGHT = 3 * AdvVoyGUI.getFrameHeight() / 4;
    private static final int TEXT_IO_WIDTH = 2 * AdvVoyGUI.getFrameWidth() / 3;
    private static final CommonUI CMN_UI = new CommonUI();

    private JLabel textOut = new JLabel();
    private JTextField textIn = new JTextField();
    private GameState gs;
    private AdvVoyGUI mainGUI;

    // EFFECTS: initializes the text in and out components
    public TextInOutGUI(AdvVoyGUI mainGUI, GameState gs) {
        this.mainGUI = mainGUI;
        this.gs = gs;
        setBackground(new Color(200,200,200));
        setBorder(BorderFactory.createLineBorder(LINE_COLOR));
        setBounds(0, 0, TEXT_IO_WIDTH, TEXT_IO_HEIGHT);

        textOutInit();
        textInInit();

        add(textOut);
        add(textIn);
    }

    // MODIFIES: this, textOut
    // EFFECTS: initializes the textOut field to be empty with the correct background and border colours
    private void textOutInit() {
        textOut.setPreferredSize(new Dimension(TEXT_IO_WIDTH - 12, TEXT_IO_HEIGHT - 64));
        textOut.setBackground(new Color(240,240,240));
        textOut.setOpaque(true);
        textOut.setHorizontalAlignment(SwingConstants.LEFT);
        textOut.setVerticalAlignment(SwingConstants.TOP);
        textOut.setBorder(BorderFactory.createLineBorder(LINE_COLOR));
    }

    // MODIFIES: this, textIn
    // EFFECTS: initializes the
    private void textInInit() {
        textIn.setPreferredSize(new Dimension(TEXT_IO_WIDTH - 12, 48));
        textIn.setBackground(new Color(230,230,240));
        textIn.setFocusable(true);
        textIn.addKeyListener(new KeyHandler());
    }

    // MODIFIES: gs
    // EFFECTS: fires the current text in the textIn JTextField to the actionText field in gs
    private void fireToActionText(GameState gs) {
        String actionText = textIn.getText();
        gs.setActionText(actionText);
        System.out.println(gs.getActionText());
    }

    // MODIFIES: this
    // EFFECTS: clears the textIn box
    private void clearTextIn() {
        textIn.setText("");
    }

    // MODIFIES: this
    // EFFECTS: displays the GameState's displayText on the textOut JLabel
    public void updateTextOut() {
        CMN_UI.setTextOut(textOut, gs.getDisplayText());
    }

    // MODIFIES: this, gs
    // EFFECTS: fires the current text in the textIn JTextField to the actionText field in gs. Clears the textIn box
    //          when the enter key is pressed and fakeTextIn replaces the textIn component in this.
    private class KeyHandler extends KeyAdapter {
        private boolean controlDown;

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                fireToActionText(gs);
                clearTextIn();
                gs.handleState();
                updateTextOut();
                mainGUI.setEnemies(gs.getEnemies());
                mainGUI.tryDetailsPanel();
                mainGUI.refreshPlayerGUI();
            } else if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                System.out.println("caught control");
                controlDown = true;
            } else if (e.getKeyCode() == KeyEvent.VK_S && controlDown) {
                gs.save();
                notifySaved();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                controlDown = false;
            }
        }
    }

    // EFFECTS: pops up a message JOptionPane to notify the user that their game has been saved.
    public void notifySaved() {
        JOptionPane saveFrame = new JOptionPane();
        JOptionPane.showMessageDialog(saveFrame, "Your game has been saved.");
    }

    // EFFECTS: returns the mainGUI object
    public AdvVoyGUI getMainGUI() {
        return mainGUI;
    }
}
