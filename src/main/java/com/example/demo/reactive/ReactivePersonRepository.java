package com.example.demo.reactive;

import com.example.demo.Person;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

public interface ReactivePersonRepository extends ReactiveCassandraRepository<Person, String> {
}
