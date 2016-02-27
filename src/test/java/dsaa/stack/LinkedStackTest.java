package dsaa.stack;

import org.junit.Assert;
import org.junit.Test;

public class LinkedStackTest {

	@Test
	public void pushPop() {
		LinkedStack s = new LinkedStack();
		s.push("0");
		s.push("1");
		s.push("2");
		s.push("3");
		s.push("4");
		Assert.assertEquals("43210", s.toString());
		Assert.assertEquals("4", s.pop());
		Assert.assertEquals("3", s.pop());
		Assert.assertEquals("2", s.pop());
		Assert.assertEquals("1", s.pop());
		Assert.assertEquals("0", s.pop());
	}
	
}
