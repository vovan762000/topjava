package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoInMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.CALORIES_PER_DAY;
import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

@WebServlet("/meals")
public class MealServlet extends HttpServlet {

    private MealDao dao;

    private static final Logger log = getLogger(MealServlet.class);

    @Override
    public void init() throws ServletException {
        super.init();
        dao = new MealDaoInMemory();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action") == null ? "all" : req.getParameter("action");
        String mealIdString = req.getParameter("id");
        switch (action) {
            case "delete":
                int mealId = Integer.parseInt(mealIdString);
                dao.delete(mealId);
                log.debug("delete meal with id {}", mealId);
                resp.sendRedirect("meals");
                break;
            case "addOrUpdate":
                Meal newMeal;
                if (mealIdString == null) {
                    newMeal = new Meal();
                    log.debug("add new meal");
                } else {
                    newMeal = dao.getById(Integer.parseInt(mealIdString));
                    log.debug("update meal with id {}", Integer.parseInt(mealIdString));
                }
                req.setAttribute("meal", newMeal);
                req.getRequestDispatcher("/mealEdit.jsp").forward(req, resp);
                break;
            default:
                log.debug("get all meals");
                List<MealTo> mealToList = filteredByStreams(dao.getAll(), CALORIES_PER_DAY);
                req.setAttribute("mealsToList", mealToList);
                req.getRequestDispatcher("/meals.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Meal newMeal = new Meal();
        newMeal.setDateTime(LocalDateTime.parse(req.getParameter("dateTime")));
        newMeal.setDescription(req.getParameter("description"));
        newMeal.setCalories(Integer.parseInt(req.getParameter("calories")));
        String mealIdString = req.getParameter("id");
        if (!mealIdString.isEmpty()) {
            newMeal.setId(Integer.parseInt(mealIdString));
            dao.save(newMeal);
        } else {
            dao.save(newMeal);
        }
        resp.sendRedirect("meals");
    }
}
