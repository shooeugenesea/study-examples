package examples.lambda.function;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author <a href="https://github.com/shooeugenesea">isaac</a>
 */
public class FileTest {

    @Test
    public void foreachPrintLine() throws IOException {
        StringBuilder sb = new StringBuilder();
        Files.lines(new File("src/test/resources/testfile.txt").toPath())
            .filter((line) -> line.contains("apache"))
            .forEach((line) -> sb.append(line));
        Assert.assertEquals("http://www.apache.org/licenses/LICENSE-2.0", sb.toString().trim());
    }

}
