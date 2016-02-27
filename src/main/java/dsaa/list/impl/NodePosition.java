package dsaa.list.impl;

import dsaa.list.PositionList.Position;

public class NodePosition<E> implements Position<E> {

    private NodePosition<E> prev;
    private NodePosition<E> next;
    private E element;
    
    public NodePosition() { }
    
    public NodePosition(E e) {
        this.element = e;
    }
    
    public void setNext(NodePosition<E> next) {
        this.next = next;
    }
    
    public NodePosition<E> getNext() {
        return next;
    }
    
    public void setPrev(NodePosition<E> prev) {
        this.prev = prev;
    }
    
    public NodePosition<E> getPrev() {
        return prev;
    }
    
    @Override
    public E getElement() {
        return element;
    }
    
    public void setElement(E element) {
        this.element = element;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.element);
    }
    
}
