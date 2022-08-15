package ua.com.alevel.alexshent;

import ua.com.alevel.alexshent.exception.ReadResourceFileException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Text file reader class
 */
public class FileReader {

    public List<String> readResourceFile(String fileName) {
        try {
            Path path = Path.of(ClassLoader.getSystemResource(fileName).toURI());
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            lines.removeAll(List.of(""));   // remove all empty lines
            return lines;
        } catch (URISyntaxException | IOException e) {
            throw new ReadResourceFileException("resource file " + fileName + " read error");
        }
    }
}
