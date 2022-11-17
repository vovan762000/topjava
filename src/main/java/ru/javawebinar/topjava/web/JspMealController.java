package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;
import static ru.javawebinar.topjava.util.MealsUtil.getTos;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;


@Controller
@RequestMapping(value = "/meals")
public class JspMealController {

    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    private final MealService service;

    public JspMealController(MealService service) {
        this.service = service;
    }

    @GetMapping
    public String getAll(Model model) {
        log.info("get all");
        int userId = SecurityUtil.authUserId();
        model.addAttribute("meals", getTos(service.getAll(userId), DEFAULT_CALORIES_PER_DAY));
        return "meals";
    }

    @PostMapping
    public String getFiltered(Model model, HttpServletRequest request) {
        log.info("get filtered");
        int userId = SecurityUtil.authUserId();
        LocalDate startDate = request.getParameter("startDate").equals("") ? null : LocalDate.parse(request.getParameter("startDate"));
        LocalDate endDate = request.getParameter("endDate").equals("") ? null : LocalDate.parse(request.getParameter("endDate"));
        LocalTime startTime = request.getParameter("startTime").equals("") ? null : LocalTime.parse(request.getParameter("startTime"));
        LocalTime endTime = request.getParameter("endTime").equals("") ? null : LocalTime.parse(request.getParameter("endTime"));
        List<Meal> mealsDateFiltered = service.getBetweenInclusive(startDate, endDate, userId);
        List<MealTo> meals = MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
        model.addAttribute("meals", meals);
        return "meals";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        log.info("delete meal with id {}", id);
        int userId = SecurityUtil.authUserId();
        service.delete(id, userId);
        return "redirect:/meals";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable int id, Model model) {
        log.info("update meal with id {}", id);
        int userId = SecurityUtil.authUserId();
        Meal meal = service.get(id, userId);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/create")
    public String create(Model model) {
        log.info("create meal");
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "mealForm";
    }

    @PostMapping("/mealForm")
    public String save(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        int userId = SecurityUtil.authUserId();
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        String idString = request.getParameter("id");

        if (StringUtils.hasLength(idString)) {
            assureIdConsistent(meal, Integer.parseInt(idString));
            log.info("update {} for user {}", meal, userId);
            service.update(meal, userId);
        } else {
            checkNew(meal);
            service.create(meal, userId);
        }
        return "redirect:/meals";
    }

}
