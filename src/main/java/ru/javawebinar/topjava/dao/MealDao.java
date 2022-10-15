package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    Meal getById(int id);

    void delete(int id);

    void save(Meal meal);

    List<Meal> getAll();
}
