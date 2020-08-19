New Cassandra Docker instance
`sudo docker run --name test-cassandra -p 9042:9042 -d cassandra:latest
 sudo docker run --name test-cassandra-2 --link test-cassandra:cassandra -d cassandra:latest`
 
 Execute bash in Docker instance
 `docker exec -it test-cassandra /bin/bash`
 
 Create keyspace and table
 `cqlsh> CREATE KEYSPACE test WITH REPLICATION = { 'class' : 'NetworkTopologyStrategy', 'datacenter1' : 3 } AND DURABLE_WRITES = false;
  cqlsh> use test;
  cqlsh:test> CREATE TABLE person ( id text PRIMARY KEY, name text );`
  