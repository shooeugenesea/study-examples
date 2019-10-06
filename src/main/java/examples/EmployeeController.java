package examples;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @GetMapping("/{id}")
    private Mono<Employee> getEmployeeById(@PathVariable String id) {
        return Mono.just(new Employee(id));
    }

    @GetMapping
    private Flux<Employee> getAllEmployees() {
        return Flux.fromIterable(Arrays.asList(new Employee(UUID.randomUUID().toString()),
                new Employee(UUID.randomUUID().toString()),
                new Employee(UUID.randomUUID().toString())));
    }

}