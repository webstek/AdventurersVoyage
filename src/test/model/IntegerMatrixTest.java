package model;

import model.IntegerMatrix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for IntegerMatrix
 */

public class IntegerMatrixTest {
    IntegerMatrix mat1;
    IntegerMatrix mat2;
    IntegerMatrix mat3;

    @BeforeEach
    public void setup() {
        mat1 = new IntegerMatrix();
        mat2 = new IntegerMatrix(new int[][]{{0,0,0,0,0,0,-30},{0,0,0,0,0,10,0}});
        mat3 = new IntegerMatrix(new int[][]{{6,7,3,4,5,200,100},{0,0,0,0,0,0,0}});
    }

    @Test
    public void noArgumentsConstructorsTest() {
        assertEquals(2, mat1.getMaxRows());
        assertEquals(2, mat2.getMaxRows());
        assertEquals(2, mat3.getMaxRows());
    }

    @Test
    public void addByIntegerMatrixTest() {
        mat1.add(mat3);
        assertEquals(6,mat1.in(0,0));
    }

}
