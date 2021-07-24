package model;

import java.util.Iterator;

/**
 *  Basic List class with a maximum number of elements. Available for use in for-each loops. Cannot change maximum
 *  number of elements after construction. The Size field tracks the number of elements in the list, not the maximum.
 *  Note there is a smallest maximum size for the list of 2.
 */
public class SizedList<T> implements Iterable<T> {
    private static final int SMALLEST_MAX_SIZE = 2;
    private static int maxSize;
    Object[] listData;
    private int size;

    // EFFECTS: creates the object array of maximum size n
    public SizedList(int n) {
        maxSize = Math.max(n, SMALLEST_MAX_SIZE);
        this.listData = new Object[n];
    }

    // EFFECTS: creates a SizedList of the minimum size, 2.
    public SizedList() {
        this.listData = new Object[SMALLEST_MAX_SIZE];
    }

    // EFFECTS: throws a SizeException if size is greater than, or equal to maxSize.
    private void canAddElement() throws SizeException {
        if (size >= maxSize) {
            throw new SizeException("No slot available!");
        }
    }

    // EFFECTS: returns the size (the current number of items) in the SizedList
    public int size() {
        return this.size;
    }

    // MODIFIES: this
    // EFFECTS: adds the parameter object t to the end of the list if there is space and returns true, otherwise returns
    //          returns false and does not add t to the list.
    //TODO create a test class for this method
    public boolean add(T t) {
        try {
            this.canAddElement();
            listData[size++] = t;
            return true;
        } catch (SizeException e) {
            return false;
        }
    }


    // MODIFIES: this
    // EFFECTS: removes object t from the list if it is on it and returns true, otherwise returns false.
    //TODO create a method that will test this one
    public boolean remove(T t) {
        for (int i = 0; i < size; i++) {
            if (t.equals(listData[i])) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: removes the element at index and decrements size
    private void remove(int index) {
        int lenToMove = size - index - 1;
        if (lenToMove > 0) {
            System.arraycopy(listData, index + 1, listData, index, lenToMove);
        }
        listData[--size] = null;
    }

    // MODIFIES: this
    // EFFECTS: removes all elements from the sized list and sets the size to zero.
    public void clear() {
        for (int i = 0; i < size; i++) {
            listData[i] = null;
        }
        size = 0;
    }

    // EFFECTS: returns the iterator object that enables for-each loop implementation
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int onIndex = 0;

            // EFFECTS: returns true if there is a next item to iterate to (determined by size), otherwise false.
            public boolean hasNext() {
                return onIndex < size;
            }

            // EFFECTS: returns the element in the SizedList in the index after onIndex
            @SuppressWarnings("unchecked")
            public T next() {
                return (T) listData[onIndex++];
            }

            // Not implemented.
            public void remove() {}
        };
    }
}
