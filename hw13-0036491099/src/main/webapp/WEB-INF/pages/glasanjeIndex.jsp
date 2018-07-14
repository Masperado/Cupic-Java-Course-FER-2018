<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Glasanje</title>
    <style>body {
        background-color: <c:choose><c:when test="${pickedBgCol!=null}">${pickedBgCol}</c:when><c:otherwise>white</c:otherwise></c:choose>;

    }

    </style>
</head>
<body>
<a href="/webapp2">Home</a>
<br>
<ol>
    <c:forEach var="song" items="${songs}">
        <li>Link za glasanje: <a
                href="glasanje-glasaj?id=${song.id}">${song.name}</a><br>Link
            na
            pjesmu: <a href="${song.link}" target="_blank">${song.link}</a></li>
        <br><br></c:forEach>
</ol>

</body>
</html>
