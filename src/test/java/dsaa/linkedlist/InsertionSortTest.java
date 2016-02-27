package dsaa.linkedlist;

import org.junit.Assert;
import org.junit.Test;

import dsaa.linkedlist.InsertionSort;
import dsaa.linkedlist.InsertionSort.DList;
import dsaa.linkedlist.InsertionSort.DNode;

public class InsertionSortTest {

	@Test
	public void sort() {
		InsertionSort sort1 = new InsertionSort(new int[] {9,5,1,8,4,2,6,7,3,0});
		Assert.assertArrayEquals(new int[]{0,1,2,3,4,5,6,7,8,9}, sort1.getSortedNumbers());
		
		InsertionSort sort2 = new InsertionSort(new int[] {0,1,2,3,4,5,6,7,8,9});
		Assert.assertArrayEquals(new int[]{0,1,2,3,4,5,6,7,8,9}, sort2.getSortedNumbers());
	}
	
	@Test
	public void addFirst() {
		DList l = new DList();
		l.addFirst(new DNode(1));
		l.addFirst(new DNode(2));
		l.addFirst(new DNode(3));
		l.addFirst(new DNode(4));
		l.addFirst(new DNode(5));
		Assert.assertEquals("54321", toString(l));
		Assert.assertArrayEquals(new int[]{5,4,3,2,1}, l.toNumbers());
	}

	@Test
	public void addAfter() {
		DList l = new DList();
		DNode nodeA = new DNode(1);
		DNode nodeB = new DNode(2);
		DNode nodeC = new DNode(3);
		l.addFirst(nodeA);
		l.addAfter(nodeB, nodeA);
		l.addAfter(nodeC, nodeA);
		Assert.assertEquals("132", toString(l));
		Assert.assertArrayEquals(new int[]{1,3,2}, l.toNumbers());
		Assert.assertEquals(3, l.getSize());
	}

	@Test
	public void remove() {
		DList l = new DList();
		DNode nodeA = new DNode(1);
		DNode nodeB = new DNode(2);
		DNode nodeC = new DNode(3);
		l.addFirst(nodeA);
		l.addAfter(nodeB, nodeA);
		l.addAfter(nodeC, nodeA);
		Assert.assertEquals("132", toString(l));
		Assert.assertArrayEquals(new int[]{1,3,2}, l.toNumbers());
		Assert.assertEquals(3, l.getSize());
		l.remove(nodeC);
		Assert.assertEquals("12", toString(l));
		Assert.assertArrayEquals(new int[]{1,2}, l.toNumbers());
		Assert.assertEquals(2, l.getSize());
		l.remove(nodeA);
		Assert.assertEquals("2", toString(l));
		Assert.assertArrayEquals(new int[]{2}, l.toNumbers());
		Assert.assertEquals(1, l.getSize());
		l.remove(nodeB);
		Assert.assertEquals("", toString(l));
		Assert.assertArrayEquals(new int[]{}, l.toNumbers());
		Assert.assertEquals(0, l.getSize());
	}
	
	private String toString(DList l) {
		StringBuilder sb = new StringBuilder();
		DNode node = l.getHead();
		while (node != null) {
			sb.append(node.getNumber());
			node = node.getNext();
		}
		return sb.toString();
	}
	
}
