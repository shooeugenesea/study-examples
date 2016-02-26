package examples.cassandra;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

public class CassandraTest {

    @Test
    public void test() throws IOException {
        System.out.println(Paths.get("src/test/resources/apache-cassandra-1.2.19").toFile().getAbsolutePath());
        
        try (Cassandra cas = new Cassandra("target/test/resources/apache-cassandra-1.2.19/bin/cassandra.bat")) {
            cas.start();
            TimeUnit.SECONDS.sleep(30);
            assertCassandraStarted();
            cas.stop();
            System.out.println("stopped");
            TimeUnit.SECONDS.sleep(30);
            assertCassandraStopped();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    private void copyFolder(File from, File to) throws IOException {
        if (!from.isDirectory() || !to.isDirectory()) {
            throw new IllegalArgumentException("must be a folder. from:" + from + ",to:" + to);
        }
        for ( File file: from.listFiles() ) {
            if (file.isDirectory()) {
                
            }
        }
    }
    
    private void assertCassandraStopped() {
        try (Socket s = new Socket("127.0.0.1",9160)) {
            Assert.fail("cassandra should be stopped");
        } catch (Throwable ex) {
        }
    }
    
    private void assertCassandraStarted() {
        try (Socket s = new Socket("127.0.0.1",9160)) {
            System.out.println("cassandra is started");
        } catch (Throwable ex) {
            ex.printStackTrace();
            Assert.fail("cassandra should be started");
        }
    }
    
}
