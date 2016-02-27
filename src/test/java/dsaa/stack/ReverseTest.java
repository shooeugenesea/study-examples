package dsaa.stack;

import org.junit.Assert;
import org.junit.Test;

import dsaa.stack.Reverse.ReverseStack;

public class ReverseTest {

	@Test
	public void reverse() {
		String[] sAry = {"0","1","2","3","4"};
		new Reverse().reverse(sAry);;
		Assert.assertEquals("4", sAry[0]);
		Assert.assertEquals("3", sAry[1]);
		Assert.assertEquals("2", sAry[2]);
		Assert.assertEquals("1", sAry[3]);
		Assert.assertEquals("0", sAry[4]);
	}
	
	@Test
	public void stackPushPop() {
		ReverseStack s = new ReverseStack();
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
