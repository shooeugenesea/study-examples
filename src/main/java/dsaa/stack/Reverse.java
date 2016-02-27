package dsaa.stack;

public class Reverse {

	public void reverse(String[] sAry) {
		ReverseStack stack = new ReverseStack();
		for ( String s: sAry ) {
			stack.push(s);
		}
		for ( int i = 0; i < sAry.length; i++ ) {
			sAry[i] = stack.pop();
		}
	}
	
	static class ReverseStack {
		private Node tail = new Node(null);
		public void push(String s) {
			Node prev = new Node(s);
			prev.setPrev(tail.getPrev());
			tail.setPrev(prev);
		}
		public String pop() {
			Node prev = tail.getPrev();
			if ( prev == null ) {
				return null;
			}
			tail.setPrev(prev.getPrev());
			prev.setPrev(null);
			return prev.getVal();
		}
		@Override
		public String toString() {
			StringBuilder s = new StringBuilder();
			Node n = tail;
			while ((n = n.getPrev()) != null) {
				s.append(n.getVal());
			}
			return s.toString();
		}
	}
	
	
	private static class Node {
		private String val;
		private Node prev;
		public Node(String val) {
			this.val = val;
		}
		public void setPrev(Node prev) {
			this.prev = prev;
		}
		public String getVal() {
			return val;
		}
		public Node getPrev() {
			return prev;
		}
	}
	
}
