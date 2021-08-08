package ui.gui;

import model.ItemMatrix;
import model.items.Item;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class InventoryTableModel extends DefaultTableModel {
    private ItemMatrix inventory;

    public InventoryTableModel(ItemMatrix inventory) {
        this.inventory = inventory;
    }

    @Override
    public int getRowCount() {
        return 4;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return ImageIcon.class;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Item itemInInventory = inventory.in(rowIndex,columnIndex);
        if (itemInInventory != null) {
            return inventory.in(rowIndex, columnIndex).getItemIcon();
        }
        return new ImageIcon();
    }
}
