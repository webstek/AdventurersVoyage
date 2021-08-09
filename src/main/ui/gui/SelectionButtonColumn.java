package ui.gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * A SelectionButtonColumn class based on the ButtonColumn class by Rob Camick which can be found here:
 * <href>https://tips4java.wordpress.com/2009/07/12/table-button-column/</href>
 * Provides a renderer and editor that appears like a JButton. Clicking the button creates an action with the value of
 * the model in the clicked cell as the command.
 *
 * The table model value must be a String to display as the text on the button.
 */
class SelectionButtonColumn extends AbstractCellEditor
        implements TableCellRenderer, TableCellEditor, ActionListener, MouseListener {
    private JTable table;
    private Action action;
    private Border originalBorder;


    private JButton renderButton;
    private JButton selectButton;
    private String selectValue;
    private boolean isButtonColumnSelectAvailable;

    /**
     * Simplified Constructor, removing focus highlighting and mnemonic.
     * <p>
     * Create the SelectionButtonColumn to be used as a renderer and editor. The
     * renderer and editor will automatically be installed on the TableColumn
     * of the specified column.
     *
     * @param table  the table containing the button renderer/editor
     * @param action the Action to be invoked when the button is invoked
     * @param column the column to which the button renderer/editor is added
     */
    public SelectionButtonColumn(JTable table, Action action, int column) {
        this.table = table;
        this.action = action;

        renderButton = new JButton();
        selectButton = new JButton();
        selectButton.addActionListener(this);
        originalBorder = selectButton.getBorder();

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(column).setCellRenderer(this);
        columnModel.getColumn(column).setCellEditor(this);
        table.addMouseListener(this);
    }

    // MODIFIES: this, selectButton
    // EFFECTS: returns the selectButton JButton component to use when cell is clicked.
    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column) {
        selectButton.setText(value.toString());
        this.selectValue = (String) value;
        return selectButton;
    }

    // EFFECTS: returns the String value from the table model.
    @Override
    public String getCellEditorValue() {
        return selectValue;
    }


    // Implement TableCellRenderer interface
    // MODIFIES: this, renderButton
    // EFFECTS: sets the button to be rendered in the table.
    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            renderButton.setForeground(table.getSelectionForeground());
            renderButton.setBackground(table.getSelectionBackground());
        } else {
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor("Button.background"));
        }
        renderButton.setBorder(originalBorder);

        renderButton.setText(value.toString());

        return renderButton;
    }


    // Implement ActionListener interface
    // The button has been pressed. Stop editing and invoke the custom Action
    // EFFECTS: calls the action associated with pressing the button
    public void actionPerformed(ActionEvent e) {
        String value = selectValue;
        int column = table.getEditingColumn();
        fireEditingStopped();

        // Sets the event command to be performed to be the column# concatenated with the model value
        ActionEvent event = new ActionEvent(table, ActionEvent.ACTION_PERFORMED, column + value);
        action.actionPerformed(event);
    }


    // Implement MouseListener interface
    // MODIFIES: this
    // EFFECTS: signals the selectButton is available when pressed, and unavailable when not pressed.
    /*
     *  When the mouse is pressed the selectButton becomes available. If you then drag
     *  the mouse to another cell before releasing it, the editor is still
     *  active. Make sure editing is stopped when the mouse is released.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (table.isEditing() && table.getCellEditor() == this) {
            isButtonColumnSelectAvailable = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isButtonColumnSelectAvailable && table.isEditing()) {
            table.getCellEditor().stopCellEditing();
        }
        isButtonColumnSelectAvailable = false;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
