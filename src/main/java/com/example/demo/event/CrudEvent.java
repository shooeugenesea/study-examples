package com.example.demo.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

//@AllArgsConstructor
@Data
@Table("crud_event")
public class CrudEvent {

    @PrimaryKey
    private CrudEventKey crudEventKey;

    @Column("description")
    private String description;

    public CrudEvent(CrudEventKey crudEventKey, String description) {
        this.crudEventKey = crudEventKey;
        this.description = description;
        System.out.println("new instance");
    }

}
