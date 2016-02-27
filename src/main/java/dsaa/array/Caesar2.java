package dsaa.array;

public class Caesar2 {

	private static final char[] A_Z = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	private static final char[] ENCRYPT_WORDS = new char[A_Z.length];
	private static final char[] DECRYPT_WORDS = new char[A_Z.length];
	
	/**
	 * ABCDEFGHIJKLMNOPQRSTUVWXYZ
	 * DEFGHIJKLMNOPQRSTUVWXYZABC
	 * 
	 * */
	public Caesar2(int shift) {
		shift = shift % A_Z.length;
		for ( int i = 0; i < A_Z.length; i++ ) {
			ENCRYPT_WORDS[i] = A_Z[(i + (shift)) % A_Z.length];
		}
		for ( int i = 0; i < DECRYPT_WORDS.length; i++ ) {
			DECRYPT_WORDS[ENCRYPT_WORDS[i] - 'A'] = A_Z[i];
		}
		System.out.println(new String(ENCRYPT_WORDS));
	}
	
	public String encrypt(String s) {
		StringBuilder sb = new StringBuilder();
		for ( char c: s.toCharArray() ) {
			if ( Character.isWhitespace(c) ) {
				sb.append(c);
				continue;
			}
			if ( !Character.isUpperCase(c) || !Character.isLetter(c) ) {
				throw new IllegalArgumentException("Illegal char: " + c);
			}
			sb.append(ENCRYPT_WORDS[c - 'A']);
		}
		return sb.toString();
	}

	public String decrypt(String s) {
		StringBuilder sb = new StringBuilder();
		for ( char c: s.toCharArray() ) {
			if ( Character.isWhitespace(c) ) {
				sb.append(c);
				continue;
			}
			if ( !Character.isUpperCase(c) || !Character.isLetter(c) ) {
				throw new IllegalArgumentException("Illegal char: " + c);
			}
			sb.append(DECRYPT_WORDS[c - 'A']);
		}
		return sb.toString();
	}
	
}
