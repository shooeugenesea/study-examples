package examples.hector.cql;

import java.util.ArrayList;
import java.util.List;

public class Insert implements DmlCommand {

    private final String cfName;
    private final List<KeyValue> keyValues = new ArrayList<>();
    
    public Insert(String cfName) {
        this.cfName = cfName;
    }
    
    public Insert keyValue(String key, String val) {
        this.keyValues.add(new KeyValue(key, val));
        return this;
    }
    
    @Override
    public String toCQL() {
        StringBuilder cql = new StringBuilder("INSERT INTO " + cfName);
        cql.append(" (");
        for ( KeyValue kv: keyValues ) {
            cql.append("'" + kv.getKey() + "',");
        }
        cql.deleteCharAt(cql.length()-1);
        cql.append(" )");
        cql.append(" VALUES (");
        for ( KeyValue kv: keyValues ) {
            cql.append("'" + kv.getVal() + "',");
        }
        cql.deleteCharAt(cql.length()-1);
        cql.append(" );");
        return cql.toString();
    }
    
}
