<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html lang="ru">
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Users</h2>
<table border="1" cellpadding="8" cellspacing="0">
    <thead>
    <tr>
        <th>Name</th>
        <th>Email</th>
        <th>Password</th>
        <th>Enabled</th>
        <th>Registered</th>
        <th>Roles</th>
    </tr>
    </thead>
    <c:forEach items="${users}" var="user">
        <jsp:useBean id="user" type="ru.javawebinar.topjava.model.User"/>
        <tr>
                <%--            <td>--%>
                <%--                    &lt;%&ndash;${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}&ndash;%&gt;--%>
                <%--                    &lt;%&ndash;<%=TimeUtil.toString(meal.getDateTime())%>&ndash;%&gt;--%>
                <%--                    &lt;%&ndash;${fn:replace(meal.dateTime, 'T', ' ')}&ndash;%&gt;--%>
                <%--                    ${fn:formatDateTime(meal.dateTime)}--%>
                <%--            </td>--%>
            <th>${user.name}</th>
            <td>${user.email}</td>
            <td>${user.password}</td>
            <td>${user.enabled}</td>
            <td>${user.registered}</td>
            <td>${user.roles}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>