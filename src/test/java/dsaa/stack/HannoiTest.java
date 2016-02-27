package dsaa.stack;

import org.junit.Assert;
import org.junit.Test;

import dsaa.stack.Hannoi;
import dsaa.stack.Hannoi.LinkedStack;
import dsaa.stack.Hannoi.Node;

public class HannoiTest {

	@Test
	public void stack() {
		LinkedStack s = new LinkedStack("A");
		s.push(new Node("1"));
		s.push(new Node("2"));
		s.push(new Node("3"));
		Assert.assertEquals("3", s.pop().getName());
		Assert.assertEquals("2", s.pop().getName());
		Assert.assertEquals("1", s.pop().getName());
	}
	
	@Test
	public void move() {
		Hannoi h = new Hannoi(6);
		h.move();
	}
	
}
