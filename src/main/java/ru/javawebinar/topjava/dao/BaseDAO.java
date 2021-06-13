package ru.javawebinar.topjava.dao;

import java.util.List;

public interface BaseDAO<T, I> {

    T byId(I id);

    void save(T object);

    T update(T object);

    boolean remove(I id);

    List<T> list();
}
