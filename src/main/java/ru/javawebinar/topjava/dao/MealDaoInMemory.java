package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import static ru.javawebinar.topjava.util.MealsUtil.id;
import static ru.javawebinar.topjava.util.MealsUtil.mealMap;

public class MealDaoInMemory implements MealDao {
    @Override
    public Meal getById(int id) {
        return mealMap.get(id);
    }

    @Override
    public void delete(int id) {
        mealMap.remove(id);
    }

    @Override
    public void save(Meal meal) {
        if (meal.getId() == null){
            meal.setId(id.incrementAndGet());
        }
        mealMap.put(meal.getId(), meal);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealMap.values());
    }
}
