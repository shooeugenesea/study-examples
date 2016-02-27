package dsaa.linkedlist;

/*
 * cursor = node -> next -> next2 -> node
 * */
public class CircleLinkedList {
	
	private CNode cursor;
	private int size;

	/*
	 * step 0. add0(cursor) -> add0(cursor)
	 * step 1. add0(cursor) -> add1 -> add0(cursor)
	 * step 2. add0(cursor) -> add2 -> add1 -> add0(cursor)
	 * */
	public CNode add(CNode add) {
		if ( cursor == null ) {
			cursor = add;
			add.setNext(add);
		} else {
			add.setNext(cursor.getNext());
			cursor.setNext(add);
		}
		size++;
		return add;
	}
	
	/*
	 * step 0. node0(cursor) -> node1 -> node2 -> node0(cursor)
	 * step 1. node0(cursor) -> node2 -> node0(cursor)
	 * step 2. node0(cursor) -> node0(cursor)
	 * */
	public CNode remove() {
		if ( cursor == null ) {
			return null;
		} else if ( cursor.getNext() == cursor ) {
			CNode removed = cursor.getNext();
			cursor = null;
			size--;
			return removed;
		} else {
			CNode removed = cursor.getNext();
			CNode next2 = removed.getNext();
			cursor.setNext(next2);
			removed.setNext(null);
			size--;
			return removed;
		}
	}
	
	public CNode advance() {
		cursor = cursor.getNext();
		return cursor;
	}
	
	public CNode getCursor() {
		return cursor;
	}
	
	public int getSize() {
		return size;
	}
	
	public static class CNode {
		private CNode next;
		private String name;
		public CNode(String name) {
			this.name = name;
		}
		public void setNext(CNode next) {
			this.next = next;
		}
		public CNode getNext() {
			return next;
		}
		public String getName() {
			return name;
		}
		@Override
		public String toString() {
			return "name:"+ name;
		}
	}
	
}
