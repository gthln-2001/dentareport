package de.dentareport.utils.xls;

import java.util.Arrays;
import java.util.Comparator;

public class XlsColumnComparerByIndex implements Comparator<XlsColumn> {

    @Override
    public int compare(XlsColumn column1,
                       XlsColumn column2) {
        int[] index1 = columnIndexToIntArray(column1);
        int[] index2 = columnIndexToIntArray(column2);

        if (indexesAreEqual(index1, index2)) {
            return 0;
        }
        if (firstIndexIsSmaller(index1, index2)) {
            return -1;
        }
        return 1;
    }

    private int[] columnIndexToIntArray(XlsColumn column) {
        return Arrays.stream(column.index().split("\\."))
                     .mapToInt(Integer::parseInt)
                     .toArray();
    }

    private boolean indexesAreEqual(int[] index1,
                                    int[] index2) {
        return Arrays.equals(index1, index2);
    }

    private boolean firstIndexIsSmaller(int[] index1,
                                        int[] index2) {
        return index1[0] < index2[0] || (index1[0] == index2[0] && index1[1] < index2[1]);
    }
}
