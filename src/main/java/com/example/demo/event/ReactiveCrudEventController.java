package com.example.demo.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/crudevents")
public class ReactiveCrudEventController {

    @Autowired
    private ReactiveCrudEventService reactiveCrudEventService;

    @GetMapping(value = "")
    public Flux<CrudEvent> getAllEvents() {
        return reactiveCrudEventService.findAll();
    }

}
