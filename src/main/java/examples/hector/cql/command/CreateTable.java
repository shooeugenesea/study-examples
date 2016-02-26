package examples.hector.cql.command;

import java.util.ArrayList;
import java.util.List;

public class CreateTable implements CqlCommand {

    private final String cfName;
    private final List<KeyType> keyTypes = new ArrayList<>();
    private final List<KeyType> pks = new ArrayList<>();
    
    public CreateTable(String cfName) {
        this.cfName = cfName;
    }

    public CreateTable keyTypes(String key, String type) {
        this.keyTypes.add(new KeyType(key,type));
        return this;
    }
    
    public CreateTable pk(String key, String type) {
        this.pks.add(new KeyType(key,type));
        return this;
    }
    
    @Override
    public String toCQL() {
        StringBuilder cql = new StringBuilder("CREATE TABLE " + cfName + " (");
        for ( KeyType pk: pks ) {
            cql.append(pk.getKey() + " " + pk.getType() + " PRIMARY KEY,");
        }
        for ( KeyType keyType: keyTypes ) {
            cql.append(keyType.getKey() + " " + keyType.getType() + ",");
        }
        cql.deleteCharAt(cql.length()-1);
        cql.append(");");
        return cql.toString();
    }
    
}
