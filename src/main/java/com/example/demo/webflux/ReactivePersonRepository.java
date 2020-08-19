package com.example.demo.webflux;

import com.example.demo.Person;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

public interface ReactivePersonRepository extends ReactiveCassandraRepository<Person, String> {
}
