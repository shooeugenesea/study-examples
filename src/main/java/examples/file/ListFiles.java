package examples.file;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class ListFiles {

    public static void main(String[] params) throws IOException {
        listAll_glob(Paths.get("f:\\projects"), "*.{java,jar}");
        listAll_walkFileTree("f:\\projects");
    }

    private static void listAll_walkFileTree(String path) throws IOException {
        Path p = Paths.get(path);
        FileVisitor<Path> f = new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                String filepath = file.toString();
                if (filepath.endsWith(".java") || filepath.endsWith(".jar")) {
                    System.out.println(filepath);
                }
                return super.visitFile(file, attrs);
            }
        };
        Files.walkFileTree(p, f);
    }

    private static void listAll_glob(Path p, String globPattern) throws IOException {
        try (DirectoryStream<Path> s = Files.newDirectoryStream(p, globPattern)) {
            for (Path c : s) {
                System.out.println(c);
            }
        }
        try (DirectoryStream<Path> s = Files.newDirectoryStream(p)) {
            for (Path c : s) {
                if (Files.isDirectory(c, LinkOption.NOFOLLOW_LINKS)) {
                    listAll_glob(c, globPattern);
                }
            }
        }
    }

}
