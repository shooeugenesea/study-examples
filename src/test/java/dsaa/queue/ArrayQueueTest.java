package dsaa.queue;

import org.junit.Assert;
import org.junit.Test;

public class ArrayQueueTest {

    @Test
    public void enqueueDequeue() {
        Queue<String> testee = new ArrayQueue<String>(10);
        testee.enqueue("1");
        testee.enqueue("2");
        testee.enqueue("3");
        testee.enqueue("4");
        testee.enqueue("5");
        testee.enqueue("6");
        testee.enqueue("7");
        testee.enqueue("8");
        testee.enqueue("9");
        Assert.assertEquals("1", testee.dequeue());
        Assert.assertEquals("2", testee.dequeue());
        Assert.assertEquals("3", testee.dequeue());
        Assert.assertEquals("4", testee.dequeue());
        Assert.assertEquals("5", testee.dequeue());
        Assert.assertEquals("6", testee.dequeue());
        Assert.assertEquals("7", testee.dequeue());
        Assert.assertEquals("8", testee.dequeue());
        Assert.assertEquals("9", testee.dequeue());
    }
    
    @Test
    public void size() {
        Queue<String> testee = new ArrayQueue<String>(10);
        testee.enqueue("1");  Assert.assertEquals(1, testee.size());
        testee.enqueue("2");  Assert.assertEquals(2, testee.size());
        testee.enqueue("3");  Assert.assertEquals(3, testee.size());
        testee.enqueue("4");  Assert.assertEquals(4, testee.size());
        testee.enqueue("5");  Assert.assertEquals(5, testee.size());
        testee.enqueue("6");  Assert.assertEquals(6, testee.size());
        testee.enqueue("7");  Assert.assertEquals(7, testee.size());
        testee.enqueue("8");  Assert.assertEquals(8, testee.size());
        testee.enqueue("9");  Assert.assertEquals(9, testee.size());
        testee.enqueue("10"); Assert.assertEquals(10, testee.size());
        testee.dequeue(); Assert.assertEquals(9, testee.size());
        testee.dequeue(); Assert.assertEquals(8, testee.size());
        testee.dequeue(); Assert.assertEquals(7, testee.size());
        testee.dequeue(); Assert.assertEquals(6, testee.size());
        testee.dequeue(); Assert.assertEquals(5, testee.size());
        testee.dequeue(); Assert.assertEquals(4, testee.size());
        testee.dequeue(); Assert.assertEquals(3, testee.size());
        testee.dequeue(); Assert.assertEquals(2, testee.size());
        testee.dequeue(); Assert.assertEquals(1, testee.size());
        testee.dequeue(); Assert.assertEquals(0, testee.size());
    }

    @Test
    public void isEmpty() {
        Queue<String> testee = new ArrayQueue<String>(10);
        Assert.assertTrue(testee.isEmpty());
        testee.enqueue("1"); Assert.assertFalse(testee.isEmpty());
        testee.enqueue("2"); Assert.assertFalse(testee.isEmpty());
        testee.enqueue("3"); Assert.assertFalse(testee.isEmpty());
        testee.enqueue("4"); Assert.assertFalse(testee.isEmpty());
        testee.enqueue("5"); Assert.assertFalse(testee.isEmpty());
        testee.enqueue("6"); Assert.assertFalse(testee.isEmpty());
        testee.enqueue("7"); Assert.assertFalse(testee.isEmpty());
        testee.enqueue("8"); Assert.assertFalse(testee.isEmpty());
        testee.enqueue("9"); Assert.assertFalse(testee.isEmpty());
        testee.enqueue("10"); Assert.assertFalse(testee.isEmpty());
        Assert.assertEquals("1",testee.dequeue()); Assert.assertFalse(testee.isEmpty());
        Assert.assertEquals("2",testee.dequeue()); Assert.assertFalse(testee.isEmpty());
        Assert.assertEquals("3",testee.dequeue()); Assert.assertFalse(testee.isEmpty());
        Assert.assertEquals("4",testee.dequeue()); Assert.assertFalse(testee.isEmpty());
        Assert.assertEquals("5",testee.dequeue()); Assert.assertFalse(testee.isEmpty());
        Assert.assertEquals("6",testee.dequeue()); Assert.assertFalse(testee.isEmpty());
        Assert.assertEquals("7",testee.dequeue()); Assert.assertFalse(testee.isEmpty());
        Assert.assertEquals("8",testee.dequeue()); Assert.assertFalse(testee.isEmpty());
        Assert.assertEquals("9",testee.dequeue()); Assert.assertFalse(testee.isEmpty());
        Assert.assertEquals("10",testee.dequeue()); Assert.assertTrue(testee.isEmpty());
    }
    
    @Test
    public void front() {
        Queue<String> testee = new ArrayQueue<String>(10);
        testee.enqueue("1");  Assert.assertEquals("1", testee.front());
        testee.enqueue("2");  Assert.assertEquals("1", testee.front());
        testee.enqueue("3");  Assert.assertEquals("1", testee.front());
        testee.enqueue("4");  Assert.assertEquals("1", testee.front());
        testee.enqueue("5");  Assert.assertEquals("1", testee.front());
        testee.enqueue("6");  Assert.assertEquals("1", testee.front());
        testee.enqueue("7");  Assert.assertEquals("1", testee.front());
        testee.enqueue("8");  Assert.assertEquals("1", testee.front());
        testee.enqueue("9");  Assert.assertEquals("1", testee.front());
        testee.enqueue("10"); Assert.assertEquals("1", testee.front());
    }
    
    @Test
    public void queueIsFull() {
        Queue<String> testee = new ArrayQueue<String>(10);
        testee.enqueue("1");
        testee.enqueue("2");
        testee.enqueue("3");
        testee.enqueue("4");
        testee.enqueue("5");
        testee.enqueue("6");
        testee.enqueue("7");
        testee.enqueue("8");
        testee.enqueue("9");
        testee.enqueue("10");
        try {
            testee.enqueue("11");
            Assert.fail();
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail();
    }
    
}
