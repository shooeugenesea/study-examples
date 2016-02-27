package dsaa.stack;

public class ArrayStack {

	private int tailIdx = 0;
	private String[] ary;
	
	public ArrayStack(int capacity) {
		this.ary = new String[capacity];
	}
	
	private boolean isFull() {
		return tailIdx == ary.length - 1;
	}
	
	private boolean isEmpty() {
		return tailIdx == 0;
	}
	
	public void push(String s) {
		if ( isFull() ) {
			throw new RuntimeException("full, can't push any data. s:" + s);
		}
		ary[tailIdx++] = s;
	}
	
	public String pop() {
		if ( isEmpty() ) {
			throw new RuntimeException("empty, can't pop any data");
		}
		String result = ary[--tailIdx];
		ary[tailIdx] = null;
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for ( int i = 0; i < tailIdx; i++ ) {
			s.append(ary[i]);
		}
		return s.toString();
	}
	
}
