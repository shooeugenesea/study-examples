package dsaa.list.impl;

import java.util.Iterator;

import dsaa.list.PositionList;

public class PositionListImpl<E> implements PositionList<E> {

    private NodePosition<E> first, last;
    private int size = 0;
    
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Position<E> first() {
        return first;
    }

    @Override
    public Position<E> last() {
        return last;
    }

    @Override
    public Position<E> next(Position<E> p) {
        NodePosition<E> nodeP = checkPosition(p);
        return nodeP.getNext();
    }

    private NodePosition<E> checkPosition(Position<E> p) {
        if ( !NodePosition.class.isInstance(p) ) {
            throw new IllegalArgumentException("p is not " + NodePosition.class);
        }
        if ( p == null ) {
            throw new IllegalArgumentException("p must not null");
        }
        return (NodePosition<E>) p;
    }
    
    @Override
    public E set(Position<E> p, E e) {
        NodePosition<E> nodeP = checkPosition(p);
        E temp = nodeP.getElement();
        nodeP.setElement(e);
        return temp;
    }

    @Override
    public Position<E> addFirst(E e) {
        NodePosition<E> newFirst = new NodePosition<E>(e);
        if ( first == null ) {
            first = newFirst;
            last = newFirst;
        } else {
            first.setPrev(newFirst);
            newFirst.setNext(first);
            first = newFirst;
        }
        size++;
        return newFirst;
    }

    @Override
    public Position<E> addLast(E e) {
        NodePosition<E> newLast = new NodePosition<E>(e);
        if ( last == null ) {
            first = newLast;
            last = newLast;
        } else {
            last.setNext(newLast);
            newLast.setPrev(last);
            last = newLast;
        }
        size++;
        return newLast;
    }

    @Override
    public Position<E> addBefore(Position<E> p, E e) {
        NodePosition<E> nodeP = checkPosition(p);
        NodePosition<E> prevPrev = nodeP.getPrev();
        NodePosition<E> newBefore = new NodePosition<E>(e);
        if ( prevPrev != null ) {
            prevPrev.setNext(newBefore);
        }
        newBefore.setNext(nodeP);
        nodeP.setPrev(newBefore);
        size++;
        first = first == p ? newBefore : first;
        return newBefore;
    }

    @Override
    public Position<E> addAfter(Position<E> p, E e) {
        NodePosition<E> afterThis = checkPosition(p);
        NodePosition<E> nextP = afterThis.getNext();
        NodePosition<E> addThis = new NodePosition<E>(e);
        afterThis.setNext(addThis);
        addThis.setPrev(afterThis);
        addThis.setNext(nextP);
        if ( nextP != null ) {
            nextP.setPrev(addThis);
        }
        size++;
        last = last == p ? addThis : last;
        return addThis;
    }

    @Override
    public void remove(Position<E> p) {
        NodePosition<E> nodeP = checkPosition(p);
        NodePosition<E> prev = nodeP.getPrev();
        NodePosition<E> next = nodeP.getNext();
        nodeP.setNext(null);
        nodeP.setPrev(null);
        if ( nodeP == first ) {
            first = next;
        } 
        if ( nodeP == last ) {
            last = prev;
        }
        if ( prev != null ) {
            prev.setNext(next);
        }
        if ( next != null ) {
            next.setPrev(prev);
        }
        size--;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            
            private Position<E> current;
            
            @Override
            public boolean hasNext() {
                if ( current == null ) {
                    return first() != null;
                }
                return PositionListImpl.this.next(current) != null;
            }

            @Override
            public E next() {
                if ( current == null ) {
                    current = first();
                } else {
                    current = PositionListImpl.this.next(current);
                }
                return current == null ? null : current.getElement();
            }

            @Override
            public void remove() {
                NodePosition<E> removeThis = checkPosition(current);
                current = removeThis.getPrev();
                PositionListImpl.this.remove(removeThis);
            }
            
        };
    }

}
