package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.meals;

public class MealDAOInMemory implements MealDAO {
    @Override
    public Meal byId(Integer id) {
        return meals.stream().filter(meal -> meal.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void save(Meal object) {
        Meal newMeal = new Meal(object);
        newMeal.setId(MealsUtil.id.incrementAndGet());
        meals.add(newMeal);
    }

    @Override
    public Meal update(Meal object) {
        Meal updateMeal = byId(object.getId());
        updateMeal.setDateTime(object.getDateTime());
        updateMeal.setDescription(object.getDescription());
        updateMeal.setCalories(object.getCalories());
        return updateMeal;
    }

    @Override
    public boolean remove(Integer id) {
        return list().remove(byId(id));
    }

    @Override
    public List<Meal> list() {
        return meals;
    }
}
