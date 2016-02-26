package examples.hector.cql.command;

public class KeyType {

    private final String key;
    private final String type;
    
    public KeyType(String key, String type) {
        this.key = key;
        this.type = type;
    }
    
    public String getKey() {
        return key;
    }
    
    public String getType() {
        return type;
    }
    
}
