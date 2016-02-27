package dsaa.array;

import org.junit.Assert;
import org.junit.Test;

import dsaa.array.Score;
import dsaa.array.Scores;

public class ScoresTest {

	@Test
	public void add() {
		Scores s = new Scores();
		Assert.assertEquals(0, s.getLength());
		s.add(new Score("a", 0));
		Assert.assertEquals(1, s.getLength());
		s.add(new Score("b", 4));
		Assert.assertEquals(2, s.getLength());
		s.add(new Score("c", 2));
		Assert.assertEquals(3, s.getLength());
		s.add(new Score("d", 3));
		Assert.assertEquals(4, s.getLength());
		s.add(new Score("e", 5));
		Assert.assertEquals(5, s.getLength());
		s.add(new Score("f", -1));
		Assert.assertEquals(5, s.getLength());
		Assert.assertEquals(5, s.get(0).getScore());
		Assert.assertEquals(4, s.get(1).getScore());
		Assert.assertEquals(3, s.get(2).getScore());
		Assert.assertEquals(2, s.get(3).getScore());
		Assert.assertEquals(0, s.get(4).getScore());
		Assert.assertEquals("e", s.get(0).getName());
		Assert.assertEquals("b", s.get(1).getName());
		Assert.assertEquals("d", s.get(2).getName());
		Assert.assertEquals("c", s.get(3).getName());
		Assert.assertEquals("a", s.get(4).getName());
	}
	
	@Test
	public void remove() {
		Scores s = new Scores();
		s.add(new Score("a", 0));
		s.add(new Score("b", 4));
		s.add(new Score("c", 2));
		s.add(new Score("d", 3));
		s.add(new Score("e", 5));
		s.add(new Score("f", -1));
		Assert.assertEquals(5, s.getLength());
		Score rs = s.remove(0);
		Assert.assertEquals(5, rs.getScore());
		Assert.assertEquals("e", rs.getName());
		
		Assert.assertEquals(4, s.getLength());
		Assert.assertEquals(4, s.get(0).getScore());
		Assert.assertEquals(3, s.get(1).getScore());
		Assert.assertEquals(2, s.get(2).getScore());
		Assert.assertEquals(0, s.get(3).getScore());
		Assert.assertEquals("b", s.get(0).getName());
		Assert.assertEquals("d", s.get(1).getName());
		Assert.assertEquals("c", s.get(2).getName());
		Assert.assertEquals("a", s.get(3).getName());
		rs = s.remove(1);
		Assert.assertEquals(3, rs.getScore());
		Assert.assertEquals("d", rs.getName());
		
		Assert.assertEquals(3, s.getLength());
		Assert.assertEquals(4, s.get(0).getScore());
		Assert.assertEquals(2, s.get(1).getScore());
		Assert.assertEquals(0, s.get(2).getScore());
		Assert.assertEquals("b", s.get(0).getName());
		Assert.assertEquals("c", s.get(1).getName());
		Assert.assertEquals("a", s.get(2).getName());
		rs = s.remove(2);
		Assert.assertEquals(0, rs.getScore());
		Assert.assertEquals("a", rs.getName());

		Assert.assertEquals(2, s.getLength());
		Assert.assertEquals(4, s.get(0).getScore());
		Assert.assertEquals(2, s.get(1).getScore());
		Assert.assertEquals("b", s.get(0).getName());
		Assert.assertEquals("c", s.get(1).getName());
		try {
			s.remove(3);
			Assert.fail();
		} catch (IndexOutOfBoundsException e) {}
		Assert.assertEquals(2, s.getLength());
		rs = s.remove(0);
		Assert.assertEquals(4, rs.getScore());
		Assert.assertEquals("b", rs.getName());

		Assert.assertEquals(1, s.getLength());
		Assert.assertEquals(2, s.get(0).getScore());
		Assert.assertEquals("c", s.get(0).getName());
		rs = s.remove(0);
		Assert.assertEquals(2, rs.getScore());
		Assert.assertEquals("c", rs.getName());

		Assert.assertEquals(0, s.getLength());
		Assert.assertEquals(0, s.getLength());
		try {
			s.remove(0);
			Assert.fail();
		} catch (IndexOutOfBoundsException e) {}
		Assert.assertEquals(0, s.getLength());
	}
	
}
