package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {
    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal get(int id) {
        return checkNotFoundWithId(repository.get(id,  SecurityUtil.authUserId()), id);
    }

    public void delete(int id){
        checkNotFoundWithId(repository.delete(id, SecurityUtil.authUserId()),id);
    }

    public List<MealTo> getAll(){
        return MealsUtil.getTos(repository.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public Meal create(Meal meal){
        return repository.save(meal, SecurityUtil.authUserId());
    }

    public void update(Meal meal){
        checkNotFoundWithId(repository.save(meal, SecurityUtil.authUserId()), meal.getId());
    }

    public List<MealTo> getFiltered(LocalDate startDate,
                                    LocalDate endDate,
                                    LocalTime startTime,
                                    LocalTime endTime){
        return getAll().stream()
                .filter(m-> DateTimeUtil.isBetweenDate(m.getDate(),startDate,endDate))
                .filter((m->DateTimeUtil.isBetweenHalfOpen(m.getTime(),startTime,endTime)))
                .collect(Collectors.toList());
    }
}