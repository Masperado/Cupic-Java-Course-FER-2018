<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Index</title>
</head>
<body>
<h1>Molim odaberite anketu na koju želite glasati</h1>
<br>

<ol>
    <c:forEach var="poll" items="${polls}">
        <li>${poll.message}: <a
                href="glasanje?pollID=${poll.id}">${poll.title}</a></li>
        <br><br></c:forEach>
</ol>

</body>
</html>
