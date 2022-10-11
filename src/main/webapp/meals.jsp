<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
</head>
<style>
    table, th, td {
        border: 1px solid black;
    }
    .normal{
        color: green;
    }
    .excess{
        color: red;
    }
</style>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h3>Meals</h3>
<table>
    <tr>

        <th>DateTime</th>
        <th>Description</th>
        <th>Calories</th>

    </tr>
    <%--    <jsp:useBean id="mealsToList" type="ru.javawebinar.topjava.model.MealTo" scope="request"/>--%>
    <c:forEach var="mealTo" items="${mealsToList}">
        <tr class="${mealTo.excess ? 'excess' : 'normal'}">
            <td>
                <fmt:parseDate value="${mealTo.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                               type="both"/>
                <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}"/>
            </td>
            <td>${mealTo.description}</td>
            <td>${mealTo.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
