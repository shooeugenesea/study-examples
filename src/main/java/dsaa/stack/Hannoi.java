package dsaa.stack;

public class Hannoi {

	private final int size;
	private LinkedStack stackA, stackB, stackC;
	
	public Hannoi(int size) {
		this.size = size;
		this.stackA = new LinkedStack("A");
		this.stackB = new LinkedStack("B");
		this.stackC = new LinkedStack("C");
		for ( int i = size; i > 0; i-- ) {
			stackA.push(new Node(String.valueOf(i)));
		}
		System.out.println("init: " + stackA);
	}
	
	public void move() {
		move(size, stackA, stackB, stackC);
	}
	
	private void move(int size, LinkedStack stackA, LinkedStack stackB, LinkedStack stackC) {
		if ( size == 1 ) {
			Node popA = stackA.pop();
			stackC.push(popA);
			System.out.printf("move %s: %10s, %10s, %10s\n", popA, this.stackA, this.stackB, this.stackC);
		} else {
			move(size-1, stackA, stackC, stackB);
			move(1, stackA, stackB, stackC);
			move(size-1, stackB, stackA, stackC);
		}
	}

	public LinkedStack getStackA() {
		return stackA;
	}
	
	public LinkedStack getStackB() {
		return stackB;
	}
	
	public LinkedStack getStackC() {
		return stackC;
	}
	
	public static class LinkedStack {
		
		private final String name;
		private Node head;

		public LinkedStack(String name) {
			this.name = name;
		}
		
		public void push(Node node) {
			if ( head == null ) {
				this.head = node;
			} else {
				node.setNext(head);
				head = node;
			}
		}
		
		public Node pop() {
			if ( head == null ) {
				throw new RuntimeException("nothing to pop");
			} else {
				Node result = head;
				Node next = head.getNext();
				head.setNext(null);
				head = next;
				return result;
			}
		}
		
		@Override
		public String toString() {
			StringBuilder s = new StringBuilder();
			if ( head == null ) {
				return s.append("[" + name + "]").toString();
			} else {
				Node node = head;
				while (node != null) {
					s.append(node.name);
					node = node.getNext();
				}
				return s.append("[" + name + "]").toString();
			}
		}
		
	}

	public static class Node {
		private final String name;
		private Node next;
		public Node(String name) {
			this.name = name;
		}
		public void setNext(Node next) {
			this.next = next;
		}
		public String getName() {
			return name;
		}
		public Node getNext() {
			return next;
		}
		@Override
		public String toString() {
			return "[" + name + "]";
		}
	}
	
}
