package examples.hector;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import examples.cassandra.CassandraTestSupport;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;

public class ThriftTemplateTest extends CassandraTestSupport {
    
    public ThriftTemplateTest() throws IOException {
        super();
    }

    @Test
    public void test() {
        ThriftTemplate ex = new ThriftTemplate();
        System.out.println("create cluster:" + ex.cluster.getName());
        
        String ksName = ex.createKeyspace();
        System.out.println("create ks:" + ksName);
        
        String cfName = ex.createColumnFamily(ksName);
        System.out.println("create cf:" + cfName);

        KeyspaceDefinition ksDef = ex.cluster.describeKeyspace(ksName);
        System.out.println("read ks:" + ksDef.getName());
        for ( ColumnFamilyDefinition cfDef: ksDef.getCfDefs() ) {
            System.out.println("read cf:" + cfDef.getName());
        }
        
        String rowKey = ex.createRow(ksName, cfName);
        System.out.println("create row:" + rowKey);
        
        Map<String, String> m = ex.readRow(ksName, cfName, rowKey);
        System.out.println("read row:" + m);
        
        ex.deleteRow(ksName, cfName, rowKey);
        System.out.println("delete row:" + rowKey);

        List<String> rowKeys = ex.createRows(ksName, cfName);
        System.out.println("create rows:" + rowKeys);
        
        Map<String, Map<String,String>> readRows = ex.readRows(ksName, cfName, rowKeys);
        for ( Map.Entry<String, Map<String,String>> entry: readRows.entrySet() ) {
            String key = entry.getKey();
            Map<String,String> row = entry.getValue();
            System.out.println("read rows. rowKey:" + key + ", row:" + row);
        }

        ex.deleteRows(ksName, cfName, rowKeys);
        System.out.println("delete rows:" + rowKeys);
        
        ex.cluster.dropColumnFamily(ksName, cfName);
        System.out.println("drop columnfamily. ks:" + ksName + ", cf:" + cfName);
        
        ex.cluster.dropKeyspace(ksName);
        System.out.println("drop keyspace:" + ksName);
    }
    
}
