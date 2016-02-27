package dsaa.queue;

public class ArrayQueue<T> implements Queue<T> {

    private T[] ary;
    private int headIdx = 0;
    private int tailIdx = 0;
    
    @SuppressWarnings("unchecked")
    public ArrayQueue(int capacity) {
        this.ary = (T[]) new Object[capacity+1];
    }
    
    @Override
    public int size() {
        if ( tailIdx >= headIdx ) {
            return tailIdx - headIdx;
        } else {
            return ary.length + tailIdx - headIdx;
        }
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public T front() {
        return ary[headIdx];
    }

    @Override
    public void enqueue(T t) {
        if ( headIdx == nextIdx(tailIdx) ) {
            throw new RuntimeException("queue is full");
        }
        ary[tailIdx] = t;
        tailIdx = nextIdx(tailIdx);
    }

    private int nextIdx(int idx) {
        return (idx + 1) % ary.length;
    }
    
    @Override
    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        T result = ary[headIdx];
        ary[headIdx] = null;
        headIdx = nextIdx(headIdx);
        return result;
    }

}
