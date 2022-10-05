package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));

        System.out.println(filteredByOnePass(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mapCaloriesPerDay = new HashMap<>();
        for (UserMeal meal : meals) {
            mapCaloriesPerDay.merge(meal.getDate(), meal.getCalories(), Integer::sum);
        }
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        for (UserMeal meal : meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime)) {
                userMealWithExcesses.add(new UserMealWithExcess(meal, mapCaloriesPerDay.get(meal.getDate()) > caloriesPerDay));
            }
        }
        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mapCaloriesPerDay = meals.stream()
                .collect(Collectors.toMap(UserMeal::getDate, UserMeal::getCalories, Integer::sum));
        return meals.stream()
                .filter(m -> TimeUtil.isBetweenHalfOpen(m.getTime(), startTime, endTime))
                .map(m -> new UserMealWithExcess(m, mapCaloriesPerDay.get(m.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExcess> filteredByOnePass(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        class UserMealWithExcessByOnePass
                implements Collector<UserMeal, Map<LocalDate, Integer>, List<UserMealWithExcess>> {
            @Override
            public Supplier<Map<LocalDate, Integer>> supplier() {
                return HashMap::new;
            }

            @Override
            public BiConsumer<Map<LocalDate, Integer>, UserMeal> accumulator() {
                return (map, meal) -> map.merge(meal.getDate(), meal.getCalories(), Integer::sum);
            }

            @Override
            public BinaryOperator<Map<LocalDate, Integer>> combiner() {
                return (map1, map2) -> {
                    Map<LocalDate, Integer> map3 = new HashMap<>();
                    map3.putAll(map1);
                    map3.putAll(map2);
                    return map3;
                };
            }

            @Override
            public Function<Map<LocalDate, Integer>, List<UserMealWithExcess>> finisher() {
                return (map) -> {
                    return meals.stream()
                            .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                            .map(meal -> new UserMealWithExcess(meal, map.get(meal.getDate()) > caloriesPerDay))
                            .collect(Collectors.toList());
                };
            }

            @Override
            public Set<Characteristics> characteristics() {
                return new HashSet<>(Collections.singletonList(Characteristics.UNORDERED));
            }
        }
        return meals.stream().collect(new UserMealWithExcessByOnePass());
    }
}
