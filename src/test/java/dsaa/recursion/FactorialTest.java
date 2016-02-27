package dsaa.recursion;

import org.junit.Assert;
import org.junit.Test;

import dsaa.recursion.Factorial;

public class FactorialTest {

	@Test
	public void factorialTest() {
		Factorial f = new Factorial();
		Assert.assertEquals( 1, f.factorial(0) );
		Assert.assertEquals( 1, f.factorial(1) );
		Assert.assertEquals( 2, f.factorial(2) );
		Assert.assertEquals( 6, f.factorial(3) );
		Assert.assertEquals( 24, f.factorial(4) );
		Assert.assertEquals( 120, f.factorial(5) );
		Assert.assertEquals( 720, f.factorial(6) );
		Assert.assertEquals( 5040, f.factorial(7) );
		Assert.assertEquals( 40320, f.factorial(8) );
		Assert.assertEquals( 362880, f.factorial(9) );
		
	}
	
}
