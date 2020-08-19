package com.example.demo;

public enum EntityType {

    PERSON(Person.class);

    private Class<?> clazz;

    private EntityType(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
