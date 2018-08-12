package examples;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.io.File.*;

/**
 * @author <a href="https://github.com/shooeugenesea">isaac</a>
 */
public class ZipTest {


    public static void main(String[] params) throws IOException {
        File testfile1 = createTempFile("TroubleshootingControllerTest", "zipFiles-test1");
        File testfile2 = createTempFile("TroubleshootingControllerTest", "zipFiles-test2");
        File outputfile = createTempFile("TroubleshootingControllerTest", "zipFiles-output");
        FileUtils.write(testfile1, "TEST1");
        FileUtils.write(testfile2, "TEST2");
        OutputStream out = new FileOutputStream(outputfile);
        zipFiles(testfile1, testfile2, out);
        System.out.println(outputfile);
    }


    static void zipFiles(File apPcapFile, File dpPcapFile, OutputStream responseOutputStream) throws IOException {
        try (ZipOutputStream zipOut = new ZipOutputStream(responseOutputStream);
             FileInputStream apPcapFis = new FileInputStream(apPcapFile);
             FileInputStream dpPcapFis = new FileInputStream(dpPcapFile)) {
            zipOut.putNextEntry(new ZipEntry("ap/" + apPcapFile.getName()));
            IOUtils.copy(apPcapFis, zipOut);

            zipOut.putNextEntry(new ZipEntry("dp/" + dpPcapFile.getName()));
            IOUtils.copy(dpPcapFis, zipOut);
        }
    }

}
