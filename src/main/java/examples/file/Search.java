package examples.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Search {

    public static void main(String[] params) throws IOException {
        listAll_DirectoryStream(Paths.get("f:\\projects\\git-apache-activemq"), "Queue");
    }
    
    private static void listAll_DirectoryStream(Path p, final String containTxt) throws IOException {
        DirectoryStream.Filter<Path> f = new DirectoryStream.Filter<Path>() {
            @Override
            public boolean accept(Path entry) throws IOException {
                if (Files.isDirectory(entry, LinkOption.NOFOLLOW_LINKS) || !entry.toString().endsWith(".java")) {
                    return false;
                }
                try (BufferedReader reader = Files.newBufferedReader(entry, Charset.forName("UTF8"))) {
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        if (line.contains(containTxt)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        };
        try (DirectoryStream<Path> s = Files.newDirectoryStream(p, f)) {
            for (Path c : s) {
                System.out.println("match " + c);
            }
        }
        try (DirectoryStream<Path> s = Files.newDirectoryStream(p)) {
            for (Path c : s) {
                if (Files.isDirectory(c, LinkOption.NOFOLLOW_LINKS)) {
                    listAll_DirectoryStream(c, containTxt);
                }
            }
        }
    }
    
}
