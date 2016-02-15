package examples.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

public class BigFile {

    public static void main(String[] params) throws IOException {
        Charset cs = Charset.forName("utf-8");
        Path path = Paths.get("d:/", "test.txt");
        int wLineCnt = 25000000;
        try (
            BufferedWriter writer = Files.newBufferedWriter(path, cs, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for ( int i = 0; i < wLineCnt; i++ ) {
                writer.write(i + ":" + UUID.randomUUID().toString() + "\n");
            }
            writer.flush();            
        }
        try (BufferedReader reader = Files.newBufferedReader(path, cs)) {
            int rLineCnt = 0;
            String line = null;
            while ((line = reader.readLine()) != null) {
                int num = Integer.valueOf(line.substring(0, line.indexOf(":")));
                if ( rLineCnt++ != num ) {
                    throw new RuntimeException(
                            "not equal! " + num + "," + rLineCnt);
                }
            }
            if ( rLineCnt != wLineCnt ) {
                throw new RuntimeException(
                        "not equal! " + wLineCnt + "," + rLineCnt);
            }
        }
        System.out.println("done");
    }

}
