package ui.gui;

import model.ItemMatrix;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;

public class InventoryGUI extends JPanel {
    private static final Color LINE_COLOR = new Color(100,110,120);
    private static final Color BG_COLOR = new Color(200,200,200);
    private static final int XTR_Y = 25;

    private ItemMatrix inventory;
    private TableModel itemIcons;
    private JTable inventoryTable;

    // EFFECTS: sets the inventory field and calls the draw inventory method.
    public InventoryGUI(ItemMatrix inventory) {
        this.inventory = inventory;
        setBorder(BorderFactory.createLineBorder(LINE_COLOR));
        setBounds(2 * AdvVoyGUI.getFrameWidth() / 3 - 1, AdvVoyGUI.getFrameHeight() / 2 - XTR_Y,
                AdvVoyGUI.getFrameWidth() / 3, AdvVoyGUI.getFrameHeight() / 2 + XTR_Y);
        setBackground(BG_COLOR);

        JLabel inventoryText = new JLabel("Inventory: ");
        inventoryText.setFont(new Font("Arial",Font.PLAIN,15));
        add(inventoryText);

        drawInventory();
    }

    // EFFECTS: draws the inventory as a JTable, and adds it to the InventoryGUI
    public void drawInventory() {
        itemIcons = new InventoryTableModel(inventory);
        inventoryTable = new JTable(itemIcons);
        inventoryTable.setDefaultRenderer(ImageIcon.class, new ItemRenderer());

        inventoryTable.setPreferredSize(new Dimension(AdvVoyGUI.getFrameWidth() / 3 - 10,
                AdvVoyGUI.getFrameHeight() / 2 - 10));
        inventoryTable.setRowHeight((AdvVoyGUI.getFrameHeight() / 2) / 4);
        inventoryTable.setBorder(BorderFactory.createLineBorder(LINE_COLOR));

        add(inventoryTable,BorderLayout.EAST);
    }

    // MODIFIES: this
    // EFFECTS: resets the table model so that a tableChangeEvent is fired as the contents are updated.
    public void refresh() {
        itemIcons = new InventoryTableModel(inventory);
    }

    // Private renderer class to display inventory item icons
    private static class ItemRenderer extends JLabel implements TableCellRenderer {

        // MODIFIES: this
        // EFFECTS: returns the ItemRenderer with the Icon set to the item Icon from the table.
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            this.setIcon((ImageIcon) value);
            this.setBackground(BG_COLOR);
            this.setOpaque(true);
            return this;
        }
    }
}
