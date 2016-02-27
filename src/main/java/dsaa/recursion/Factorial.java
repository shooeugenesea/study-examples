package dsaa.recursion;

/**
 * n! = n*(n-1)*(n-2)...3*2*1
 * */
public class Factorial {

	public int factorial(int n) {
		if ( n == 0 ) {
			return 1;
		} 
		return n * factorial(n-1);
	}
	
}
