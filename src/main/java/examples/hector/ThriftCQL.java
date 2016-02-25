package examples.hector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import examples.hector.cql.Batch;
import examples.hector.cql.CreateTable;
import examples.hector.cql.Insert;
import me.prettyprint.cassandra.model.CqlQuery;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.template.ColumnFamilyResult;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;

/**
 * https://hector-client.github.io/hector/build/html/content/getting_started.html
 * */
public class ThriftCQL {
    
    final Cluster cluster;
    
    private ThriftCQL() {
        this.cluster = createCluster();
    }
    
    private void deleteRows(String ksName, String cfName, List<String> rowKeys) {
        Keyspace ksp = HFactory.createKeyspace(ksName, cluster);
        ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(ksp, cfName, StringSerializer.get(), StringSerializer.get());
        Mutator<String> m = template.createMutator();
        m.addDeletion(rowKeys, cfName);
        m.execute();
        
        if ( !readRows(ksName, cfName, rowKeys).isEmpty() ) {
            throw new RuntimeException("shouldn't find rows by keys:" + rowKeys);
        }
    }
    
    private Map<String, Map<String, String>> readRows(String ksName, String cfName, List<String> rowKeys) {
        Keyspace ksp = HFactory.createKeyspace(ksName, cluster);
        ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(ksp, cfName, StringSerializer.get(), StringSerializer.get());
        ColumnFamilyResult<String, String> results = template.queryColumns(rowKeys);
        Map<String,Map<String,String>> result = new LinkedHashMap<>();
        while (results.hasResults()) {
            Map<String,String> row = new LinkedHashMap<>();
            for ( String columnName: results.getColumnNames() ) {
                row.put(columnName, results.getString(columnName));
            }
            result.put(results.getKey(), row);
            if (results.hasNext()) {
                results = results.next();
            } else {
                break;
            }
        }
        return result;
    }
    
    private void deleteRow(String ksName, String cfName, String rowKey) {
        Keyspace ksp = HFactory.createKeyspace(ksName, cluster);
        ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(ksp, cfName, StringSerializer.get(), StringSerializer.get());
        template.deleteRow(rowKey);
        
        if ( !readRow(ksName, cfName, rowKey).isEmpty() ) {
            throw new RuntimeException("shouldn't find row by key:" + rowKey);
        }
    }
    
    private Map<String, String> readRow(String ksName, String cfName, String rowKey) {
        Keyspace ksp = HFactory.createKeyspace(ksName, cluster);
        ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(ksp, cfName, StringSerializer.get(), StringSerializer.get());
        ColumnFamilyResult<String, String> results = template.queryColumns(rowKey);
        Map<String, String> result = new LinkedHashMap<>();
        for ( String columnName: results.getColumnNames() ) {
            result.put(columnName, results.getString(columnName));
        }
        return result;
    }
    
    private List<String> createRows(String ksName, String cfName) {
        List<String> rowKeys = new ArrayList<>();
        Keyspace ksp = HFactory.createKeyspace(ksName, cluster);
        CqlQuery<String,String,String> cqlQuery = new CqlQuery<>(ksp, StringSerializer.get(), StringSerializer.get(), StringSerializer.get());
        Batch batch = new Batch();
        for ( int i = 0; i < 3; i++ ) {
            String key = "rowKey_" + i;
            rowKeys.add(key);
            Insert insert = new Insert(cfName).keyValue("k", key);
            for ( int j = 0; j < 3; j++ ) {
                insert.keyValue(key + "_column_" + j, "測試" + i);
            }
            batch.command(insert);
        }
        System.out.println(batch.toCQL());
        cqlQuery.setQuery(batch.toCQL());
        cqlQuery.execute();
        return rowKeys;
    }
    
    private String createRow(String ksName, String cfName) {
        Keyspace ksp = HFactory.createKeyspace(ksName, cluster);
        CqlQuery<String,String,String> cqlQuery = new CqlQuery<>(ksp, StringSerializer.get(), StringSerializer.get(), StringSerializer.get());
        Insert insert = new Insert(cfName).keyValue("k", UUID.randomUUID().toString());
        for ( int i = 0; i < 3; i++ ) {
            insert.keyValue("測試" + i, "測試" + i);
        }
        System.out.println(insert.toCQL());
        cqlQuery.setQuery(insert.toCQL());
        cqlQuery.execute();
        return cfName;
    }
    
    private void assertTrue(boolean state, String msg) {
        if (!state) {
            throw new IllegalStateException(msg);
        }
    }

    private String createColumnFamily(String ksName) {
        Keyspace ksp = HFactory.createKeyspace(ksName, cluster);
        CqlQuery<String,String,String> cqlQuery = new CqlQuery<>(ksp, StringSerializer.get(), StringSerializer.get(), StringSerializer.get());
        String cfName = "cf_" + new Random().nextInt(100);
        String cql = new CreateTable(cfName).pk("k", "varchar").toCQL();
        System.out.println(cql);
        cqlQuery.setQuery(cql);
        cqlQuery.execute();
        return cfName;
    }
    
    private String createKeyspace() {
        String keyspaceName = "ks_" + new Random().nextInt(100);
        KeyspaceDefinition newKeyspace = HFactory.createKeyspaceDefinition(keyspaceName);
        cluster.addKeyspace(newKeyspace);
        return keyspaceName;
    }
    
    private Cluster createCluster() {
        CassandraHostConfigurator config = new CassandraHostConfigurator("127.0.0.1");
        config.setMaxActive(50);
        config.setAutoDiscoverHosts(true);
        config.setAutoDiscoveryDelayInSeconds(60);
        Map<String, String> loginUser = new HashMap<String, String>();
        loginUser.put("username", "admin");
        loginUser.put("password", "ubituscassandra");
        String clusterName = "cluster_" + new Random().nextInt(100);
        Cluster cluster = HFactory.getOrCreateCluster(clusterName, config, loginUser);
        assertTrue(cluster != null, "cluster should not null");
        return cluster;
    }

    public static void main(String[] params) {
        ThriftCQL ex = new ThriftCQL();
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
