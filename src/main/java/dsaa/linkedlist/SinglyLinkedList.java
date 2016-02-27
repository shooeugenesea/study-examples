package dsaa.linkedlist;

public class SinglyLinkedList {

	private Node head;
	private Node tail;
	private int size;
	
	public void addFirst(Node node) {
		if ( head == null ) {
			head = node;
			tail = node;
		} else {
			node.setNext(head);
			head = node;
		}
		size++;
	}
	
	public void addLast(Node node) {
		if ( tail == null ) {
			head = node;
			tail = node;
		} else {
			tail.setNext(node);
			tail = node;
		}
		size++;
	}
	
	public void removeFirst() {
		if ( head == null ) {
			return;
		}
		head = head.getNext();
		size--;
	}
	
	public void removeLast() {
		if ( head == null ) {
			return;
		}
		if ( head == tail ) {
			head = null;
			tail = null;
			size--;
			return;
		}
		Node next = head;
		while ( next.getNext() != null ) {
			if ( tail == next.getNext() ) {
				next.setNext(null);
				tail = next;
			} else {
				next = next.getNext();
			}
		}
		size--;
	}
	
	public Node getHead() {
		return head;
	}
	
	public Node getTail() {
		return tail;
	}
	
	public int getSize() {
		return size;
	}
	
	public static class Node {
		private Node next;
		private final String name;
		public Node(String name) {
			this.name = name;
		}
		void setNext(Node next) {
			this.next = next;
		}
		public Node getNext() {
			return this.next;
		}
		public String getName() {
			return name;
		}
		@Override
		public String toString() {
			return name;
		}
	}
	
}
