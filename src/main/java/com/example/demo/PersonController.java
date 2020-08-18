package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    PersonService personService;

    @PostMapping(value = "/person", consumes = "application/json")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        System.out.println("create person" + person);
        person.setId(UUID.randomUUID().toString());
        return new ResponseEntity<>(personService.save(person), HttpStatus.OK);
    }

    @GetMapping(value = "/person")
    public ResponseEntity<Person> getPerson(@RequestParam String id) {
        return ResponseEntity.of(personService.findById(id));
    }

    @GetMapping(value = "/persons")
    public List<Person> getAllPersons() {
        return personService.findAll();
    }

    @DeleteMapping("/person/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable String id) {
        personService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/persons")
    public ResponseEntity<Void> deleteAll() {
        personService.deleteAll();
        return ResponseEntity.ok().build();
    }

}
