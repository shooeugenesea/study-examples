package com.example.demo.reactive;

import com.example.demo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v2/")
public class ReactivePersonController {

    @Autowired
    private ReactivePersonService reactivePersonService;

    @PostMapping(value = "/person", consumes = "application/json")
    public Mono<Person> createPerson(@RequestBody Person person) {
        System.out.println("create person" + person);
        person.setId(UUID.randomUUID().toString());
        return reactivePersonService.save(person);
    }

    @GetMapping(value = "/person")
    public Mono<Person> getPerson(@RequestParam String id) {
        return reactivePersonService.findById(id);
    }

    @GetMapping(value = "/persons")
    public Flux<Person> getAllPersons() {
        return reactivePersonService.findAll();
    }

    @DeleteMapping("/person/{id}")
    public Mono<Void> deletePerson(@PathVariable String id) {
        return reactivePersonService.deleteById(id);
    }

    @DeleteMapping("/persons")
    public Mono<Void> deleteAll() {
        return reactivePersonService.deleteAll();
    }

}
