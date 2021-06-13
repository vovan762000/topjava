<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <title>Meals</title>
    <style>
        .excess {
            color: red;
        }

        .normal {
            color: green;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<p><a href="meals?action=insert">Add Meal</a></p>
<table border="1" cellpadding="5">
    <%--    <caption><h2>List of users</h2></caption>--%>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    <c:forEach var="mealTo" items="${mealToList}">
        <%--        <tr class="${mealTo.excess} ? excess : normal">--%>
        <%--            <td><c:out value="${mealTo.dateTime}" /></td>--%>
        <%--            <td><c:out value="${mealTo.description}" /></td>--%>
        <%--            <td><c:out value="${mealTo.calories}" /></td>--%>
        <%--            <td><c:out value="${mealTo.excess}" /></td>--%>
        <%--        </tr>--%>
        <javatime:format value="${mealTo.dateTime}" pattern="yyyy-MM-dd HH:mm" var="parsedDate"/>
        <c:choose>
            <c:when test="${mealTo.excess}">
                <tr class=excess>
                    <td><c:out value="${parsedDate}"/></td>
                    <td><c:out value="${mealTo.description}"/></td>
                    <td><c:out value="${mealTo.calories}"/></td>
                    <td><a href="meals?action=edit&mealId=<c:out value="${mealTo.id}"/>">Update</a></td>
                    <td><a href="meals?action=delete&mealId=<c:out value="${mealTo.id}"/>">Delete</a></td>
                </tr>
            </c:when>
            <c:otherwise>
                <tr class=normal>
                    <td><c:out value="${parsedDate}"/></td>
                    <td><c:out value="${mealTo.description}"/></td>
                    <td><c:out value="${mealTo.calories}"/></td>
                    <td><a href="meals?action=edit&mealId=<c:out value="${mealTo.id}"/>">Update</a></td>
                    <td><a href="meals?action=delete&mealId=<c:out value="${mealTo.id}"/>">Delete</a></td>
                </tr>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</table>
</body>
</html>
