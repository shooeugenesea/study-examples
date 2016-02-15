package examples.file;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class RootPath {

    public static void main(String[] params) throws IOException {
        for (Path path: FileSystems.getDefault().getRootDirectories()) {
            System.out.println(path); //列出 C:/ & D:/ ..etc
        }
    }
    
}
