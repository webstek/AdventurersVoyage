package model;


/**
 * Representation of all statistics for the game. The following table represents which values are stored where:
 *  |                                TypeStatistics                                  | HealthStatistics  |
 *  | -------------------------------------------------------------------------------------------------- |
 *  | Intelligence      | Strength      | Speed      | Dexterity      | Stealth      | Hp     | Mp       |
 *  | ------------------|---------------|------------|----------------|--------------|--------|--------- |
 *  | bonusIntelligence | bonusStrength | bonusSpeed | bonusDexterity | bonusStealth | Damage | ManaBurn |
 *  | -------------------------------------------------------------------------------------------------- |
 *
 *  Can be initialized either into all zeroes or by passing the constructor a full int[][] object.
 */

public class Statistics implements Cloneable {
    private final int[][] matrixData;
    private int maxRows;
    private int maxColumns;
    private int maxLength;

    // EFFECTS: creates a 2 by 7 IntegerMatrix with all zero entries
    public Statistics() {
        matrixData = new int[2][7];
        maxRows = 2;
        maxColumns = 7;
        maxLength = 14;
    }


    // EFFECTS: creates a clone statistics object that the method is called on
    @Override
    public Statistics clone() {
        Statistics cloned = new Statistics();
        cloned.clear();
        cloned.add(this);
        return cloned;
    }

    // EFFECTS: sets the correct field data for a 2d array
    public Statistics(int[][] initMatrix) {
        matrixData = initMatrix;
        for (int[] row : initMatrix) {
            maxRows++;
            for (Object ignored : row) {
                maxLength++;
            }
        }
        maxColumns = maxLength / maxRows;
    }


    // MODIFIES: this
    // EFFECTS: changes the values in this matrixData to be the sum of itself and the modifyerStats matrixData values.
    public void add(Statistics modifyerStats) {
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
    // EFFECTS: sets matrixData[i][j] to val
    public void set(int i, int j, int val) {
        matrixData[i][j] = val;
    }

    // MODIFIES: this
    // EFFECTS: changes the values in this matrixData to the difference of the modifyerStats matrixData values.
    public void sub(Statistics modifyerStats) {
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

    // MODIFIES: this
    // EFFECTS: sets all entries to zero.
    public void clear() {
        for (int i = 0; i < maxRows; i++) {
            for (int j = 0; j < maxColumns; j++) {
                matrixData[i][j] = 0;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: sets matrixData[i][j] to zero.
    public void clear(int i, int j) {
        matrixData[i][j] = 0;
    }

    // MODIFIES: this
    // EFFECTS: checks if any statistics are less than zero, and clears them if so
    public void checkForNegativeStats() {
        for (int i = 0; i < maxRows; i++) {
            for (int j = 0; j < maxColumns; j++) {
                if (matrixData[i][j] < 0) {
                    clear(i,j);
                }
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

    // REQUIRES: index must be between 0 and 4
    // EFFECTS: returns the total particular statistic (e.x. Intelligence) represented in the statistics object
    public int getStat(int index) {
        return this.in(0,index) + this.in(1,index);
    }

    // EFFECTS: returns the damage value in the statistic object
    public int damage() {
        return this.in(1,5);
    }
}
