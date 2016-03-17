package examples.lambda;

import org.junit.Assert;
import org.junit.Test;

import examples.lambda.DrawCommand.DrawPanel;

public class DrawCommandTest {

    @Test
    public void draw() {
        DrawPanel testee = new DrawPanel();
        Assert.assertEquals("draw linedraw circle", testee.draw(DrawCommand.LINE, DrawCommand.CIRCLE));
    }
    
}
