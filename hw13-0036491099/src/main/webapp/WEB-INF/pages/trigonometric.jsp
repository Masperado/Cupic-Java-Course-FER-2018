<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Trigonometric</title>
    <style>body {
        background-color: <c:choose><c:when test="${pickedBgCol!=null}">${pickedBgCol}</c:when><c:otherwise>white</c:otherwise></c:choose>;
    }
    </style>
</head>
<body>
<a href="/webapp2">Home</a>
<hr>

<table>
    <tr>
        <th>Kut</th>
        <th>Sinus</th>
        <th>Kosinus</th>
    </tr>
    <c:forEach var="angle" items="${angleData}">
        <tr>
            <td>${angle.angle}</td>
            <td>${angle.sinus}</td>
            <td>${angle.kosinus}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
