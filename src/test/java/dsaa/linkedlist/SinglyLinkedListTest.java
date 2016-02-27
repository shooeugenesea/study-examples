package dsaa.linkedlist;

import org.junit.Assert;
import org.junit.Test;

import dsaa.linkedlist.SinglyLinkedList;
import dsaa.linkedlist.SinglyLinkedList.Node;

public class SinglyLinkedListTest {

	@Test
	public void addFirst() {
		SinglyLinkedList l = new SinglyLinkedList();
		l.addFirst(new Node("a"));
		l.addFirst(new Node("b"));
		l.addFirst(new Node("c"));
		l.addFirst(new Node("d"));
		l.addFirst(new Node("e"));
		StringBuilder sb = new StringBuilder();
		
		Node node = l.getHead();
		while (node != null) {
			sb.append(node.getName());
			node = node.getNext();
		}
		Assert.assertEquals("edcba", sb.toString());
	}
	
	@Test
	public void addLast() {
		SinglyLinkedList l = new SinglyLinkedList();
		l.addLast(new Node("a"));
		l.addLast(new Node("b"));
		l.addLast(new Node("c"));
		l.addLast(new Node("d"));
		l.addLast(new Node("e"));
		StringBuilder sb = new StringBuilder();
		Node node = l.getHead();
		while (node != null) {
			sb.append(node.getName());
			node = node.getNext();
		}
		Assert.assertEquals("abcde", sb.toString());
	}
	
	@Test
	public void removeFirst() {
		SinglyLinkedList l = new SinglyLinkedList();
		l.addLast(new Node("a"));
		l.addLast(new Node("b"));
		l.addLast(new Node("c"));
		l.addLast(new Node("d"));
		l.addLast(new Node("e"));
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
		SinglyLinkedList l = new SinglyLinkedList();
		l.addLast(new Node("a"));
		l.addLast(new Node("b"));
		l.addLast(new Node("c"));
		l.addLast(new Node("d"));
		l.addLast(new Node("e"));
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
	
	private String toString(SinglyLinkedList l) {
		StringBuilder sb = new StringBuilder();
		Node node = l.getHead();
		while (node != null) {
			sb.append(node.getName());
			node = node.getNext();
		}
		return sb.toString();
	}
	
}
