package dsaa.tree.impl;

import java.util.Iterator;

import dsaa.tree.Position;
import dsaa.tree.Tree;

public class TreeImpl<E> implements Tree<E> {

    private Position<E> root;
    private int size;
    
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return Iterator<E> newIterator();
    }

    @Override
    public Iterable<E> positions() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public E replace(Position<E> p, E e) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Position<E> root() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Position<E> parent(Position<E> p) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterable<E> children() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isInternal(Position<E> p) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isExternal(Position<E> p) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isRoot(Position<E> p) {
        // TODO Auto-generated method stub
        return false;
    }

}
