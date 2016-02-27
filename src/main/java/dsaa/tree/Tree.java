package dsaa.tree;

import java.util.Iterator;

public interface Tree<E> {

    int size();
    boolean isEmpty();
    Iterator<E> iterator();
    Iterable<E> positions();
    E replace(Position<E> p, E e);
    Position<E> root();
    Position<E> parent(Position<E> p);
    Iterable<E> children();
    boolean isInternal(Position<E> p);
    boolean isExternal(Position<E> p);
    boolean isRoot(Position<E> p);
    
}
