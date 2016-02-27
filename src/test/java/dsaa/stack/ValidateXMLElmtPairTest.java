package dsaa.stack;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import dsaa.stack.ValidateXMLElmtPair.XMLElmtStack;

public class ValidateXMLElmtPairTest {

	@Test
	public void isValid() throws IOException {
		Assert.assertTrue(ValidateXMLElmtPair.isValid("<abc>\n<qq>abc</qq><cc/>\n</abc>"));
		Assert.assertFalse(ValidateXMLElmtPair.isValid("<abc>\n<qq>abc</qq></cc>\n</abc>"));
	}
	
	@Test
	public void stackPushPop() {
		XMLElmtStack s = new XMLElmtStack();
		s.push("0");
		s.push("1");
		s.push("2");
		s.push("3");
		s.push("4");
		Assert.assertEquals("4", s.pop());
		Assert.assertEquals("3", s.pop());
		Assert.assertEquals("2", s.pop());
		Assert.assertEquals("1", s.pop());
		Assert.assertEquals("0", s.pop());
		Assert.assertEquals(null, s.pop());
	}
	
}
