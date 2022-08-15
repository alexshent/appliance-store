package ua.com.alevel.alexshent;

import ua.com.alevel.alexshent.exception.InvalidCsvLineException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for parsing appliances data
 */
public class FileParser {
    private static final String CSV_DELIMITER = ",";
    private static final String WORDS_DELIMITER = "[\\W_]+";

    /**
     * Parse appliances from data lines
     * @param lines data lines read from file
     * @return list of data values mapped to column names
     * @throws InvalidCsvLineException
     */
    public List<Map<String, String>> parseCsv(List<String> lines) throws InvalidCsvLineException {
        if (lines == null) {
            throw new IllegalArgumentException("input list is null");
        }
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("input list is empty");
        }
        List<Map<String, String>> result = new ArrayList<>();
        String header = lines.remove(0);
        String[] columns = header.split(CSV_DELIMITER);
        if (columns.length == 0) {
            throw new IllegalArgumentException("invalid CSV header");
        }
        for (int i=0; i < columns.length; i++) {
            columns[i] = toCamelCase(columns[i]);
        }
        for (String line : lines) {
            Map<String, String> map = new HashMap<>();
            String[] items = line.split(CSV_DELIMITER);
            for (int i=0; i < columns.length; i++) {
                if (items[i].isEmpty()) {
                    throw new InvalidCsvLineException("empty item for column " + columns[i] + " in the line " + line);
                }
                map.put(columns[i], items[i]);
            }
            result.add(map);
        }
        return result;
    }

    /**
     * Convert string to camel case
     * @param string input string
     * @return camel cased string
     */
    private String toCamelCase(String string) {
        String[] words = string.split(WORDS_DELIMITER);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (i == 0) {
                word = word.isEmpty() ? word : word.toLowerCase();
            } else {
                word = word.isEmpty() ? word : Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
            }
            builder.append(word);
        }
        return builder.toString();
    }
}
