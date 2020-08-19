package com.example.demo.event;

import com.example.demo.EntityType;
import lombok.AllArgsConstructor;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@PrimaryKeyClass
public class CrudEventKey implements Serializable {

    @PrimaryKeyColumn(name = "entity_type", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private EntityType entityType;
    @PrimaryKeyColumn(name = "entity_id", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private String entityId;
    @PrimaryKeyColumn(name = "event_time", ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private LocalDateTime eventTime;

}