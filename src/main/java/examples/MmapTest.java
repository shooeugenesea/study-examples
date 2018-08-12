package examples;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

/**
 * @author <a href="https://github.com/shooeugenesea">isaac</a>
 */
public class MmapTest {

    public static void main(String[] params) throws IOException {
        int gigabyte = 1024*1024*1024;
        File file = new File("C:/Users/isaac/bigfile.test");
        FileChannel fileChannel = (FileChannel) Files.newByteChannel(file.toPath(), StandardOpenOption.READ);
        File write = new File("C:/Users/isaac/write.avi");
        FileOutputStream fout = new FileOutputStream(write);
        System.out.println(fileChannel.size());
        long position = 0;
        long size = Integer.MAX_VALUE;
        while ( position + size <= fileChannel.size() ) {
            MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, size);
            while (buffer.remaining() != 0) {
                int nextLength = Math.min(buffer.remaining(), 10240);
                byte[] data = new byte[nextLength];
                buffer.get(data);
                fout.write(data);
                System.out.printf("position:%d \t freememory:%d MB\n", buffer.position(), Runtime.getRuntime().freeMemory()/1024/1024);
            }
            size = Math.min(1024, fileChannel.size() - position);
            position = position + size;
        }

    }

}
