package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal get(int id) {
        log.info("get with id {}", id);
        return service.get(id);
    }

    public void delete(int id) {
        log.info("delete with id {}", id);
        service.delete(id);
    }

    public List<MealTo> getAll() {
        log.info("get all");
        return service.getAll();
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        return service.create(meal);
    }

    public void update(Meal meal, int id) {
        log.info("update {}", meal);
        ValidationUtil.assureIdConsistent(meal, id);
        service.update(meal);
    }

    public List<MealTo> grtFiltered(LocalDate startDate,
                                    LocalDate endDate,
                                    LocalTime startTime,
                                    LocalTime endTime) {
        log.info("get meal from date {} to date {} from time {} to time {} ", startDate, endDate, startTime, endTime);
        return service.getFiltered(startDate,endDate,startTime,endTime);
    }
}