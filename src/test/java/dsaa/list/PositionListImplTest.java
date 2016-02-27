package dsaa.list;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import dsaa.list.PositionList.Position;
import dsaa.list.impl.PositionListImpl;

public class PositionListImplTest {

    @Test
    public void addFirst() {
        PositionListImpl<String> testee = new PositionListImpl<String>();
        Position<String> add1 = testee.addFirst("1"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("1", testee.last().getElement()); Assert.assertEquals(1, testee.size());
        Position<String> add2 = testee.addFirst("2"); Assert.assertEquals("2", testee.first().getElement()); Assert.assertEquals("1", testee.last().getElement()); Assert.assertEquals(2, testee.size());
        Position<String> add3 = testee.addFirst("3"); Assert.assertEquals("3", testee.first().getElement()); Assert.assertEquals("1", testee.last().getElement()); Assert.assertEquals(3, testee.size());
        Position<String> add4 = testee.addFirst("4"); Assert.assertEquals("4", testee.first().getElement()); Assert.assertEquals("1", testee.last().getElement()); Assert.assertEquals(4, testee.size());
        Position<String> add5 = testee.addFirst("5"); Assert.assertEquals("5", testee.first().getElement()); Assert.assertEquals("1", testee.last().getElement()); Assert.assertEquals(5, testee.size());
        Position<String> add6 = testee.addFirst("6"); Assert.assertEquals("6", testee.first().getElement()); Assert.assertEquals("1", testee.last().getElement()); Assert.assertEquals(6, testee.size());
        
        testee.remove(add1); Assert.assertEquals("6", testee.first().getElement()); Assert.assertEquals("2", testee.last().getElement()); Assert.assertEquals(5, testee.size());
        testee.remove(add3); Assert.assertEquals("6", testee.first().getElement()); Assert.assertEquals("2", testee.last().getElement()); Assert.assertEquals(4, testee.size());
        testee.remove(add2); Assert.assertEquals("6", testee.first().getElement()); Assert.assertEquals("4", testee.last().getElement()); Assert.assertEquals(3, testee.size());
        testee.remove(add5); Assert.assertEquals("6", testee.first().getElement()); Assert.assertEquals("4", testee.last().getElement()); Assert.assertEquals(2, testee.size());
        testee.remove(add4); Assert.assertEquals("6", testee.first().getElement()); Assert.assertEquals("6", testee.last().getElement()); Assert.assertEquals(1, testee.size());
        testee.remove(add6); Assert.assertNull(testee.first()); Assert.assertNull(testee.last()); Assert.assertEquals(0, testee.size());
    }
 
    @Test
    public void addLast() {
        PositionListImpl<String> testee = new PositionListImpl<String>();
        Position<String> add1 = testee.addLast("1"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("1", testee.last().getElement()); Assert.assertEquals(1, testee.size());
        Position<String> add2 = testee.addLast("2"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("2", testee.last().getElement()); Assert.assertEquals(2, testee.size());
        Position<String> add3 = testee.addLast("3"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("3", testee.last().getElement()); Assert.assertEquals(3, testee.size());
        Position<String> add4 = testee.addLast("4"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("4", testee.last().getElement()); Assert.assertEquals(4, testee.size());
        Position<String> add5 = testee.addLast("5"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("5", testee.last().getElement()); Assert.assertEquals(5, testee.size());
        Position<String> add6 = testee.addLast("6"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("6", testee.last().getElement()); Assert.assertEquals(6, testee.size());
        
        testee.remove(add1); Assert.assertEquals("2", testee.first().getElement()); Assert.assertEquals("6", testee.last().getElement()); Assert.assertEquals(5, testee.size());
        testee.remove(add3); Assert.assertEquals("2", testee.first().getElement()); Assert.assertEquals("6", testee.last().getElement()); Assert.assertEquals(4, testee.size());
        testee.remove(add2); Assert.assertEquals("4", testee.first().getElement()); Assert.assertEquals("6", testee.last().getElement()); Assert.assertEquals(3, testee.size());
        testee.remove(add5); Assert.assertEquals("4", testee.first().getElement()); Assert.assertEquals("6", testee.last().getElement()); Assert.assertEquals(2, testee.size());
        testee.remove(add4); Assert.assertEquals("6", testee.first().getElement()); Assert.assertEquals("6", testee.last().getElement()); Assert.assertEquals(1, testee.size());
        testee.remove(add6); Assert.assertNull(testee.first()); Assert.assertNull(testee.last()); Assert.assertEquals(0, testee.size());        
    }
    
    @Test
    public void addBefore() {
        PositionListImpl<String> testee = new PositionListImpl<String>();
        Position<String> add1 = testee.addFirst("1"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("1", testee.last().getElement()); Assert.assertEquals(1, testee.size());
        Position<String> add2 = testee.addBefore(add1, "2"); Assert.assertEquals("2", testee.first().getElement()); Assert.assertEquals("1", testee.last().getElement()); Assert.assertEquals(2, testee.size());
        Position<String> add3 = testee.addBefore(add2, "3"); Assert.assertEquals("3", testee.first().getElement()); Assert.assertEquals("1", testee.last().getElement()); Assert.assertEquals(3, testee.size());
        Position<String> add4 = testee.addBefore(add3, "4"); Assert.assertEquals("4", testee.first().getElement()); Assert.assertEquals("1", testee.last().getElement()); Assert.assertEquals(4, testee.size());
        Position<String> add5 = testee.addBefore(add4, "5"); Assert.assertEquals("5", testee.first().getElement()); Assert.assertEquals("1", testee.last().getElement()); Assert.assertEquals(5, testee.size());
        Position<String> add6 = testee.addBefore(add5, "6"); Assert.assertEquals("6", testee.first().getElement()); Assert.assertEquals("1", testee.last().getElement()); Assert.assertEquals(6, testee.size());
        
        testee.remove(add1); Assert.assertEquals("6", testee.first().getElement()); Assert.assertEquals("2", testee.last().getElement()); Assert.assertEquals(5, testee.size());
        testee.remove(add3); Assert.assertEquals("6", testee.first().getElement()); Assert.assertEquals("2", testee.last().getElement()); Assert.assertEquals(4, testee.size());
        testee.remove(add2); Assert.assertEquals("6", testee.first().getElement()); Assert.assertEquals("4", testee.last().getElement()); Assert.assertEquals(3, testee.size());
        testee.remove(add5); Assert.assertEquals("6", testee.first().getElement()); Assert.assertEquals("4", testee.last().getElement()); Assert.assertEquals(2, testee.size());
        testee.remove(add4); Assert.assertEquals("6", testee.first().getElement()); Assert.assertEquals("6", testee.last().getElement()); Assert.assertEquals(1, testee.size());
        testee.remove(add6); Assert.assertNull(testee.first()); Assert.assertNull(testee.last()); Assert.assertEquals(0, testee.size());
    }
    
    @Test
    public void addAfter() {
        PositionListImpl<String> testee = new PositionListImpl<String>();
        Position<String> add1 = testee.addFirst("1"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("1", testee.last().getElement()); Assert.assertEquals(1, testee.size());
        Position<String> add2 = testee.addAfter(add1, "2"); 
        Assert.assertEquals("1", testee.first().getElement()); 
        Assert.assertEquals("2", testee.last().getElement()); 
        Assert.assertEquals(2, testee.size());
        Position<String> add3 = testee.addAfter(add2, "3"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("3", testee.last().getElement()); Assert.assertEquals(3, testee.size());
        Position<String> add4 = testee.addAfter(add3, "4"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("4", testee.last().getElement()); Assert.assertEquals(4, testee.size());
        Position<String> add5 = testee.addAfter(add4, "5"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("5", testee.last().getElement()); Assert.assertEquals(5, testee.size());
        Position<String> add6 = testee.addAfter(add5, "6"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("6", testee.last().getElement()); Assert.assertEquals(6, testee.size());
        
        testee.remove(add1); Assert.assertEquals("2", testee.first().getElement()); Assert.assertEquals("6", testee.last().getElement()); Assert.assertEquals(5, testee.size());
        testee.remove(add3); Assert.assertEquals("2", testee.first().getElement()); Assert.assertEquals("6", testee.last().getElement()); Assert.assertEquals(4, testee.size());
        testee.remove(add2); Assert.assertEquals("4", testee.first().getElement()); Assert.assertEquals("6", testee.last().getElement()); Assert.assertEquals(3, testee.size());
        testee.remove(add5); Assert.assertEquals("4", testee.first().getElement()); Assert.assertEquals("6", testee.last().getElement()); Assert.assertEquals(2, testee.size());
        testee.remove(add4); Assert.assertEquals("6", testee.first().getElement()); Assert.assertEquals("6", testee.last().getElement()); Assert.assertEquals(1, testee.size());
        testee.remove(add6); Assert.assertNull(testee.first()); Assert.assertNull(testee.last()); Assert.assertEquals(0, testee.size());
    }
    
    @Test
    public void next() {
        PositionListImpl<String> testee = new PositionListImpl<String>();
        Position<String> add1 = testee.addLast("1"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("1", testee.last().getElement()); Assert.assertEquals(1, testee.size());
        Position<String> add2 = testee.addLast("2"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("2", testee.last().getElement()); Assert.assertEquals(2, testee.size());
        Position<String> add3 = testee.addLast("3"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("3", testee.last().getElement()); Assert.assertEquals(3, testee.size());
        Position<String> add4 = testee.addLast("4"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("4", testee.last().getElement()); Assert.assertEquals(4, testee.size());
        Position<String> add5 = testee.addLast("5"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("5", testee.last().getElement()); Assert.assertEquals(5, testee.size());
        Position<String> add6 = testee.addLast("6"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("6", testee.last().getElement()); Assert.assertEquals(6, testee.size());

        Assert.assertEquals(add2, testee.next(add1));
        Assert.assertEquals(add3, testee.next(add2));
        Assert.assertEquals(add4, testee.next(add3));
        Assert.assertEquals(add5, testee.next(add4));
        Assert.assertEquals(add6, testee.next(add5));
        Assert.assertNull(testee.next(add6));
    }
    
    @Test
    public void set() {
        PositionListImpl<String> testee = new PositionListImpl<String>();
        Position<String> add1 = testee.addLast("1"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("1", testee.last().getElement()); Assert.assertEquals(1, testee.size());
        Position<String> add2 = testee.addLast("2"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("2", testee.last().getElement()); Assert.assertEquals(2, testee.size());
        Position<String> add3 = testee.addLast("3"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("3", testee.last().getElement()); Assert.assertEquals(3, testee.size());
        Position<String> add4 = testee.addLast("4"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("4", testee.last().getElement()); Assert.assertEquals(4, testee.size());
        Position<String> add5 = testee.addLast("5"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("5", testee.last().getElement()); Assert.assertEquals(5, testee.size());
        Position<String> add6 = testee.addLast("6"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("6", testee.last().getElement()); Assert.assertEquals(6, testee.size());

        Assert.assertEquals("1", testee.set(add1, "7"));
        Assert.assertEquals("2", testee.set(add2, "8"));
        Assert.assertEquals("3", testee.set(add3, "9"));
        Assert.assertEquals("4", testee.set(add4, "10"));
        Assert.assertEquals("5", testee.set(add5, "11"));
        Assert.assertEquals("6", testee.set(add6, "12"));
        
        Position<String> node1 = testee.first();
        Position<String> node2 = testee.next(node1);
        Position<String> node3 = testee.next(node2);
        Position<String> node4 = testee.next(node3);
        Position<String> node5 = testee.next(node4);
        Position<String> node6 = testee.next(node5);
        
        Assert.assertEquals("7", node1.getElement());
        Assert.assertEquals("8", node2.getElement());
        Assert.assertEquals("9", node3.getElement());
        Assert.assertEquals("10", node4.getElement());
        Assert.assertEquals("11", node5.getElement());
        Assert.assertEquals("12", node6.getElement());
    }
    
    @Test
    public void iterator() {
        PositionListImpl<String> testee = new PositionListImpl<String>();
        testee.addLast("1"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("1", testee.last().getElement()); Assert.assertEquals(1, testee.size());
        testee.addLast("2"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("2", testee.last().getElement()); Assert.assertEquals(2, testee.size());
        testee.addLast("3"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("3", testee.last().getElement()); Assert.assertEquals(3, testee.size());
        testee.addLast("4"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("4", testee.last().getElement()); Assert.assertEquals(4, testee.size());
        testee.addLast("5"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("5", testee.last().getElement()); Assert.assertEquals(5, testee.size());
        testee.addLast("6"); Assert.assertEquals("1", testee.first().getElement()); Assert.assertEquals("6", testee.last().getElement()); Assert.assertEquals(6, testee.size());

        StringBuilder sb = new StringBuilder();
        for ( Iterator<String> i = testee.iterator(); i.hasNext(); ) {
             sb.append(i.next());
             i.remove();
        }
        Assert.assertEquals("123456", sb.toString());
    }
    
}
