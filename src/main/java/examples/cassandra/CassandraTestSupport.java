package examples.cassandra;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

public class CassandraTestSupport {

    private final Cassandra cassandra;
    
    public CassandraTestSupport() throws IOException {
        final Path srcDir = Paths.get("src/test/resources/apache-cassandra-1.2.19");
        final Path targetDir = Paths.get("target/test/resources/apache-cassandra-1.2.19");

        delete(targetDir);
        
        if (Files.exists(targetDir)) {
            throw new RuntimeException("file is not deleted. target:" + targetDir);
        }
        
        copy(srcDir, targetDir);
        this.cassandra = new Cassandra(targetDir.resolve("bin/cassandra.bat"));
    }
    
    @Before
    public void before() throws Exception {
        cassandra.start();
        System.out.println("wait 10 seconds");
        TimeUnit.SECONDS.sleep(10);
        assertCassandraStarted();
    }

    @After
    public void after() throws Exception {
        cassandra.stop();
        System.out.println("wait 10 seconds");
        TimeUnit.SECONDS.sleep(10);
        assertCassandraStopped();
    }
    
    private void copy(final Path source, final Path target) throws IOException {
        Files.walkFileTree(source, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult preVisitDirectory(Path srcDir, BasicFileAttributes attrs) throws IOException {
                Path targetDir = target.resolve(source.relativize(srcDir));
                Files.createDirectory(targetDir);
                return super.preVisitDirectory(srcDir, attrs);
            }
            @Override
            public FileVisitResult visitFile(Path srcFile, BasicFileAttributes attrs) throws IOException {
                Path targetFile = target.resolve(source.relativize(srcFile));
                System.out.println("copy " + srcFile + " to " + targetFile);
                Files.copy(srcFile, targetFile);
                return super.visitFile(srcFile, attrs);
            }
        });
    }
    
    private void delete(Path target) throws IOException {
        System.out.println("delete:" + target);
        if (!Files.exists(target)) {
            return;
        }
        Files.walkFileTree(target, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                System.out.println("delete dir:" + dir);
                if (Files.exists(dir)) {
                    Files.delete(dir);
                }
                return super.postVisitDirectory(dir, exc);
            }
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("delete file:" + file);
                if (Files.exists(file)) {
                    Files.delete(file);
                }
                return super.visitFile(file, attrs);
            }
        });
    }

    private void assertCassandraStopped() {
        try (Socket s = new Socket("127.0.0.1", 9160)) {
            Assert.fail("cassandra should be stopped");
        } catch (Throwable ex) {
        }
    }

    private void assertCassandraStarted() {
        try (Socket s = new Socket("127.0.0.1", 9160)) {
            System.out.println("cassandra is started");
        } catch (Throwable ex) {
            ex.printStackTrace();
            Assert.fail("cassandra should be started");
        }
    }

}
