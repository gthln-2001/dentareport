package de.dentareport.imports.dampsoft.convert;

import de.dentareport.utils.Toothnumbers;
import de.dentareport.utils.db.DbCell;
import de.dentareport.utils.dbf.DbfCell;
import de.dentareport.utils.dbf.DbfRow;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

// TODO: TEST?
public class Helper {
    public static DbfRow testRow() {
        DbfRow ret = new DbfRow();
        for (String toothnumber : Toothnumbers.ALL) {
            ret.addCell(new DbfCell("ZA" + toothnumber, "                                                            "));
        }
        return ret;
    }

    public static String value(List<DbCell> cells, String name) {
        for (DbCell cell : cells) {
            if (Objects.equals(cell.column(), name)) {
                return cell.value();
            }
        }
        return null;
    }

    public static String sortSurfaces(String surfaceString) {
        String[] surfaces = surfaceString.split(",");
        Arrays.sort(surfaces);
        return String.join(",", (CharSequence[]) surfaces);
    }
}
