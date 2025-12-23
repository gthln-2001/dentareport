package de.dentareport.imports.charly;


import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

// Refactor!, Test!
public class CharlyImport {


    public void import_data() throws IOException {
        long startTime = System.currentTimeMillis();

        // findValueInAllFiles("2451893");


        String filename = String.valueOf("import/patienten.asc");
        RandomAccessFile raFile = new RandomAccessFile(filename, "r");
        List<String> header = getHeader(raFile);
        System.out.println(header);
        for (Map<String, String> row : getData(header, raFile)) {
            if (Objects.equals(row.get("patID"), "1388") && Objects.equals(row.get("befund01"), "1")) {
                System.out.println();
                System.out.println(row);
            }
        }
        raFile.close();


        long stopTime = System.currentTimeMillis();
        double elapsedTime = ((double) stopTime - (double) startTime) / 1000;
        System.out.println(String.format("Elapsed time for import: %.2f seconds", elapsedTime));

    }

    private void findValueInAllFiles(String value) throws IOException {
        Files.walk(Paths.get("/home/guenther/code/dentareport/raw_data/SolutioData")).forEach(filePath -> {
            if (Files.isRegularFile(filePath)) {
                String filename = String.valueOf(filePath);
                RandomAccessFile raFile = null;
                try {
                    raFile = new RandomAccessFile(filename, "r");
                    List<String> header = getHeader(raFile);
                    for (Map<String, String> row : getData(header, raFile)) {
                        for (String column : row.keySet()) {
                            if (Objects.equals(row.get(column), value)) {
                                System.out.println();
                                System.out.println(filename);
                                System.out.println();
                                System.out.println(header);
                                System.out.println();
                                System.out.println(column);
                                System.out.println();
                                System.out.println(row);
                                System.exit(0);
                            }
                        }
                    }
                    raFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private List<Map<String, String>> getData(List<String> header, RandomAccessFile raFile) throws IOException {
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> row = new LinkedHashMap<>();
        int value;
        int columnIndex = 0;
        String cell = "";
        while ((value = raFile.read()) != -1) {
            if (value == 2) {
                data.add(row);
                row = new LinkedHashMap<>();
                columnIndex = 0;
            } else if (value == 1) {
                row.put(header.get(columnIndex++), cell.replace("'", ""));
                cell = "";
            } else {
                cell += Character.toString((char) value);
            }
        }
        return data;
    }

    private List<String> getHeader(RandomAccessFile raFile) throws IOException {
        List<String> header = new ArrayList<>();
        int value;
        String column = "";
        while ((value = raFile.read()) != -1) {
            if (value == 2) {
                break;
            }
            if (value != 1) {
                column += Character.toString((char) value);
            } else {
                header.add(column);
                column = "";
            }
        }
        return header;
    }


}
