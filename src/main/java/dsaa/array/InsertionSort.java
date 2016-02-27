package dsaa.array;

import java.util.Arrays;

public class InsertionSort {

	public void sort(char[] ary) {
		for ( int i = 1; i < ary.length; i++ ) {
			System.out.println("array:" + Arrays.toString(ary));
			for ( int j = i; j > 0; j-- ) {
				char current = ary[j];
				System.out.printf("current:%s, ary[%d]:%s, ary[%d]:%s", current, j-1, ary[j-1], j, ary[j]);
				if ( current < ary[j-1] ) {
					System.out.printf(" => switch ary[%d] and ary[%d]\n", j-1, j);
					ary[j] = ary[j-1];
					ary[j-1] = current;
				} else {
					System.out.println("");
				}
			}
		}
	}
	
}
