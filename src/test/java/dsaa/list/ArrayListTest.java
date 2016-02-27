package dsaa.list;

import org.junit.Assert;
import org.junit.Test;

import dsaa.list.ArrayList.IndexOutOfBoundException;

public class ArrayListTest {

    @Test
    public void test2() {
        int size = 11;
        System.out.println(size >> 1);
        System.out.println(Integer.toBinaryString((size)));
        System.out.println(Integer.toBinaryString((size >> 1)));
        size = 16;
        System.out.println(size >> 2);
    }
    
    @Test
    public void test() {
        ArrayList<String> testee = new ArrayList<>();
        testee.add(0,  "1"); Assert.assertEquals( "1", testee.get(0)); Assert.assertEquals(1, testee.size());
        testee.add(0,  "2"); Assert.assertEquals( "2", testee.get(0)); Assert.assertEquals(2, testee.size());
        testee.add(0,  "3"); Assert.assertEquals( "3", testee.get(0)); Assert.assertEquals(3, testee.size());
        testee.add(0,  "4"); Assert.assertEquals( "4", testee.get(0)); Assert.assertEquals(4, testee.size());
        testee.add(0,  "5"); Assert.assertEquals( "5", testee.get(0)); Assert.assertEquals(5, testee.size());
        testee.add(0,  "6"); Assert.assertEquals( "6", testee.get(0)); Assert.assertEquals(6, testee.size());
        testee.set(0,  "7"); Assert.assertEquals( "7", testee.get(0)); Assert.assertEquals(6, testee.size());
        testee.set(1,  "8"); Assert.assertEquals( "8", testee.get(1)); Assert.assertEquals(6, testee.size());
        testee.set(2,  "9"); Assert.assertEquals( "9", testee.get(2)); Assert.assertEquals(6, testee.size());
        testee.set(3, "10"); Assert.assertEquals("10", testee.get(3)); Assert.assertEquals(6, testee.size());
        testee.set(4, "11"); Assert.assertEquals("11", testee.get(4)); Assert.assertEquals(6, testee.size());
        testee.set(5, "12"); Assert.assertEquals("12", testee.get(5)); Assert.assertEquals(6, testee.size());
        testee.remove(0); Assert.assertEquals( "8", testee.get(0)); Assert.assertEquals(5, testee.size());
        testee.remove(0); Assert.assertEquals( "9", testee.get(0)); Assert.assertEquals(4, testee.size());
        testee.remove(0); Assert.assertEquals("10", testee.get(0)); Assert.assertEquals(3, testee.size());
        testee.remove(0); Assert.assertEquals("11", testee.get(0)); Assert.assertEquals(2, testee.size());
        testee.remove(0); Assert.assertEquals("12", testee.get(0)); Assert.assertEquals(1, testee.size());
        testee.remove(0); Assert.assertEquals(0, testee.size());
        try {
            testee.get(0);
            Assert.fail();
        } catch (IndexOutOfBoundException e) {
            return;
        }
    }
    
}
