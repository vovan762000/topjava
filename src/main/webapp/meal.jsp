<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<style>
    div.field {
        padding-bottom: 10px;
    }

    div.field label {
        display: block;
        float: left;
        width: 90px;
        height: 15px;
    }
</style>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>
<form method="POST" action='meals' name="frmAddMeal">
    <input type="hidden" name="mealId" value="${meal.id}">
    <div class="field">
        <label>Date/Time : </label>
        <input type="datetime-local" name="dateTime"
        value="<c:out value="${meal.dateTime}"/>" /> <br/>
    </div>
    <div class="field">
        <label>Description :</label>
        <input
                type="text" name="description"
                value="<c:out value="${meal.description}" />"/> <br/>
    </div>
    <div class="field">
        <label>Calories :</label>
        <input
                type="text" name="calories"
                value="<c:out value="${meal.calories}" />"/>
    </div>
    <div class="field">
        <input type="submit" value="Save"/>
        <button type="button" name="back" onclick="history.back()">Cancel</button>
    </div>
</form>
</body>
</html>
