package de.dentareport.imports.dampsoft.convert;

import de.dentareport.utils.Toothnumbers;
import de.dentareport.utils.db.DbCell;
import de.dentareport.utils.dbf.DbfRow;

import java.util.List;
import java.util.stream.Collectors;

public class EvidenceHkpConvert {

    public static List<DbCell> convert(DbfRow evidence) {
        return Toothnumbers.ALL.stream()
                .map(toothnumber -> new DbCell(
                        "status_" + toothnumber,
                        evidence.value("ZA" + toothnumber).trim()))
                .collect(Collectors.toList());
    }
}
