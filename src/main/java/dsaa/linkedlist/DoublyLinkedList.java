package dsaa.linkedlist;


/*
 * head -> first <-> last <- tail
 * */
public class DoublyLinkedList {

	private DNode head;
	private DNode tail;
	private int size;
	
	public DoublyLinkedList() {
		this.head = new DNode(null);
		this.tail = new DNode(null);
		this.size = 0;
	}
	
	/*
	 * head -> first <-> last <- tail
	 * */
	public void addFirst(DNode node) {
		DNode first = head.getNext();
		if ( first == null ) {
			head.setNext(node);
			tail.setPrev(node);
		} else {
			first.setPrev(node);
			node.setNext(first);
			head.setNext(node);
		}
		size++;
	}

	/*
	 * origPrev <-> afterThis <-> addThis <-> origNext
	 * origPrev <-> afterThis <-> addThis <- tail
	 * */
	public void addAfter(DNode addThis, DNode afterThis) {
		DNode origNext = afterThis.getNext();
		addThis.setPrev(afterThis);
		afterThis.setNext(addThis);
		if ( origNext != null ) {
			addThis.setNext(origNext);
			origNext.setPrev(addThis);
		}
		size++;
	}
	
	/*
	 * prev <-> removeNode <-> next
	 * head -> removeNode <-> next
	 * prev <-> removeNode <- tail
	 * */
	public void remove(DNode removeNode) {
		DNode prev = removeNode.getPrev();
		DNode next = removeNode.getNext();
		if ( prev != null ) {
			prev.setNext(next);
		}
		if ( next != null ) {
			next.setPrev(prev);
		}
		size--;
	}
	
	/*
	 * head -> first <-> last <- tail
	 * */
	public void addLast(DNode node) {
		DNode last = tail.getPrev();
		if ( last == null ) {
			head.setNext(node);
			tail.setPrev(node);
		} else {
			last.setNext(node);
			node.setPrev(last);
			tail.setPrev(node);
		}
		size++;
	}
	
	/*
	 * head -> first <-> last <- tail
	 * */
	public void removeFirst() {
		DNode first = head.getNext();
		if ( first == null ) {
			return;
		}
		DNode second = first.getNext();
		if ( second == null ) {
			head.setNext(null);
			tail.setPrev(null);
		} else {
			head.setNext(second);
			second.setPrev(null);
		}
		size--;
	}
	
	/*
	 * head -> first <-> last <- tail
	 * */
	public void removeLast() {
		DNode last = tail.getPrev();
		if ( last == null ) {
			return;
		}
		DNode last2 = last.getPrev();
		if ( last2 == null ) {
			head.setNext(null);
			tail.setPrev(null);
		} else {
			tail.setPrev(last2);
			last2.setNext(null);
		}
		size--;
	}
	
	public int getSize() {
		return size;
	}
	
	public DNode getFirst() {
		return head.getNext();
	}
	
	public DNode getLast() {
		return tail.getPrev();
	}
	
	public static class DNode {
		private DNode prev;
		private DNode next;
		private String name;
		public DNode(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
		public void setNext(DNode next) {
			this.next = next;
		}
		public void setPrev(DNode prev) {
			this.prev = prev;
		}
		public DNode getNext() {
			return next;
		}
		public DNode getPrev() {
			return prev;
		}
		@Override
		public String toString() {
			return "name:" + name;
		}
	}

}
