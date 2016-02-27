package dsaa.array;

import org.junit.Assert;
import org.junit.Test;

import dsaa.array.Caesar1;
import dsaa.array.Caesar2;

public class CaesarTest {

	@Test
	public void encryptAndDecryptByCascar1() {
		Caesar1 t = new Caesar1(3);
		
		String encryptString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ";
		String decryptResult = "DEFGHIJKLMNOPQRSTUVWXYZABC ";
		Assert.assertEquals(decryptResult, t.encrypt(encryptString));
		Assert.assertEquals(encryptString, t.decrypt(decryptResult));
	}
	
	@Test
	public void encryptAndDecryptByCascar2() {
		Caesar2 t = new Caesar2(3);
		
		String encryptString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ";
		String decryptResult = "DEFGHIJKLMNOPQRSTUVWXYZABC ";
		Assert.assertEquals(decryptResult, t.encrypt(encryptString));
		Assert.assertEquals(encryptString, t.decrypt(decryptResult));
	}

}
