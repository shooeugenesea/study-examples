package dsaa.queue;

import org.junit.Assert;
import org.junit.Test;

public class LinkedDequeTest {

    @Test
    public void addFirst() {
        LinkedDeque<String> d = new LinkedDeque<String>();
        d.addFirst("1"); Assert.assertEquals("1", d.getFirst()); Assert.assertEquals("1", d.getLast());
        d.addFirst("2"); Assert.assertEquals("2", d.getFirst()); Assert.assertEquals("1", d.getLast());
        d.addFirst("3"); Assert.assertEquals("3", d.getFirst()); Assert.assertEquals("1", d.getLast());
        d.addFirst("4"); Assert.assertEquals("4", d.getFirst()); Assert.assertEquals("1", d.getLast());
        d.addFirst("5"); Assert.assertEquals("5", d.getFirst()); Assert.assertEquals("1", d.getLast());
    }

    @Test
    public void addLast() {
        LinkedDeque<String> d = new LinkedDeque<String>();
        d.addLast("1"); Assert.assertEquals("1", d.getFirst()); Assert.assertEquals("1", d.getLast());
        d.addLast("2"); Assert.assertEquals("1", d.getFirst()); Assert.assertEquals("2", d.getLast());
        d.addLast("3"); Assert.assertEquals("1", d.getFirst()); Assert.assertEquals("3", d.getLast());
        d.addLast("4"); Assert.assertEquals("1", d.getFirst()); Assert.assertEquals("4", d.getLast());
        d.addLast("5"); Assert.assertEquals("1", d.getFirst()); Assert.assertEquals("5", d.getLast());
    }
    
    @Test
    public void removeFirst() {
        LinkedDeque<String> d = new LinkedDeque<String>();
        d.addFirst("1"); Assert.assertEquals("1", d.getFirst()); Assert.assertEquals("1", d.getLast());
        d.addFirst("2"); Assert.assertEquals("2", d.getFirst()); Assert.assertEquals("1", d.getLast());
        d.addFirst("3"); Assert.assertEquals("3", d.getFirst()); Assert.assertEquals("1", d.getLast());
        d.addFirst("4"); Assert.assertEquals("4", d.getFirst()); Assert.assertEquals("1", d.getLast());
        d.addFirst("5"); Assert.assertEquals("5", d.getFirst()); Assert.assertEquals("1", d.getLast());
        
        Assert.assertEquals("5", d.removeFirst()); Assert.assertEquals("4",  d.getFirst()); Assert.assertEquals("1", d.getLast());
        Assert.assertEquals("4", d.removeFirst()); Assert.assertEquals("3",  d.getFirst()); Assert.assertEquals("1", d.getLast());
        Assert.assertEquals("3", d.removeFirst()); Assert.assertEquals("2",  d.getFirst()); Assert.assertEquals("1", d.getLast());
        Assert.assertEquals("2", d.removeFirst()); Assert.assertEquals("1",  d.getFirst()); Assert.assertEquals("1", d.getLast());
        Assert.assertEquals("1", d.removeFirst()); Assert.assertEquals(null, d.getFirst()); Assert.assertEquals(null, d.getLast());
    }
    
    @Test
    public void removeLast() {
        LinkedDeque<String> d = new LinkedDeque<String>();
        d.addFirst("1"); Assert.assertEquals("1", d.getFirst()); Assert.assertEquals("1", d.getLast());
        d.addFirst("2"); Assert.assertEquals("2", d.getFirst()); Assert.assertEquals("1", d.getLast());
        d.addFirst("3"); Assert.assertEquals("3", d.getFirst()); Assert.assertEquals("1", d.getLast());
        d.addFirst("4"); Assert.assertEquals("4", d.getFirst()); Assert.assertEquals("1", d.getLast());
        d.addFirst("5"); Assert.assertEquals("5", d.getFirst()); Assert.assertEquals("1", d.getLast());
        
        Assert.assertEquals("1", d.removeLast()); Assert.assertEquals("5",  d.getFirst()); Assert.assertEquals("2", d.getLast());
        Assert.assertEquals("2", d.removeLast()); Assert.assertEquals("5",  d.getFirst()); Assert.assertEquals("3", d.getLast());
        Assert.assertEquals("3", d.removeLast()); Assert.assertEquals("5",  d.getFirst()); Assert.assertEquals("4", d.getLast());
        Assert.assertEquals("4", d.removeLast()); Assert.assertEquals("5",  d.getFirst()); Assert.assertEquals("5", d.getLast());
        Assert.assertEquals("5", d.removeLast()); Assert.assertEquals(null, d.getFirst()); Assert.assertEquals(null, d.getLast());
    }
    
    @Test
    public void size() {
        LinkedDeque<String> d = new LinkedDeque<String>();
        Assert.assertTrue(d.isEmpty());
        d.addFirst("1"); Assert.assertEquals(1, d.size()); Assert.assertFalse(d.isEmpty());
        d.addFirst("2"); Assert.assertEquals(2, d.size()); Assert.assertFalse(d.isEmpty());
        d.addFirst("3"); Assert.assertEquals(3, d.size()); Assert.assertFalse(d.isEmpty());
        d.addFirst("4"); Assert.assertEquals(4, d.size()); Assert.assertFalse(d.isEmpty());
        d.addFirst("5"); Assert.assertEquals(5, d.size()); Assert.assertFalse(d.isEmpty());
        d.removeFirst(); Assert.assertEquals(4, d.size()); Assert.assertFalse(d.isEmpty());
        d.removeFirst(); Assert.assertEquals(3, d.size()); Assert.assertFalse(d.isEmpty());
        d.removeFirst(); Assert.assertEquals(2, d.size()); Assert.assertFalse(d.isEmpty());
        d.removeFirst(); Assert.assertEquals(1, d.size()); Assert.assertFalse(d.isEmpty());
        d.removeFirst(); Assert.assertEquals(0, d.size()); Assert.assertTrue(d.isEmpty());
        
        d.addLast("1"); Assert.assertEquals(1, d.size()); Assert.assertFalse(d.isEmpty());
        d.addLast("2"); Assert.assertEquals(2, d.size()); Assert.assertFalse(d.isEmpty());
        d.addLast("3"); Assert.assertEquals(3, d.size()); Assert.assertFalse(d.isEmpty());
        d.addLast("4"); Assert.assertEquals(4, d.size()); Assert.assertFalse(d.isEmpty());
        d.addLast("5"); Assert.assertEquals(5, d.size()); Assert.assertFalse(d.isEmpty());
        d.removeLast(); Assert.assertEquals(4, d.size()); Assert.assertFalse(d.isEmpty());
        d.removeLast(); Assert.assertEquals(3, d.size()); Assert.assertFalse(d.isEmpty());
        d.removeLast(); Assert.assertEquals(2, d.size()); Assert.assertFalse(d.isEmpty());
        d.removeLast(); Assert.assertEquals(1, d.size()); Assert.assertFalse(d.isEmpty());
        d.removeLast(); Assert.assertEquals(0, d.size()); Assert.assertTrue(d.isEmpty());
    }
    
}
