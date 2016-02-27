package dsaa.list;

public class ArrayList<T> {

    @SuppressWarnings("unchecked")
    private T[] ary = (T[]) new Object[2];
    private int lastIdx = -1;
    
    public T get(int idx) {
        if ( idx > lastIdx ) {
            throw new IndexOutOfBoundException();
        }
        return ary[idx];
    }

    public T set(int idx, T val) {
        if ( idx > lastIdx ) {
            throw new IndexOutOfBoundException();
        }
        T result = ary[idx];
        ary[idx] = val;
        return result;
    }
    
    @SuppressWarnings("unchecked")
    public void add(int idx, T val) {
        T tmp = val;
        for ( int i = idx; i < ary.length; i++ ) {
            T next = ary[i];
            ary[i] = tmp;
            tmp = next;
        }
        lastIdx++;
        if ( lastIdx > (ary.length / 2) ) {
            T[] newAry = (T[]) new Object[ary.length * 2];
            for ( int i = 0; i < lastIdx; i++ ) {
                newAry[i] = ary[i];
            }
            this.ary = newAry;
        }
    }
    
    public T remove(int idx) {
        if ( idx > lastIdx ) {
            throw new IndexOutOfBoundException();
        }
        T result = ary[idx];
        for ( int i = idx; i <= lastIdx; i++  ) {
            ary[i] = ary[i+1];
        }
        ary[lastIdx--] = null;
        lastIdx = lastIdx < -1 ? -1 : lastIdx;
        return result;
    }
    
    public int size() {
        return lastIdx + 1;
    }

    public static class IndexOutOfBoundException extends RuntimeException {
        private static final long serialVersionUID = 3688778112882272159L;
    }
    
}
