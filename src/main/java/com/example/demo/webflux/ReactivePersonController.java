package com.example.demo.webflux;

import com.example.demo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v2/persons")
public class ReactivePersonController {

    @Autowired
    private ReactivePersonService reactivePersonService;

    @PostMapping(value = "", consumes = "application/json")
    public Mono<Person> createPerson(@RequestBody Person person) {
        System.out.println("create person" + person);
        return reactivePersonService.save(person);
    }

    @GetMapping(value = "{id}")
    public Mono<Person> getPerson(@PathVariable String id) {
        return reactivePersonService.findById(id);
    }

    @GetMapping(value = "")
    public Flux<Person> getAllPersons() {
        return reactivePersonService.findAll();
    }

    @DeleteMapping("{id}")
    public Mono<Void> deletePerson(@PathVariable String id) {
        return reactivePersonService.deleteById(id);
    }

    @DeleteMapping("")
    public Mono<Void> deleteAll() {
        return reactivePersonService.deleteAll();
    }

}
