package examples.hector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.template.ColumnFamilyResult;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ColumnFamilyUpdater;
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
public class ThriftTemplate {
    
    final Cluster cluster;
    
    public ThriftTemplate() {
        this.cluster = createCluster();
    }
    
    public void deleteRows(String ksName, String cfName, List<String> rowKeys) {
        Keyspace ksp = HFactory.createKeyspace(ksName, cluster);
        ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(ksp, cfName, StringSerializer.get(), StringSerializer.get());
        Mutator<String> m = template.createMutator();
        m.addDeletion(rowKeys, cfName);
        m.execute();
        
        if ( !readRows(ksName, cfName, rowKeys).isEmpty() ) {
            throw new RuntimeException("shouldn't find rows by keys:" + rowKeys);
        }
    }
    
    public Map<String, Map<String, String>> readRows(String ksName, String cfName, List<String> rowKeys) {
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
    
    public void deleteRow(String ksName, String cfName, String rowKey) {
        Keyspace ksp = HFactory.createKeyspace(ksName, cluster);
        ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(ksp, cfName, StringSerializer.get(), StringSerializer.get());
        template.deleteRow(rowKey);
        
        if ( !readRow(ksName, cfName, rowKey).isEmpty() ) {
            throw new RuntimeException("shouldn't find row by key:" + rowKey);
        }
    }
    
    public Map<String, String> readRow(String ksName, String cfName, String rowKey) {
        Keyspace ksp = HFactory.createKeyspace(ksName, cluster);
        ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(ksp, cfName, StringSerializer.get(), StringSerializer.get());
        ColumnFamilyResult<String, String> results = template.queryColumns(rowKey);
        Map<String, String> result = new LinkedHashMap<>();
        for ( String columnName: results.getColumnNames() ) {
            result.put(columnName, results.getString(columnName));
        }
        return result;
    }
    
    public List<String> createRows(String ksName, String cfName) {
        List<String> rowKeys = new ArrayList<>();
        for ( int i = 0; i < 3; i++ ) {
            rowKeys.add("rowKey_" + i);
        }
        Keyspace ksp = HFactory.createKeyspace(ksName, cluster);
        ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(ksp, cfName, StringSerializer.get(), StringSerializer.get());
        ColumnFamilyUpdater<String, String> updater = template.createUpdater();
        for ( String rowKey: rowKeys ) {
            updater.addKey(rowKey);
            for ( int i = 0; i < 3; i++ ) {
                updater.setString(rowKey + "_column_" + i, "測試" + i);
            }
        }
        template.update(updater);
        return rowKeys;
    }
    
    public String createRow(String ksName, String cfName) {
        String rowKey = "rowKey_" + new Random().nextInt(100);
        Keyspace ksp = HFactory.createKeyspace(ksName, cluster);
        ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(ksp, cfName, StringSerializer.get(), StringSerializer.get());
        ColumnFamilyUpdater<String, String> updater = template.createUpdater(rowKey);
        for ( int i = 0; i < 3; i++ ) {
            updater.setString("測試" + i, "測試" + i);
        }
        template.update(updater);
        return rowKey;
    }
    
    
    public void assertTrue(boolean state, String msg) {
        if (!state) {
            throw new IllegalStateException(msg);
        }
    }

    public String createColumnFamily(String ksName) {
        String cfName = "cf_" + new Random().nextInt(100);
        ColumnFamilyDefinition cfDef = HFactory.createColumnFamilyDefinition(ksName, cfName);
        cluster.addColumnFamily(cfDef);
        return cfName;
    }
    
    public String createKeyspace() {
        String keyspaceName = "ks_" + new Random().nextInt(100);
        KeyspaceDefinition newKeyspace = HFactory.createKeyspaceDefinition(keyspaceName);
        cluster.addKeyspace(newKeyspace);
        return keyspaceName;
    }
    
    public Cluster createCluster() {
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

}
