package examples.file;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class SmallFile {

    public static void main(String[] params) throws IOException {
        Charset cs = Charset.forName("utf-8");
        Path path = Paths.get("d:/", "test.txt");
        List<String> lines = Arrays.asList("line1", "line2", "line3");
        Files.write(path, lines, cs, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        List<String> readLines = Files.readAllLines(path, cs);
        System.out.println(readLines);
    }
    
}
