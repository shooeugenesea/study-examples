package examples.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Directories {

    public static void main(String[] params) throws IOException {
        createDirectories();
        
        /*
         * JDK 7提供新的 interface 讓你 iterate 資料夾: DirectoryStream. 
         * 在 API 上說這個 interface 比較 scalable, 可以用來 iterate 很大的 folder.
         * 上網查有人說就的 File#listFiles 會把所有的 folder 載入會佔用比較多的記憶體.
         * 要省記憶體應該要讓資料夾下有上百萬或上千萬個資料夾或檔案才有感覺吧.
         * 我測一百萬個資料夾也沒甚麼差.
         * 後來覺得關鍵是 API 這句話:
         * The iterator is weakly consistent. It is thread safe but does not freeze the directory while iterating, 
         * so it may (or may not) reflect updates to the directory that occur after the DirectoryStream is created.
         * 在資料夾下檔案或資料夾數量很大時, 原本的 File 仍須要給 API client 精準的資訊.
         * scale 愈大, "精準"的成本就愈大.
         * DirectoryStream 選擇一開始就告訴你它不準, 所以贏在起跑點, 可以用較少的資源取資料.
         * 等你真的要處理某個檔案再動作就行了.
         * 我覺得這種 interface 更有利於硬碟在遠端的 case. 硬碟不在本機,
         * 要求 File 物件保證整個資料夾資訊要對太難, 這時候 DirectoryStream 就可以處理很好.
         * 要注意 DirectoryStream 用完要關掉, 可以在 try () 裡面宣告.
         * */
        iterateDirectories();
    }

    private static void iterateDirectories() throws IOException {
        long start = System.currentTimeMillis();
        createFolders();
        System.out.println("spend " + (System.currentTimeMillis() - start) + " millis to create folders");
        start = System.currentTimeMillis();
        System.out.println(listFoldersNIO(Paths.get("d:/test")));
        System.out.println("spend " + (System.currentTimeMillis() - start) + " millis to list folders (NIO)");

        start = System.currentTimeMillis();
        System.out.println(listFoldersIO(Paths.get("d:/test").toFile()));
        System.out.println("spend " + (System.currentTimeMillis() - start) + " millis to list folders (IO)");
    }

    private static void createFolders() throws IOException {
        for (int i = 0; i < 1000000; i++) {
            Files.createDirectories(Paths.get("d:/test/test" + i));
            System.out.println(i);
        }
        System.out.println("done");
    }

    private static int listFoldersIO(File folder) {
        int cnt = 0;
        for (String child : folder.list()) {
            new File(child);
            cnt++;
        }
        return cnt;
    }

    private static int listFoldersNIO(Path path) throws IOException {
        int cnt = 0;
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(path)) {
            for (Path child : dirStream) {
                child.toFile();
                cnt++;
            }
        }
        return cnt;
    }


    private static void createDirectories() throws IOException {
        Files.createDirectories(Paths.get("d:/test1/test2/test3/test4"));
    }

}
