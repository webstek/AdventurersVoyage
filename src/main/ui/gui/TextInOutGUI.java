package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TextInOutGUI extends JPanel {
    private static final Color LINE_COLOR = new Color(100,110,120);
    private static final int TEXT_IO_HEIGHT = 3 * AdvVoyGUI.getFrameHeight() / 4;
    private static final int TEXT_IO_WIDTH = 2 * AdvVoyGUI.getFrameWidth() / 3;

    private JLabel textOut = new JLabel();
    private JTextField textIn = new JTextField();

    // EFFECTS: initializes the text in and out components
    public TextInOutGUI() {
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
    }

    // MODIFIES: this, textOut
    // EFFECTS: displays the String in the argument with \n creating a new line in the textOut box.
    public void setTextOut(String str) {
        remove(textOut);
        String formattedStr = str.replace("\n","<br>");
        formattedStr = "<html>" + formattedStr + "</html>";
        textOut.setText(formattedStr);
        add(textOut,0);
    }

    // KeyHandler class adapted from CPSC 210 Space Invaders project.
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                // stub
            }
        }
    }
}
