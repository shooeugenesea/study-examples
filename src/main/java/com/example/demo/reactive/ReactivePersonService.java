package com.example.demo.reactive;

import com.example.demo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactivePersonService {

    @Autowired
    private ReactivePersonRepository reactivePersonRepository;

    public Mono<Person> save(Person person) {
        return reactivePersonRepository.save(person);
    }

    public Mono<Person> findById(String id) {
        return reactivePersonRepository.findById(id);
    }

    public Flux<Person> findAll() {
        return reactivePersonRepository.findAll();
    }

    public Mono<Void> deleteById(String id) {
        return reactivePersonRepository.deleteById(id);
    }

    public Mono<Void> deleteAll() {
        return reactivePersonRepository.deleteAll();
    }

}
