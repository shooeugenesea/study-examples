package com.example.demo.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ReactiveCrudEventService {

    @Autowired
    private ReactiveCrudEventRepository reactiveCrudEventRepository;

    public Flux<CrudEvent> findAll() {
        return reactiveCrudEventRepository.findAll();
    }

}
