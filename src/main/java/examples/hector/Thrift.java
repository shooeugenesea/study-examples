package examples.hector;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
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

/**
 * https://hector-client.github.io/hector/build/html/content/getting_started.html
 * */
public class Thrift {

    public static void main(String[] params) {
        Cluster cluster = createCluster();
        System.out.println("create cluster:" + cluster.getName());
        
        String ksName = createKeyspace(cluster);
        System.out.println("create ks:" + ksName);
        
        String cfName = createColumnFamily(cluster, ksName);
        System.out.println("create cf:" + cfName);

        KeyspaceDefinition ksDef = cluster.describeKeyspace(ksName);
        System.out.println("read ks:" + ksDef.getName());
        for ( ColumnFamilyDefinition cfDef: ksDef.getCfDefs() ) {
            System.out.println("read cf:" + cfDef.getName());
        }
        
        String rowKey = createRow(cluster, ksName, cfName);
        System.out.println("create row:" + rowKey);
        
        Map<String, String> m = readRow(cluster, ksName, cfName, rowKey);
        System.out.println("read row:" + m);
        
        deleteRow(cluster, ksName, cfName, rowKey);
        System.out.println("delete row:" + rowKey);

        cluster.dropColumnFamily(ksName, cfName);
        System.out.println("drop columnfamily. ks:" + ksName + ", cf:" + cfName);
        
        cluster.dropKeyspace(ksName);
        System.out.println("drop keyspace:" + ksName);
        
    }
    
    private static void deleteRow(Cluster cluster, String ksName, String cfName, String rowKey) {
        Keyspace ksp = HFactory.createKeyspace(ksName, cluster);
        ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(ksp, cfName, StringSerializer.get(), StringSerializer.get());
        template.deleteRow(rowKey);
        
        if ( !readRow(cluster, ksName, cfName, rowKey).isEmpty() ) {
            throw new RuntimeException("shouldn't find row by key:" + rowKey);
        }
        
    }
    
    private static Map<String, String> readRow(Cluster cluster, String ksName, String cfName, String rowKey) {
        Keyspace ksp = HFactory.createKeyspace(ksName, cluster);
        ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(ksp, cfName, StringSerializer.get(), StringSerializer.get());
        ColumnFamilyResult<String, String> results = template.queryColumns(rowKey);
        Map<String, String> result = new LinkedHashMap<>();
        for ( String columnName: results.getColumnNames() ) {
            result.put(columnName, results.getString(columnName));
        }
        return result;
    }
    
    private static String createRow(Cluster cluster, String ksName, String cfName) {
        String rowKey = "rowKey_" + new Random().nextInt(100);
        Keyspace ksp = HFactory.createKeyspace(ksName, cluster);
        ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(ksp, cfName, StringSerializer.get(), StringSerializer.get());
        ColumnFamilyUpdater<String, String> updater = template.createUpdater(rowKey);
        for ( int i = 0; i < 10; i++ ) {
            updater.setString("test" + i, "test" + i);
        }
        template.update(updater);
        return rowKey;
    }
    
    
    private static void assertTrue(boolean state, String msg) {
        if (!state) {
            throw new IllegalStateException(msg);
        }
    }

    private static String createColumnFamily(Cluster cluster, String ksName) {
        String cfName = "cf_" + new Random().nextInt(100);
        ColumnFamilyDefinition cfDef = HFactory.createColumnFamilyDefinition(ksName, cfName);
        cluster.addColumnFamily(cfDef);
        return cfName;
    }
    
    private static String createKeyspace(Cluster cluster) {
        String keyspaceName = "ks_" + new Random().nextInt(100);
        KeyspaceDefinition newKeyspace = HFactory.createKeyspaceDefinition(keyspaceName);
        cluster.addKeyspace(newKeyspace);
        return keyspaceName;
    }
    
    private static Cluster createCluster() {
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
