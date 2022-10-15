<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>updateAddMeal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit Meal</h2>
<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
<form method="POST" action='meals' name="updateAddMeal">
    <input type="hidden" name="id" value="${meal.id}">
    <label for="dateTime" style="margin-right: 30px">
        DateTime :
    </label>
    <input type="datetime-local" id="dateTime" name="dateTime"
           value="<c:out value="${meal.dateTime}" />"/>
    <br/><br/>
    <label for="description" style="margin-right: 20px">
        Description :
    </label>
    <input type="text" id="description" name="description"
           value="<c:out value="${meal.description}" />"/>
    <br/><br/>
    <label for="calories" style="margin-right: 40px">
        Calories :
    </label>
    <input type="text" id="calories" name="calories"
           value="<c:out value="${meal.calories}" />"/>
    <br/><br/>
    <input type="submit" value="Save">
    <input type="button" onclick="history.back()" value="Cancel">
</form>
</body>
</html>
