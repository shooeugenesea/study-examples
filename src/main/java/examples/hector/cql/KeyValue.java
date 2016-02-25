package examples.hector.cql;

public class KeyValue {

    private final String key;
    private final String val;
    
    public KeyValue(String key, String val) {
        this.key = key;
        this.val = val;
    }
    
    public String getKey() {
        return key;
    }
    
    public String getVal() {
        return val;
    }
    
}
