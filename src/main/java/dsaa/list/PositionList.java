package dsaa.list;

public interface PositionList<E> extends Iterable<E> {

    int size();
    boolean isEmpty();
    Position<E> first();
    Position<E> last();
    Position<E> next(Position<E> p);
    E set(Position<E> p, E e);
    Position<E> addFirst(E e);
    Position<E> addLast(E e);
    Position<E> addBefore(Position<E> p, E e);
    Position<E> addAfter(Position<E> p, E e);
    void remove(Position<E> p);
    
    public static interface Position<E> {
        E getElement();
    }
    
}
