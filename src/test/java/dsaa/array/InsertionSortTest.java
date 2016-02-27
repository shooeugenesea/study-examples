package dsaa.array;

import org.junit.Assert;
import org.junit.Test;

import dsaa.array.InsertionSort;

public class InsertionSortTest {

	@Test
	public void sort() {
		InsertionSort testee = new InsertionSort();
		
		char[] ary = "9517836420".toCharArray();
		testee.sort(ary);
		Assert.assertEquals("0123456789", new String(ary));
		
		ary = "qazwsxedcrfvtgbyhnujmikolp".toCharArray();
		testee.sort(ary);
		Assert.assertEquals("abcdefghijklmnopqrstuvwxyz", new String(ary));
		
		ary = "qazwsxedcrfvtgbyfhnujmikolp".toCharArray();
		testee.sort(ary);
		Assert.assertEquals("abcdeffghijklmnopqrstuvwxyz", new String(ary));
		
		ary = "qazwsxedcrfvffffftgbyfhnujmikqqqqqolp".toCharArray();
		testee.sort(ary);
		Assert.assertEquals("abcdefffffffghijklmnopqqqqqqrstuvwxyz", new String(ary));
		
		ary = "q".toCharArray();
		testee.sort(ary);
		Assert.assertEquals("q", new String(ary));
	}
	
}
