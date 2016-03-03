package examples.hector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
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
        ThriftTemplate template = new ThriftTemplate();
        System.out.println("create cluster:" + template.cluster.getName());
        
        String ksName = template.createKeyspace();
        System.out.println("create ks:" + ksName);
        
        String cfName = template.createColumnFamily(ksName);
        System.out.println("create cf:" + cfName);

        KeyspaceDefinition ksDef = template.cluster.describeKeyspace(ksName);
        Assert.assertEquals(ksName, ksDef.getName());

        Assert.assertEquals(1, ksDef.getCfDefs().size());
        ColumnFamilyDefinition cfDef = ksDef.getCfDefs().get(0);
        Assert.assertEquals(cfName, cfDef.getName());
        
        Map<String, String> columns = new HashMap<String, String>();
        columns.put("test1", "test1");
        columns.put("test2", "test2");
        columns.put("test3", "test3");
        String rowKey = template.createRow(ksName, cfName, columns);
        Assert.assertNotNull(rowKey);
        
        Map<String, String> m = template.readRow(ksName, cfName, rowKey);
        Assert.assertEquals("test1", m.get("test1"));
        Assert.assertEquals("test2", m.get("test2"));
        Assert.assertEquals("test3", m.get("test3"));
        System.out.println("read row:" + m);
        
        template.deleteRow(ksName, cfName, rowKey);
        System.out.println("delete row:" + rowKey);
        
        Assert.assertEquals(0, template.readRow(ksName, cfName, rowKey).size());
        
        List<Map<String, String>> rows = new ArrayList<>();
        Map<String, String> row1Columns = new HashMap<>(); row1Columns.put("r1c1", "r1c1v1");
        Map<String, String> row2Columns = new HashMap<>(); row2Columns.put("r2c2", "r2c2v2");
        Map<String, String> row3Columns = new HashMap<>(); row3Columns.put("r3c3", "r3c3v3");
        rows.add(row1Columns);
        rows.add(row2Columns);
        rows.add(row3Columns);
        
        List<String> rowKeys = template.createRows(ksName, cfName, rows);
        Map<String, Map<String,String>> readRows = template.readRows(ksName, cfName, rowKeys);
        Assert.assertEquals(3, readRows.size());
        Map<String, String> readRow1 = readRows.get(rowKeys.get(0));
        Map<String, String> readRow2 = readRows.get(rowKeys.get(1));
        Map<String, String> readRow3 = readRows.get(rowKeys.get(2));
        Assert.assertTrue(readRow1.equals(row1Columns));
        Assert.assertTrue(readRow2.equals(row2Columns));
        Assert.assertTrue(readRow3.equals(row3Columns));
        
        template.deleteRows(ksName, cfName, rowKeys);
        readRows = template.readRows(ksName, cfName, rowKeys);
        Assert.assertTrue(readRows.isEmpty());
        
        template.cluster.dropColumnFamily(ksName, cfName);
        System.out.println("drop columnfamily. ks:" + ksName + ", cf:" + cfName);
        
        template.cluster.dropKeyspace(ksName);
        System.out.println("drop keyspace:" + ksName);
        
        ksDef = template.cluster.describeKeyspace(ksName);
        Assert.assertNull(ksDef);
    }
    
}
