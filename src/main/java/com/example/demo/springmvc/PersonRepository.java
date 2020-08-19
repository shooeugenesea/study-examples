package com.example.demo.springmvc;

import com.example.demo.Person;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface PersonRepository extends CassandraRepository<Person, String> {
}
