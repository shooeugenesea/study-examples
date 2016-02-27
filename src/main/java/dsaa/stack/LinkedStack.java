package dsaa.stack;

public class LinkedStack {

    private Node tail = new Node(null);
    
    public void push(String s) {
        Node newPrev = new Node(s);
        Node oldPrev = tail.getPrev();
        newPrev.setPrev(oldPrev);
        tail.setPrev(newPrev);
    }
    
    public String pop() {
        Node prev = tail.getPrev();
        if ( prev == null ) {
            throw new RuntimeException("empty. can't pop");
        }
        String result = prev.getValue();
        Node prev2 = prev.getPrev();
        prev.setPrev(prev2);
        tail.setPrev(prev2);
        return result;
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        Node node = tail.getPrev();
        while (node != null) {
            s.append(node.getValue());
            node = node.getPrev();
        }
        return s.toString();
    }
    
    private static class Node {
        private String value;
        private Node prev;
        public Node(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
        public Node getPrev() {
            return prev;
        }
        public void setPrev(Node prev) {
            this.prev = prev;
        }
    }
    
}
