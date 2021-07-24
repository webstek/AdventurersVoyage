package model;

// TODO Write test class for this class

import model.items.Item;

/**
 * A two dimensional array of fixed maximum size. Intended for use like ArrayList, but with rows and items. Using the
 * remove function will reorder the matrix such that the !null elements are arranged right to left, top to bottom.
 */

public class ItemMatrix {
    protected Object[][] matrixData;
    protected int length = 0;
    private static int maxRows;
    private static int maxColumns;
    private static int maxLength;

    // EFFECTS: creates the 2D object array of rows number of rows, and columns number of columns
    public ItemMatrix(int rows, int columns) {
        matrixData = new Object[rows][columns];
        maxRows = rows;
        maxColumns = columns;
        maxLength = rows * columns;
    }

    // REQUIRES: item parameter object to be in the list
    // EFFECTS: returns an integer array {i, j} of the first instance of parameter item in the ItemMatrix
    //          if the requires invalidated and the item is not in the list, returns {-1, -1}.
    public int[] itemIndex(Item item) {
        for (int i = 0; i < maxRows; i++) {
            for (int j = 0; j < maxColumns; j++) {
                if (matrixData[i][j].equals(item)) {
                    return new int[]{i,j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    // MODIFIES: this
    // EFFECTS: inserts the parameter object E in the first available spot looking from top left across to top right and
    //          and then down in rows and returns true. If no spot is available, returns false.
    public boolean add(Item item) {
        if (canInsertElement()) {
            for (int i = 0; i < maxRows; i++) {
                for (int j = 0; j < maxColumns; j++) {
                    if (matrixData[i][j] == null) {
                        matrixData[i][j] = item;
                        length++;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: removes element from the array if it is in it and returns true, otherwise returns false.
    public boolean sub(Object item) {
        for (int i = 0; i < maxRows; i++) {
            for (int j = 0; j < maxColumns; j++) {
                if (item.equals(matrixData[i][j])) {
                    remove(i,j);
                    return true;
                }
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: removes the element at (i,j) and shifts the inventory items accordingly
    private void remove(int i, int j) {
        int p0 = j + 1 + (maxColumns * i);
        if (length > p0) {
            for (int p = p0; p < length; p++) {
                int copyToI = (p - 1) / maxColumns;
                int copyToJ = p - 1 - (copyToI * maxColumns);
                int copyFromI = p / maxColumns;
                int copyFromJ = p - (copyFromI * maxColumns);
                matrixData[copyToI][copyToJ] = matrixData[copyFromI][copyFromJ];
            }
        }
        length--;
        matrixData[length / maxColumns][length - ((length / maxColumns) * maxColumns)] = null;
    }

    // EFFECTS: checks if parameter type is not Stat and returns true if length is less than maxLength, false otherwise.
    private boolean canInsertElement() {
        return length < maxLength;
    }

    // EFFECTS: returns the length of the current number of elements in the MatrixIterable
    public int length() {
        return this.length;
    }

    // EFFECTS: removes all elements from the Inventory and sets length to zero.
    public void clear() {
        for (int i = 0; i < maxRows; i++) {
            for (int j = 0; j < maxColumns; j++) {
                matrixData[i][j] = null;
            }
        }
        length = 0;
    }
}
