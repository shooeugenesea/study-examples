package dsaa.array;

public class Caesar1 {

	private static final String A_Z = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final char[] WORDS = (A_Z + A_Z + A_Z).toCharArray();
	private final int shift;
	
	public Caesar1(int shift) {
		this.shift = shift % A_Z.length();
	}
	
	public String encrypt(String s) {
		StringBuilder sb = new StringBuilder();
		char[] cs = s.toCharArray();
		for ( char c: cs ) {
			if ( Character.isWhitespace(c) ) {
				sb.append(c);
				continue;
			}
			if ( !Character.isUpperCase(c) ) {
				throw new IllegalArgumentException("illegal word:" + c);
			}
			sb.append(WORDS[A_Z.length() + (c-'A') + shift]);
		}
		return sb.toString();
	}

	public String decrypt(String s) {
		StringBuilder sb = new StringBuilder();
		char[] cs = s.toCharArray();
		for ( char c: cs ) {
			if ( Character.isWhitespace(c) ) {
				sb.append(c);
				continue;
			}
			if ( !Character.isUpperCase(c) ) {
				throw new IllegalArgumentException("illegal word:" + c);
			}
			sb.append(WORDS[A_Z.length() + (c-'A') - shift]);
		}
		return sb.toString();
	}

}
