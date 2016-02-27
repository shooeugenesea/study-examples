package dsaa.queue;


public class LinkedDeque<T> implements Deque<T> {

    private Node first = null;
    private Node last = null;
    private int size = 0;
    
    @Override
    public void addFirst(T t) {
        Node newFirst = new Node(t);
        if ( first == null ) {
            first = newFirst;
            last = newFirst;
        } else {
            newFirst.setNext(first);
            first.setPrev(newFirst);
            first = newFirst;
        }
        size++;
    }

    @Override
    public void addLast(T t) {
        Node newLast = new Node(t);
        if ( last == null ) {
            first = newLast;
            last = newLast;
        } else {
            last.setNext(newLast);
            newLast.setPrev(last);
            last = newLast;
        }
        size++;
    }

    @Override
    public T removeFirst() {
        if ( first == null ) {
            return null;
        } else if ( first == last ) {
            T result = first.getVal();
            first = null;
            last = null;
            size--;
            return result;
        } else {
            T result = first.getVal();
            Node newFirst = first.getNext();
            first.setNext(null);
            newFirst.setPrev(null);
            first = newFirst;
            size--;
            return result;            
        }
    }

    @Override
    public T removeLast() {
        if ( last == null ) {
            return null;
        } else if ( first == last ) {
            T result = last.getVal();
            first = null;
            last = null;
            size--;
            return result;
        } else {
            T result = last.getVal();
            Node newLast = last.getPrev();
            last.setPrev(null);
            newLast.setNext(null);
            last = newLast;
            size--;
            return result;
        }
    }

    @Override
    public T getFirst() {
        if ( first == null ) {
            return null;
        } else {
            return first.getVal();
        }
    }

    @Override
    public T getLast() {
        if ( last == null ) {
            return null;
        } else {
            return last.getVal();
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    private class Node {
        private final T val;
        private Node prev;
        private Node next;
        public Node(T t) {
            this.val = t;
        }
        public void setPrev(Node prev) {
            this.prev = prev;
        }
        public Node getPrev() {
            return prev;
        }
        public void setNext(Node next) {
            this.next = next;
        }
        public Node getNext() {
            return next;
        }
        public T getVal() {
            return val;
        }
    }
    
}
