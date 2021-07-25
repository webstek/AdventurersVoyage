package model;

// TODO Write test class for this class

/**
 * A two dimensional array of fixed maximum size.
 * Intended for use like ArrayList, but with rows.
 */

public class IntegerMatrix {
    private final int[][] matrixData;
    private int maxRows;
    private int maxColumns;
    private int maxLength;

    // EFFECTS: creates a 2 by 7 IntegerMatrix with all zero entries
    public IntegerMatrix() {
        matrixData = new int[2][7];
        maxRows = 2;
        maxColumns = 7;
        maxLength = 14;
    }

    // EFFECTS: creates the 2D object array of rows number of rows, and columns number of columns
    public IntegerMatrix(int rows, int columns) {
        matrixData = new int[rows][columns];
        maxRows = rows;
        maxColumns = columns;
        maxLength = rows * columns;
    }

    // EFFECTS: sets the correct field data for a 2d array
    public IntegerMatrix(int[][] initMatrix) {
//        maxRows = 0;
//        maxColumns = 0;
//        maxLength = 0;
        matrixData = initMatrix;
        for (int[] row : initMatrix) {
            maxRows++;
            for (Object element : row) {
                maxLength++;
            }
        }
        maxColumns = maxLength / maxRows;
    }


    // MODIFIES: this
    // EFFECTS: changes the values in this matrixData to be the sum of itself and the modifyerStats matrixData values.
    public void add(IntegerMatrix modifyerStats) {
        for (int i = 0; i < maxRows; i++) {
            for (int j = 0; j < maxColumns; j++) {
                this.matrixData[i][j] += modifyerStats.matrixData[i][j];
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: sets matrixData[i][j] to be itself plus val
    public void add(int i, int j, int val) {
        matrixData[i][j] += val;
    }

    // MODIFIES: this
    // EFFECTS: changes the values in this matrixData to the difference of the modifyerStats matrixData values.
    public void sub(IntegerMatrix modifyerStats) {
        for (int i = 0; i < maxRows; i++) {
            for (int j = 0; j < maxColumns; j++) {
                matrixData[i][j] -= modifyerStats.matrixData[i][j];
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: sets matrixData[i][j] to be itself minus val
    public void sub(int i, int j, int val) {
        matrixData[i][j] -= val;
    }

    // EFFECTS: returns the element of matrixData at the appropriate indices
    public int in(int i, int j) {
        return matrixData[i][j];
    }

    // EFFECTS: removes all elements from the Inventory and sets length to zero.
    public void clear() {
        for (int i = 0; i < maxRows; i++) {
            for (int j = 0; j < maxColumns; j++) {
                matrixData[i][j] = 0;
            }
        }
    }

    // EFFECTS: returns the maxRows field
    public int getMaxRows() {
        return maxRows;
    }

    // EFFECTS: returns the maxColumns field
    public int getMaxColumns() {
        return maxColumns;
    }
}
