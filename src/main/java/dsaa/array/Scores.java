package dsaa.array;

class Scores {

	private static final int MAX_ENTRIES = 5;
	private int length;
	private Score[] entries;
	
	public Scores() {
		entries = new Score[MAX_ENTRIES];
		length = 0;
	}
	
	public Score remove(int idx) {
		if ( idx < 0 || idx > length ) {
			throw new IndexOutOfBoundsException();
		}
		Score e = entries[idx];
		for ( int i = idx; i < length-1; i++ ) {
			entries[i] = entries[i+1];
		}
		entries[length-1] = null;
		length--;
		return e;
	}
	
	public void add(Score entry) {
		boolean arrayFull = length == entries.length;
		if ( arrayFull ) {
			boolean lessThanLast = entries[length-1].getScore() > entry.getScore();
			if ( lessThanLast ) {
				return;
			}
		}
		int targetIdx = 0;
		for ( int oriIdx = length-1; oriIdx >= 0; oriIdx-- ) {
			if ( entries[oriIdx].getScore() < entry.getScore() ) {
				entries[oriIdx+1] = entries[oriIdx];
			} else {
				targetIdx = oriIdx+1;
				break;
			}
		}
		entries[targetIdx] = entry;
		length++;
	}

	public int getLength() {
		return length;
	}
	
	public Score get(int idx) {
		return entries[idx];
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for ( int i = 0; i < length; i++ ) {
			sb.append(entries[i]).append(",");
		}
		sb.append("length:" + length);
		return sb.toString();
	}
	
}
