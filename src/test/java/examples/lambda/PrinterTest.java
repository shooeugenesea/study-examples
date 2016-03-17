package examples.lambda;

import org.junit.Assert;
import org.junit.Test;

public class PrinterTest {

    @Test
    public void test() {
        Assert.assertEquals("A1A2", Printer.PREFIXA.print("1", "2"));
        Assert.assertEquals("1A2A", Printer.SUFFIXA.print("1", "2"));
    }
    
}
