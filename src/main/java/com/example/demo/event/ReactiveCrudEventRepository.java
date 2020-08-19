package com.example.demo.event;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

public interface ReactiveCrudEventRepository extends ReactiveCassandraRepository<CrudEvent, CrudEventKey> {
}
