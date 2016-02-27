package dsaa.linkedlist;

import org.junit.Assert;
import org.junit.Test;

import dsaa.linkedlist.DoublyLinkedList;
import dsaa.linkedlist.DoublyLinkedList.DNode;

public class DoublyLinkedListTest {

	@Test
	public void addFirst() {
		DoublyLinkedList l = new DoublyLinkedList();
		l.addFirst(new DNode("a"));
		l.addFirst(new DNode("b"));
		l.addFirst(new DNode("c"));
		l.addFirst(new DNode("d"));
		l.addFirst(new DNode("e"));
		Assert.assertEquals("edcba", toString(l));
	}
	
	@Test
	public void addLast() {
		DoublyLinkedList l = new DoublyLinkedList();
		l.addLast(new DNode("a"));
		l.addLast(new DNode("b"));
		l.addLast(new DNode("c"));
		l.addLast(new DNode("d"));
		l.addLast(new DNode("e"));
		Assert.assertEquals("abcde", toString(l));
		Assert.assertEquals(5, l.getSize());
	}
	
	@Test
	public void remove() {
		DoublyLinkedList l = new DoublyLinkedList();
		DNode nodeC = new DNode("c");
		l.addLast(new DNode("a"));
		l.addLast(new DNode("b"));
		l.addLast(nodeC);
		l.addLast(new DNode("d"));
		l.addLast(new DNode("e"));
		Assert.assertEquals("abcde", toString(l));
		Assert.assertEquals(5, l.getSize());
		l.remove(nodeC);
		Assert.assertEquals("abde", toString(l));
		Assert.assertEquals(4, l.getSize());
	}
	
	@Test
	public void addAfter() {
		DoublyLinkedList l = new DoublyLinkedList();
		DNode nodeA = new DNode("a");
		DNode nodeB = new DNode("b");
		DNode nodeC = new DNode("c");
		l.addLast(nodeA);
		l.addAfter(nodeB, nodeA);
		l.addAfter(nodeC, nodeA);
		Assert.assertEquals("acb", toString(l));
		Assert.assertEquals(3, l.getSize());
	}
	
	@Test
	public void removeFirst() {
		DoublyLinkedList l = new DoublyLinkedList();
		l.addLast(new DNode("a"));
		l.addLast(new DNode("b"));
		l.addLast(new DNode("c"));
		l.addLast(new DNode("d"));
		l.addLast(new DNode("e"));
		Assert.assertEquals("abcde", toString(l));
		Assert.assertEquals(5, l.getSize());
		l.removeFirst();
		Assert.assertEquals("bcde", toString(l));
		Assert.assertEquals(4, l.getSize());
		l.removeFirst();
		Assert.assertEquals("cde", toString(l));
		Assert.assertEquals(3, l.getSize());
		l.removeFirst();
		Assert.assertEquals("de", toString(l));
		Assert.assertEquals(2, l.getSize());
		l.removeFirst();
		Assert.assertEquals("e", toString(l));
		Assert.assertEquals(1, l.getSize());
		l.removeFirst();
		Assert.assertEquals("", toString(l));
		Assert.assertEquals(0, l.getSize());
		l.removeFirst();
		Assert.assertEquals("", toString(l));
		Assert.assertEquals(0, l.getSize());
	}
	
	@Test
	public void removeLast() {
		DoublyLinkedList l = new DoublyLinkedList();
		l.addLast(new DNode("a"));
		l.addLast(new DNode("b"));
		l.addLast(new DNode("c"));
		l.addLast(new DNode("d"));
		l.addLast(new DNode("e"));
		Assert.assertEquals("abcde", toString(l));
		Assert.assertEquals(5,l.getSize());
		l.removeLast();
		Assert.assertEquals("abcd", toString(l));
		Assert.assertEquals(4,l.getSize());
		l.removeLast();
		Assert.assertEquals("abc", toString(l));
		Assert.assertEquals(3,l.getSize());
		l.removeLast();
		Assert.assertEquals("ab", toString(l));
		Assert.assertEquals(2,l.getSize());
		l.removeLast();
		Assert.assertEquals("a", toString(l));
		Assert.assertEquals(1,l.getSize());
		l.removeLast();
		Assert.assertEquals("", toString(l));
		Assert.assertEquals(0,l.getSize());
		l.removeLast();
		Assert.assertEquals("", toString(l));
		Assert.assertEquals(0,l.getSize());
		l.removeLast();
		Assert.assertEquals("", toString(l));
		Assert.assertEquals(0,l.getSize());
	}
	
	private String toString(DoublyLinkedList l) {
		StringBuilder sb = new StringBuilder();
		DNode node = l.getFirst();
		while (node != null) {
			sb.append(node.getName());
			node = node.getNext();
		}
		return sb.toString();
	}
	
}
