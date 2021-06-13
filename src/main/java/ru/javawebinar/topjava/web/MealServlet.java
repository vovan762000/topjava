package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MealDAOInMemory;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.CALORIES_PER_DAY;
import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

@WebServlet("/meals")
public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);
    private final MealDAO dao;

    public MealServlet() {
        super();
        dao = new MealDAOInMemory();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal();
        meal.setDescription(request.getParameter("description"));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), formatter);
        meal.setDateTime(dateTime);
        String mealId = request.getParameter("mealId");
        if (mealId == null || mealId.isEmpty()) {
            dao.save(meal);
        } else {
            meal.setId(Integer.parseInt(mealId));
            dao.update(meal);
            log.debug("Update meal with id {}", mealId);
        }
        request.setAttribute("mealToList", filteredByStreams(dao.list(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String forward = "";
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            log.debug("delete meal {}", dao.byId(mealId));
            dao.remove(mealId);
            forward = "/meals.jsp";
            request.setAttribute("mealToList", filteredByStreams(dao.list(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
        } else if (action.equalsIgnoreCase("edit")) {
            forward = "/meal.jsp";
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = dao.byId(mealId);
            log.debug("edit meal {}", meal);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("insert")) {
            log.debug("Add new meal");
            forward = "/meal.jsp";
        } else {
            log.debug("forward to meals");
            request.setAttribute("mealToList", filteredByStreams(dao.list(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
            forward = "/meals.jsp";
        }
        request.getRequestDispatcher(forward).forward(request, response);
    }
}
