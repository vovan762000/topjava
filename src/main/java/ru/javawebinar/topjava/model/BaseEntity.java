package ru.javawebinar.topjava.model;

public class BaseEntity {
    protected Integer id;

    public BaseEntity(int id) {
        this.id = id;
    }

    public BaseEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
