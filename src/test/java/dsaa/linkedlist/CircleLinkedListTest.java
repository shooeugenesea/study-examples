package dsaa.linkedlist;

import org.junit.Assert;
import org.junit.Test;

import dsaa.linkedlist.CircleLinkedList;
import dsaa.linkedlist.CircleLinkedList.CNode;

public class CircleLinkedListTest {

	@Test
	public void add() {
		CircleLinkedList list = new CircleLinkedList();
		CNode nodeA = list.add(new CNode("a"));
		Assert.assertEquals(nodeA, list.getCursor().getNext());
		Assert.assertEquals(1, list.getSize());
		Assert.assertEquals("a", toString(list));
		CNode nodeB = list.add(new CNode("b"));
		Assert.assertEquals(nodeB, list.getCursor().getNext());
		Assert.assertEquals(2, list.getSize());
		Assert.assertEquals("ab", toString(list));
	}
	
	@Test
	public void advance() {
		CircleLinkedList list = new CircleLinkedList();
		CNode nodeA = list.add(new CNode("a"));
		CNode nodeB = list.add(new CNode("b"));
		CNode nodeC = list.add(new CNode("c"));
		CNode nodeD = list.add(new CNode("d"));
		CNode nodeE = list.add(new CNode("e"));
		Assert.assertEquals(nodeA, list.getCursor());
		Assert.assertEquals(nodeE, list.advance());
		Assert.assertEquals(nodeD, list.advance());
		Assert.assertEquals(nodeC, list.advance());
		Assert.assertEquals(nodeB, list.advance());
		Assert.assertEquals(nodeA, list.advance());
	}
	
	@Test
	public void remove() {
		CircleLinkedList list = new CircleLinkedList();
		CNode nodeA = list.add(new CNode("a"));
		Assert.assertEquals("a", toString(list));
		Assert.assertEquals(1, list.getSize());
		Assert.assertEquals(nodeA, list.remove());
		Assert.assertEquals("", toString(list));
		Assert.assertEquals(0, list.getSize());
		Assert.assertEquals(null, list.remove());
		Assert.assertEquals(0, list.getSize());
		
		CNode nodeB = list.add(new CNode("b"));
		CNode nodeC = list.add(new CNode("c"));
		CNode nodeD = list.add(new CNode("d"));
		Assert.assertEquals("bdc", toString(list));
		Assert.assertEquals(nodeB, list.getCursor());
		Assert.assertEquals(3, list.getSize());
		Assert.assertEquals(nodeD, list.remove());
		Assert.assertEquals(nodeC, list.remove());
		Assert.assertEquals(nodeB, list.remove());
		Assert.assertEquals("", toString(list));
		Assert.assertEquals(0, list.getSize());
	}
	
	private String toString(CircleLinkedList list) {
		StringBuilder sb = new StringBuilder();
		CNode cursor = list.getCursor();
		CNode node = cursor;
		do {
			if ( node == null ) {
				break;
			}
			sb.append(node.getName());
		} while ((node = node.getNext()) != cursor );
		return sb.toString();
	}
	
}
