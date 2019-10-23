package examples;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private static final Map<String, Employee> data = new HashMap<>();

    static {
        data.put("1", new Employee("1"));
        data.put("2", new Employee("2"));
        data.put("3", new Employee("3"));
    }

    @GetMapping("/{id}")
    private Mono<Employee> getEmployeeById(@PathVariable String id) {
        return Mono.justOrEmpty(data.get(id));
    }

    @GetMapping
    private Flux<Employee> getAllEmployees() {
        return Flux.fromStream(data.values().stream());
    }

    @PostMapping
    private Mono<Void> create(@RequestBody Flux<Employee> employees) {
        return employees.map(employee -> {
            System.out.println("new Employee:" + employee.getName());
            data.put(employee.getName(), employee);
            return employee.getName();
        }).then();
    }


}