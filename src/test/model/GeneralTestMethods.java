package model;

public class GeneralTestMethods {
    public boolean statsEqualValues(Statistics stats1, Statistics stats2) {
        for (int p = 1; p <= 14; p++) {
            int i = (p-1) / 7;
            int j = p - 1 - (i * 7);
            if (stats1.in(i,j) != stats2.in(i,j)) {
                return false;
            }
        }
        return true;
    }
}
