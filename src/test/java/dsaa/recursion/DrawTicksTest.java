package dsaa.recursion;

import org.junit.Assert;
import org.junit.Test;

import dsaa.recursion.DrawTicks;

public class DrawTicksTest {

    @Test
    public void test() {
        DrawTicks t = new DrawTicks();
        String inch1Length4 = t.drawTicks(1, 4);
        Assert.assertEquals("----\n"
                          + "-\n"
                          + "--\n"
                          + "-\n"
                          + "---\n"
                          + "-\n"
                          + "--\n"
                          + "-\n"
                          + "----\n"
                , inch1Length4);
    }
    
}
