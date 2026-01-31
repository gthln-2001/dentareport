package de.dentareport.models;

import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.utils.Keys;
import de.dentareport.utils.Toothnumbers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

// TODO: TEST?
public class Evidence01Factory {

    public static Evidence01 create(ResultSet rs) {
        try {
            return new Evidence01(
                    rs.getString("date"),
                    rs.getString("billingcode"),
                    rs.getString("dft"),
                    rs.getString("dmft"),
                    rs.getString("dt"),
                    rs.getString("mt"),
                    rs.getString("ft"),
                    rs.getString("tooth_count_q1"),
                    rs.getString("tooth_count_q2"),
                    rs.getString("tooth_count_q3"),
                    rs.getString("tooth_count_q4"),
                    createStatus(rs),
                    createCariesSurfaces(rs),
                    createFillingSurfaces(rs)
            );
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private static Map<String, String> createStatus(ResultSet rs) {
        try {
            Map<String, String> ret = new HashMap<>();
            for (String toothnumber : Toothnumbers.ALL) {
                ret.put(toothnumber, rs.getString(String.format("status_%s", toothnumber)));
            }
            return ret;
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private static Map<String, Map<String, String>> createCariesSurfaces(ResultSet rs) {
        try {
            Map<String, Map<String, String>> ret = new HashMap<>();
            for (String toothnumber : Toothnumbers.ALL) {
                Map<String, String> surfaces = new HashMap<>();
                for (String surface : Keys.SURFACES) {
                    surfaces.put(surface, rs.getString(String.format("caries_%s_%s", toothnumber, surface)));
                }
                ret.put(toothnumber, surfaces);
            }
            return ret;
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private static Map<String, Map<String, String>> createFillingSurfaces(ResultSet rs) {
        try {
            Map<String, Map<String, String>> ret = new HashMap<>();
            for (String toothnumber : Toothnumbers.ALL) {
                Map<String, String> surfaces = new HashMap<>();
                for (String surface : Keys.SURFACES) {
                    surfaces.put(surface, rs.getString(String.format("filling_%s_%s", toothnumber, surface)));
                }
                ret.put(toothnumber, surfaces);
            }
            return ret;
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }
}
