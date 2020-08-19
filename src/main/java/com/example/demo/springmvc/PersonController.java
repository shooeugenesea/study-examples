package com.example.demo.springmvc;

import com.example.demo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {

    @Autowired
    PersonService personService;

    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        System.out.println("create person" + person);
        person.setId(UUID.randomUUID().toString());
        return new ResponseEntity<>(personService.save(person), HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Person> getPerson(@PathVariable String id) {
        return ResponseEntity.of(personService.findById(id));
    }

    @GetMapping(value = "")
    public List<Person> getAllPersons() {
        return personService.findAll();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable String id) {
        personService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteAll() {
        personService.deleteAll();
        return ResponseEntity.ok().build();
    }

}
