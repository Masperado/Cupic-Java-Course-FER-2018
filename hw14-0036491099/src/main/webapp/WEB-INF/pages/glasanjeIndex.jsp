<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Glasanje</title>
</head>

<body>
<a href="index.html">Home</a>
<br>


<ol>
    <c:forEach var="song" items="${songs}">
        <li>Link za glasanje: <a
                href="glasanje-glasaj?id=${song.id}&pollID=${pollID}">${song.name}</a><br>Link
            na
            pjesmu: <a href="${song.link}" target="_blank">${song.link}</a></li>
        <br><br></c:forEach>
</ol>

</body>
</html>
