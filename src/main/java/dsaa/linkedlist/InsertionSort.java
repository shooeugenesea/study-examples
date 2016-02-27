package dsaa.linkedlist;

import java.util.Arrays;


public class InsertionSort {

	private int[] numbers;
	private int[] sorted;
	
	public InsertionSort(int[] numbers) {
		this.numbers = new int[numbers.length];
		DList list = new DList();
		for ( int i = numbers.length-1; i >= 0; i-- ) {
			this.numbers[i] = numbers[i];
			list.addFirst(new DNode(numbers[i]));			
		}
		this.sorted = sort(list);
	}
	
	private int[] sort(DList list) {
		DNode head = list.getHead();
		DNode current = head;
		while ((current = current.getNext()) != null) {
			DNode prev = current.getPrev();
			do {
				if ( prev.getNumber() <= current.getNumber() ) {
					list.remove(current);
					list.addAfter(current, prev);
					break;
				} 				
			} while ( (prev = prev.getPrev()) != null );
			if ( prev == null ) {
				list.remove(current);
				list.addFirst(current);
			}
		}
		return list.toNumbers();
	}
	
	public int[] getSortedNumbers() {
		return sorted;
	}
	
	public static class DList {
		
		private DNode head;
		private int size;
		
		/**
		 * prev <-> node <-> next
		 * */
		public void remove(DNode node) {
			DNode prev = node.getPrev();
			DNode next = node.getNext();
			if ( prev != null && next != null ) {
				prev.setNext(next);
				next.setPrev(prev);
			} else if ( next == null && prev != null ) { // prev <-> node 
				node.setPrev(null);
				prev.setNext(null);
			} else if ( next != null && prev == null ) { // head 
				node.setNext(null);
				next.setPrev(null);
				head = next;
			} else { // node is head and is the only one node
				head = null;
			}
			if ( size > 0 ) {
				size--;
			}
		}

		public void addFirst(DNode node) {
			if ( head == null ) {
				head = node;
			} else {
				node.setNext(head);
				node.setPrev(null);
				head.setPrev(node);
				head = node;
			}
			size++;
		}
		
		/**
		 * afterThis <-> addThis <-> next
		 * */
		public void addAfter(DNode addThis, DNode afterThis) {
			DNode next = afterThis.getNext();
			addThis.setPrev(afterThis);
			afterThis.setNext(addThis);
			addThis.setNext(next);
			if ( next != null ) {
				next.setPrev(addThis);
			}
			size++;
		}
		
		public int getSize() {
			return size;
		}
		
		public DNode getHead() {
			return head;
		}
		
		public int[] toNumbers() {
			int[] result = new int[size];
			DNode node = head;
			for ( int i = 0; node != null; i++ ) {
				result[i] = node.getNumber();
				node = node.getNext();
			}
			return result;
		}
		
		@Override
		public String toString() {
			return Arrays.toString(toNumbers());
		}
	}
	
	public static class DNode {
		private DNode next;
		private DNode prev;
		private Integer number;
		public DNode(Integer number) {
			this.number = number;
		}
		public void setNext(DNode next) {
			this.next = next;
		}
		public DNode getNext() {
			return next;
		}
		public void setPrev(DNode prev) {
			this.prev = prev;
		}
		public DNode getPrev() {
			return prev;
		}
		public int getNumber() {
			return number;
		}
		@Override
		public String toString() {
			return "[" + number + "]";
		}
	}
	
}
