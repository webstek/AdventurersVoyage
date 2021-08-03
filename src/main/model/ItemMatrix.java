package model;


import model.items.Item;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;
import java.util.Iterator;

/**
 * A two dimensional array of fixed maximum size. Intended for use like ArrayList, but with rows and items. Using the
 * remove function will reorder the matrix such that the !null elements are arranged right to left, top to bottom.
 */

public class ItemMatrix implements Iterable<Item> {
    protected Object[][] matrixData;
    protected int length = 0;
    private final int maxRows;
    private final int maxColumns;
    private final int maxLength;

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
        int p0 = toPos(i,j);
        if (length > p0) {
            for (int p = p0; p < length; p++) {
                int[] copyTo = toIndices(p);
                int[] copyFrom = toIndices(p + 1);
                matrixData[copyTo[0]][copyTo[1]] = matrixData[copyFrom[0]][copyFrom[1]];
            }
        }
        length--;
        matrixData[length / maxColumns][length - ((length / maxColumns) * maxColumns)] = null;
    }

    // REQUIRES: all elements of the ItemMatrix to be type Item
    // EFFECTS: returns the item in ItemMatrix at pos
    public Item inPos(int p) {
        int[] indices = toIndices(p);
        return (Item) matrixData[indices[0]][indices[1]];
    }

    // EFFECTS: converts the position reference to a set of matrix indices (int[2] = {i,j}) like so.
    private int[] toIndices(int p) {
        return new int[]{(p - 1) / maxColumns,p - 1 - (((p - 1) / maxColumns) * maxColumns)};
    }

    // EFFECTS: returns true if an object of the same item subclass is in the item matrix. If not, returns false.
    public boolean contains(Item lookingFor) {
        for (Item item : this) {
            if (item.name().equals(lookingFor.name())) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: converts a set of indices to a single position reference
    private int toPos(int i, int j) {
        return (j + 1) + (maxColumns * i);
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

    // EFFECTS: returns the iterator object that provides the hasNext and next methods. Enables use in foreach loops.
    @Override
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            int position = 0;

            @Override
            public boolean hasNext() {
                return position < length;
            }

            @Override
            public Item next() {
                return inPos(++position);
            }
        };
    }

    // EFFECTS: represents the item matrix object in a JsonArray
    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();

        for (Item item : this) {
            jsonArray.put(item.toJson());
        }
        return jsonArray;
    }
}
