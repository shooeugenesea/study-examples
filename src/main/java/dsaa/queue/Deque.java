package dsaa.queue;

public interface Deque<T> {

    void addFirst(T t);
    void addLast(T t);
    T removeFirst();
    T removeLast();
    T getFirst();
    T getLast();
    int size();
    boolean isEmpty();
    
}
