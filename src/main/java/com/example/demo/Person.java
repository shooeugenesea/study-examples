package com.example.demo;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;

@Data
public class Person {

    @PrimaryKey private String id;
    private String name;

}
