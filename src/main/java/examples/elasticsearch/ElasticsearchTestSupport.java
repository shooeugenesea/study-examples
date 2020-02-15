package examples.elasticsearch;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import static examples.elasticsearch.Elasticsearch.LISTEN_PORT;

public class ElasticsearchTestSupport {

    private final Elasticsearch elasticsearch;
    
    public ElasticsearchTestSupport() throws IOException {
        final Path srcDir = Paths.get("src/test/resources/elasticsearch-5.0.1");
        final Path targetDir = Paths.get("target/test/resources/elasticsearch-5.0.1");

        delete(targetDir);
        
        if (Files.exists(targetDir)) {
            throw new RuntimeException("file is not deleted. target:" + targetDir);
        }
        
        copy(srcDir, targetDir);
        String osname = System.getProperty("os.name").toLowerCase();
        if (osname.contains("linux")) {
            Runtime.getRuntime().exec("chmod +x " + targetDir.resolve("bin/elasticsearch"));
            this.elasticsearch = new Elasticsearch(targetDir.resolve("bin/elasticsearch"));
        } else {
            this.elasticsearch = new Elasticsearch(targetDir.resolve("bin/elasticsearch.bat"));
        }
    }
    
    @Before
    public void before() throws Exception {
        elasticsearch.start().get();
        assertElasticsearchStarted();
    }

    @After
    public void after() throws Exception {
        elasticsearch.stop().get();
        assertElasticsearchStopped();
    }
    
    private void copy(final Path source, final Path target) throws IOException {
        Files.walkFileTree(source, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult preVisitDirectory(Path srcDir, BasicFileAttributes attrs) throws IOException {
                Path targetDir = target.resolve(source.relativize(srcDir));
                System.out.println("create " + targetDir.toAbsolutePath());
                Files.createDirectories(targetDir);
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

    private void assertElasticsearchStopped() {
        try (Socket s = new Socket("127.0.0.1", LISTEN_PORT)) {
            Assert.fail("elasticsearch should be stopped");
        } catch (Throwable ex) {
        }
    }

    private void assertElasticsearchStarted() {
        try (Socket s = new Socket("127.0.0.1", LISTEN_PORT)) {
            System.out.println("elasticsearch is started");
        } catch (Throwable ex) {
            ex.printStackTrace();
            Assert.fail("elasticsearch should be started");
        }
    }

}
