package com.example.demo.reactive;

import com.example.demo.Person;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Flux;

public interface ReactivePersonRepository extends ReactiveCassandraRepository<Person, String> {
}
