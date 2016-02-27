package dsaa.stack;

import org.junit.Assert;
import org.junit.Test;

public class ArrayStackTest {

	@Test
	public void pushPop() {
		ArrayStack s = new ArrayStack(10);
		s.push("0");
		s.push("1");
		s.push("2");
		s.push("3");
		s.push("4");
		Assert.assertEquals("01234", s.toString());
		Assert.assertEquals("4", s.pop());
		Assert.assertEquals("3", s.pop());
		Assert.assertEquals("2", s.pop());
		Assert.assertEquals("1", s.pop());
		Assert.assertEquals("0", s.pop());
	}
	
}
