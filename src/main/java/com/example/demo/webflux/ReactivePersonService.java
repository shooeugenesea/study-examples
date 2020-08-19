package com.example.demo.webflux;

import com.example.demo.EntityType;
import com.example.demo.Person;
import com.example.demo.event.CrudEvent;
import com.example.demo.event.CrudEventKey;
import com.example.demo.event.ReactiveCrudEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ReactivePersonService {

    @Autowired
    private ReactivePersonRepository reactivePersonRepository;

    @Autowired
    private ReactiveCrudEventRepository reactiveCrudEventRepository;

    public Mono<Person> save(Person person) {
        person.setId(UUID.randomUUID().toString());
        Mono<Person> result = reactivePersonRepository.save(person);
        result.then(reactiveCrudEventRepository.save(
                new CrudEvent(
                        new CrudEventKey(EntityType.PERSON, person.getId(), LocalDateTime.now()),
                        "Save " + person.getName()
                )
        ));
        return result;
    }

    public Mono<Person> findById(String id) {
        return reactivePersonRepository.findById(id);
    }

    public Flux<Person> findAll() {
        return reactivePersonRepository.findAll();
    }

    public Mono<Void> deleteById(String id) {
        Mono<Void> result = reactivePersonRepository.deleteById(id);
        result.then(reactiveCrudEventRepository.save(
                new CrudEvent(
                        new CrudEventKey(EntityType.PERSON, id, LocalDateTime.now()),
                        "Delete " + id
                )
        ));
        return result;
    }

    public Mono<Void> deleteAll() {
        Mono<Void> result = reactivePersonRepository.deleteAll();
        result.subscribe(f -> reactiveCrudEventRepository.save(
                new CrudEvent(
                        new CrudEventKey(EntityType.PERSON, "all", LocalDateTime.now()),
                        "Delete all"
                )
        ));
        return result;
    }

}
